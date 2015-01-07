package de.katho.kBorrow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Verwaltet die Settings-Datei von kBorrow. 
 */
public class Settings {
	
	/** Properties-Objekt, über das Zugriff auf Settings-Werte erfolgt */
	private Properties properties = new Properties();
	
	/** Pfad zum Settings-Verzeichnis */
	private final String filePath = System.getProperty("user.home")+"/kBorrow";
	
	/** Dateiname der Settingsdatei */
	private final String fileName = "Settings.cfg";
	
	/**
	 * Initialisiert das Settingsobjekt.
	 * 
	 * Prüft, ob am oben definierten Dateipfad eine valide Config existiert. Falls nicht, wird eine neue Config erzeugt.
	 * 
	 * @throws Exception wenn eine Exception in {@link #filePathHasValidConfig} oder {@link #createDefaultConfig} auftritt und dort nicht gefangen wird.
	 */
	public Settings() throws Exception {
		if(!filePathHasValidConfig()){
			createDefaultConfig();
		}
	}
	
	/**
	 * Prüft, ob am oben definierten Dateipfad eine valide Config existiert. 
	 * 
	 * @return true, wenn eine valide Config existiert, false, wenn keine Config existiert, oder die Config invalid ist.
	 */
	private boolean filePathHasValidConfig(){
		try {
			InputStream in = new FileInputStream(filePath+"/"+fileName);
						
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
	 * 
	 * @throws Exception wenn Configdatei nicht gefunden oder nicht erzeugt werden konnte.
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
	
	/**
	 * Gibt einen Propertieswert zum als Parameter übergebenen Key zurück.
	 * 
	 * @param pKey	Key des angefragten Propertieswerts, null gibt null zurück
	 * @return		Propertieswert als String oder null, wenn Key nicht gefunden werden konnte.
	 */
	public String getProperty(String pKey){
		if(properties.containsKey(pKey)) return properties.getProperty(pKey);
		return null;
	}
	
	/**
	 * Setzt übergebenes Key-Value-Paar und schreibt es in die Configdatei.
	 * 
	 * @param pKey			Key des zu schreibenden Key-Value-Paars, not null	
	 * @param pValue		Value des zu schreibenden Key-Value-Paares, null
	 * @throws Exception 	Wenn Key null ist, wenn Configdatei nicht gefunden oder nicht beschrieben werden konnte.
	 */
	public void setProperty(String pKey, String pValue) throws Exception{
		if(pKey == null || pKey.equals("")) throw new Exception("Settings#setProperty: Key darf nicht null oder leer sein.");
		
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
	
	/**
	 * Gibt den Pfad zum Configverzeichnis zurück.
	 * 
	 * @return	Pfad zum Configverzeichnis als String.
	 */
	public String getSettingsDir(){
		return filePath;
	}
}
