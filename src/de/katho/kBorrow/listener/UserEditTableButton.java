package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.UserPanel;
import de.katho.kBorrow.models.UserTableModel;

public class UserEditTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = -886584066497429394L;
	
	public UserEditTableButton(String pLabel, final JTable pTable, final UserPanel pPanel) throws IOException{
		super(pLabel);
		URL url = Main.class.getResource("/icons/accessories-text-editor.png");
		ImageIcon icon = new ImageIcon(url);
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);		
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((UserTableModel)pTable.getModel()).getIdFromRow(row);
				
				pPanel.setModeEditUser(id);	
			}
		});
		
	}
}
