package de.katho.kBorrow.controller;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.katho.kBorrow.Settings;
import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class NewLendingController {
	private DbConnector dbCon;
	private KUserModel kUserModel;
	private KLenderModel kLenderModel;
	private KArticleModel kArticleModel;
	private KLendingModel kLendingModel;
	private Settings settings;
	
	public NewLendingController(DbConnector pDbCon, HashMap<String, KDataModel> models, final Settings pSettings){
		dbCon = pDbCon;
		kUserModel = (KUserModel)models.get("kusermodel");
		kLenderModel = (KLenderModel)models.get("klendermodel");
		kArticleModel = (KArticleModel)models.get("karticlemodel");
		kLendingModel = (KLendingModel)models.get("klendingmodel");
		settings = pSettings;
	}
	
	/**
	 * 
	 * @return		StatusCode
	 * 				0:		Erfolgreich gespeichert
	 * 				1:		SQL-Fehler
	 * 				2:		Notwendige Daten sind leer (Art-ID, Start-Date, Est. End-Date)
	 * 				3:		Das Rückgabedatum ist früher oder gleich dem Ausleihdatum
	 * 				4:		Die gegebene Kombination aus Lender-Name, -Surname und -Studentnumber
	 * 						existiert mehrmals in der Datenbank. Das darf nicht sein und wirft daher einen Fehler!
	 * 				5:		Matrikelnummer muss eine Zahl sein!
	 * @throws Exception 
	 */
	public int newLending(int pArtId, String pLName, String pLSurname, String pLSN, String pStartDate, Date pEstEndDate, String pUsername) throws Exception{
		if(pArtId == -1 || pStartDate.isEmpty() || pEstEndDate == null || pLName.isEmpty() || pLSurname.isEmpty() || pUsername.isEmpty()) return 2;
		if(pEstEndDate.before(new Date())) return 3;
		if(!pLSN.matches("[0-9]+")) return 5;
		
		ArrayList<KLender> lenders = kLenderModel.getLenders(pLName, pLSurname, pLSN);
		if(lenders.size() == 0) {
			int result = dbCon.createNewLender(pLName, pLSurname, pLSN);
			
			if(result == 0){
				kLenderModel.updateModel();
				
				return newLending(pArtId, pLName, pLSurname, pLSN, pStartDate, pEstEndDate, pUsername);
			}
			else return result;
		}
		else if(lenders.size() == 1){
			KLender lender = lenders.get(0);
			int uId = kUserModel.getIdByFullname(pUsername);
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			
			int[] result = dbCon.createNewLending(pArtId, uId, lender.getId(), pStartDate, dateFormat.format(pEstEndDate));
			
			if(result[0] == 0){
				kArticleModel.updateModel();
				kLendingModel.updateModel();
				createPdfFile(result[1]);
				
				return result[0];
			}
			else return result[0];
		}
		return 4;
	}
	
	
	private void createPdfFile(int pLendingId) throws Exception {
		KLending lending = kLendingModel.getElement(pLendingId);
		KArticle article = kArticleModel.getElement(lending.getArticleId());
		KUser user = kUserModel.getElement(lending.getUserId());
		KLender lender = kLenderModel.getElement(lending.getLenderId());		
		
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
		PDRectangle rect = page.getMediaBox();
		doc.addPage(page);
		
		PDFont fontNormal = PDType1Font.HELVETICA;
		PDFont fontBold = PDType1Font.HELVETICA_BOLD;
		
		String[] text = {
							"Artikel: ",
							"Verliehen von: ",
							"Ausgeliehen an: ",
							"Start der Ausleihe: ",
							"Voraussichtliche Rückgabe: "
						};
		String[] vars = {
							article.getName(),
							user.getName()+" "+user.getSurname(),
							lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")",
							lending.getStartDate(),
							lending.getExpectedEndDate()
						};
		try {
			File file = createRandomFile();
			
			PDPageContentStream cos = new PDPageContentStream(doc, page);
			
			cos.beginText();
			cos.moveTextPositionByAmount(100, rect.getHeight() - 100);
			cos.setFont(fontBold, 16);
			cos.drawString("Ausleihe #"+lending.getId());
			cos.endText();
			
			int i = 0;
			
			while (i < text.length){
				cos.beginText();
				cos.moveTextPositionByAmount(100, rect.getHeight() - 25*(i+2) - 100 );
				cos.setFont(fontBold, 12);
				cos.drawString(text[i]);
				cos.moveTextPositionByAmount(rect.getWidth() / 2 - 100, 0);
				cos.setFont(fontNormal, 12);
				cos.drawString(vars[i]);
				cos.endText();
				i++;
			}
			
			i = i+2;
			cos.setLineWidth(1);
			cos.addLine(100, rect.getHeight() - 25*(i+2) - 100, 300, rect.getHeight() - 25*(i+2) - 100);
			cos.closeAndStroke();
			
			i++;
			
			cos.beginText();
			cos.moveTextPositionByAmount(100, rect.getHeight() - 25*(i+2) - 100);
			cos.setFont(fontNormal, 12);
			cos.drawString("Unterschrift "+lender.getName()+" "+lender.getSurname());
			cos.endText();			
		
			cos.close();
			doc.save(file);
			doc.close();
			
			if(Desktop.isDesktopSupported()){
				Desktop desktop = Desktop.getDesktop();
				if(desktop.isSupported(Desktop.Action.OPEN)){
					desktop.open(file);
				}
			}
		} catch (IOException | COSVisitorException e) {
			throw new Exception("Problem bei der Erstellung der PDF-Datei.", e);
		}
	}
	
	private File createRandomFile() throws IOException{
		File dir = new File(settings.getSettingsDir()+"/tmp");
		File file = new File(settings.getSettingsDir()+"/tmp/"+Util.generateRandomString(8)+".pdf");
		if(!dir.isDirectory()) dir.mkdir();
		if(!file.isFile()) file.createNewFile();
		else {
			file.delete();
			file.createNewFile();
		}
		
		return file;
	}
}
