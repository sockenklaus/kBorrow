package de.katho.kBorrow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Settings {
	
	private Properties properties;
	private String filePath = System.getProperty("user.home")+"\\kBorrow\\Settings.cfg";
	private File cfgFile;
	
	public Settings() {
		this.properties = new Properties();
		this.cfgFile = new File(this.filePath);
	}
	
	private Properties readPropFile(String pFilePath){
		Properties prop = new Properties();
		File tFile = new File(pFilePath);
		
		InputStream is = Properties.class.getResourceAsStream(pFilePath);
		
		
	}
	
	/**
	 * Writes a default config to the config file.
	 * 
	 * @param pFilePath
	 * @throws IOException
	 */
	private void createDefaultPropFile(String pFilePath) throws IOException {
		Properties prop = new Properties();
		File tFile = new File(pFilePath);
		
		if(!tFile.isFile() && !tFile.isDirectory()){
			tFile.createNewFile();
		}
		
		InputStream is = Properties.class.getResourceAsStream(pFilePath);
		prop.load(is);
		prop.clear();
		
		prop.put("dBType", "sqlite");
		prop.put("dBPath", System.getProperty("user.home")+"\\kBorrow.db" );
		
	}
}
