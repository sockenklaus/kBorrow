package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import de.katho.kBorrow.gui.ArticleTableModel;

public class ArticleDeleteTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701712368979056068L;

	public ArticleDeleteTableButton(String pLabel, JTable pTable) {
		super(pLabel, pTable);
				
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				ArticleTableModel model = (ArticleTableModel) table.getModel();
				
				int row = table.getSelectedRow();
				int id = model.getArticleId(row);
				
				model.deleteArticle(id);				
			}
		});
	}
}
