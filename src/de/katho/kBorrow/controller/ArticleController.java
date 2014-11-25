package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.ArticleTableModel;
import de.katho.kBorrow.models.FreeArticleTableModel;

public class ArticleController {
	private DbConnector dbCon;
	private ArticleTableModel articleTableModel;
	private FreeArticleTableModel freeArticleTableModel;
	
	public ArticleController(DbConnector pDbCon, HashMap<String, Object> pModels){
		dbCon = pDbCon;
		articleTableModel = (ArticleTableModel)pModels.get("articletablemodel");
		freeArticleTableModel = (FreeArticleTableModel)pModels.get("freearticletablemodel");
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
		
		articleTableModel.updateModel();
		freeArticleTableModel.updateModel();
		
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
			articleTableModel.updateModel();
			freeArticleTableModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * Löscht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param pRow	Row des Artikels, der gelöscht werden soll.
	 * @return		true, wenn der Artikel erfolgreich gelöscht wurde. false, wenn ein Fehler aufgetreten ist.
	 */
	public boolean deleteArticle(int pRow) {
				
		int id = articleTableModel.getArticleByRow(pRow).getId();
		
		if(this.dbCon.deleteArticle(id)){
			articleTableModel.updateModel();
			freeArticleTableModel.updateModel();
			
			return true;
		}
		
		return false;
	}

}
