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

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
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

	public int getArticleId(int row) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getArticleName(int pRow){
		return "";
	}
	
	public String getArticleDescription(int pRow){
		return null;
	}

	public void deleteArticle(int id) {
		// TODO Auto-generated method stub
		
	}

}
