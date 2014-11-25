package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.controller.ManageLendingsController;

public class LendingReturnTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841475504601928160L;

	public LendingReturnTableButton(String pLabel, final JTable pTable, final ManageLendingsController pController) throws IOException {
		super(pLabel);
		
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/edit-undo.png")));
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();

				pController.returnLending(row);
			}
		});
	}

}
