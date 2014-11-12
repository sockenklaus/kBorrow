package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.IOException;
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

import de.katho.kBorrow.Util;
import de.katho.kBorrow.converter.LenderNameConverter;
import de.katho.kBorrow.converter.LenderStudentnumberConverter;
import de.katho.kBorrow.converter.LenderSurnameConverter;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.NewLendingTableButton;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.LenderModel;
import de.katho.kBorrow.models.UserListModel;

import java.awt.event.FocusEvent;

public class NewLendingPanel extends JPanel implements ActionListener, FocusListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7346953418572781322L;
	private FreeArticleTableModel freeArticleTableModel;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfStudentNumber;
	private UserListModel userListModel;
	private JLabel lblStartDate;
	private JLabel lblArticleName;
	private int articleId;
	private LenderModel lenderModel;
	private JButton btnCancel;
	private JButton btnSave;
	private Object lastFocussedComponent;
	
	/**
	 * Create the panel.
	 * @param dbCon 
	 * @throws IOException 
	 */
	public NewLendingPanel(final DbConnector dbCon, HashMap<String, Object> pModel) throws IOException {
		setLayout(null);
		articleId = -1;
		lastFocussedComponent = null;
		
		// FreeArticleTable
		freeArticleTableModel = (FreeArticleTableModel)pModel.get("freearticlemodel");
		userListModel = (UserListModel)pModel.get("userlistmodel");
		lenderModel = (LenderModel)pModel.get("lendermodel");
		
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
		
		
		// Panel: NewLending
		JPanel panelNewLending = new JPanel();
		panelNewLending.setBorder(new TitledBorder(null, "Neue Ausleihe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNewLending.setBounds(0, 252, 589, 205);
		
		this.add(panelFreeArticleList);
		this.add(panelNewLending);
		panelNewLending.setLayout(null);
		
		JLabel lblArtikelname = new JLabel("Artikelname:");
		lblArtikelname.setBounds(10, 20, 90, 20);
		panelNewLending.add(lblArtikelname);
		
		lblArticleName = new JLabel("");
		lblArticleName.setBounds(110, 20, 205, 20);
		panelNewLending.add(lblArticleName);
		
		JLabel lblAusgeliehenAm = new JLabel("Ausgeliehen am:");
		lblAusgeliehenAm.setBounds(10, 40, 90, 20);
		panelNewLending.add(lblAusgeliehenAm);
		
		lblStartDate = new JLabel("");
		lblStartDate.setBounds(110, 40, 205, 20);
		panelNewLending.add(lblStartDate);
		
		JLabel lblNewLabel = new JLabel("Vor. R�ckgabe:");
		lblNewLabel.setBounds(10, 60, 90, 20);
		panelNewLending.add(lblNewLabel);
		
		JXDatePicker xpicker = new JXDatePicker(new Date());
		xpicker.setBounds(110, 60, 200, 20);
		panelNewLending.add(xpicker);	
				
		
		JLabel lblBenutzer = new JLabel("Benutzer:");
		lblBenutzer.setBounds(350, 20, 70, 20);
		panelNewLending.add(lblBenutzer);
		
		JComboBox<String> comboBox = new JComboBox<String>(userListModel);
		comboBox.setBounds(430, 20, 130, 20);
		panelNewLending.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 90, 569, 2);
		
		
		
		// Lender Labels
		JLabel lblLenderName = new JLabel("Name:");
		JLabel lblLenderSurname = new JLabel("Nachname:");
		JLabel lblLenderStudentnumber = new JLabel("Matrikelnummer:");
		lblLenderName.setBounds(10, 100, 90, 20);
		lblLenderSurname.setBounds(10, 130, 90, 20);
		lblLenderStudentnumber.setBounds(10, 160, 90, 20);
		
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
		AutoCompleteDecorator.decorate(tfName, lenderModel.getList(), false, new LenderNameConverter() );
		AutoCompleteDecorator.decorate(tfSurname, lenderModel.getList(), false, new LenderSurnameConverter());
		AutoCompleteDecorator.decorate(tfStudentNumber, lenderModel.getList(), false, new LenderStudentnumberConverter());
		
		// Buttons
		btnCancel = new JButton("Abbrechen");
		btnSave = new JButton("Speichern");
		btnCancel.setBounds(471, 130, 89, 23);
		btnSave.setBounds(471, 160, 89, 23);
		btnCancel.addActionListener(this);
		
		// Tab Traversal Policy
		Vector<Component> order = new Vector<Component>();
		order.add(tfName);
		order.add(tfSurname);
		order.add(tfStudentNumber);
		order.add(btnCancel);
		order.add(btnSave);
		MyFocusTraversalPolicy focusPolicy = new MyFocusTraversalPolicy(order);
		
		// New Lending Pane
		panelNewLending.add(lblLenderStudentnumber);
		panelNewLending.add(lblLenderSurname);
		panelNewLending.add(lblLenderName);
		panelNewLending.add(separator);
		panelNewLending.add(btnSave);
		panelNewLending.add(btnCancel);
		panelNewLending.add(tfName);
		panelNewLending.add(tfSurname);
		panelNewLending.add(tfStudentNumber);
		panelNewLending.setFocusTraversalPolicy(focusPolicy);
		panelNewLending.setFocusCycleRoot(true);
	}

	public void setModeNewLending(int pArticleId, String articleName) {
		
		lblStartDate.setText(Util.getCurrentDate());
		lblArticleName.setText(articleName);
		articleId = pArticleId;
		
	}

	public void actionPerformed(ActionEvent pEvent) {
		if(pEvent.getSource() == btnCancel){
			lblStartDate.setText("");
			lblArticleName.setText("");
			articleId = -1;
			tfName.setText("");
			tfSurname.setText("");
			tfStudentNumber.setText("");
		}
		
	}

	@Override
	public void focusGained(FocusEvent pEvent) {
		if(pEvent.getSource() == tfName) System.out.println("tfName got focus");
		if(pEvent.getSource() == tfSurname) System.out.println("tfSurname got focus");
		if(pEvent.getSource() == tfStudentNumber) System.out.println("tfStudentNumber got focus");
		
		
	}

	public void focusLost(FocusEvent pEvent) {
		KLender result = lenderModel.getLender(tfName.getText(), tfSurname.getText(), tfStudentNumber.getText());
		
		if(result != null){
			tfName.setText(result.getName());
			tfSurname.setText(result.getSurname());
			tfStudentNumber.setText(String.valueOf(result.getStudentnumber()));
		}
	}
}
