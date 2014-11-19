package de.katho.kBorrow.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	 * 				1:		SQL-Fehler
	 * 				2:		Notwendige Daten sind leer (Art-ID, Start-Date, Est. End-Date)
	 * 				3:		Das Rückgabedatum ist früher oder gleich dem Ausleihdatum
	 * 				4:		Die gegebene Kombination aus Lender-Name, -Surname und -Studentnumber
	 * 						existiert mehrmals in der Datenbank. Das darf nicht sein und wirft daher einen Fehler!
	 */
	public int newLending(int pArtId, String pLName, String pLSurname, String pLSN, String pStartDate, Date pEstEndDate, String pUsername){
		if (pArtId == -1 || pStartDate.isEmpty() || pEstEndDate == null || pLName.isEmpty() || pLSurname.isEmpty() || pUsername.isEmpty()) return 2;
		if (pEstEndDate.before(new Date())) return 3;
		
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
			int result = dbCon.createNewLending(pArtId, uId, lender.getId(), pStartDate, dateFormat.format(pEstEndDate));
			
			if(result == 0){
				freeArticleModel.updateModel();
				return result;
			}
			else return result;
		}
		return 4;
	}
}
