/*
 * Copyright (C) 2015  Pascal König
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.katho.kBorrow.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

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

/**
 * Erzeugt die Anwendung und alle Komponenten des Hauptfensters.
 */
public class MainWindow {
	
	/**
	 * Erzeugt die Anwendung und alle Komponenten des Hauptfensters.
	 */
	public MainWindow() {		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			DbConnector dbCon;
			JFrame frame = new JFrame();
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			HashMap<String, KDataModel> models = new HashMap<String, KDataModel>();
			final Settings set = new Settings();
			
			// Delete all files in tmp-dir
			Runtime.getRuntime().addShutdownHook(new Thread(){
				public void run(){
					File dir = new File(set.getSettingsDir()+"/tmp/");
					if(dir.isDirectory()){
						for(File file : dir.listFiles()) file.delete();
					}
				}
			});
			
			
			frame.setResizable(false);
			frame.setBounds(100, 100, 600, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Util.setMainWindow(frame);
			
			
			if(set.getProperty("dBType").equals("sqlite")){
				dbCon = new SqliteConnector(set.getProperty("sqlitePath"));			
			}
			else if(set.getProperty("dBType").equals("mysql")) {
				dbCon = new SqlConnector();
			}
			else {
				throw new Exception("Keine Datenbankverbindung. DbType in Settings.cfg nicht oder falsch hinterlegt.");
			}
			
			models.put("karticlemodel", new KArticleModel(dbCon));
			models.put("klendermodel", new KLenderModel(dbCon));
			models.put("klendingmodel", new KLendingModel(dbCon));
			models.put("kusermodel", new KUserModel(dbCon));

			frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			tabbedPane.addTab("Neue Ausleihe", new NewLendingPanel(dbCon, models, set));
			tabbedPane.addTab("Ausleihen verwalten", new ManageLendingsPanel(dbCon, models));
			tabbedPane.addTab("Artikel verwalten", new ArticlePanel(dbCon, models));
			tabbedPane.addTab("Benutzer verwalten", new UserPanel(dbCon, models));
			
			frame.setVisible(true);
		}
		catch(Exception e) {
			Util.showError(e);
			System.exit(1);
		}
	}
	
	/**
	 * Main-Methode.
	 * 
	 * @param	args	Liste der Kommandozeilenparameter.
	 */
	public static void main(String[] args) {
		new MainWindow();
	}
}
