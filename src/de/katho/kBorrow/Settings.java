package de.katho.kBorrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Settings {
	
	private Properties properties;
	private String filePath;
	private String fileName;
	
	public Settings() {
		this.properties = new Properties();
		this.filePath = System.getProperty("user.home")+"/kBorrow";
		this.fileName = "Settings.cfg";
				
		if(!this.filePathHasValidConfig()){
			this.createDefaultConfig();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean filePathHasValidConfig(){
		try {
			InputStream in = new FileInputStream(this.filePath+"/"+this.fileName);
			this.properties = new Properties();
			
			this.properties.load(in);
			
			// Check if the properties file holds certain keys and values.
			if(		(this.properties.containsKey("dBType") && this.properties.containsValue("sqlite") && this.properties.containsKey("sqlitePath")) || 
					(this.properties.contains("dbType") && this.properties.containsValue("mysql") && this.properties.containsKey("mysqlDB") && this.properties.containsKey("mysqlHost") && this.properties.containsKey("mysqlUser") && this.properties.containsKey("mysqlPass"))) {
				in.close();
				return true;
				
			}
			else {
				in.close();
				return false;
			}
				
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Writes a default config to the config file.
	 * 
	 */
	private void createDefaultConfig() {		
		try {
			File dir = new File(this.filePath);
			File file = new File(this.filePath+"/"+this.fileName);
			if(!dir.isDirectory()) dir.mkdir();
			if(!file.isFile()) file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
			
			OutputStream os = new FileOutputStream(this.filePath+"/"+this.fileName);
			
			this.properties.put("dBType", "sqlite");
			this.properties.put("sqlitePath",System.getProperty("user.home").replace("\\", "/")+"/kBorrow/kBorrow.db" );
			this.properties.store(os, null);
			
			os.close();
		} catch (FileNotFoundException e) {
			System.out.println("I couldn't find the specified properties file while trying to create a default config.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I had problems, writing to the properties file while trying to create a default config.");
			e.printStackTrace();
		}		
	}
	
	public String getProperty(String pKey){
		return this.properties.getProperty(pKey);
	}
	
	public void setProperty(String pKey, String pValue){
		this.properties.put(pKey, pValue);
		
		OutputStream os;
		try {
			os = new FileOutputStream(this.filePath+"/"+this.fileName);
			this.properties.store(os, null);
			
			os.close();
		} catch (FileNotFoundException e) {
			System.out.println("I couldn't find the specified properties file while trying to write the config.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I had problems, writing to the properties file while saving a setting.");
			e.printStackTrace();
		}		
	}
}
