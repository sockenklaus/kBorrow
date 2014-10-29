package de.katho.kBorrow.gui;

import javax.swing.JPanel;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.listener.NewLendingTableButton;
import de.katho.kBorrow.models.FreeArticleTableModel;

import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.io.IOException;

public class PanelNewLending extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7346953418572781322L;
	private FreeArticleTableModel freeArticleTableModel;
	
	/**
	 * Create the panel.
	 * @param dbCon 
	 * @throws IOException 
	 */
	public PanelNewLending(final DbConnector dbCon) throws IOException {
		this.setLayout(null);
		
		// FreeArticleTable
		this.freeArticleTableModel = new FreeArticleTableModel(dbCon);
		JTable freeArticleTable = new JTable(freeArticleTableModel);
		freeArticleTable.setRowHeight(30);
		
		NewLendingTableButton newLendingTableButton = new NewLendingTableButton("Artikel ausleihen", freeArticleTable, this);
		freeArticleTable.getColumnModel().getColumn(3).setCellEditor(newLendingTableButton);
		freeArticleTable.getColumnModel().getColumn(3).setCellRenderer(newLendingTableButton);
		freeArticleTable.getColumnModel().getColumn(3).setMinWidth(30);
		freeArticleTable.getColumnModel().getColumn(3).setMaxWidth(30);
		freeArticleTable.getColumnModel().getColumn(3).setPreferredWidth(30);
		
		
		freeArticleTable.setFillsViewportHeight(true);
		
		
		// Panel: FreeArticleList
		JPanel panelFreeArticleList = new JPanel();
		panelFreeArticleList.setBorder(new TitledBorder(null, "Vorhandene Artikel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFreeArticleList.setBounds(0, 0, 589, 253);
		panelFreeArticleList.setLayout(new BorderLayout(0, 0));
		panelFreeArticleList.add(new JScrollPane(freeArticleTable));
		
		
		// Panel: NewLending
		JPanel panelNewLending = new JPanel();
		panelNewLending.setBorder(new TitledBorder(null, "Neue Ausleihe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelNewLending.setBounds(0, 252, 589, 205);
		
		this.add(panelFreeArticleList);
		this.add(panelNewLending);

	}

	public void setModeNewLending(int articleId, String articleName,
			String articleDescription) {
		// TODO Auto-generated method stub
		
	}
}
