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
import java.util.Map.Entry;

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
		this.sqlScheme = this.loadScheme();
		
		try {
			File dbFile = new File(this.dbHandle);
			Class.forName("org.sqlite.JDBC");
			
			if(dbFile.exists()){
				if(dbFile.isFile()){
					this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
										
					if(!this.isValidDB(this.sqlScheme, this.connection)){
						throw new SQLException("The given db file doesn't match the required sql schema.");
					}
					else {
						System.out.println("Db Scheme looks fine to me.");
					}
				}
				else {
					throw new IOException("Provided db handle may not be a file but a directory or a symlink!");
				}
			}
			else {
				System.out.println("There is no db file yet... creating a new db.");
				dbFile.createNewFile();
				
				this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
				this.initNewDB(this.sqlScheme, this.connection);
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
	
	private boolean isValidDB(Hashtable<String, String> pScheme, Connection pConn){
		try {
			Statement st = pConn.createStatement();
			String query = "SELECT name, sql FROM sqlite_master WHERE type = 'table'";
			Hashtable<String, String> dbScheme = new Hashtable<String, String>();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				dbScheme.put(rs.getString("name"), this.removeLineBreaks(rs.getString("sql")));
			}
			
			for (Entry<String, String> pEntry : pScheme.entrySet()){
				String pSql = pEntry.getValue();
				boolean match = false;
				
				for (Entry<String, String> dbEntry : dbScheme.entrySet()){
					if(pSql.equalsIgnoreCase(dbEntry.getValue())){
						match = true;
						break;
					}
				}
				if(!match) return false;
			}
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	private boolean initNewDB(Hashtable<String, String> pScheme, Connection pConn){
		try {
			Statement st = pConn.createStatement();
			
			for (Entry<String, String> pEntry : pScheme.entrySet()){
				st.executeUpdate(pEntry.getValue());
			}
			return true;
		}
		catch (SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	private Hashtable<String, String> loadScheme(){
		Hashtable<String, String> tScheme= new Hashtable<String, String>();
		
		tScheme.put("kborrow",
				"CREATE TABLE kborrow ("
					+ "setting_name TEXT,"
					+ "value INT"
				+ ")");
		
		tScheme.put("article",
				"CREATE TABLE article ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT NOT NULL,"
					+ "description TEXT"
				+ ")");
		
		tScheme.put("lender",
				"CREATE TABLE lender ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "student_number INT,"
					+ "comment TEXT"
				+ ")");
		
		tScheme.put("user",
				"CREATE TABLE user ("
					+ "id INT PRIMARY KEY,"
					+ "name TEXT,"
					+ "surname TEXT"
				+ ")");
		
		tScheme.put("lending",
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
		
		return tScheme;
	}
	
	private String removeLineBreaks(String pString){
		StringBuffer text = new StringBuffer(pString);
		int i = 0;
		boolean addI = true;
		
		while (i < text.length()) {
			if (text.charAt(i) == '\n') {
				text.deleteCharAt(i);
				addI = false;

			}

			if (text.charAt(i) == '\r') {
				text.deleteCharAt(i);
				addI = false;
			}

			if (text.charAt(i) == '\t') {
				text.deleteCharAt(i);
				addI = false;
			}

			if (addI) {
				i++;
			}

			addI = true;
		}

		return text.toString();
	}
	
}
