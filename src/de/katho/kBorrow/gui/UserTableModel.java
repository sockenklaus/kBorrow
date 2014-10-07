package de.katho.kBorrow.gui;

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
	private String[] header = {"Vorname", "Nachname", "Aktion"};
	private ArrayList<KUser> data;
	
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
		if(col == 0) return this.data.get(row).getName();
		else if (col == 1) return this.data.get(row).getSurname();
		else return null;
	}

	public String getColumnName(int index){
		return header[index];
	}
	
	public void updateTable(){
		this.data = this.dbCon.getUserList();
		this.fireTableDataChanged();
	}
}
