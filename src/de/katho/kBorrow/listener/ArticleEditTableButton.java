package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.gui.ArticlePanel;
import de.katho.kBorrow.models.ArticleModel;

public class ArticleEditTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5902626427691636902L;

	public ArticleEditTableButton(String pLabel, final JTable pTable, final ArticlePanel articleTab) throws IOException {
		super(pLabel);
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/accessories-text-editor.png")));
				
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				ArticleModel model = (ArticleModel) pTable.getModel();
				int row = pTable.getSelectedRow();
				
				articleTab.setModeEditArticle(model.getArticleId(row), model.getArticleName(row), model.getArticleDescription(row));
			}
		});
	}
}
