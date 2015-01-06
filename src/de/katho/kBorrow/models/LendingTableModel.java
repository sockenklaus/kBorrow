package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class LendingTableModel extends AbstractTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1375465648631587292L;
	private String[] header = {"ID", "Artikel", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", ""};
	private ArrayList<KLending> data;
	private KDataModel articleModel;
	private KDataModel lenderModel;
	private KDataModel userModel;
	private KDataModel lendingModel;
	
	public LendingTableModel(HashMap<String, KDataModel> pModels ) {
		articleModel = pModels.get("karticlemodel");
		lenderModel = pModels.get("klendermodel");
		userModel = pModels.get("kusermodel");
		lendingModel = pModels.get("klendingmodel");
		
		lendingModel.register(this);
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
			KArticle art = (KArticle) articleModel.getElement(artId);
			
			return art.getName();
			
		case 2:
			int uId = data.get(row).getUserId();
			KUser user = (KUser) userModel.getElement(uId);
			
			return user.getName()+" "+user.getSurname();
			
		case 3:
			int lenderId = data.get(row).getLenderId();
			KLender lender = (KLender) lenderModel.getElement(lenderId);
			
			return lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")";
		
		case 4:
			return data.get(row).getStartDate();
			
		case 5:
			return data.get(row).getExpectedEndDate();
		
		default:
			return null;
		}
	}
	
	public boolean isCellEditable(int row, int col){
		if (col > 4) return true;
		return false;
	}

	public void fetchData(KDataModel pModel) {
		data = new ArrayList<KLending>();
		
		if(pModel instanceof KLendingModel){
			for(KLending elem : ((KLendingModel)pModel).getData()){
				if(elem.getEndDate() == null || elem.getEndDate().equals("")) data.add(elem);
			}
			fireTableDataChanged();
		}		
	}
	
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}

}
