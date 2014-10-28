package de.katho.kBorrow.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.db.DbConnector;

public class ArticleTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1860949772989116745L;
	private DbConnector dbCon;
	private String[] header = {"ID", "Artikelname", "Artikelbeschreibung", "", ""};
	private ArrayList<KArticle> data = new ArrayList<KArticle>();

	public ArticleTableModel(DbConnector pDbCon){
		this.dbCon = pDbCon;
		this.updateTable();
	}
	
	public String getColumnName(int index){
		return header[index];
	}
	
	private void updateTable() {
		this.data = this.dbCon.getArticleList();
		this.fireTableDataChanged();
		
	}

	@Override
	public int getColumnCount() {
		return this.header.length;
	}

	
	public int getRowCount() {
		return this.data.size();
	}

	
	public Object getValueAt(int pRow, int pCol) {
		switch(pCol){
		case 0:
			return String.valueOf(this.data.get(pRow).getId());
			
		case 1:
			return this.data.get(pRow).getName();
			
		case 2:
			return this.data.get(pRow).getDescription();
			
		default:
			return null;
		}
	}
	
	public boolean isCellEditable(int pRow, int pCol){
		if (pCol > 2) return true;
		return false;
	}

	/**
	 * Gibt die ID der gegebenen Tabellenzeile aus.
	 * 
	 * @param row	Tabellenzeile, zu der die ID ausgegeben werden soll.
	 * @return		Artikel-ID als int.
	 */
	public int getArticleId(int row) {
		return this.data.get(row).getId();
	}
	
	/**
	 * Gibt den Artikelnamen der gegebenen Tabellenzeile aus.
	 * 
	 * @param pRow	Tabellenzeile, zu der der Name ausgegeben werden soll.
	 * @return		Artikelname als String
	 */
	public String getArticleName(int pRow){
		return this.data.get(pRow).getName();
	}
	
	/**
	 * Gibt die Artikelbeschreibung der gegebenen Tabellenzeile aus.
	 * 
	 * @param pRow	Tabellenzeile, zu der die Beschreibung ausgegeben werden soll
	 * @return		Artikelbeschreibung als String.
	 */
	public String getArticleDescription(int pRow){
		return this.data.get(pRow).getDescription();
	}

	/**
	 * Löscht den Artikel mit der gegebenen ID in der Datenbank und aktualisiert die Tabelle.
	 * 
	 * @param id	ID des Artikels, der gelöscht werden soll.
	 * @return		true, wenn der Artikel erfolgreich gelöscht wurde. false, wenn ein Fehler aufgetreten ist.
	 */
	public boolean deleteArticle(int id) {
		if(this.dbCon.deleteArticle(id)){
			int row = this.getRowFromId(id);
			this.data.remove(row);
			this.fireTableRowsDeleted(row, row);
			
			return true;
		}
		
		return false;
	}
	/**
	 * Gibt die entsprechende Zeile in der Tabelle für ein Objekt mit der gegebenen ID zurück.
	 * 
	 * @param id	ID, für die die Tabellenzeile herausgesucht werden soll
	 * @return		Zeile in der Tabelle. -1, wenn die ID nicht vorhanden ist.
	 */
	private int getRowFromId(int id) {
		for (KArticle elem : this.data){
			if(elem.getId() == id) return data.indexOf(elem);
		}
		return -1;
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
		int status = this.dbCon.createArticle(pName, pDesc);
		
		updateTable();
		
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
			int row = this.getRowFromId(pId);
			
			this.data.get(row).setName(pName);
			this.data.get(row).setDescription(pDesc);
			this.fireTableRowsUpdated(row, row);
		}
		
		return status;
	}

}
