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
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.listener.ArticleDeleteTableButton;
import de.katho.kBorrow.listener.ArticleEditTableButton;
import de.katho.kBorrow.listener.ArticleInspectTableButton;
import de.katho.kBorrow.models.ArticleTableModel;

/**
 * Erzeugt das Artikelpanel und implementiert damit verbundene Action- und KeyListener.
 */
public class ArticlePanel extends JPanel implements ActionListener, KeyListener {

	/** Serial Version UID */
	private static final long serialVersionUID = -8511924597640457608L;
	
	/** Textarea, die die Artikelbeschreibung enthält. */
	private JTextArea textAreaArticleDescription;
	
	/** Textfeld, das den Artikelnamen enthält. */
	private JTextField textFieldArticleName;
	
	/** Speicherbutton */
	private JButton btnArticleSave;
	
	/** Abbrechenbutton */
	private JButton btnArticleCancel;
	
	/** Label, in dem Statusmeldungen angezeigt werden */
	private JLabel lblArticleStatus;
	
	/** True, wenn ein Artikel bearbeitet wird. Ansonsten false. */
	private boolean articleModeEdit;
	
	/** Enthält die Artikel-ID, wenn ein Arikel bearbeitet wird. */
	private int articleEditId;
	
	/** Referenz auf {@link ArticleController} */
	private ArticleController articleController;
	
	/** Referenz auf {@link KArticleModel} */
	private KArticleModel articleModel;

	/**
	 * Gestaltet und erzeugt das Panel.
	 * 
	 * @param	dbCon			Referenz auf die Datenbankverbindung.
	 * @param	models			HashMap mit KDataModels.
	 * @throws 	IOException		Wirft alle IOExceptions, die sonst nirgends bearbeitet wurden (???).
	 */
	public ArticlePanel(final DbConnector dbCon, HashMap<String, KDataModel> models) throws IOException {	
		super();
		setLayout(null);
		articleModel = (KArticleModel)models.get("karticlemodel");
		articleController = new ArticleController(dbCon, models);
		
		/*
		 * Tabelle und drumherum
		 */
		
		JTable articleTable = new JTable(new ArticleTableModel(articleModel));
		articleTable.setRowHeight(30);
		ArticleDeleteTableButton articleDeleteTableButton = new ArticleDeleteTableButton("Löschen", articleTable, this, articleController);
		ArticleEditTableButton articleEditTableButton = new ArticleEditTableButton("Bearbeiten", articleTable, this);
		ArticleInspectTableButton articleInspectTableButton = new ArticleInspectTableButton("Details", articleTable, models);
		
		for (int i = 3; i <= 5; i++){
			articleTable.getColumnModel().getColumn(i).setCellEditor(i == 3 ? articleInspectTableButton : i == 4 ? articleEditTableButton : articleDeleteTableButton);
			articleTable.getColumnModel().getColumn(i).setCellRenderer(i == 3 ? articleInspectTableButton : i == 4 ? articleEditTableButton : articleDeleteTableButton);
			
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
		lblArticleStatus = new JLabel("");
		lblName.setBounds(10, 30, 70, 20);
		lblDescription.setBounds(10, 61, 70, 20);
		lblArticleStatus.setBounds(90, 145, 390, 14);
		
		// Edit: Name-Textfield
		textFieldArticleName = new JTextField();
		textFieldArticleName.setBounds(90, 30, 250, 20);
		textFieldArticleName.setColumns(10);
		textFieldArticleName.addKeyListener(this);
		
		//Edit: Desc-TextArea
		textAreaArticleDescription = new JTextArea(5, 30);
		textAreaArticleDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textAreaArticleDescription.setLineWrap(true);
		textAreaArticleDescription.setBounds(90, 59, 250, 80);
		textAreaArticleDescription.setBorder(BorderFactory.createEtchedBorder());
		
		//Edit: Button-Save 
		btnArticleSave = new JButton("Speichern");
		btnArticleSave.addActionListener(this);
		btnArticleSave.setBounds(490, 136, 89, 23);
		
		//Edit: Button-Cancel
		btnArticleCancel = new JButton("Abbrechen");
		btnArticleCancel.addActionListener(this);
		btnArticleCancel.setBounds(490, 102, 89, 23);
		
		//Traversal-Policy
		Vector<Component> order = new Vector<Component>();
		order.add(textFieldArticleName);
		order.add(textAreaArticleDescription);
		order.add(btnArticleCancel);
		order.add(btnArticleSave);
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
		panelArticleEdit.add(textAreaArticleDescription);
		panelArticleEdit.add(btnArticleSave);
		panelArticleEdit.add(btnArticleCancel);		
		panelArticleEdit.add(lblArticleStatus);
		panelArticleEdit.setFocusTraversalPolicy(focusPolicy);
		panelArticleEdit.setFocusCycleRoot(true);
		
		add(panelArticleList);
		add(panelArticleEdit);
	}

	/**
	 * ActionListener für den Button-Press.
	 * 
	 * @param	e	ActionEvent, von dem das Event erzeugt wurde.
	 */
	public void actionPerformed(ActionEvent e) {		
		
		/*
		 * Aktionen für den Button "Artikel speichern"
		 */
		if(e.getSource() == btnArticleSave){
			saveButtonPressed();
		}
				
		/*
		 * Aktionen für den Button "Artikel abbrechen"
		 */
		if(e.getSource() == btnArticleCancel){
			resetModeEditArticle();
		}
	}

	/**
	 * Setzt die Instanzvariable articleModeEdit zurück auf false und leer Textfeld und -area.	 * 
	 */
	public void resetModeEditArticle() {
		articleModeEdit = false;
		textFieldArticleName.setText("");
		textAreaArticleDescription.setText("");
	}

	/**
	 * Setzt die Instanzvariable articleModeEdit auf true und füllt Textfeld und -area entsprechend der übergebenen ID aus.
	 * 
	 * @param	pId		Artikel-ID, gemäß der Textfeld und -area ausgefüllt werden sollen.
	 */
	public void setModeEditArticle(int pId) {
		KArticle art = articleModel.getElement(pId);
		
		articleModeEdit = true;
		articleEditId = art.getId();
		textFieldArticleName.setText(art.getName());
		textAreaArticleDescription.setText(art.getDescription());
	}

	/**
	 * Führt die Aktionen aus, die beim Speichern eines Artikels geschehen.
	 * 
	 * <p>
	 * Je nachdem, ob {@link #articleModeEdit} true oder false ist, werden
	 * {@link ArticleController#editArticle} oder {@link ArticleController#createArticle}
	 * aufgerufen.
	 * </p>
	 * <p>
	 * Falls editArticle aufgerufen wird, werden je nach Rückgabecode folgende Statusmeldungen angezeigt:
	 * </p>
	 * <ul>
	 * <li>0: Artikel-ID "foo" erfolgreich bearbeitet.</li>
	 * <li>1: SQL-Fehler: Artikel konnte nicht bearbeitet werden.</li>
	 * <li>2: Artikelname muss ausgefüllt sein.</li>
	 * </ul>
	 * <p>
	 * Falls createArticle aufgerufen wird, werden je nach Rückgabecode folgende Statusmeldungen angezeigt:
	 * </p>
	 * <ul>
	 * <li>0: Artikel "foo" erfolgreich hinzugefügt.</li>
	 * <li>1: SQL-Fehler. Artikel konnte nicht erstellt werden.</li>
	 * <li>2: Es muss ein Artikelname vergeben werden.</li>
	 * </ul>
	 */
	private void saveButtonPressed(){
		if(articleModeEdit){
			int re = articleController.editArticle(articleEditId, textFieldArticleName.getText(), textAreaArticleDescription.getText());
			
			switch(re){
			case 0:
				lblArticleStatus.setText("Artikel-ID \""+articleEditId+"\" erfolgreich bearbeitet.");
				textFieldArticleName.setText("");
				textAreaArticleDescription.setText("");
				break;
				
			case 1:
				lblArticleStatus.setText("SQL-Fehler. Artikel konnte nicht bearbeitet werden.");
				textFieldArticleName.setText("");
				textAreaArticleDescription.setText("");
				break;
				
			case 2:
				lblArticleStatus.setText("Artikelname muss ausgefüllt sein.");
				break;
				
			}
			
			articleModeEdit = false;
			articleEditId = -1;
		}
		
		else {
			int re = articleController.createArticle(textFieldArticleName.getText(), textAreaArticleDescription.getText());
			
			switch(re){
			case 0:
				lblArticleStatus.setText("Artikel \""+textFieldArticleName.getText()+"\" erfolgreich hinzugefügt.");
				textFieldArticleName.setText("");
				textAreaArticleDescription.setText("");
				break;
				
			case 1:
				lblArticleStatus.setText("SQL-Fehler. Artikel konnte nicht erstellt werden.");
				textFieldArticleName.setText("");
				textAreaArticleDescription.setText("");
				break;
			
			case 2:
				lblArticleStatus.setText("Es muss ein Artikelname vergeben werden");
				break;
			}
		}		
	}
	
	/**
	 * KeyListener für den Druck einer Taste
	 * 
	 * <p>
	 * Ruft {@link #saveButtonPressed} auf, wenn die gedrückte Taste die Entertaste war.
	 * </p>
	 * 
	 * @param	e	KeyEvent, von dem das Event erzeugt wurde.
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) saveButtonPressed();
		
	}

	/**
	 * KeyListener für das Loslassen einer Taste.
	 * 
	 * <p>Nicht implementiert</p>
	 * 
	 * @param	e	KeyEvent, von dem das Event erzeugt wird. 
	 */
	public void keyReleased(KeyEvent e) {
		return;
	}

	/**
	 * KeyListener für das Tippen (Drücken und Loslassen) einer Taste.
	 * 
	 * <p>Nicht implementiert</p>
	 * 
	 * @param	e	KeyEvent, von dem das Event erzeugt wird.
	 */
	public void keyTyped(KeyEvent e) {
		return;	
	}
	
	/**
	 * Setzt das {@link #lblArticleStatus} je nach übergebenem Statuscode.
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: Artikel kann nicht gelöscht werden.</li>
	 * <li>2: Artikel kann nicht gelöscht werden, während er verliehen ist.</li>
	 * </ul>
	 *  
	 * @param	pCode	Statuscode als Int.
	 */
	public void setDeleteStatusLabel(int pCode){
		switch(pCode){
			case 0:
				lblArticleStatus.setText("Artikel erfolgreich gelöscht.");
				break;
			case 1:
				lblArticleStatus.setText("Artikel kann nicht gelöscht werden.");
				break;
			case 2:
				lblArticleStatus.setText("Artikel kann nicht gelöscht werden, während er verliehen ist.");
				break;
		}
	}

}
