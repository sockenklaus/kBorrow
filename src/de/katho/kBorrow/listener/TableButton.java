package de.katho.kBorrow.listener;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public abstract class TableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = -5902626427691636902L;
	private String label;
	protected JTable table;
	protected JButton buttonR;
	protected JButton buttonE;
	
	public TableButton (ImageIcon pIcon, JTable pTable) {
		//this.label = pLabel;
		this.table = pTable;
		this.buttonR = new JButton(pIcon);
		this.buttonE = new JButton(pIcon);
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
