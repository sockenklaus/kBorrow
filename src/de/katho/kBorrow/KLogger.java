package de.katho.kBorrow;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Kümmert sich um das Logging von kBorrow.
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
	 * Gibt eine kLogger-Instanz zurück.
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
	 * @param e			Exception, um Stacktrace in das Log schreiben zu können.
	 */
	public static void log(Level pLevel, String pMsg, Exception e){
		getLogger().log(pLevel, pMsg, e);
		System.out.println(pMsg);
	}
}
