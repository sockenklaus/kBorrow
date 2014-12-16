package de.katho.kBorrow;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KLogger {
	private static Logger logger;
	private Handler fileHandler;
	private Formatter plainText;
	

	private KLogger() throws IOException {
		logger = Logger.getLogger(Util.class.getName());
		String fDir = System.getProperty("user.home")+"/kBorrow";
		String fName = "kBorrow.log";
		File dir = new File(fDir);
		File file = new File(fDir+"/"+fName);
		
		if(!dir.isDirectory()) dir.mkdir();
		if(!file.isFile()) file.createNewFile();
		
		fileHandler = new FileHandler(fDir+"/"+fName, true);
		plainText = new SimpleFormatter();
		fileHandler.setFormatter(plainText);
		logger.addHandler(fileHandler);
	}
	
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
	
	public static void log(Level pLevel, String pMsg, Exception e){
		getLogger().log(pLevel, pMsg, e);
		System.out.println(pMsg);
	}
}
