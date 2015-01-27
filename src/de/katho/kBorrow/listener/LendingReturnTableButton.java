package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.controller.ManageLendingsController;
import de.katho.kBorrow.models.LendingTableModel;

/**
 * Erzeugt den "Rückgabe"-Button in der LendingTable.
 */
public class LendingReturnTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = 4841475504601928160L;

	/**
	 * Erzeugt den "Rückgabe"-Button in der Tabelle der Ausleihen.
	 * 
	 * @param 	pLabel			Name des Buttons, dient als Tooltip.
	 * @param 	pTable			Referenz auf die LendingTable.
	 * @param 	pController		Referenz auf den ManageLendingsController.
	 * @throws	IOException		Wenn Probleme beim Einbinden des Icons auftreten.
	 */
	public LendingReturnTableButton(String pLabel, final JTable pTable, final ManageLendingsController pController) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/edit-undo.png");
		ImageIcon icon = new ImageIcon(url);
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((LendingTableModel)pTable.getModel()).getIdFromRow(row);

				pController.returnLending(id);
			}
		});
	}

}
