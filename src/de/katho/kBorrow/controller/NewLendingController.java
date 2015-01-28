/*
 * Copyright (C) 2015  Pascal König
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * NewLendingController führt sämtliche Datenbankoperationen aus, die von {@link de.katho.kBorrow.gui.NewLendingPanel} angestoßen werden.
 */
public class NewLendingController {
	
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/** Referenz auf KUserModel, wird benötigt um Tabellen und Listen zu aktualisieren. */
	private KUserModel kUserModel;
	
	/** Referenz auf KLenderModel, wird benötigt um Tabellen und Listen zu aktualisieren. */
	private KLenderModel kLenderModel;
	
	/** Referenz auf KArticleModel, wird benötigt um Tabellen und Listen zu aktualisieren. */
	private KArticleModel kArticleModel;
	
	/** Referenz auf KLendingModel, wird benötigt um Tabellen und Listen zu aktualisieren. */
	private KLendingModel kLendingModel;
	
	/** Referenz auf die Settings, wird benötigt, um Zugriff auf das Programmverzeichnis im Benutzerprofil zu haben. */
	private Settings settings;
	
	/**
	 * Erzeugt eine neue Instanz des NewLendingController und setzt alle benötigten Referenzen.
	 * 
	 * @param pDbCon		Referenz auf die Datenbank.
	 * @param models		HashMap mit den KDataModels.
	 * @param pSettings		Referenz auf die Settings.
	 */
	public NewLendingController(DbConnector pDbCon, HashMap<String, KDataModel> models, final Settings pSettings){
		dbCon = pDbCon;
		kUserModel = (KUserModel)models.get("kusermodel");
		kLenderModel = (KLenderModel)models.get("klendermodel");
		kArticleModel = (KArticleModel)models.get("karticlemodel");
		kLendingModel = (KLendingModel)models.get("klendingmodel");
		settings = pSettings;
	}
	
	/**
	 * Erzeugt eine neue Ausleihe.
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Erfolgreich gespeichert</li>
	 * <li>1: SQL-Fehler</li>
	 * <li>2: Notwendige Daten sind leer (Art-ID, Start-Date, Est. End Date)</li>
	 * <li>3: Das Rückgabedatum ist früher oder gleich dem Ausleihdatum</li>
	 * <li>4: Die gegebene Kombination aus Lender-Name, -Surname und -Studentnumer existiert mehrmals in der Datenbank. Das darf nicht sein und wird daher einen Fehler!</li>
	 * <li>5: Matrikelnummer muss eine Zahl sein!</li>
	 * </ul>
	 * 
	 * @param pArtId		Artikel-ID des Artikels, der verliehen wird (darf nicht -1 sein).
	 * @param pLName		Vorname des Ausleihers (darf nicht leer sein).
	 * @param pLSurname		Nachname des Ausleihers (darf nicht leer sein).
	 * @param pLSN			Matrikelnummer des Ausleihers (muss numerisch sein).
	 * @param pStartDate	Startdatum der Ausleihe (darf nicht leer sein).
	 * @param pEstEndDate	Voraussichtliches Rückgabedatum (darf weder 'null' sein, noch in der Vergagenheit liegen).
	 * @param pUsername		Username des Ausleihenden.
	 * 
	 * @return Statuscode als Int.
	 * @throws Exception	???
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
	
	/**
	 * Erzeugt ein PDF-File mit allen relevanten Daten zur als Parameter übergebenen Lending-ID.
	 * 
	 * @param pLendingId	ID der Ausleihe, für die ein PDF erzeugt werden soll.
	 * @throws Exception	Wenn Probleme beim Erstellen der Datei auftreten.
	 */
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
			File file = createRandomPdf();
			
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
	
	/**
	 * Erzeugt ein PDF-File mit zufälligem Dateinamen.
	 * 
	 * @return	Gibt eine Referenz auf ein File-Objekt zurück.
	 * @throws IOException	Wenn Probleme bei der Erstellung der Datei auftreten.
	 */
	private File createRandomPdf() throws IOException{
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
