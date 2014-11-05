package de.katho.kBorrow.models;

import de.katho.kBorrow.db.DbConnector;

public class FreeArticleModel extends ArticleModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908669541941834593L;
		private String[] header;
	
	public FreeArticleModel(DbConnector pDbCon) {
		super(pDbCon);
		this.header = new String[] {"ID", "Artikelname", "Artikelbeschreibung", ""};
		
		updateTable();
	}
	
	public void updateTable(){
		data = dbCon.getFreeArticleList();
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return header.length;
	}
}
