package de.katho.kBorrow.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.io.IOException;

import de.katho.kBorrow.db.DbConnector;


public class MainWindow {

	private DbConnector dbCon;

	
	private JFrame frame;
	private JTabbedPane tabbedPane;

	

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public MainWindow(DbConnector pDbCon) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		this.dbCon = pDbCon;
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
		this.frame = new JFrame();
		this.frame.setResizable(false);
		this.frame.setBounds(100, 100, 600, 500);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		this.frame.getContentPane().add(this.tabbedPane, BorderLayout.CENTER);
		
		this.tabbedPane.addTab("Neue Ausleihe", new NewLendingTab(this.dbCon));
		this.tabbedPane.addTab("Ausleihen verwalten", new ManageLendingTab(this.dbCon));
		this.tabbedPane.addTab("Artikel verwalten", new ArticleTab(this.dbCon));
		this.tabbedPane.addTab("Benutzer verwalten", new UserTab(this.dbCon));
	}
}
