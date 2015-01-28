/*
 * Copyright (C) 2015  Pascal K�nig
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

package de.katho.kBorrow;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * K�mmert sich um das Logging von kBorrow.
 */
public class KLogger {
	
	/** Das Logger-Objekt */
	private static Logger logger;	
	
	/** Das Verzeichnis, in dem das Log angelegt wird */
	private final String fDir = System.getProperty("user.home")+"/kBorrow";
	
	/** Der Dateiname des Logs */
	private final String fName = "kBorrow.log";
	
	/**
	 * Erzeugt eine Instanz des KLoggers
	 * 
	 * @throws IOException wenn das Logfile nicht erstellt werden kann.
	 */
	private KLogger() throws IOException {
		logger = Logger.getLogger(Util.class.getName());
		File dir = new File(fDir);
		File file = new File(fDir+"/"+fName);
		
		if(!dir.isDirectory()) dir.mkdir();
		if(!file.isFile()) file.createNewFile();
		
		Handler fileHandler = new FileHandler(fDir+"/"+fName, true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}
	
	/**
	 * Gibt eine kLogger-Instanz zur�ck.
	 * 
	 * Wenn noch keine Instanz existiert, erzeuge eine neue.
	 * 
	 * @return Instanz des kLoggers.
	 */
	private static Logger getLogger(){
		if(logger == null){
			try{
				new KLogger();
			} catch(IOException e){
				Util.showError(e);
			}
		}
		return logger;
	}
	
	/**
	 * Loggt eine Nachricht in den kLogger und gibt eine Fehlermeldung auf der Konsole aus.
	 * 
	 * @param pLevel	Schwere der Meldung.
	 * @param pMsg		Fehlermeldung
	 * @param e			Exception, um Stacktrace in das Log schreiben zu k�nnen.
	 */
	public static void log(Level pLevel, String pMsg, Exception e){
		getLogger().log(pLevel, pMsg, e);
		System.out.println(pMsg);
	}
}
