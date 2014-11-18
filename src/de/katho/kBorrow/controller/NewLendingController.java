package de.katho.kBorrow.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.LenderModel;
import de.katho.kBorrow.models.UserListModel;

public class NewLendingController {
	private DbConnector dbCon;
	private UserListModel userListModel;
	private LenderModel lenderModel;
	private FreeArticleTableModel freeArticleModel;
	
	public NewLendingController(DbConnector pDbCon, HashMap<String, Object> pModels){
		dbCon = pDbCon;
		userListModel = (UserListModel)pModels.get("userlistmodel");
		lenderModel = (LenderModel)pModels.get("lendermodel");
		freeArticleModel = (FreeArticleTableModel)pModels.get("freearticlemodel");
	}
	
	/**
	 * 
	 * @return		StatusCode
	 * 				0:		Erfolgreich gespeichert
	 * 				1:		Notwendige Daten sind leer (Art-ID, Start-Date, Est. End-Date)
	 * 				2:		Das Rückgabedatum ist früher oder gleich dem Ausleihdatum
	 * 				3:		Die gegebene Kombination aus Lender-Name, -Surname und -Studentnumber
	 * 						existiert mehrmals in der Datenbank. Das darf nicht sein und wirft daher einen Fehler!
	 */
	public int newLending(int pArtId, String pLName, String pLSurname, String pLSN, String pStartDate, Date pEstEndDate, String pUsername){
		if (pArtId == -1 || pStartDate.isEmpty() || pEstEndDate == null || pLName.isEmpty() || pLSurname.isEmpty() || pUsername.isEmpty()) return 1;
		if (pEstEndDate.before(new Date())) return 2;
		
		ArrayList<KLender> lenders = lenderModel.getLenders(pLName, pLSurname, pLSN);
		if(lenders.size() == 0) {
			// Lender existiert noch nicht
		}
		else if(lenders.size() == 1){
			// Lender existiert bereits
		}
		else return 3;
		
		return 0;
	}
}
