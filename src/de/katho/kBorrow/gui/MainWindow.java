package de.katho.kBorrow.gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.ArticleDeleteTableButton;
import de.katho.kBorrow.listener.ArticleEditTableButton;
import de.katho.kBorrow.listener.UserDeleteTableButton;
import de.katho.kBorrow.listener.UserEditTableButton;

import javax.swing.JTable;

import java.awt.Color;

import javax.swing.JTextArea;

import java.awt.Font;

public class MainWindow implements ActionListener {

	private DbConnector dbCon;
	private boolean userModeEdit = false;
	private boolean articleModeEdit = false;
	private int userEditId;
	private int articleEditId;
	
	private JFrame frame;
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
	private ArticleTableModel articleTableModel;
	private JScrollPane scrollUserList;
	private JPanel panelUserList;
	private JButton btnUserCancel;
	private JPanel panelArticleList;
	private JPanel panelArticleEdit;
	private JScrollPane scrollArticleList;
	private JTable articleTable;
	private JTextField textFieldArticleName;
	private JButton btnArticleSave;
	private JButton btnArticleCancel;
	private JTextArea textAreaArticleDescription;
	private JLabel lblArticleStatus;
	

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
		
		this.initArticleTab();
		this.initUserTab();
	}
	
	private void initArticleTab() {
		JPanel panelArticle = new JPanel();
		articleTableModel = new ArticleTableModel(this.dbCon);
		articleTable = new JTable(articleTableModel);
		panelArticleList = new JPanel();
		
		ArticleDeleteTableButton articleDeleteTableButton = new ArticleDeleteTableButton("Löschen", this.articleTable);
		articleTable.getColumnModel().getColumn(4).setCellEditor(articleDeleteTableButton);
		articleTable.getColumnModel().getColumn(4).setCellRenderer(articleDeleteTableButton);
		
		ArticleEditTableButton articleEditTableButton = new ArticleEditTableButton("Bearbeiten", this.articleTable, this);
		articleTable.getColumnModel().getColumn(3).setCellEditor(articleEditTableButton);
		articleTable.getColumnModel().getColumn(3).setCellRenderer(articleEditTableButton);
		
		this.tabbedPane.addTab("Artikel verwalten", null, panelArticle, null);
		panelArticle.setLayout(null);
		
		panelArticleList.setBorder(new TitledBorder(null, "Artikelliste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelArticleList.setBounds(0, 0, 589, 275);
		panelArticle.add(panelArticleList);
		panelArticleList.setLayout(new BorderLayout(0, 0));
		
		articleTable.setFillsViewportHeight(true);
		scrollArticleList = new JScrollPane(articleTable);
		panelArticleList.add(scrollArticleList);
		
		panelArticleEdit = new JPanel();
		panelArticleEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Artikel hinzuf\u00FCgen / bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelArticleEdit.setBounds(0, 273, 589, 170);
		panelArticle.add(panelArticleEdit);
		panelArticleEdit.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 30, 70, 20);
		panelArticleEdit.add(lblNewLabel);
		
		JLabel lblBeschreibung = new JLabel("Beschreibung");
		lblBeschreibung.setBounds(10, 61, 70, 20);
		panelArticleEdit.add(lblBeschreibung);
		
		textFieldArticleName = new JTextField();
		textFieldArticleName.setBounds(90, 30, 250, 20);
		panelArticleEdit.add(textFieldArticleName);
		textFieldArticleName.setColumns(10);
		
		this.textAreaArticleDescription = new JTextArea(5, 30);
		this.textAreaArticleDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.textAreaArticleDescription.setLineWrap(true);
		this.textAreaArticleDescription.setBounds(90, 59, 250, 80);
		this.textAreaArticleDescription.setBorder(BorderFactory.createEtchedBorder());
		panelArticleEdit.add(this.textAreaArticleDescription);
		
		this.btnArticleSave = new JButton("Speichern");
		this.btnArticleSave.addActionListener(this);
		this.btnArticleSave.setBounds(490, 136, 89, 23);
		panelArticleEdit.add(this.btnArticleSave);
		
		this.btnArticleCancel = new JButton("Abbrechen");
		this.btnArticleCancel.addActionListener(this);
		this.btnArticleCancel.setBounds(490, 102, 89, 23);
		panelArticleEdit.add(this.btnArticleCancel);
		
		this.lblArticleStatus = new JLabel("");
		lblArticleStatus.setBounds(90, 145, 46, 14);
		panelArticleEdit.add(lblArticleStatus);
		
			
				
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
		
		userTableModel = new UserTableModel(this.dbCon);
		userTable = new JTable(userTableModel);
		userTable.setFillsViewportHeight(true);
	
		UserDeleteTableButton userDeleteTableButton = new UserDeleteTableButton("Löschen", this.userTable);
		userTable.getColumnModel().getColumn(4).setCellEditor(userDeleteTableButton);
		userTable.getColumnModel().getColumn(4).setCellRenderer(userDeleteTableButton);
		
		UserEditTableButton userEditTableButton = new UserEditTableButton("Bearbeiten", this.userTable, this);
		userTable.getColumnModel().getColumn(3).setCellEditor(userEditTableButton);
		userTable.getColumnModel().getColumn(3).setCellRenderer(userEditTableButton);
				
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
		lblUserSurname = new JLabel("Nachname");
		lblUserStatus = new JLabel("");
		textFieldUserName = new JTextField();
		textFieldUserSurname= new JTextField();
		btnUserSave = new JButton("Speichern");
		btnUserCancel = new JButton("Abbrechen");
		
		lblUserName.setBounds(10, 30, 70, 20);
		textFieldUserName.setBounds(90, 30, 120, 20);
		textFieldUserName.setColumns(10);
		lblUserSurname.setBounds(10, 61, 70, 20);
		textFieldUserSurname.setBounds(90, 61, 121, 20);
		textFieldUserSurname.setColumns(10);
		btnUserSave.addActionListener(this);
		btnUserSave.setBounds(479, 61, 100, 20);
		btnUserCancel.setBounds(479, 30, 100, 20);
		btnUserCancel.addActionListener(this);
		lblUserStatus.setBounds(6, 89, 413, 14);
		
		panelUserEdit.add(this.lblUserName);
		panelUserEdit.add(this.textFieldUserName);
		panelUserEdit.add(this.lblUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.textFieldUserSurname);
		panelUserEdit.add(this.btnUserSave);
		panelUserEdit.add(this.lblUserStatus);		
		panelUserEdit.add(this.btnUserCancel);
	}
	
	public void setModeEditUser(int pId, String pName, String pSurname){
		this.userModeEdit = true;
		this.userEditId = pId;
		this.textFieldUserName.setText(pName);
		this.textFieldUserSurname.setText(pSurname);		
	}
	
	public void setModeEditArticle(int articleId, String articleName, String articleDescription) {
		this.articleModeEdit = true;
		this.articleEditId = articleId;
		this.textFieldArticleName.setText(articleName);
		this.textAreaArticleDescription.setText(articleDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		/**
		 * Aktionen für den Button "Benutzer speichern"
		 */
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
					this.lblUserStatus.setText("Entweder Vor- oder Nachname müssen ausgefüllt sein.");
					break;
					
				}
				
				this.userModeEdit = false;
				this.userEditId = -1;
			}
			else {
				int re = this.userTableModel.createUser(this.textFieldUserName.getText(), this.textFieldUserSurname.getText());
				
				switch (re){
				case 0:
					this.lblUserStatus.setText("Benutzer \""+this.textFieldUserName.getText()+" "+this.textFieldUserSurname.getText()+"\" erfolgreich hinzugefügt.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
				
				case 1:
					this.lblUserStatus.setText("SQL-Fehler. Benutzer konnte nicht erstellt werden.");
					this.textFieldUserName.setText("");
					this.textFieldUserSurname.setText("");
					break;
				
				case 2:
					this.lblUserStatus.setText("Entweder Vor- oder Nachname müssen ausgefüllt sein.");
					break;
				}
			}
		}
		
		/**
		 * Aktionen für den Button "Artikel speichern"
		 */
		if(e.getSource() == this.btnArticleSave){
			if(this.userModeEdit){
				int re = this.articleTableModel.editArticle(this.articleEditId, this.textFieldArticleName.getText(), this.textAreaArticleDescription.getText());
				
				switch(re){
				case 0:
					this.lblArticleStatus.setText("Artikel-ID \""+this.articleEditId+"\" erfolgreich bearbeitet.");
					this.textFieldArticleName.setText("");
					this.textAreaArticleDescription.setText("");
					break;
					
				case 1:
					this.lblArticleStatus.setText("SQL-Fehler. Artikel konnte nicht bearbeitet werden.");
					this.textFieldArticleName.setText("");
					this.textAreaArticleDescription.setText("");
					break;
					
				case 2:
					this.lblArticleStatus.setText("Artikelname muss ausgefüllt sein.");
					break;
					
				}
				
				this.articleModeEdit = false;
				this.articleEditId = -1;
			}
			
			else {
				int re = this.articleTableModel.createArticle(this.textFieldArticleName.getText(), this.textAreaArticleDescription.getText());
				
				switch(re){
				case 0:
					this.lblArticleStatus.setText("Artikel \""+this.textFieldArticleName.getText()+"\" erfolgreich hinzugefügt.");
					this.textFieldArticleName.setText("");
					this.textAreaArticleDescription.setText("");
					break;
					
				case 1:
					this.lblArticleStatus.setText("SQL-Fehler. Artikel konnte nicht erstellt werden.");
					this.textFieldArticleName.setText("");
					this.textAreaArticleDescription.setText("");
					break;
				
				case 2:
					this.lblArticleStatus.setText("Es muss ein Artikelname vergeben werden");
					break;
				}
			}
		}
		
		/**
		 * Aktionen für den Button "Benutzer abbrechen"
		 */
		if(e.getSource() == this.btnUserCancel){
			this.userModeEdit = false;
			this.textFieldUserName.setText("");
			this.textFieldUserSurname.setText("");
		}
		
		/**
		 * Aktionen für den Button "Artikel abbrechen"
		 */
		if(e.getSource() == this.btnArticleCancel){
			this.articleModeEdit = false;
			this.textFieldArticleName.setText("");
			this.textAreaArticleDescription.setText("");
		}
	}
}
