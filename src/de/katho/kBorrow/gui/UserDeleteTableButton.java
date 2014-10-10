package de.katho.kBorrow.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class UserDeleteTableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886584066497429394L;

	private JButton buttonR;
	private JButton buttonE;
	private String label;
	private JTable table;
	private UserTableModel model;
	
	public UserDeleteTableButton(String pLabel, JTable pTable){
		this.label = pLabel;
		this.table = pTable;
		this.model = (UserTableModel) pTable.getModel();
		this.buttonR = new JButton(pLabel);
		this.buttonE = new JButton(pLabel);
				
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = table.getSelectedRow();
				int id = model.getUserId(row);

				model.deleteUser(id);
			}
		});
		
	}
	
	public Object getCellEditorValue() {
		return this.label;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return this.buttonE;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return this.buttonR;
	}

}
