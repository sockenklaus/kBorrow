package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import de.katho.kBorrow.controller.ArticleController;
import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.ArticleDeleteTableButton;
import de.katho.kBorrow.listener.ArticleEditTableButton;
import de.katho.kBorrow.models.ArticleTableModel;

public class ArticlePanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = -8511924597640457608L;
	private ArticleController articleController;
	private JTextArea textAreaArticleDescription;
	private JTextField textFieldArticleName;
	private JButton btnArticleSave;
	private JButton btnArticleCancel;
	private JLabel lblArticleStatus;
	private boolean articleModeEdit;
	private int articleEditId;
	private ArticleTableModel articleTableModel;

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	public ArticlePanel(final DbConnector dbCon, HashMap<String, Object> pModels) throws IOException {	
		super();
		this.setLayout(null);
		articleTableModel = (ArticleTableModel)pModels.get("articletablemodel");
		articleController = new ArticleController(dbCon, pModels);
		
		/*
		 * Tabelle und drumherum
		 */
		
		JTable articleTable = new JTable(articleTableModel);
		articleTable.setRowHeight(30);
		ArticleDeleteTableButton articleDeleteTableButton = new ArticleDeleteTableButton("Löschen", articleTable, this, articleController);
		ArticleEditTableButton articleEditTableButton = new ArticleEditTableButton("Bearbeiten", articleTable, this);
		
		for (int i = 3; i <= 4; i++){
			articleTable.getColumnModel().getColumn(i).setCellEditor(i == 3 ? articleEditTableButton : articleDeleteTableButton);
			articleTable.getColumnModel().getColumn(i).setCellRenderer(i == 3 ? articleEditTableButton : articleDeleteTableButton);
			
			articleTable.getColumnModel().getColumn(i).setMinWidth(30);
			articleTable.getColumnModel().getColumn(i).setMaxWidth(30);
			articleTable.getColumnModel().getColumn(i).setPreferredWidth(30);
		}
		articleTable.getColumnModel().getColumn(0).setMinWidth(30);
		articleTable.getColumnModel().getColumn(0).setMaxWidth(30);
		articleTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		
		articleTable.setFillsViewportHeight(true);
		
		
		/*
		 * Panel Articlelist
		 */
		JPanel panelArticleList = new JPanel();
		panelArticleList.setBorder(new TitledBorder(null, "Artikelliste", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelArticleList.setBounds(0, 0, 589, 275);
		panelArticleList.setLayout(new BorderLayout(0, 0));
		panelArticleList.add(new JScrollPane(articleTable));
		
		/*
		 * Edit: Labels
		 */
		JLabel lblName = new JLabel("Name");
		JLabel lblDescription = new JLabel("Beschreibung");
		this.lblArticleStatus = new JLabel("");
		lblName.setBounds(10, 30, 70, 20);
		lblDescription.setBounds(10, 61, 70, 20);
		this.lblArticleStatus.setBounds(90, 145, 250, 14);
		
		// Edit: Name-Textfield
		this.textFieldArticleName = new JTextField();
		this.textFieldArticleName.setBounds(90, 30, 250, 20);
		this.textFieldArticleName.setColumns(10);
		this.textFieldArticleName.addKeyListener(this);
		
		//Edit: Desc-TextArea
		this.textAreaArticleDescription = new JTextArea(5, 30);
		this.textAreaArticleDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.textAreaArticleDescription.setLineWrap(true);
		this.textAreaArticleDescription.setBounds(90, 59, 250, 80);
		this.textAreaArticleDescription.setBorder(BorderFactory.createEtchedBorder());
		
		//Edit: Button-Save 
		this.btnArticleSave = new JButton("Speichern");
		this.btnArticleSave.addActionListener(this);
		this.btnArticleSave.setBounds(490, 136, 89, 23);
		
		//Edit: Button-Cancel
		this.btnArticleCancel = new JButton("Abbrechen");
		this.btnArticleCancel.addActionListener(this);
		this.btnArticleCancel.setBounds(490, 102, 89, 23);
		
		//Traversal-Policy
		Vector<Component> order = new Vector<Component>();
		order.add(this.textFieldArticleName);
		order.add(this.textAreaArticleDescription);
		order.add(this.btnArticleCancel);
		order.add(this.btnArticleSave);
		MyFocusTraversalPolicy focusPolicy = new MyFocusTraversalPolicy(order);
		
		/*
		 * PanelArticleEdit
		 */		
		JPanel panelArticleEdit = new JPanel();
		panelArticleEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Artikel hinzufügen / bearbeiten", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelArticleEdit.setBounds(0, 273, 589, 170);
		panelArticleEdit.setLayout(null);
		panelArticleEdit.add(lblName);
		panelArticleEdit.add(lblDescription);
		panelArticleEdit.add(textFieldArticleName);
		panelArticleEdit.add(this.textAreaArticleDescription);
		panelArticleEdit.add(this.btnArticleSave);
		panelArticleEdit.add(this.btnArticleCancel);		
		panelArticleEdit.add(lblArticleStatus);
		panelArticleEdit.setFocusTraversalPolicy(focusPolicy);
		panelArticleEdit.setFocusCycleRoot(true);
		
		this.add(panelArticleList);
		this.add(panelArticleEdit);
	}

	public void actionPerformed(ActionEvent e) {		
		
		/**
		 * Aktionen für den Button "Artikel speichern"
		 */
		if(e.getSource() == this.btnArticleSave){
			saveButtonPressed();
		}
				
		/**
		 * Aktionen für den Button "Artikel abbrechen"
		 */
		if(e.getSource() == this.btnArticleCancel){
			this.resetModeEditArticle();
		}
	}

	public void resetModeEditArticle() {
		this.articleModeEdit = false;
		this.textFieldArticleName.setText("");
		this.textAreaArticleDescription.setText("");
	}

	public void setModeEditArticle(int pRow) {
		KArticle art = articleTableModel.getArticleByRow(pRow);
		
		this.articleModeEdit = true;
		this.articleEditId = art.getId();
		this.textFieldArticleName.setText(art.getName());
		this.textAreaArticleDescription.setText(art.getDescription());
	}

	private void saveButtonPressed(){
		if(this.articleModeEdit){
			int re = articleController.editArticle(this.articleEditId, this.textFieldArticleName.getText(), this.textAreaArticleDescription.getText());
			
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
			int re = articleController.createArticle(this.textFieldArticleName.getText(), this.textAreaArticleDescription.getText());
			
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
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) saveButtonPressed();
		
	}

	public void keyReleased(KeyEvent e) {
		// Nothign to implement
		
	}

	public void keyTyped(KeyEvent e) {
		// Nothing to implement
		
	}

}
