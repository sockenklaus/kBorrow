package de.katho.kBorrow.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;







import de.katho.kBorrow.Main;
import de.katho.kBorrow.db.DbConnector;

import javax.swing.JTable;

public class MainWindow implements ActionListener {

	private DbConnector dbCon;
	private boolean userModeSave = true;
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTabbedPane tabbedPane;
	private JPanel panelUserEdit;
	private JLabel lblUserName;
	private JLabel lblUserSurname;
	private JTextField textFieldUserName;
	private JTextField textFieldUserSurname;
	private JButton btnUserSave;
	private JLabel lblUserStatus;
	private JTable userTable;
	private UserTableModel userTableModel;
	private JScrollPane scrollUserList;
	private JPanel panelUserList;
	

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MainWindow(DbConnector pDbCon) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.dbCon = pDbCon;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.frame = new JFrame();
		frame.setResizable(false);
		this.frame.setBounds(100, 100, 600, 500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		this.tabbedPane.addTab("New tab", null, panel, null);
		
		this.textField_1 = new JTextField();
		panel.add(this.textField_1);
		this.textField_1.setColumns(10);
		
		this.textField = new JTextField();
		panel.add(this.textField);
		this.textField.setColumns(10);
		
		this.initUserTab();
	}
	
	private void initUserTab(){
		JPanel panelUser = new JPanel();
		this.tabbedPane.addTab("Benutzer verwalten", null, panelUser, null);
		
		panelUser.setLayout(null);
		
		panelUserList = new JPanel();
		panelUserList.setBounds(0, 0, 589, 320);
		panelUserList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzerliste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUser.add(panelUserList);
		panelUserList.setLayout(new BorderLayout(0, 0));
		
		userTable = new JTable(new UserTableModel(this.dbCon));
		userTable.setFillsViewportHeight(true);
		userTableModel = (UserTableModel)userTable.getModel();
		//userTableModel.
		
		
		scrollUserList = new JScrollPane(userTable);
		panelUserList.add(scrollUserList);
		scrollUserList.setViewportBorder(null);
		
		// User-Edit-Pane
		panelUserEdit = new JPanel();
		panelUserEdit.setBounds(0, 331, 589, 111);
		panelUserEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Benutzer hinzuf\u00FCgen / bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelUserEdit.setLayout(null);
		panelUser.add(this.panelUserEdit);
		
		lblUserName = new JLabel("Vorname");
		lblUserName.setBounds(6, 27, 55, 20);
				
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(71, 27, 120, 20);
		textFieldUserName.setColumns(10);
		
		lblUserSurname = new JLabel("Nachname");
		lblUserSurname.setBounds(6, 58, 62, 20);
		
		textFieldUserSurname= new JTextField();
		textFieldUserSurname.setBounds(70, 58, 121, 20);
		textFieldUserSurname.setColumns(10);
		
		btnUserSave = new JButton("Speichern");
		btnUserSave.addActionListener(this);
		btnUserSave.setBounds(333, 58, 86, 20);
		
		lblUserStatus = new JLabel("");
		lblUserStatus.setBounds(6, 89, 413, 14);
		
		
		panelUserEdit.add(this.lblUserName);
		panelUserEdit.add(this.textFieldUserName);
		panelUserEdit.add(this.lblUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.btnUserSave);
		panelUserEdit.add(this.lblUserStatus);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.btnUserSave){
			if(this.userModeSave){
				if(this.dbCon.createUser(this.textFieldUserName.getText(), this.textFieldUserSurname.getText())){
					this.lblUserStatus.setText("Benutzer \""+this.textFieldUserName.getText()+" "+this.textFieldUserSurname.getText()+"\" erfolgreich hinzugefügt.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					this.userTableModel.updateTable();
				}
				else {
					this.lblUserStatus.setText("Benutzer konnte nicht erstellt werden.");
				}
			}
			else {
				// Hier kommt die Funktion rein, die Benutzer editieren lässt.
			}
			
		}
		
	}
}
