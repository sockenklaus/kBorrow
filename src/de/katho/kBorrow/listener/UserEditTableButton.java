package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import de.katho.kBorrow.gui.UserTab;
import de.katho.kBorrow.gui.UserTableModel;

public class UserEditTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886584066497429394L;
	
	public UserEditTableButton(String pLabel, JTable pTable, final UserTab pPanel){
		super(pLabel, pTable);
					
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				UserTableModel model = (UserTableModel) table.getModel();
				int row = table.getSelectedRow();
				
				pPanel.setModeEditUser(model.getUserId(row), model.getUserName(row), model.getUserSurname(row));	
			}
		});
		
	}
}
