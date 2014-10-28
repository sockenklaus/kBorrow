package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.gui.ArticleTableModel;

public class ArticleDeleteTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701712368979056068L;

	public ArticleDeleteTableButton(String pLabel, JTable pTable) throws IOException {
		super(new ImageIcon(ImageIO.read(new File("assets/icons/edit-delete.png"))), pTable);
				
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
