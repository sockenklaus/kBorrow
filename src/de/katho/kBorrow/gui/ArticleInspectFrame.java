package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.db.DbConnector;
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
	public ArticleInspectFrame(int pRow, final DbConnector dbCon, HashMap<String, Object> pModels) {
		articleModel = (ArticleTableModel)pModels.get("articletablemodel");
		article = articleModel.getArticleByRow(pRow);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 541);
		setTitle("Details: "+article.getName());
		
		// Table
		artInsModel = new ArticleInspectTableModel(pRow, dbCon, pModels);
		table = new JTable(artInsModel);
		table.setFillsViewportHeight(true);
		table.setRowHeight(30);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		JScrollPane scrollPane = new JScrollPane(table);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Artikelname:");
		JLabel lblArticleName = new JLabel(article.getName());
		JLabel lblNewLabel_2 = new JLabel ("Artikelbeschreibung:");
		JLabel lblArticleDesc = new JLabel(article.getDescription());
		
		lblNewLabel_1.setBounds(0, 0, 100, 30);
		lblNewLabel_2.setBounds(0, 20, 100, 30);
		lblArticleName.setBounds(120, 0, 100, 30);
		lblArticleDesc.setBounds(120, 20, 200, 60);
		
		
		panel.add(lblNewLabel_1);
		panel.add(lblNewLabel_2);
		panel.add(lblArticleName);
		panel.add(lblArticleDesc);
		
		setContentPane(contentPane);
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		setVisible(true);
	}

}
