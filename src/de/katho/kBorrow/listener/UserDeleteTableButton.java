package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import de.katho.kBorrow.gui.UserTableModel;

public class UserDeleteTableButton extends TableButton {

	private static final long serialVersionUID = -886584066497429394L;
	
	public UserDeleteTableButton(String pLabel, JTable pTable){
		super(pLabel, pTable);
				
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				UserTableModel model = (UserTableModel) table.getModel();
				
				int row = table.getSelectedRow();
				int id = model.getUserId(row);

				model.deleteUser(id);
			}
		});
		
	}
}
