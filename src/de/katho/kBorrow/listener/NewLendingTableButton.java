package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.gui.PanelNewLending;
import de.katho.kBorrow.models.FreeArticleTableModel;

public class NewLendingTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7492272258718253745L;

	public NewLendingTableButton(String pLabel, final JTable pTable, final PanelNewLending pPanel) throws IOException {
		super(pLabel);
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/go-down.png")));
	
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				FreeArticleTableModel model = (FreeArticleTableModel) pTable.getModel();
				int row = pTable.getSelectedRow();
				
				pPanel.setModeNewLending(model.getArticleId(row), model.getArticleName(row), model.getArticleDescription(row));
			}
		});
	}

}
