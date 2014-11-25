package de.katho.kBorrow.gui;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JPanel;

import de.katho.kBorrow.controller.ManageLendingsController;
import de.katho.kBorrow.db.DbConnector;



import de.katho.kBorrow.listener.LendingReturnTableButton;
import de.katho.kBorrow.models.LendingTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.io.IOException;


public class ManageLendingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 925691137664223491L;

	private LendingTableModel lendingTableModel;
	private ManageLendingsController manageLendingsController;
	
	public ManageLendingsPanel(DbConnector dbCon, HashMap<String, Object> models) throws IOException {
		
		lendingTableModel = (LendingTableModel)models.get("lendingtablemodel");
		manageLendingsController = new ManageLendingsController(dbCon, models);
		
		// Lending-Table
		JTable lendingTable = new JTable(lendingTableModel);
		lendingTable.setFillsViewportHeight(true);
		lendingTable.setRowHeight(30);
		
		LendingReturnTableButton lrtb = new LendingReturnTableButton("Ausleihe beenden", lendingTable, manageLendingsController);
		
		for(int i = 5; i <= 6; i++){
			lendingTable.getColumnModel().getColumn(i).setCellEditor(i == 5 ? null : lrtb);
			lendingTable.getColumnModel().getColumn(i).setCellRenderer(i == 5 ? null : lrtb);
			
			lendingTable.getColumnModel().getColumn(i).setMinWidth(30);
			lendingTable.getColumnModel().getColumn(i).setMaxWidth(30);
			lendingTable.getColumnModel().getColumn(i).setPreferredWidth(30);
		}
		lendingTable.getColumnModel().getColumn(0).setMinWidth(30);
		lendingTable.getColumnModel().getColumn(0).setMaxWidth(30);
		lendingTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Aktive Ausleihen", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		add(new JScrollPane(lendingTable));
	}

}
