package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.controller.UserController;
import de.katho.kBorrow.gui.UserPanel;
import de.katho.kBorrow.models.UserTableModel;

public class UserDeleteTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = -886584066497429394L;
	
	public UserDeleteTableButton(String pLabel, final JTable pTable, final UserPanel pPanel, final UserController pController ) throws IOException{
		super(pLabel);
		URL url = Main.class.getResource("/icons/edit-delete.png");
		ImageIcon icon = new ImageIcon(url);
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((UserTableModel)pTable.getModel()).getIdFromRow(row);

				if(pController.deleteUser(id)) pPanel.setStatusLabel("Benutzer erfolgreich gel�scht.");
				else pPanel.setStatusLabel("Beuntzer konnte nicht gel�scht werden.");
				
				pPanel.resetModeEditUser();
			}
		});
		
	}
}
