package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.NewLendingPanel;
import de.katho.kBorrow.models.FreeArticleTableModel;

public class NewLendingTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7492272258718253745L;

	public NewLendingTableButton(String pLabel, final JTable pTable, final NewLendingPanel pPanel) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/go-down.png");
		ImageIcon icon = new ImageIcon(url);
	
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((FreeArticleTableModel)pTable.getModel()).getIdFromRow(row);
				
				pPanel.setModeNewLending(id);
			}
		});
	}

}
