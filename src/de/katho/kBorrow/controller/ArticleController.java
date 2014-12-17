package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.models.ArticleTableModel;
import de.katho.kBorrow.models.FreeArticleTableModel;

public class ArticleController {
	private DbConnector dbCon;
	private ArticleTableModel articleTableModel;
	private FreeArticleTableModel freeArticleTableModel;
	
	public ArticleController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		articleTableModel = (ArticleTableModel)models.get("articletablemodel");
		freeArticleTableModel = (FreeArticleTableModel)models.get("freearticletablemodel");
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
			articleTableModel.updateModel();
			freeArticleTableModel.updateModel();
		}
		
		return status;
	}
	
	/**
	 * L�scht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param pRow	Row des Artikels, der gel�scht werden soll.
	 * @return		0: Artikel konnte erfolgreich gel�scht werden
	 * 				1: Artikel konnte nicht gel�scht werden, unbekannter Fehler (SQL-Fehler)
	 * 				2: Artikel konnte nicht gel�scht werden, weil er im Moment verliehen ist.
	 */
	public int deleteArticle(int pRow) {
		
		if(!articleTableModel.getArticleByRow(pRow).getIsFree()) return 2;
		
		int id = articleTableModel.getArticleByRow(pRow).getId();
		int returnCode = dbCon.deleteArticle(id);
		
		if(returnCode == 0){
			articleTableModel.updateModel();
			freeArticleTableModel.updateModel();
		}
		
		return returnCode;
	}

}
