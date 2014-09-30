package de.katho.kBorrow;

import java.awt.EventQueue;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.db.SqlConnector;
import de.katho.kBorrow.db.SqliteConnector;
import de.katho.kBorrow.gui.MainWindow;

public class Main {
	private DbConnector dbCon;
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){		
		/*
		 * Create the apps main window.
		 */
		Settings set = new Settings();

		if(set.getProperty("dBType").equals("sqlite")){
			this.dbCon = new SqliteConnector(set.getProperty("sqlitePath"));
		}
		else if(set.getProperty("dBType").equals("mysql")) {
			this.dbCon = new SqlConnector();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
