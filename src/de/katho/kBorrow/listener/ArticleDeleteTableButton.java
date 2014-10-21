package de.katho.kBorrow.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import de.katho.kBorrow.gui.ArticleTableModel;

public class ArticleDeleteTableButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701712368979056068L;
	private JButton buttonR;
	private JButton buttonE;
	private String label;
	private JTable table;
	private ArticleTableModel model;

	public ArticleDeleteTableButton(String pLabel, JTable pTable) {
		this.label = pLabel;
		this.table = pTable;
		this.model = (ArticleTableModel) pTable.getModel();
		this.buttonR = new JButton(pLabel);
		this.buttonE = new JButton(pLabel);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = table.getSelectedRow();
				int id = model.getArticleId(row);
				
				model.deleteArticle(id);				
			}
		});
	}

	@Override
	public Object getCellEditorValue() {
		return this.label;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
		return this.buttonE;
	}

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		return this.buttonR;
	}

}
