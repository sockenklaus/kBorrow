package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import de.katho.kBorrow.Settings;
import de.katho.kBorrow.Util;
import de.katho.kBorrow.controller.NewLendingController;
import de.katho.kBorrow.converter.LenderNameConverter;
import de.katho.kBorrow.converter.LenderStudentnumberConverter;
import de.katho.kBorrow.converter.LenderSurnameConverter;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.listener.NewLendingTableButton;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.UserListModel;

import java.awt.event.FocusEvent;

public class NewLendingPanel extends JPanel implements ActionListener, FocusListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7346953418572781322L;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfStudentNumber;
	private JLabel lblStartDate;
	private JLabel lblArticleName;
	private int articleId;
	private JButton btnCancel;
	private JButton btnSave;
	private NewLendingController newLendingController;
	private JComboBox<String> cbUserName;
	private JXDatePicker dpEstEndDate;
	private JLabel lblStatus;
	
	private KArticleModel kArticleModel;
	private KUserModel kUserModel;
	private KLenderModel kLenderModel;
	
	private UserListModel userListModel;
	private FreeArticleTableModel freeArticleTableModel;
	
	/**
	 * Create the panel.
	 * @param dbCon 
	 * @throws IOException 
	 */
	public NewLendingPanel(final DbConnector dbCon, HashMap<String, KDataModel> models, final Settings pSettings) throws IOException {
		setLayout(null);
		articleId = -1;
		
		kArticleModel = (KArticleModel)models.get("karticlemodel");
		kUserModel = (KUserModel)models.get("kusermodel");
		kLenderModel = (KLenderModel)models.get("klendermodel");
		
		userListModel = new UserListModel(kUserModel);
		freeArticleTableModel = new FreeArticleTableModel(kArticleModel);
		
		// FreeArticleTable
		newLendingController = new NewLendingController(dbCon, models, pSettings);
		
		JTable freeArticleTable = new JTable(freeArticleTableModel);
		freeArticleTable.setRowHeight(30);
		
		NewLendingTableButton newLendingTableButton = new NewLendingTableButton("Artikel ausleihen", freeArticleTable, this);
		freeArticleTable.getColumnModel().getColumn(3).setCellEditor(newLendingTableButton);
		freeArticleTable.getColumnModel().getColumn(3).setCellRenderer(newLendingTableButton);
		freeArticleTable.getColumnModel().getColumn(3).setMinWidth(30);
		freeArticleTable.getColumnModel().getColumn(3).setMaxWidth(30);
		freeArticleTable.getColumnModel().getColumn(3).setPreferredWidth(30);
		freeArticleTable.getColumnModel().getColumn(0).setMinWidth(30);
		freeArticleTable.getColumnModel().getColumn(0).setMaxWidth(30);
		freeArticleTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		freeArticleTable.setFillsViewportHeight(true);
		
		
		// Panel: FreeArticleList
		JPanel panelFreeArticleList = new JPanel();
		panelFreeArticleList.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Verf\u00FCgbare Artikel", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelFreeArticleList.setBounds(0, 0, 589, 253);
		panelFreeArticleList.setLayout(new BorderLayout(0, 0));
		panelFreeArticleList.add(new JScrollPane(freeArticleTable));
		
		
		// Datepicker
		dpEstEndDate = new JXDatePicker(new Date());
		dpEstEndDate.setBounds(110, 60, 200, 20);
			
				
		// Userlist Combobox
		cbUserName = new JComboBox<String>(userListModel);
		cbUserName.setBounds(430, 20, 130, 20);
		
		// Separator
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 90, 569, 2);
		
		// Lender Labels
		lblArticleName = new JLabel("");
		JLabel lblArtikelname = new JLabel("Artikelname:");
		JLabel lblLenderName = new JLabel("Name:");
		JLabel lblLenderSurname = new JLabel("Nachname:");
		JLabel lblLenderStudentnumber = new JLabel("Matrikelnummer:");
		JLabel lblAusgeliehenAm = new JLabel("Ausgeliehen am:");
		JLabel lblNewLabel = new JLabel("Vor. Rückgabe:");
		JLabel lblBenutzer = new JLabel("Benutzer:");
		lblStartDate = new JLabel("");
		lblStatus = new JLabel("");
		
		lblArticleName.setBounds(110, 20, 205, 20);
		lblArtikelname.setBounds(10, 20, 90, 20);
		lblAusgeliehenAm.setBounds(10, 40, 90, 20);
		lblLenderName.setBounds(10, 100, 90, 20);
		lblStatus.setBounds(210, 100, 400, 20);
		lblLenderSurname.setBounds(10, 130, 90, 20);
		lblLenderStudentnumber.setBounds(10, 160, 90, 20);
		lblNewLabel.setBounds(10, 60, 90, 20);
		lblBenutzer.setBounds(350, 20, 70, 20);
		lblStartDate.setBounds(110, 40, 205, 20);
		
		// Lender Textfields
		tfName = new JTextField();
		tfSurname = new JTextField();
		tfStudentNumber = new JTextField();
		tfName.addFocusListener(this);
		tfSurname.addFocusListener(this);
		tfStudentNumber.addFocusListener(this);
		tfName.setColumns(10);
		tfSurname.setColumns(10);
		tfStudentNumber.setColumns(10);
		tfName.setBounds(110, 100, 90, 20);
		tfSurname.setBounds(110, 130, 90, 20);
		tfStudentNumber.setBounds(110, 160, 90, 20);
		tfName.addKeyListener(this);
		tfSurname.addKeyListener(this);
		tfStudentNumber.addKeyListener(this);
		AutoCompleteDecorator.decorate(tfName, kLenderModel.getData(), false, new LenderNameConverter() );
		AutoCompleteDecorator.decorate(tfSurname, kLenderModel.getData(), false, new LenderSurnameConverter());
		AutoCompleteDecorator.decorate(tfStudentNumber, kLenderModel.getData(), false, new LenderStudentnumberConverter());
		
		// Buttons
		btnCancel = new JButton("Abbrechen");
		btnSave = new JButton("Speichern");
		btnCancel.setBounds(471, 130, 89, 23);
		btnSave.setBounds(471, 160, 89, 23);
		btnCancel.addActionListener(this);
		btnSave.addActionListener(this);
		
		// Tab Traversal Policy
		Vector<Component> order = new Vector<Component>();
		order.add(tfName);
		order.add(tfSurname);
		order.add(tfStudentNumber);
		order.add(btnCancel);
		order.add(btnSave);
		MyFocusTraversalPolicy focusPolicy = new MyFocusTraversalPolicy(order);
		
		// New Lending Pane
		JPanel panelNewLending = new JPanel();
		panelNewLending.setBorder(new TitledBorder(null, "Neue Ausleihe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNewLending.setBounds(0, 252, 589, 205);
		panelNewLending.setLayout(null);
		
		panelNewLending.add(lblArtikelname);
		panelNewLending.add(lblLenderStudentnumber);
		panelNewLending.add(lblLenderSurname);
		panelNewLending.add(lblLenderName);
		panelNewLending.add(lblStatus);
		panelNewLending.add(separator);
		panelNewLending.add(btnSave);
		panelNewLending.add(lblAusgeliehenAm);
		panelNewLending.add(btnCancel);
		panelNewLending.add(tfName);
		panelNewLending.add(tfSurname);
		panelNewLending.add(tfStudentNumber);
		panelNewLending.add(lblNewLabel);
		panelNewLending.add(lblBenutzer);
		panelNewLending.add(lblStartDate);
		panelNewLending.add(dpEstEndDate);
		panelNewLending.add(cbUserName);
		panelNewLending.add(lblArticleName);
		panelNewLending.setFocusTraversalPolicy(focusPolicy);
		panelNewLending.setFocusCycleRoot(true);
		
		// this
		add(panelFreeArticleList);
		add(panelNewLending);
	}

	private void resetForm(){
		lblStartDate.setText("");
		lblArticleName.setText("");
		articleId = -1;
		tfName.setText("");
		tfSurname.setText("");
		tfStudentNumber.setText("");
	}
	
	private void saveButtonPressed() throws Exception {
		String pLName = tfName.getText();
		String pLSurname = tfSurname.getText();
		String startDate = lblStartDate.getText();
		Date estEndDate = dpEstEndDate.getDate();
		String pLSN = tfStudentNumber.getText();
		String pUsername = userListModel.getSelectedItem();
		
		int r = newLendingController.newLending(articleId, pLName, pLSurname, pLSN, startDate, estEndDate, pUsername);
			
		switch (r) {
			case 0:
				lblStatus.setText("Art-ID "+articleId+" erfolgreich ausgeliehen.");
				resetForm();
				break;
			case 1:
				lblStatus.setText("Fehler bei Ausleihe, SQL-Fehler.");
				break;
			case 2:
				lblStatus.setText("Notwendige Daten sind leer (Art-ID, Start-Date, Est. End-Date)");
				break;
			case 3:
				lblStatus.setText("Das Rückgabedatum ist früher oder gleich dem Ausleihdatum");
				break;
			case 4:
				lblStatus.setText("Die gegebene Kombination aus Lender-Name, -Surname und -Studentnumber" +
  						"existiert mehrmals in der Datenbank. Das darf nicht sein und wirft daher einen Fehler!");
				break;
			case 5:
				lblStatus.setText("Matrikelnummer muss eine Zahl sein!");
				break;
		}
	}

	public void setModeNewLending(int id) {
		KArticle art = kArticleModel.getElement(id);		
		
		lblStartDate.setText(Util.getCurrentDate());
		lblArticleName.setText(art.getName());
		articleId = art.getId();
		
	}

	public void actionPerformed(ActionEvent pEvent) {
		if(pEvent.getSource() == btnCancel){
			resetForm();
		}
		
		if(pEvent.getSource() == btnSave){
			try {
				saveButtonPressed();
			} catch (Exception e) {
				Util.showError(e);
			}
		}
		
	}

	@Override
	public void focusGained(FocusEvent pEvent) {
		// TODO Auto-generated method stub
	}

	public void focusLost(FocusEvent pEvent) {
		ArrayList<KLender> result = kLenderModel.getLenders(tfName.getText(), tfSurname.getText(), tfStudentNumber.getText());
		
		if(result.size() == 1){
			tfName.setText(result.get(0).getName());
			tfSurname.setText(result.get(0).getSurname());
			tfStudentNumber.setText(String.valueOf(result.get(0).getStudentnumber()));
		}
	}

	public void keyPressed(KeyEvent pKeyPress) {
		if(pKeyPress.getKeyCode() == KeyEvent.VK_ENTER)
			try {
				saveButtonPressed();
			} catch (Exception e) {
				Util.showError(e);
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
