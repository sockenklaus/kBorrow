package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.ArticlePanel;
import de.katho.kBorrow.models.ArticleTableModel;

public class ArticleEditTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5902626427691636902L;

	public ArticleEditTableButton(String pLabel, final JTable pTable, final ArticlePanel articleTab) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/accessories-text-editor.png");
		ImageIcon icon = new ImageIcon(url);
				
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((ArticleTableModel)pTable.getModel()).getIdFromRow(row);
				
				articleTab.setModeEditArticle(id);
			}
		});
	}
}
