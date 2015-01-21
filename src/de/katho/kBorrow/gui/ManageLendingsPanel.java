package de.katho.kBorrow.gui;
import java.util.HashMap;

import javax.swing.JPanel;

import de.katho.kBorrow.controller.ManageLendingsController;

import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.listener.LendingReturnTableButton;
import de.katho.kBorrow.models.LendingTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.io.IOException;

/**
 * Erzeugt das JPanel, auf dem Ausleihen verwaltet werden.
 */
public class ManageLendingsPanel extends JPanel {

	/** Serial Version UID */
	private static final long serialVersionUID = 925691137664223491L;

	/**
	 * Erzeugt das JPanel, auf dem Ausleihen verwaltet werden.
	 * 
	 * @param 	dbCon			Referenz auf die Datenbankverbindung.
	 * @param 	models			HashMap mit KDataModels
	 * @throws	IOException		Wenn es Probleme beim Erzeugen des {@link LendingReturnTableButton} gibt.
	 */
	public ManageLendingsPanel(DbConnector dbCon, HashMap<String, KDataModel> models) throws IOException {
		
		ManageLendingsController manageLendingsController = new ManageLendingsController(dbCon, models);
		
		// Lending-Table
		JTable lendingTable = new JTable(new LendingTableModel(models));
		LendingReturnTableButton lrtb = new LendingReturnTableButton("Ausleihe beenden", lendingTable, manageLendingsController);
		lendingTable.getColumnModel().getColumn(0).setMinWidth(30);
		lendingTable.getColumnModel().getColumn(0).setMaxWidth(30);
		lendingTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		lendingTable.setFillsViewportHeight(true);
		lendingTable.setRowHeight(30);
		
		
		lendingTable.getColumnModel().getColumn(6).setCellEditor(lrtb);
		lendingTable.getColumnModel().getColumn(6).setCellRenderer(lrtb);
			
		lendingTable.getColumnModel().getColumn(6).setMinWidth(30);
		lendingTable.getColumnModel().getColumn(6).setMaxWidth(30);
		lendingTable.getColumnModel().getColumn(6).setPreferredWidth(30);
		
		setBorder(null);
		setLayout(null);
		
		JPanel panelActiveLendings = new JPanel();
		panelActiveLendings.setBorder(new TitledBorder(null, "Aktive Ausleihen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelActiveLendings.setBounds(0, 0, 595, 296);
		add(panelActiveLendings);
		
		
		panelActiveLendings.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane(lendingTable);
		panelActiveLendings.add(scrollPane);
		
		JPanel panelManageLendings = new JPanel();
		panelManageLendings.setBounds(0, 293, 595, 189);
		add(panelManageLendings);
	}

}
