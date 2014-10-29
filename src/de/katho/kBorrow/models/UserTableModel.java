package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;

public class UserTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 435829735305533728L;
	private DbConnector dbCon;
	private String[] header = {"ID", "Vorname", "Nachname", "", ""};
	private ArrayList<KUser> data = new ArrayList<KUser>();
	
	public UserTableModel(DbConnector pDbCon){
		this.dbCon = pDbCon;
		this.updateTable();
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
	
	public void updateTable(){
		this.data = this.dbCon.getUserList();
		this.fireTableDataChanged();
	}
	
	// Die Funktion muss differenzierter werden
	public boolean isCellEditable(int row, int col){
		if (col > 2) return true;
		return false;
	}
	
	public int getUserId(int row){
		return this.data.get(row).getId();
	}
	
	public String getUserName(int row){
		return this.data.get(row).getName();
	}
	
	public String getUserSurname(int row){
		return this.data.get(row).getSurname();
	}
	
	public boolean deleteUser(int id){
		if(dbCon.deleteUser(id)){
			int row = this.getRowFromId(id);
			this.data.remove(row);
			this.fireTableRowsDeleted(row, row);
			
			return true;
		}
		return false;
	}
	
	public int createUser(String pName, String pSurname){
		int status = this.dbCon.createUser(pName, pSurname);
		
		updateTable();
		
		return status;
	}

	public int editUser(int pId, String pName, String pSurname) {
		int status = this.dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			int row = this.getRowFromId(pId);
			
			this.data.get(row).setName(pName);
			this.data.get(row).setSurname(pSurname);
			this.fireTableRowsUpdated(row, row);
		}
		
		return status;
	}
	
	/**
	 * 
	 * @param pId
	 * @return Returns -1 if there is no row for the given id.
	 */
	private int getRowFromId(int pId){
		for(KUser elem : this.data){
			if(elem.getId() == pId) return data.indexOf(elem);
		}
		return -1;
	}

	
}
