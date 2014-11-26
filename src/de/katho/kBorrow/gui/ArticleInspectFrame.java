package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.GridLayout;

public class ArticleInspectFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8993341404926674307L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ArticleInspectFrame(int pArtId, HashMap<String, Object> pModels) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 541);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Artikelname");
		lblNewLabel_1.setBounds(0, 0, 100, 30);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 45, 634, 45);
		panel.add(lblNewLabel);
		table = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		setVisible(true);
	}

}
