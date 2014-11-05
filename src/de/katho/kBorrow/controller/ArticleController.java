package de.katho.kBorrow.controller;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.ArticleModel;

public class ArticleController {
	private DbConnector dbCon;
	private ArticleModel articleModel;
	
	public ArticleController(DbConnector pDbCon, ArticleModel pModel){
		dbCon = pDbCon;
		articleModel = pModel;
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
		
		articleModel.updateTable();
		
		return status;
	}
	
	/**
	 * �ndert einen Artikel in der Datenbank und aktualisiert die Tabelle
	 * 
	 * @param pId	Id des Artikels, der ge�ndert werden soll 
	 * @param pName	(Neuer) Name des Artikels
	 * @param pDesc	(Neue) Beschreibung des Artikels
	 * @return	0: Artikel erfolgreich ge�ndert
	 * 			1: SQL-Fehler beim Erzeugen
	 * 			2: Artikelname ist leer
	 */
	public int editArticle(int pId, String pName, String pDesc) {
		int status = this.dbCon.editArticle(pId, pName, pDesc);
		
		if(status == 0){
			int row = articleModel.getRowFromId(pId);
			
			articleModel.getArticleByRow(row).setName(pName);
			articleModel.getArticleByRow(row).setDescription(pDesc);
			articleModel.fireTableRowsUpdated(row, row);
		}
		
		return status;
	}
	
	/**
	 * L�scht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param id	ID des Artikels, der gel�scht werden soll.
	 * @return		true, wenn der Artikel erfolgreich gel�scht wurde. false, wenn ein Fehler aufgetreten ist.
	 */
	public boolean deleteArticle(int id) {
		if(this.dbCon.deleteArticle(id)){
			int row = articleModel.getRowFromId(id);
			articleModel.removeRow(row);
			
			return true;
		}
		
		return false;
	}

}
