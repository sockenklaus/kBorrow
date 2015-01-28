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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.gui.RewriteToNewUserDialog;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * UserController führt sämtliche Datenbankoperationen durch, die durch {@link de.katho.kBorrow.gui.UserPanel} angestoßen werden.
 */
public class UserController {

	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/** Referenz auf das KUserModel, wird benötigt, um Tabellen und Listen zu aktualisieren. */
	private KUserModel userModel;
	
	/** Referenz auf das KLendingModel, wird benötigt, um Tabellen und Listen zu aktualiseren. */
	private KLendingModel lendingModel;
	
	/**
	 * Erzeugt eine neue Instanz des UserController und setzt nötige Referenzen.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 * @param models	HashMap mit den KDataModels.
	 */
	public UserController(DbConnector pDbCon, HashMap<String, KDataModel> models) {
		dbCon = pDbCon;
		userModel = (KUserModel)models.get("kusermodel");
		lendingModel = (KLendingModel)models.get("klendingmodel");
	}

	/**
	 * Erzeugt einen neuen User in der Datenbank.
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis verschiedene Statuscodes zurück.</p>
	 * 
	 * <ul>
	 * <li>0: Benutzer wurde erfolgreich erzeugt.</li>
	 * <li>1: SQL-Fehler.</li>
	 * <li>2: pName und pSurname sind leer.</li>
	 * </ul>
	 * 
	 * @param pName		Vorname des Users.
	 * @param pSurname	Nachname des Users.
	 * @return			Statuscode als Int.
	 */
	public int createUser(String pName, String pSurname){
		if (pName.isEmpty() && pSurname.isEmpty()) return 2;
		
		int status = dbCon.createUser(pName, pSurname);
		
		userModel.updateModel();
		
		return status;
	}
	
	/**
	 * Bearbeitet einen bestehenden User in der Datenbank.
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis verschiedene Statuscodes zurück.</p>
	 * 
	 * <ul>
	 * <li>0: Benutzer wurde erfolgreich bearbeitet.</li>
	 * <li>1: SQL-Fehler.</li>
	 * <li>2: pName und pSurname sind leer.</li>
	 * </ul>
	 * 
	 * @param pId		ID des Benutzers, der bearbeitet werden soll.
	 * @param pName		(Neuer) Vorname des Users.
	 * @param pSurname	(Neuer) Nachname des Users.
	 * @return			Statuscode als Int.
	 */
	public int editUser(int pId, String pName, String pSurname) {
		if(pName.isEmpty() && pSurname.isEmpty()) return 2;
		
		int status = dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			userModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * Löscht einen Benutzer aus der Datenbank.
	 * 
	 * <p>
	 * Falls ein Benutzer gelöscht werden soll, auf den noch Ausleihen eingetragen sind, wird {@link de.katho.kBorrow.gui.RewriteToNewUserDialog}
	 * aufgerufen, wo der Benutzer die Möglichkeit hat, einen Benutzer auszuwählen, auf den die Ausleihen umgeschrieben werden sollen.
	 * </p>
	 * 
	 * @param pId	ID des Benutzers, der gelöscht werden soll.
	 * @return		True, wenn der Benutzer gelöscht werden konnte, false, wenn er nicht gelöscht werden konnte.
	 */
	public boolean deleteUser(int pId) {
		if(!(lendingModel instanceof KLendingModel)) {
			KLogger.log(Level.SEVERE, "UserController: lendingModel type error!", new Exception("UserController: lendingModel type error!"));
			return false;
		}
		
		boolean isOccupied = false;
		
		ArrayList<KLending> lendingList = ((KLendingModel)lendingModel).getData();
		for(KLending elem : lendingList){
			if(elem.getUserId() == pId){
				isOccupied = true;
				break;
			}
		}
		
		if(isOccupied){
			RewriteToNewUserDialog dialog = new RewriteToNewUserDialog(pId, dbCon, userModel);
			if(dialog.getResult() == 0){
				lendingModel.updateModel();
				userModel.updateModel();
				
				return deleteUser(pId);
			}
			else return false;
				
		}
		else {
			if(dbCon.deleteUser(pId)){
				userModel.updateModel();
				lendingModel.updateModel();
			
				return true;
			}
			return false;
		}
	}
}
