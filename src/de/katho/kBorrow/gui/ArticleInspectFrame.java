package de.katho.kBorrow.gui;

import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.models.ArticleInspectTableModel;
import de.katho.kBorrow.models.ArticleTableModel;

public class ArticleInspectFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8993341404926674307L;
	private JPanel contentPane;
	private JTable table;
	private ArticleInspectTableModel artInsModel;
	private ArticleTableModel articleModel;
	private KArticle article;

	/**
	 * Create the frame.
	 */
	public ArticleInspectFrame(int pRow, final DbConnector dbCon, HashMap<String, KDataModel> models) {
		articleModel = (ArticleTableModel)models.get("articletablemodel");
		article = articleModel.getArticleByRow(pRow);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(150, 150, 660, 541);
		setTitle("Details: "+article.getName());
		
		// ContentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Panel Info
		JPanel panelInfo = new JPanel();
		panelInfo.setBounds(0, 0, 644, 134);
		panelInfo.setBorder(BorderFactory.createTitledBorder("Artikeldetails"));
		
		panelInfo.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Artikelname:");
		JLabel lblArticleName = new JLabel(article.getName());
		JLabel lblNewLabel_2 = new JLabel ("Artikelbeschreibung:");
		JTextArea taArticleDesc = new JTextArea(article.getDescription());
		taArticleDesc.setRows(5);
		taArticleDesc.setBorder(BorderFactory.createEtchedBorder());
				
		lblNewLabel_1.setBounds(10, 21, 100, 20);
		lblNewLabel_2.setBounds(10, 47, 100, 20);
		lblArticleName.setBounds(120, 21, 250, 20);
		taArticleDesc.setBounds(120, 45, 250, 78);
		taArticleDesc.setEditable(false);
			
		panelInfo.add(lblNewLabel_1);
		panelInfo.add(lblNewLabel_2);
		panelInfo.add(lblArticleName);
		panelInfo.add(taArticleDesc);
		
		// Table
		artInsModel = new ArticleInspectTableModel(pRow, dbCon, models);
		table = new JTable(artInsModel);
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 131, 644, 371);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Alle Ausleihen des Artikels \""+article.getName()+"\""));
		
		// Add components to ContentPane
		contentPane.add(panelInfo);
		contentPane.add(scrollPane);
		
		setVisible(true);
	}

}
