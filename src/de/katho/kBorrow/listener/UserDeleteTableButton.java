package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.controller.UserController;
import de.katho.kBorrow.gui.UserPanel;

public class UserDeleteTableButton extends TableButton {

	private static final long serialVersionUID = -886584066497429394L;
	
	public UserDeleteTableButton(String pLabel, final JTable pTable, final UserPanel pPanel, final UserController pController ) throws IOException{
		super(pLabel);
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/edit-delete.png")));
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();

				pController.deleteUser(row);
				pPanel.resetModeEditUser();
			}
		});
		
	}
}
