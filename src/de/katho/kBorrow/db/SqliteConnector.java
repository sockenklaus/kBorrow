package de.katho.kBorrow.db;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @class sqliteConnector
 * @author Servicepoint
 *
 * This class handles connections to a sqlite database.
 */
public class SqliteConnector implements DbConnector {
	private Connection connection;
	private String dbHandle;
	
	/**
	 * @param pHandle This string contains the path to database file the connector has to use
	 * @throws FileNotFoundException, SQLException
	 */
	public SqliteConnector(String pHandle) {
		this.dbHandle = pHandle;
	
		try {
			Class.forName("org.sqlite.JDBC");	
			System.out.println(this.dbHandle);
			this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
			
			System.out.println(this.isConfigured());
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
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
	
}
