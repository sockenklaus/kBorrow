package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class ArticleController {
	private DbConnector dbCon;
	private KArticleModel kArticleModel;
	
	public ArticleController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		kArticleModel = (KArticleModel)models.get("karticlemodel");
	}
	
	/**
	 * Erzeugt einen neuen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * @param pName Name des Artikels
	 * @param pDesc Beschreibung des Artikels
	 * @return  0: Artikel erfolgreich erzeugt
	 * 			1: SQL-Fehler beim Erzeugen
	 * 			2: Feld "Name" leer
	 */
	public int createArticle(String pName, String pDesc) {
		int status = dbCon.createArticle(pName, pDesc);
		
		kArticleModel.updateModel();
		
		return status;
	}
	
	/**
	 * Ändert einen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * @param pId	Id des Artikels, der geändert werden soll 
	 * @param pName	(Neuer) Name des Artikels
	 * @param pDesc	(Neue) Beschreibung des Artikels
	 * @return	0: Artikel erfolgreich geändert
	 * 			1: SQL-Fehler beim Erzeugen
	 * 			2: Artikelname ist leer
	 */
	public int editArticle(int pId, String pName, String pDesc) {
		int status = this.dbCon.editArticle(pId, pName, pDesc);
		
		if(status == 0){
			kArticleModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * Löscht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param pId	ID des Artikels, der gelöscht werden soll.
	 * @return		0: Artikel konnte erfolgreich gelöscht werden
	 * 				1: Artikel konnte nicht gelöscht werden, unbekannter Fehler (SQL-Fehler)
	 * 				2: Artikel konnte nicht gelöscht werden, weil er im Moment verliehen ist.
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
