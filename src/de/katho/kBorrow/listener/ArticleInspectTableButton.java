package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.ArticleInspectFrame;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class ArticleInspectTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2591133864537097893L;

	public ArticleInspectTableButton(String pLabel, final JTable pTable, final DbConnector dbCon, final HashMap<String, KDataModel> models) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/system-search.png");
		ImageIcon icon = new ImageIcon(url);
	
		buttonE.setIcon(icon);
		buttonR.setIcon(icon);
		
		buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				
				new ArticleInspectFrame(row, dbCon, models);
			}
		});
	}

}
