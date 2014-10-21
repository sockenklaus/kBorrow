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
	private MainWindow mainwindow;

	public ArticleEditTableButton(String pLabel, JTable pTable, MainWindow pMainwindow) {
		super(pLabel, pTable);
		
		this.mainwindow = pMainwindow;
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				ArticleTableModel model = (ArticleTableModel) table.getModel();
				int row = table.getSelectedRow();
				
				mainwindow.setModeEditArticle(model.getArticleId(row), model.getArticleName(row), model.getArticleDescription(row));
			}
		});
	}
}
