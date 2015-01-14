package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * ArticleController f�hrt s�mtliche Datenbankoperationen durch, die durch {@link de.katho.kBorrow.gui.ArticlePanel} angesto�en werden.
 */
public class ArticleController {
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/** Referenz auf das KArticleModel. Wird ben�tigt, um Tabellen und Listen zu aktualisieren. */
	private KArticleModel kArticleModel;
	
	/**
	 * Erzeugt eine neue Instanz des ArticleController.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank
	 * @param models	HashMap, die die KDataModels enth�lt.
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
	 * �ndert einen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich ge�ndert</li>
	 * <li>1: SQL-Fehler beim Erzeugen</li>
	 * <li>2: Artikelname ist leer</li>
	 * </ul>
	 * 
	 * @param pId	Id des Artikels, der ge�ndert werden soll 
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
	 * L�scht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	* <p>Gibt je nach Bearbeitungsergebnis einen anderen Statuscode aus:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel konnte erfolgreich gel�scht werden.</li>
	 * <li>1: Artikel konnte nicht gel�scht werden, unbekannter Fehler (SQL-Fehler).</li>
	 * <li>2: Artikel konnte nicht gel�scht werden, weil er im Moment verliehen ist.</li>
	 * </ul>
	 *  
	 * @param pId	ID des Artikels, der gel�scht werden soll.
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
