package de.katho.kBorrow.controller;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.data.KLending;
import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.ArticleTableModel;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.LenderModel;
import de.katho.kBorrow.models.LendingTableModel;
import de.katho.kBorrow.models.UserListModel;

public class NewLendingController {
	private DbConnector dbCon;
	private UserListModel userListModel;
	private LenderModel lenderModel;
	private FreeArticleTableModel freeArticleModel;
	private LendingTableModel lendingTableModel;
	private ArticleTableModel articleTableModel;
	
	public NewLendingController(DbConnector pDbCon, HashMap<String, Object> pModels){
		dbCon = pDbCon;
		userListModel = (UserListModel)pModels.get("userlistmodel");
		lenderModel = (LenderModel)pModels.get("lendermodel");
		freeArticleModel = (FreeArticleTableModel)pModels.get("freearticletablemodel");
		articleTableModel = (ArticleTableModel)pModels.get("articletablemodel");
		lendingTableModel = (LendingTableModel)pModels.get("lendingtablemodel");
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
	 */
	public int newLending(int pArtId, String pLName, String pLSurname, String pLSN, String pStartDate, Date pEstEndDate, String pUsername){
		if(pArtId == -1 || pStartDate.isEmpty() || pEstEndDate == null || pLName.isEmpty() || pLSurname.isEmpty() || pUsername.isEmpty()) return 2;
		if(pEstEndDate.before(new Date())) return 3;
		if(!pLSN.matches("[0-9]+")) return 5;
		
		ArrayList<KLender> lenders = lenderModel.getLenders(pLName, pLSurname, pLSN);
		if(lenders.size() == 0) {
			int result = dbCon.createNewLender(pLName, pLSurname, pLSN);
			
			if(result == 0){
				lenderModel.updateModel();
				
				return newLending(pArtId, pLName, pLSurname, pLSN, pStartDate, pEstEndDate, pUsername);
			}
			else return result;
		}
		else if(lenders.size() == 1){
			KLender lender = lenders.get(0);
			int uId = userListModel.getIdByFullname(pUsername);
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			
			int[] result = dbCon.createNewLending(pArtId, uId, lender.getId(), pStartDate, dateFormat.format(pEstEndDate));
			
			if(result[0] == 0){
				freeArticleModel.updateModel();
				articleTableModel.updateModel();
				lendingTableModel.updateModel();
				createPdfFile(result[1]);
				
				return result[0];
			}
			else return result[0];
		}
		return 4;
	}
	
	
	// TODO http://www.coderanch.com/how-to/java/PDFBoxExample
	private void createPdfFile(int pLendingId){
		KLending lending = lendingTableModel.getLendingById(pLendingId);
		KArticle article = articleTableModel.getArticleById(lending.getArticleId());
		KUser user = userListModel.getUserById(lending.getUserId());
		KLender lender = lenderModel.getLenderById(lending.getLenderId());
		
		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		doc.addPage(page);
		
		PDFont font = PDType1Font.HELVETICA;
		try {
			PDPageContentStream contentStream = new PDPageContentStream(doc, page);
			contentStream.beginText();
			contentStream.setFont(font, 12);
			contentStream.moveTextPositionByAmount(100, 700);
			contentStream.drawString("hallo");
			contentStream.drawString("hallo2");
			contentStream.endText();
			contentStream.close();
			
			doc.save("test.pdf");
			doc.close();
		} catch (IOException | COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
