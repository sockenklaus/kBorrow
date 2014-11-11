package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.db.DbConnector;

public class ArticleTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1860949772989116745L;
	protected String[] header;
	protected DbConnector dbCon;
	protected ArrayList<KArticle> data = new ArrayList<KArticle>();

	public ArticleTableModel(DbConnector pDbCon){
		header = new String [] {"ID", "Artikelname", "Artikelbeschreibung", "", ""};
		this.dbCon = pDbCon;
		this.updateModel();
	}
	
	public String getColumnName(int index){
		return header[index];
	}
	
	public void updateModel() {
		this.data = this.dbCon.getArticleList();
		this.fireTableDataChanged();		
	}

	public int getColumnCount() {
		return header.length;
	}

	
	public int getRowCount() {
		return data.size();
	}

	
	public Object getValueAt(int pRow, int pCol) {
		switch(pCol){
		case 0:
			return String.valueOf(data.get(pRow).getId());
			
		case 1:
			return data.get(pRow).getName();
			
		case 2:
			return data.get(pRow).getDescription();
			
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
	 * Gibt die entsprechende Zeile in der Tabelle für ein Objekt mit der gegebenen ID zurück.
	 * 
	 * @param id	ID, für die die Tabellenzeile herausgesucht werden soll
	 * @return		Zeile in der Tabelle. -1, wenn die ID nicht vorhanden ist.
	 */
	public int getRowFromId(int id) {
		for (KArticle elem : this.data){
			if(elem.getId() == id) return data.indexOf(elem);
		}
		return -1;
	}
		
	/**
	 * Gibt das Article-Objekt der übergebenen Zeile zurück.
	 * 
	 * @param pRow	Zeile, deren Article-Objekt zurückgegeben werden soll.
	 * @return	KArticle-Objekt
	 */
	public KArticle getArticleByRow(int pRow){
		return this.data.get(pRow);
	}
	
	/**
	 * Gibt das Article-Objekt mit der entsprechendne ID zurück.
	 * 
	 * @param pId	Id, deren Objekt zurückgegeben werden soll.
	 * @return		KArticle-Objekt
	 */
	public KArticle getArticleById(int pId){
		for(KArticle elem : data){
			if(elem.getId() == pId) return elem;
		}
		return null;
	}

}
