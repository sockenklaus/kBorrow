package de.katho.kBorrow.db;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
			
			/*String query = "create table test "
					+ "(ID INT PRIMARY KEY NOT NULL,"
					+ "NAME TEXT NOT NULL)";
			
			Statement stm = this.connection.createStatement();
			stm.executeUpdate(query);
			stm.close();
			this.connection.close();*/
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
