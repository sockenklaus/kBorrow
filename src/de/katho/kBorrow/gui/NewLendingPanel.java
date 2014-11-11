package de.katho.kBorrow.gui;

import javax.swing.JPanel;

import de.katho.kBorrow.Util;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.NewLendingTableButton;
import de.katho.kBorrow.models.FreeArticleModel;
import de.katho.kBorrow.models.UserListModel;

import org.jdatepicker.*;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;

public class NewLendingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7346953418572781322L;
	private FreeArticleModel freeArticleModel;
	private JTextField tfName;
	private JTextField tfSurname;
	private JTextField tfStudentNumber;
	private UserListModel userListModel;
	private JLabel lblStartDate;
	private JLabel lblArticleName;
	private int articleId = -1;
	
	/**
	 * Create the panel.
	 * @param dbCon 
	 * @throws IOException 
	 */
	public NewLendingPanel(final DbConnector dbCon, HashMap<String, Object> pModel) throws IOException {
		setLayout(null);
		
		// FreeArticleTable
		freeArticleModel = (FreeArticleModel)pModel.get("freearticlemodel");
		userListModel = (UserListModel)pModel.get("userlistmodel");
		
		JTable freeArticleTable = new JTable(freeArticleModel);
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
		
		JLabel lblNewLabel = new JLabel("Vor. Rückgabe:");
		lblNewLabel.setBounds(10, 60, 90, 20);
		panelNewLending.add(lblNewLabel);
		
		JDatePicker picker = new JDateComponentFactory().createJDatePicker();
		picker.setTextEditable(true);
		JPanel pickerPanel = new JPanel();
		pickerPanel.setBounds(106, 60, 205, 28);
		pickerPanel.add((JComponent)picker);
		panelNewLending.add(pickerPanel);	
		
		
		JLabel lblBenutzer = new JLabel("Benutzer:");
		lblBenutzer.setBounds(350, 20, 70, 20);
		panelNewLending.add(lblBenutzer);
		
		JComboBox<String> comboBox = new JComboBox<String>(userListModel);
		comboBox.setBounds(430, 20, 130, 20);
		panelNewLending.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 90, 569, 2);
		panelNewLending.add(separator);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.setBounds(471, 130, 89, 23);
		panelNewLending.add(btnAbbrechen);
		
		JButton btnSpeichern = new JButton("Speichern");
		btnSpeichern.setBounds(471, 160, 89, 23);
		panelNewLending.add(btnSpeichern);
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		lblNewLabel_1.setBounds(10, 100, 90, 20);
		panelNewLending.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nachname:");
		lblNewLabel_2.setBounds(10, 130, 90, 20);
		panelNewLending.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Matrikelnummer:");
		lblNewLabel_3.setBounds(10, 160, 90, 20);
		panelNewLending.add(lblNewLabel_3);
		
		tfName = new JTextField();
		tfName.setBounds(110, 100, 90, 20);
		panelNewLending.add(tfName);
		tfName.setColumns(10);
		
		tfSurname = new JTextField();
		tfSurname.setBounds(110, 130, 90, 20);
		panelNewLending.add(tfSurname);
		tfSurname.setColumns(10);
		
		tfStudentNumber = new JTextField();
		tfStudentNumber.setBounds(110, 160, 90, 20);
		panelNewLending.add(tfStudentNumber);
		tfStudentNumber.setColumns(10);

	}

	public void setModeNewLending(int pArticleId, String articleName) {
		
		lblStartDate.setText(Util.getCurrentDate());
		lblArticleName.setText(articleName);
		articleId = pArticleId;
		
	}
}
