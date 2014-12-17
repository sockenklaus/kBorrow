package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KGuiModel;

public class ArticleTableModel extends AbstractTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1860949772989116745L;
	protected String[] header;
	protected DbConnector dbCon;
	protected ArrayList<KArticle> data = new ArrayList<KArticle>();

	public ArticleTableModel(DbConnector pDbCon){
		header = new String [] {"ID", "Artikelname", "Artikelbeschreibung", "", "", ""};
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
	 * Gibt die entsprechende Zeile in der Tabelle f�r ein Objekt mit der gegebenen ID zur�ck.
	 * 
	 * @param id	ID, f�r die die Tabellenzeile herausgesucht werden soll
	 * @return		Zeile in der Tabelle. -1, wenn die ID nicht vorhanden ist.
	 */
	public int getRowFromId(int id) {
		for (KArticle elem : this.data){
			if(elem.getId() == id) return data.indexOf(elem);
		}
		return -1;
	}
		
	/**
	 * Gibt das Article-Objekt der �bergebenen Zeile zur�ck.
	 * 
	 * @param pRow	Zeile, deren Article-Objekt zur�ckgegeben werden soll.
	 * @return	KArticle-Objekt
	 */
	public KArticle getArticleByRow(int pRow){
		return data.get(pRow);
	}
	
	/**
	 * Gibt das Article-Objekt mit der entsprechendne ID zur�ck.
	 * 
	 * @param pId	Id, deren Objekt zur�ckgegeben werden soll.
	 * @return		KArticle-Objekt
	 */
	public KArticle getArticleById(int pId){
		for(KArticle elem : data){
			if(elem.getId() == pId) return elem;
		}
		return null;
	}

}
