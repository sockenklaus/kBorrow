package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.controller.ArticleController;
import de.katho.kBorrow.gui.ArticlePanel;

public class ArticleDeleteTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701712368979056068L;

	public ArticleDeleteTableButton(String pLabel, final JTable pTable, final ArticlePanel pPanel, final ArticleController pController) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/edit-delete.png");
		ImageIcon icon = new ImageIcon(url);
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				
				pController.deleteArticle(row);		
				pPanel.resetModeEditArticle();
			}
		});
	}
}
