package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import de.katho.kBorrow.gui.ArticleTableModel;
import de.katho.kBorrow.gui.MainWindow;

public class ArticleEditTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5902626427691636902L;

	public ArticleEditTableButton(String pLabel, JTable pTable, final MainWindow pMainWindow) {
		super(pLabel, pTable);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				ArticleTableModel model = (ArticleTableModel) table.getModel();
				int row = table.getSelectedRow();
				
				pMainWindow.setModeEditArticle(model.getArticleId(row), model.getArticleName(row), model.getArticleDescription(row));
			}
		});
	}
}
