package de.katho.kBorrow.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

/**
 * @class sqliteConnector
 * @author Servicepoint
 *
 * This class handles connections to a sqlite database.
 */
public class SqliteConnector implements DbConnector {
	private Connection connection;
	private String dbHandle;
	private Hashtable<String, String> sqlScheme;
	
	/**
	 * @param pHandle This string contains the path to database file the connector has to use
	 * @throws FileNotFoundException, SQLException
	 */
	public SqliteConnector(String pHandle) {
			
		this.dbHandle = pHandle;
		this.loadScheme();
		
		try {
			File dbFile = new File(this.dbHandle);
			Class.forName("org.sqlite.JDBC");
			
			if(dbFile.exists()){
				if(dbFile.isFile()){
					this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
					
					//Prüfe Schema
				}
				else {
					throw new IOException("Provided db handle may not be a file but a directory or a symlink!");
				}
			}
			else {
				dbFile.createNewFile();
				
				this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
				//INitialisiere
			}			
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isConfigured(){
		try {
			Statement st = this.connection.createStatement();
			String query = "SELECT value FROM kborrow WHERE setting_name='is_configured' LIMIT 1";
			
			ResultSet rs = st.executeQuery(query);
			
			return rs.getBoolean("value");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void loadScheme(){
		this.sqlScheme = new Hashtable<String, String>();
		
		this.sqlScheme.put("kborrow",
				"CREATE TABLE kborrow ("
					+ "setting_name TEXT,"
					+ "value INT"
				+ ")");
		
		this.sqlScheme.put("article",
				"CREATE TABLE article ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT NOT NULL,"
					+ "description TEXT"
				+ ")");
		
		this.sqlScheme.put("lender",
				"CREATE TABLE lender ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "student_number INT,"
					+ "comment TEXT"
				+ ")");
		
		this.sqlScheme.put("user",
				"CREATE TABLE user ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT,"
					+ "surname TEXT"
				+ ")");
		
		this.sqlScheme.put("lending",
				"CREATE TABLE lending ("
					+ "id INT PRIMARY KEY,"
					+ "article_id INT,"
					+ "user_id INT,"
					+ "lender_id INT,"
					+ "start_date DATE DEFAULT CURRENT_DATE,"
					+ "expected_end_date DATE,"
					+ "end_date DATE,"
					+ "comment TEXT"
				+ ")");
	}
	
}
