package de.katho.kBorrow.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

public class UserDeleteListener implements ActionListener {

	private JTable table;
	private UserTableModel model;
	
	public UserDeleteListener(JTable pTable){
		this.table = pTable;
		this.model = (UserTableModel)table.getModel();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		int row = table.getSelectedRow();

		this.model.deleteUser(model.getUserId(row));
	}

}
