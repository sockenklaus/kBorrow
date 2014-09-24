package de.katho.kBorrow.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

/**
 * @class sqliteConnector
 * @author Servicepoint
 *
 * This class handles connections to a sqlite database.
 */
public class SqliteConnector {
	private Connection connection;
	private String dbHandle;
	
	/**
	 * @param dbHandle This string contains the path to database file the connector has to use
	 * @throws FileNotFoundException, SQLException
	 */
	public SqliteConnector(String dbHandle) throws SQLException, FileNotFoundException {
		this.dbHandle = dbHandle;
		File f = new File(this.dbHandle);
		
		if(!f.exists() || f.isDirectory()){
			throw new FileNotFoundException("Provided database file \""+this.dbHandle+"\" not found.");
		}
		
		try {
			Class.forName("org.sqlite.JDBC");	
			
			this.connection = DriverManager.getConnection("jdbc:sqlite:"+this.dbHandle);
			Statement st = this.connection.createStatement();
			
			st.executeUpdate("create table object (id integer, name string, comment string)");
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
}
