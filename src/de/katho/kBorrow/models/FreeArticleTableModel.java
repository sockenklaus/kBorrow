package de.katho.kBorrow.models;

import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KGuiModel;

public class FreeArticleTableModel extends ArticleTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908669541941834593L;
		private String[] header;
	
	public FreeArticleTableModel(DbConnector pDbCon) {
		super(pDbCon);
		this.header = new String[] {"ID", "Artikelname", "Artikelbeschreibung", ""};
		
		updateModel();
	}
	
	public void updateModel(){
		data = dbCon.getFreeArticleList();
		fireTableDataChanged();
	}
	
	public int getColumnCount(){
		return header.length;
	}
}
