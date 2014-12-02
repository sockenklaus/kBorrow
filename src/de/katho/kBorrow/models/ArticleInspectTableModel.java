package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.data.KLending;
import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;

public class ArticleInspectTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293157709447086998L;
	private String[] header;
	private ArrayList<KLending> data;
	private DbConnector dbCon;
	private KArticle article;
	private ArticleTableModel articleModel;
	private UserTableModel userModel;
	private LenderModel lenderModel;
	
	
	public ArticleInspectTableModel(int pRow, DbConnector pDbCon, HashMap<String, Object> pModels){
		header = new String[] {"ID", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", "Rückgabe"};
		dbCon = pDbCon;
		articleModel = (ArticleTableModel)pModels.get("articletablemodel");
		userModel = (UserTableModel)pModels.get("usertablemodel");
		lenderModel = (LenderModel)pModels.get("lendermodel");
		
		article = articleModel.getArticleByRow(pRow);
		
		updateModel();
	}
	
	public void updateModel() {
		data = dbCon.getLendingListForArticle(article.getId());
		fireTableDataChanged();
	}

	public int getColumnCount() {
		return header.length;
	}
	
	public String getColumnName(int col){
		return header[col];
	}
	
	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return data.get(row).getId();
		
		case 1:
			int uid = data.get(row).getUserId();
			KUser user = userModel.getUserById(uid);
			
			return user.getName()+" "+user.getSurname();
			
		case 2:
			int lid = data.get(row).getLenderId();
			KLender lender = lenderModel.getLenderById(lid);
			
			return lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")";
			
		case 3:
			return data.get(row).getStartDate();
			
		case 4:
			return data.get(row).getExpectedEndDate();
		
		case 5:
			return data.get(row).getEndDate();
			
		default:
			return null;
		}
	}

}
