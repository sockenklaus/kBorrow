package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class ArticleTableModel extends AbstractTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1860949772989116745L;
	protected String[] header;
	protected DbConnector dbCon;
	protected ArrayList<KArticle> data = new ArrayList<KArticle>();

	public ArticleTableModel(KDataModel pModel){
		header = new String [] {"ID", "Artikelname", "Artikelbeschreibung", "", "", ""};
		pModel.register(this);
	}
	
	public String getColumnName(int index){
		return header[index];
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
		
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}

	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KArticleModel){
			data = ((KArticleModel)pModel).getData();
			
			fireTableDataChanged();
		}
		
	}

}
