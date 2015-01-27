package de.katho.kBorrow.listener;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Diese Klasse erzeugt das Grundger�st f�r alle TableButtons.
 */
public abstract class TableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	
	/** Serial Version UID */
	private static final long serialVersionUID = -5902626427691636902L;
	
	/** Name des Buttons, dient als Tooltip */
	private String label;
	
	/** Referenz auf den TableCellRenderer-Button */
	protected JButton buttonR;
	
	/** Referenz auf den TableCellEditor-Button */
	protected JButton buttonE;
	
	/**
	 * Erzeugt die n�tigen Button-Objekte.
	 * 
	 * @param pLabel	Name des Buttons als String, dient als Tooltip.
	 */
	public TableButton (String pLabel) {
		this.label = pLabel;
		this.buttonR = new JButton();
		this.buttonE = new JButton();
		this.buttonE.setToolTipText(this.label);
		this.buttonR.setToolTipText(this.label);
	}

	/**
	 * Gibt den Namen des Buttons als String zur�ck.
	 * 
	 * @return	Name des Buttons als String.
	 */
	public Object getCellEditorValue() {
		return this.label;
	}

	/**
	 * Gibt den TableCellEditor-Button zur�ck.
	 * 
	 * @return	Gibt den TableCellEditor-Button zur�ck.
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return this.buttonE;
	}

	/**
	 * Gibt den TableCellRenderer-Button zur�ck.
	 * 
	 * @return	Gibt den TableCellRenderer-Button zur�ck.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return this.buttonR;
	}
}
