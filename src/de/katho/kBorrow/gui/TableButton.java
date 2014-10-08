package de.katho.kBorrow.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private int selectedRow;
	private int selectedColumn;
	private JButton button;
	private JTable parentTable;
	private UserTableModel tModel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2605797435586840499L;

	public TableButton(String pLabel, JTable pTable, ActionListener listener){
		this.button = new JButton(pLabel);
		this.parentTable = pTable;
		this.tModel = (UserTableModel)this.parentTable.getModel();
		this.button.addActionListener(listener);
	}
	
	@Override
	public void addCellEditorListener(CellEditorListener arg0) {	
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public Object getCellEditorValue() {
		return "";
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
	
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		return true;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		selectedRow = row;
		selectedColumn = column;
		
		if(isSelected){
			this.button.setForeground(table.getSelectionForeground());
			this.button.setBackground(table.getSelectionBackground());
		}
		else {
			this.button.setForeground(table.getForeground());
			this.button.setBackground(table.getBackground());
		}
		
		return this.button;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		if (isSelected){
			this.button.setForeground(table.getSelectionForeground());
			this.button.setBackground(table.getSelectionBackground());
		} else {
			this.button.setForeground(table.getForeground());
			this.button.setBackground(UIManager.getColor("Button.background"));
		}
		//this.button.setText((value == null) ? "" : value.toString());
		return this.button;
	}
}
