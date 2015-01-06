package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class UserTableModel extends AbstractTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 435829735305533728L;
	private String[] header = {"ID", "Vorname", "Nachname", "", ""};
	private ArrayList<KUser> data;
	
	public UserTableModel(KDataModel pModel){
		pModel.register(this);
	}
	
	public int getColumnCount() {
		return header.length;
	}

	public int getRowCount() {		
		return data.size();
	}

	public String getValueAt(int row, int col) {
		switch(col){
		case 0:
			return String.valueOf(this.data.get(row).getId());
		
		case 1:
			return this.data.get(row).getName();
			
		case 2:
			return this.data.get(row).getSurname();
			
		default:
			return null;
		}
	}

	public String getColumnName(int index){
		return header[index];
	}
	
	// Die Funktion muss differenzierter werden
	public boolean isCellEditable(int row, int col){
		if (col > 2) return true;
		return false;
	}
	
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}
	
	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KUserModel){
			data = ((KUserModel)pModel).getData();
			
			fireTableDataChanged();
		}
		
	}
}
