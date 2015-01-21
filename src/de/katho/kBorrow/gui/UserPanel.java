package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import de.katho.kBorrow.controller.UserController;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.listener.UserDeleteTableButton;
import de.katho.kBorrow.listener.UserEditTableButton;
import de.katho.kBorrow.models.UserTableModel;

/**
 * Erzeugt das UserPanel und reicht Benutzerinteraktionen an den entsprechenden Controller weiter.
 */
public class UserPanel extends JPanel implements ActionListener, KeyListener {

	/** Serial Version UID */
	private static final long serialVersionUID = -319340262589243978L;
	
	/** Label: Statusmeldung */
	private JLabel lblUserStatus;
	
	/** Textfeld: Benutzer Vorname */
	private JTextField textFieldUserName;
	
	/** Textfeld: Benutzer Nachname */
	private JTextField textFieldUserSurname;
	
	/** Button: Speichern */
	private JButton btnUserSave;
	
	/** Button: Abbrechen */
	private JButton btnUserCancel;
	
	/** True, wenn gerade ein Benutzer bearbeitet werden soll, andernfalls false. */
	private boolean userModeEdit;
	
	/** ID des Benutzers, der bearbeitet werden soll */
	private int userEditId;
	
	/** Referenz auf das KUserModel */
	private KUserModel kUserModel;
	
	/** Referenz auf den UserController */
	private UserController userController;

	/**
	 * Erzeugt das UserPanel
	 * 
	 * @param 	dbCon			Referenz auf die Datenbankverbindung.
	 * @param 	models			HashMaps mit KDataModels.
	 * @throws 	IOException		Wenn Fehler im {@link UserDeleteTableButton} oder {@link UserEditTableButton} auftreten.
	 */
	public UserPanel(final DbConnector dbCon, HashMap<String, KDataModel> models) throws IOException{
		super();
		setLayout(null);
		kUserModel = (KUserModel)models.get("kusermodel");
		userController = new UserController(dbCon, models);
		
		//Tabelle und drumherum
		JTable userTable = new JTable(new UserTableModel(kUserModel));
		userTable.setRowHeight(30);
		UserDeleteTableButton userDeleteTableButton = new UserDeleteTableButton("Löschen", userTable, this, userController);
		UserEditTableButton userEditTableButton = new UserEditTableButton("Bearbeiten", userTable, this);
		
		for (int i = 3; i <= 4; i++){
			userTable.getColumnModel().getColumn(i).setCellEditor(i == 3 ? userEditTableButton : userDeleteTableButton);
			userTable.getColumnModel().getColumn(i).setCellRenderer(i == 3 ? userEditTableButton : userDeleteTableButton);
			
			userTable.getColumnModel().getColumn(i).setMinWidth(30);
			userTable.getColumnModel().getColumn(i).setMaxWidth(30);
			userTable.getColumnModel().getColumn(i).setPreferredWidth(30);
		}
		userTable.getColumnModel().getColumn(0).setMinWidth(30);
		userTable.getColumnModel().getColumn(0).setMaxWidth(30);
		userTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		
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
		lblUserStatus = new JLabel("");
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
		textFieldUserName.addKeyListener(this);
		textFieldUserSurname.addKeyListener(this);
		
		// Edit: Buttons
		btnUserSave = new JButton("Speichern");
		btnUserCancel = new JButton("Abbrechen");
		btnUserSave.addActionListener(this);
		btnUserSave.setBounds(479, 61, 100, 20);
		btnUserCancel.setBounds(479, 30, 100, 20);
		btnUserCancel.addActionListener(this);
		
		//Traversal-Policy
		Vector<Component> order = new Vector<Component>();
		order.add(textFieldUserName);
		order.add(textFieldUserSurname);
		order.add(btnUserCancel);
		order.add(btnUserSave);
		MyFocusTraversalPolicy focusPolicy = new MyFocusTraversalPolicy(order);
		
		// User-Edit-Pane
		JPanel panelUserEdit = new JPanel();
		panelUserEdit.setBounds(0, 331, 589, 111);
		panelUserEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzer hinzuf\u00FCgen / bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserEdit.setLayout(null);
		panelUserEdit.add(lblUserName);
		panelUserEdit.add(textFieldUserName);
		panelUserEdit.add(lblUserSurname);
		panelUserEdit.add(textFieldUserSurname);
		panelUserEdit.add(textFieldUserSurname);
		panelUserEdit.add(btnUserSave);
		panelUserEdit.add(lblUserStatus);		
		panelUserEdit.add(btnUserCancel);
		panelUserEdit.setFocusTraversalPolicy(focusPolicy);
		panelUserEdit.setFocusCycleRoot(true);
		
		add(panelUserList);
		add(panelUserEdit);
	}

	/**
	 * ActionListener für gedrückte Buttons.
	 * 
	 * <p>
	 * Ruft {@link #saveButtonPressed()} auf, wenn der Speichern-Button 
	 * und {@link #resetModeEditUser()}, wenn der Abbrechen-Button gedrückt wurde.
	 * </p>
	 * 
	 * @param	e	ActionEvent, das den Listener aufgerufen hat.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnUserSave){
			saveButtonPressed();
		}
		
		/**
		 * Aktionen für den Button "Benutzer abbrechen"
		 */
		if(e.getSource() == btnUserCancel){
			resetModeEditUser();
		}
		
	}

	/**
	 * Setzt das Formular zurück.
	 */
	public void resetModeEditUser() {
		userModeEdit = false;
		textFieldUserName.setText("");
		textFieldUserSurname.setText("");
	}

	/**
	 * Setzt Werte im Formular, wenn ein Benutzer bearbeitet werden soll.
	 * 
	 * @param	pId		ID des Benutzers, der bearbeitet werden soll.
	 */
	public void setModeEditUser(int pId){
		KUser user = kUserModel.getElement(pId);
		
		userModeEdit = true;
		userEditId = user.getId();
		textFieldUserName.setText(user.getName());
		textFieldUserSurname.setText(user.getSurname());		
	}
	
	/**
	 * Führt die Aktionen aus, die geschehen, wenn der Speichern-Button gedrückt wird.
	 * 
	 * <p>
	 * Übergibt Inhalt des Formulars an den UserController und gibt je nach Rückgabecode eine andere Statusmeldung aus.
	 * </p>
	 */
	private void saveButtonPressed(){
		if(userModeEdit){
			int re = userController.editUser(userEditId, textFieldUserName.getText(), textFieldUserSurname.getText());
			
			switch(re){
			case 0:
				lblUserStatus.setText("Benutzer-ID \""+userEditId+"\" erfolgreich bearbeitet.");
				textFieldUserName.setText("");
				textFieldUserSurname.setText("");
				break;
				
			case 1:
				lblUserStatus.setText("SQL-Fehler. Benutzer konnte nicht bearbeitet werden.");
				textFieldUserName.setText("");
				textFieldUserSurname.setText("");
				break;
				
			case 2:
				lblUserStatus.setText("Entweder Vor- oder Nachname müssen ausgefüllt sein.");
				break;
				
			}
			
			userModeEdit = false;
			userEditId = -1;
		}
		else {					
			int re = userController.createUser(textFieldUserName.getText(), textFieldUserSurname.getText());
		
			switch (re){
			case 0:
				lblUserStatus.setText("Benutzer \""+textFieldUserName.getText()+" "+textFieldUserSurname.getText()+"\" erfolgreich hinzugefügt.");
				textFieldUserName.setText("");
				textFieldUserSurname.setText("");
				break;
			
			case 1:
				lblUserStatus.setText("SQL-Fehler. Benutzer konnte nicht erstellt werden.");
				textFieldUserName.setText("");
				textFieldUserSurname.setText("");
				break;
			
			case 2:
				lblUserStatus.setText("Entweder Vor- oder Nachname müssen ausgefüllt sein.");
				break;
			}
		}
	}

	/**
	 * Setzt den als Parameter übergebenen Text in das Status-Label.
	 * 
	 * @param	pText	Zu setzender Text.
	 */
	public void setStatusLabel(String pText){
		lblUserStatus.setText(pText);
	}
	
	/**
	 * KeyListener für den Druck einer Taste
	 * 
	 * <p>
	 * Ruft {@link #saveButtonPressed} auf, wenn die gedrückte Taste die Entertaste war.
	 * </p>
	 * 
	 * @param	pKeyPress	KeyEvent, von dem das Event erzeugt wurde.
	 */
	public void keyPressed(KeyEvent pKeyPress) {
		if(pKeyPress.getKeyCode() == KeyEvent.VK_ENTER) saveButtonPressed();		
	}

	/**
	 * KeyListener für das Loslassen einer Taste.
	 * 
	 * <p>Nicht implementiert</p>
	 * 
	 * @param	arg0	KeyEvent, von dem das Event erzeugt wird. 
	 */
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * KeyListener für das Tippen (Drücken und Loslassen) einer Taste.
	 * 
	 * <p>Nicht implementiert</p>
	 * 
	 * @param	arg0	KeyEvent, von dem das Event erzeugt wird.
	 */
	public void keyTyped(KeyEvent arg0) {
	}
}
