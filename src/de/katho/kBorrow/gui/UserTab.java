package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.UserDeleteTableButton;
import de.katho.kBorrow.listener.UserEditTableButton;

public class UserTab extends JPanel implements ActionListener {

	private static final long serialVersionUID = -319340262589243978L;
	private JLabel lblUserStatus;
	private JTextField textFieldUserName;
	private JTextField textFieldUserSurname;
	private JButton btnUserSave;
	private JButton btnUserCancel;
	private boolean userModeEdit;
	private int userEditId;
	private UserTableModel userTableModel;

	public UserTab(final DbConnector dbCon){
		super();
		this.setLayout(null);
		
		//Tabelle und drumherum
		this.userTableModel = new UserTableModel(dbCon);
		JTable userTable = new JTable(userTableModel);
		UserDeleteTableButton userDeleteTableButton = new UserDeleteTableButton("L�schen", userTable);
		userTable.getColumnModel().getColumn(4).setCellEditor(userDeleteTableButton);
		userTable.getColumnModel().getColumn(4).setCellRenderer(userDeleteTableButton);
		
		UserEditTableButton userEditTableButton = new UserEditTableButton("Bearbeiten", userTable, this);
		userTable.getColumnModel().getColumn(3).setCellEditor(userEditTableButton);
		userTable.getColumnModel().getColumn(3).setCellRenderer(userEditTableButton);
		
		userTable.setFillsViewportHeight(true);
		
		//Panel Userlist
		JPanel panelUserList = new JPanel();
		panelUserList.setBounds(0, 0, 589, 320);
		panelUserList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzerliste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserList.setLayout(new BorderLayout(0, 0));
		panelUserList.add(new JScrollPane(userTable));
	
		// Edit: Labels
		JLabel lblUserName = new JLabel("Vorname");
		JLabel lblUserSurname = new JLabel("Nachname");
		this.lblUserStatus = new JLabel("");
		lblUserName.setBounds(10, 30, 70, 20);
		lblUserSurname.setBounds(10, 61, 70, 20);
		lblUserStatus.setBounds(6, 89, 413, 14);
		
		//Edit: Textfields
		textFieldUserName = new JTextField();
		textFieldUserSurname= new JTextField();
		textFieldUserName.setBounds(90, 30, 120, 20);
		textFieldUserName.setColumns(10);
		textFieldUserSurname.setBounds(90, 61, 121, 20);
		textFieldUserSurname.setColumns(10);
		
		// Edit: Buttons
		btnUserSave = new JButton("Speichern");
		btnUserCancel = new JButton("Abbrechen");
		btnUserSave.addActionListener(this);
		btnUserSave.setBounds(479, 61, 100, 20);
		btnUserCancel.setBounds(479, 30, 100, 20);
		btnUserCancel.addActionListener(this);
		
		// User-Edit-Pane
		JPanel panelUserEdit = new JPanel();
		panelUserEdit.setBounds(0, 331, 589, 111);
		panelUserEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzer hinzuf\u00FCgen / bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserEdit.setLayout(null);
		panelUserEdit.add(lblUserName);
		panelUserEdit.add(this.textFieldUserName);
		panelUserEdit.add(lblUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.btnUserSave);
		panelUserEdit.add(this.lblUserStatus);		
		panelUserEdit.add(this.btnUserCancel);
		
		this.add(panelUserList);
		this.add(panelUserEdit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnUserSave){
			if(this.userModeEdit){
				int re = this.userTableModel.editUser(this.userEditId, this.textFieldUserName.getText(), this.textFieldUserSurname.getText());
				
				switch(re){
				case 0:
					this.lblUserStatus.setText("Benutzer-ID \""+this.userEditId+"\" erfolgreich bearbeitet.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
					
				case 1:
					this.lblUserStatus.setText("SQL-Fehler. Benutzer konnte nicht bearbeitet werden.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
					
				case 2:
					this.lblUserStatus.setText("Entweder Vor- oder Nachname m�ssen ausgef�llt sein.");
					break;
					
				}
				
				this.userModeEdit = false;
				this.userEditId = -1;
			}
			else {
				int re = this.userTableModel.createUser(this.textFieldUserName.getText(), this.textFieldUserSurname.getText());
				
				switch (re){
				case 0:
					this.lblUserStatus.setText("Benutzer \""+this.textFieldUserName.getText()+" "+this.textFieldUserSurname.getText()+"\" erfolgreich hinzugef�gt.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
				
				case 1:
					this.lblUserStatus.setText("SQL-Fehler. Benutzer konnte nicht erstellt werden.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
				
				case 2:
					this.lblUserStatus.setText("Entweder Vor- oder Nachname m�ssen ausgef�llt sein.");
					break;
				}
			}
		}
		
		/**
		 * Aktionen f�r den Button "Benutzer abbrechen"
		 */
		if(e.getSource() == this.btnUserCancel){
			this.userModeEdit = false;
			this.textFieldUserName.setText("");
			this.textFieldUserSurname.setText("");
		}
		
	}

	public void setModeEditUser(int pId, String pName, String pSurname){
		this.userModeEdit = true;
		this.userEditId = pId;
		this.textFieldUserName.setText(pName);
		this.textFieldUserSurname.setText(pSurname);		
	}
}
