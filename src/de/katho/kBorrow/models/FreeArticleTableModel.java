package de.katho.kBorrow.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.data.KArticle;

public class FreeArticleTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7318589937150373541L;
	private DbConnector dbCon;
	String[] header = {"ID", "Artikelname", "Artikelbeschreibung", ""};
	private ArrayList<KArticle> data = new ArrayList<KArticle>();

	public FreeArticleTableModel(DbConnector pDbCon) {
		this.dbCon = pDbCon;
		this.updateTable();
	}

	public String getColumnName(int pIndex){
		return header[pIndex];
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
	
	public boolean isCellEditable(int row, int col){
		if (col > 2) return true;
		return false;
	}
	
	// TODO: Implement!
	private void updateTable(){
		
	}
	
	public int getArticleId(int pRow){
		return data.get(pRow).getId();
	}
	
	public String getArticleName(int pRow){
		return data.get(pRow).getName();
	}
	
	public String getArticleDescription(int pRow){
		return data.get(pRow).getDescription();
	}

}
