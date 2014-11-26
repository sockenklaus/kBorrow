package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.gui.ArticleInspectFrame;

public class ArticleInspectTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2591133864537097893L;

	public ArticleInspectTableButton(String pLabel, final JTable pTable, final HashMap<String, Object> pModels) throws IOException {
		super(pLabel);
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/system-search.png")));
	
		buttonE.setIcon(icon);
		buttonR.setIcon(icon);
		
		buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				
				new ArticleInspectFrame(row, pModels);
			}
		});
	}

}
