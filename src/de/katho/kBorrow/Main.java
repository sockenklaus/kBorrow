package de.katho.kBorrow;

import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UnsupportedLookAndFeelException;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.db.SqlConnector;
import de.katho.kBorrow.db.SqliteConnector;
import de.katho.kBorrow.gui.MainWindow;

public class Main {
	private DbConnector dbCon;
	private Settings set;
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){		
		/*
		 * Create the apps main window.
		 */
		this.set = new Settings();

		if(set.getProperty("dBType").equals("sqlite")){
			try {
				this.dbCon = new SqliteConnector(set.getProperty("sqlitePath"));
			} catch (ClassNotFoundException | SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
			
		}
		else if(set.getProperty("dBType").equals("mysql")) {
			this.dbCon = new SqlConnector();
		}
		try {
			MainWindow window = new MainWindow(this);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow((Main)this);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	public boolean userSave(String pName, String pSurname) {
		return this.dbCon.createUser(pName, pSurname);
	}
}
