package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class ArticleInspectTableModel extends AbstractTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2293157709447086998L;
	private String[] header;
	private int articleId;
	private ArrayList<KLending> data;
	private KUserModel userModel;
	private KLenderModel lenderModel;
	
	
	public ArticleInspectTableModel(int pId, HashMap<String, KDataModel> models){
		header = new String[] {"ID", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", "Rückgabe"};
		articleId = pId;
		userModel = (KUserModel)models.get("kusermodel");
		lenderModel = (KLenderModel)models.get("klendermodel");
		
		fetchData(((KLendingModel)models.get("klendingmodel")));
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

	public boolean isCellEditable(int pRow, int pCol){
		return false;
	}
	
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return data.get(row).getId();
		
		case 1:
			int uid = data.get(row).getUserId();
			KUser user = userModel.getElement(uid);
			
			return user.getName()+" "+user.getSurname();
			
		case 2:
			int lid = data.get(row).getLenderId();
			KLender lender = lenderModel.getElement(lid);
			
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

	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KLendingModel){
			data = new ArrayList<KLending>();
			
			for(KLending elem : ((KLendingModel)pModel).getData()){
				if(elem.getArticleId() == articleId) data.add(elem);
			}
			
			fireTableDataChanged();
		}
	}

}
