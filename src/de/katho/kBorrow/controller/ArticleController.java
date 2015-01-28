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

import java.util.HashMap;

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * ArticleController führt sämtliche Datenbankoperationen durch, die durch {@link de.katho.kBorrow.gui.ArticlePanel} angestoßen werden.
 */
public class ArticleController {
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/** Referenz auf das KArticleModel. Wird benötigt, um Tabellen und Listen zu aktualisieren. */
	private KArticleModel kArticleModel;
	
	/**
	 * Erzeugt eine neue Instanz des ArticleController.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank
	 * @param models	HashMap, die die KDataModels enthält.
	 */
	public ArticleController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		kArticleModel = (KArticleModel)models.get("karticlemodel");
	}
	
	/**
	 * Erzeugt einen neuen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich erzeugt.</li>
	 * <li>1: SQL-Fehler beim Erzeugen.</li>
	 * <li>2: Feld "Name" ist leer</li>
	 * </ul>
	 * 
	 * @param pName Name des Artikels
	 * @param pDesc Beschreibung des Artikels
	 * @return  Statuscode als Int.
	 */
	public int createArticle(String pName, String pDesc) {
		if (pName.isEmpty()) return 2;
		
		int status = dbCon.createArticle(pName, pDesc);
		
		kArticleModel.updateModel();
		
		return status;
	}
	
	/**
	 * Ändert einen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich geändert</li>
	 * <li>1: SQL-Fehler beim Erzeugen</li>
	 * <li>2: Artikelname ist leer</li>
	 * </ul>
	 * 
	 * @param pId	Id des Artikels, der geändert werden soll 
	 * @param pName	(Neuer) Name des Artikels
	 * @param pDesc	(Neue) Beschreibung des Artikels
	 * @return	Statuscode als Int.	
	 */
	public int editArticle(int pId, String pName, String pDesc) {
		if(pName.isEmpty()) return 2;
		
		int status = this.dbCon.editArticle(pId, pName, pDesc);
		
		if(status == 0){
			kArticleModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * Löscht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	* <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel konnte erfolgreich gelöscht werden.</li>
	 * <li>1: Artikel konnte nicht gelöscht werden, unbekannter Fehler (SQL-Fehler).</li>
	 * <li>2: Artikel konnte nicht gelöscht werden, weil er im Moment verliehen ist.</li>
	 * </ul>
	 *  
	 * @param pId	ID des Artikels, der gelöscht werden soll.
	 * @return		Statuscode als Int.			
	 */
	public int deleteArticle(int pId) {
		if(!((KArticle)kArticleModel.getElement(pId)).getIsFree()) return 2;
		
		int returnCode = dbCon.deleteArticle(pId);
		
		if(returnCode == 0){
			kArticleModel.updateModel();
		}
		
		return returnCode;
	}

}
