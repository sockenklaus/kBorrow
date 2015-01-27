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

/**
 * Erzeugt den "Benutzer l�schen"-Button in der UserTable.
 */
public class UserDeleteTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = -886584066497429394L;
	
	/**
	 * Erzeugt den "Benutzer L�schen"-Button in der UserTable.
	 * 
	 * @param 	pLabel			Name des Buttons, dient als Tooltip.
	 * @param 	pTable			Referenz auf die UserTable.
	 * @param 	pPanel			Referenz auf das UserPanel.
	 * @param 	pController		Referenz auf den UserController.
	 * @throws	IOException		Wenn Probleme beim Einbinden des Icons auftreten.
	 */
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
