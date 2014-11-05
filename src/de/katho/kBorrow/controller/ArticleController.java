package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.ArticleModel;
import de.katho.kBorrow.models.FreeArticleModel;

public class ArticleController {
	private DbConnector dbCon;
	private ArticleModel articleModel;
	private FreeArticleModel freeArticleModel;
	
	public ArticleController(DbConnector pDbCon, HashMap<String, Object> pModels){
		dbCon = pDbCon;
		articleModel = (ArticleModel)pModels.get("articlemodel");
		freeArticleModel = (FreeArticleModel)pModels.get("freearticlemodel");
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
		
		articleModel.updateModel();
		freeArticleModel.updateModel();
		
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
			articleModel.updateModel();
			freeArticleModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * Löscht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param id	ID des Artikels, der gelöscht werden soll.
	 * @return		true, wenn der Artikel erfolgreich gelöscht wurde. false, wenn ein Fehler aufgetreten ist.
	 */
	public boolean deleteArticle(int id) {
		if(this.dbCon.deleteArticle(id)){
			articleModel.updateModel();
			freeArticleModel.updateModel();
			
			return true;
		}
		
		return false;
	}

}
