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
	
	public Settings() throws Exception {
		properties = new Properties();
		filePath = System.getProperty("user.home")+"/kBorrow";
		fileName = "Settings.cfg";
				
		if(!filePathHasValidConfig()){
			createDefaultConfig();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean filePathHasValidConfig(){
		try {
			InputStream in = new FileInputStream(filePath+"/"+fileName);
			properties = new Properties();
			
			properties.load(in);
			
			// Check if the properties file holds certain keys and values.
			if(		(properties.containsKey("dBType") && properties.containsValue("sqlite") && properties.containsKey("sqlitePath")) || 
					(properties.contains("dbType") && properties.containsValue("mysql") && properties.containsKey("mysqlDB") && properties.containsKey("mysqlHost") && properties.containsKey("mysqlUser") && properties.containsKey("mysqlPass"))) {
				in.close();
				return true;
				
			}
			else {
				in.close();
				return false;
			}
				
		} catch (FileNotFoundException e) {
			Util.showWarning(new Exception("Kann die Settingsdatei nicht finden. Versuche, eine neue zu erzeugen.", e));
			return false;
		} catch (IOException e){
			Util.showWarning(e);
			return false;
		}
	}
	
	/**
	 * Writes a default config to the config file.
	 * @throws Exception 
	 * 
	 */
	private void createDefaultConfig() throws Exception {		
		try {
			File dir = new File(filePath);
			File file = new File(filePath+"/"+fileName);
			if(!dir.isDirectory()) dir.mkdir();
			if(!file.isFile()) file.createNewFile();
			else {
				file.delete();
				file.createNewFile();
			}
			
			OutputStream os = new FileOutputStream(this.filePath+"/"+this.fileName);
			
			properties.put("dBType", "sqlite");
			properties.put("sqlitePath",System.getProperty("user.home").replace("\\", "/")+"/kBorrow/kBorrow.db" );
			properties.store(os, null);
			
			os.close();
		} catch (FileNotFoundException e) {
			throw new Exception("I couldn't find the specified properties file while trying to create a default config.", e);
		} catch (IOException e) {
			throw new Exception("I had problems writing to the properties file while trying to create a default config.", e);
		}		
	}
	
	public String getProperty(String pKey){
		if(properties.containsKey(pKey)) return properties.getProperty(pKey);
		return "";
	}
	
	public void setProperty(String pKey, String pValue) throws IOException{
		properties.put(pKey, pValue);
		
		OutputStream os;
		try {
			os = new FileOutputStream(this.filePath+"/"+this.fileName);
			properties.store(os, null);
			
			os.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("I couldn't find the specified properties file while trying to write the config.");
		} catch (IOException e) {
			throw new IOException("I had problems, writing to the properties file while saving a setting.");
		}		
	}
	
	public String getSettingsDir(){
		return filePath;
	}
}
