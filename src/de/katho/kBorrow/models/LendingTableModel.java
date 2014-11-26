package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.data.KLending;
import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;

public class LendingTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1375465648631587292L;
	private DbConnector dbCon;
	private String[] header = {"ID", "Artikel", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", ""};
	private ArrayList<KLending> data = new ArrayList<KLending>();
	private ArticleTableModel articleModel;
	private LenderModel lenderModel;
	private UserTableModel userTableModel;
	
	public LendingTableModel(DbConnector pDbCon, HashMap<String, Object> pModels ) {
		dbCon = pDbCon;
		articleModel = (ArticleTableModel)pModels.get("articletablemodel");
		lenderModel = (LenderModel)pModels.get("lendermodel");
		userTableModel = (UserTableModel)pModels.get("usertablemodel");
		
		updateModel();
	}

	public int getColumnCount() {
		return header.length;
	}
	
	public String getColumnName(int index){
		return header[index];
	}

	public int getRowCount() {
		return data.size();
	}

	public Object getValueAt(int row, int col) {
		switch (col){
		case 0:
			return data.get(row).getId();
			
		case 1:
			int artId = data.get(row).getArticleId();
			KArticle art = articleModel.getArticleById(artId);
			
			return art.getName();
			
		case 2:
			int uId = data.get(row).getUserId();
			KUser user = userTableModel.getUserById(uId);
			
			return user.getName()+" "+user.getSurname();
			
		case 3:
			int lenderId = data.get(row).getLenderId();
			KLender lender = lenderModel.getLenderById(lenderId);
			
			return lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")";
		
		case 4:
			return data.get(row).getStartDate();
			
		case 5:
			return data.get(row).getExpectedEndDate();
		
		default:
			return null;
		}
	}
	
	// Die Funktion muss differenzierter werden
		public boolean isCellEditable(int row, int col){
			if (col > 4) return true;
			return false;
		}
	
	public void updateModel(){
		data = dbCon.getActiveLendingList();
		fireTableDataChanged();
	}

	public KLending getLendingByRow(int row) {
		return data.get(row);
	}
	
	public KLending getLendingById(int id){
		for(KLending elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}

}
