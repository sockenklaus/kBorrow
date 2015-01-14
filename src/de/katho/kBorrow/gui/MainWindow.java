package de.katho.kBorrow.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.io.File;
import java.util.HashMap;

import de.katho.kBorrow.Settings;
import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.db.SqlConnector;
import de.katho.kBorrow.db.SqliteConnector;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class MainWindow {

	private DbConnector dbCon;

	
	private JFrame frame;
	private JTabbedPane tabbedPane;


	private Settings set;
	private HashMap<String, KDataModel> models;

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MainWindow() {
		// Delete all files in tmp-dir
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				File dir = new File(set.getSettingsDir()+"/tmp/");
				if(dir.isDirectory()){
					for(File file : dir.listFiles()) file.delete();
				}
			}
		});
		
		try {
			frame = new JFrame();
			frame.setResizable(false);
			frame.setBounds(100, 100, 600, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Util.setMainWindow(frame);
			set = new Settings();
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			if(set.getProperty("dBType").equals("sqlite")){
				dbCon = new SqliteConnector(set.getProperty("sqlitePath"));			
			}
			else if(set.getProperty("dBType").equals("mysql")) {
				dbCon = new SqlConnector();
			}
			
			models = new HashMap<String, KDataModel>();
			models.put("karticlemodel", new KArticleModel(dbCon));
			models.put("klendermodel", new KLenderModel(dbCon));
			models.put("klendingmodel", new KLendingModel(dbCon));
			models.put("kusermodel", new KUserModel(dbCon));

			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			tabbedPane.addTab("Neue Ausleihe", new NewLendingPanel(this.dbCon, models, set));
			tabbedPane.addTab("Ausleihen verwalten", new ManageLendingsPanel(this.dbCon, models));
			tabbedPane.addTab("Artikel verwalten", new ArticlePanel(this.dbCon, models));
			tabbedPane.addTab("Benutzer verwalten", new UserPanel(this.dbCon, models));
			
		}
		catch(Exception e) {
			Util.showError(e);
			System.exit(1);
		}
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}
