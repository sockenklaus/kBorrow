package de.katho.kBorrow;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;

import de.katho.kBorrow.db.*;

public class Main {

	public static void main(String[] args) {		
		try {
			SqliteConnector sqlite = new SqliteConnector("test.db");
		} catch (FileNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
