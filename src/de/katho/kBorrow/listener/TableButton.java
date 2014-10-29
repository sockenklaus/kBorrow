package de.katho.kBorrow.listener;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public abstract class TableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	private static final long serialVersionUID = -5902626427691636902L;
	private String label;
	protected JButton buttonR;
	protected JButton buttonE;
	
	public TableButton (String pLabel) {
		this.label = pLabel;
		this.buttonR = new JButton();
		this.buttonE = new JButton();
		this.buttonE.setToolTipText(this.label);
		this.buttonR.setToolTipText(this.label);
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
