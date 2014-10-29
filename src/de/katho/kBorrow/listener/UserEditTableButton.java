package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import de.katho.kBorrow.gui.PanelUser;
import de.katho.kBorrow.models.UserTableModel;

public class UserEditTableButton extends TableButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -886584066497429394L;
	
	public UserEditTableButton(String pLabel, final JTable pTable, final PanelUser pPanel) throws IOException{
		super(pLabel);
		ImageIcon icon = new ImageIcon(ImageIO.read(new File("assets/icons/accessories-text-editor.png")));
		
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);		
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				UserTableModel model = (UserTableModel) pTable.getModel();
				int row = pTable.getSelectedRow();
				
				pPanel.setModeEditUser(model.getUserId(row), model.getUserName(row), model.getUserSurname(row));	
			}
		});
		
	}
}
