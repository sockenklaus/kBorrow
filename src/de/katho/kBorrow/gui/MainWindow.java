package de.katho.kBorrow.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import de.katho.kBorrow.Settings;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.db.SqlConnector;
import de.katho.kBorrow.db.SqliteConnector;
import de.katho.kBorrow.models.ArticleTableModel;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.LenderModel;
import de.katho.kBorrow.models.UserTableModel;
import de.katho.kBorrow.models.UserListModel;


public class MainWindow {

	private DbConnector dbCon;

	
	private JFrame frame;
	private JTabbedPane tabbedPane;


	private Settings set;
	private HashMap<String, Object> models;

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public MainWindow() {
		set = new Settings();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			if(set.getProperty("dBType").equals("sqlite")){
				dbCon = new SqliteConnector(set.getProperty("sqlitePath"));			
			}
			else if(set.getProperty("dBType").equals("mysql")) {
				dbCon = new SqlConnector();
			}
			
			models = new HashMap<String, Object>();
			models.put("usermodel", new UserTableModel(dbCon));
			models.put("userlistmodel", new UserListModel(dbCon));
			models.put("articlemodel", new ArticleTableModel(dbCon));
			models.put("freearticlemodel", new FreeArticleTableModel(dbCon));
			models.put("lendermodel", new LenderModel(dbCon));
			
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			frame.getContentPane().add(this.tabbedPane, BorderLayout.CENTER);
			tabbedPane.addTab("Neue Ausleihe", new NewLendingPanel(this.dbCon, models));
			tabbedPane.addTab("Ausleihen verwalten", new ManageLendingsPanel(this.dbCon, models));
			tabbedPane.addTab("Artikel verwalten", new ArticlePanel(this.dbCon, models));
			tabbedPane.addTab("Benutzer verwalten", new UserPanel(this.dbCon, models));
			
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | IOException | UnsupportedLookAndFeelException | SQLException e) {
			ErrorInfo info = new ErrorInfo("Exception", e.getMessage(), null, null, e, null, null);
			JXErrorPane.showDialog(frame, info);
			System.exit(1);
		}
		
		this.frame.setVisible(true);
	}
	
	public static void main(String[] args){
		new MainWindow();
	}
}
