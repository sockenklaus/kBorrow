/*
 * Copyright (C) 2015  Pascal König
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.katho.kBorrow.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;

/**
 * This class handles connections to a sqlite database.
 */
public class SqliteConnector implements DbConnector {
	
	/** Referenz auf die Verbindung zur Datenbank */
	private Connection connection;
	
	/**
	 * Konstruktor gibt eine neue Instanz des SqliteConnector zurück.
	 * 
	 * <p>
	 * Lädt das Datenbankschema und speichert es zwischen. Prüft, ob bereits eine Datenbankdatei mit dem 
	 * übergebenen Dateipfad existiert. Falls ja, wird das Datenbankschema überprüft, falls nein, wird eine neue
	 * Datenbankdatei mit dem vorher geladenen Schema erzeugt.
	 * </p>
	 * 
	 * @param 	pDbHandle 				This string contains the path to database file the connector has to use
	 * @throws 	IOException				Wenn IO-Fehler, wie Probleme mit den Berechtigungen auftreten, oder der pDbHandle auf einen Symlink oder ein Verzeichnis verweist.	
	 * @throws 	SQLException			Wenn die gegebene Datenbank nicht dem Datenbankschema entspricht.
	 * @throws 	ClassNotFoundException	Wenn der JDBC-Connector nicht gefunden werden konnte.
	 */
	public SqliteConnector(final String pDbHandle) throws ClassNotFoundException, SQLException, IOException {
			
		Hashtable<String, String> sqlScheme = loadScheme();
		
		File dbFile = new File(pDbHandle);
		Class.forName("org.sqlite.JDBC");
			
		if(dbFile.exists()){
			if(dbFile.isFile()){
				connection = DriverManager.getConnection("jdbc:sqlite:"+pDbHandle);
										
				if(!isValidDB(sqlScheme, connection)){
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
			
			connection = DriverManager.getConnection("jdbc:sqlite:"+pDbHandle);
			initNewDB(sqlScheme, connection);
		}
	}
	
	/**
	 * Prüft, ob die übergebene Datenbankverbindung eine Datenbank mit dem übergebenen DB-Schema enthält.
	 * 
	 * @param pScheme	Datenbankschema, gegen das geprüft werden soll.
	 * @param pConn		Datenbankverbindung, die geprüft werden soll.
	 * @return			True, wenn die übergebene Verbindung eine Datenbank mit dem gegebenen Schema enthält. Andernfalls false.
	 */
	private boolean isValidDB(Hashtable<String, String> pScheme, Connection pConn){
		try {
			Statement st = pConn.createStatement();
			String query = "SELECT name, sql FROM sqlite_master WHERE type = 'table'";
			Hashtable<String, String> dbScheme = new Hashtable<String, String>();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				dbScheme.put(rs.getString("name"), Util.removeLineBreaks(rs.getString("sql")));
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
			Util.showWarning(e);
			return false;
		}		
	}
	
	/**
	 * Erstellt an der übergebenen Datenbankverbindung eine neue Datenbank mit dem übergebenen Schema.
	 * 
	 * @param pScheme	Schema, nach deren Vorbild die Datenbank erzeugt werden soll.
	 * @param pConn		Datenbankverbindung, an der versucht werden soll, eine Datenbank zu erzeugen.
	 * @return			True, wenn die Datenbank erzeugt werden konnte, andernfalls false.
	 */
	private boolean initNewDB(Hashtable<String, String> pScheme, Connection pConn){
		try {
			Statement st = pConn.createStatement();
			
			for (Entry<String, String> pEntry : pScheme.entrySet()){
				st.executeUpdate(pEntry.getValue());
			}
			
			return true;
		}
		catch (SQLException e){
			Util.showWarning(e);
			return false;
		}
	}
	
	/**
	 * Erzeugt ein Datenbankschema in einer Hashtable.
	 * 
	 * @return	Die erzeugte Hashtable mit dem Datenbankschema.
	 */
	private Hashtable<String, String> loadScheme(){
		Hashtable<String, String> tScheme= new Hashtable<String, String>();
				
		tScheme.put("article",
				"CREATE TABLE article ("
					+ "id INTEGER PRIMARY KEY NOT NULL,"
					+ "name TEXT NOT NULL,"
					+ "is_free INTEGER DEFAULT 1,"
					+ "description TEXT"
				+ ")");
		
		tScheme.put("lender",
				"CREATE TABLE lender ("
					+ "id INTEGER PRIMARY KEY NOT NULL,"
					+ "name TEXT,"
					+ "surname TEXT,"
					+ "student_number INTEGER,"
					+ "comment TEXT"
				+ ")");
		
		tScheme.put("user",
				"CREATE TABLE user ("
					+ "id INTEGER PRIMARY KEY NOT NULL,"
					+ "name TEXT,"
					+ "surname TEXT"
				+ ")");
		
		tScheme.put("lending",
				"CREATE TABLE lending ("
					+ "id INTEGER PRIMARY KEY NOT NULL,"
					+ "article_id INTEGER,"
					+ "user_id INTEGER,"
					+ "lender_id INTEGER,"
					+ "start_date TEXT DEFAULT CURRENT_DATE,"
					+ "expected_end_date TEXT,"
					+ "end_date TEXT,"
					+ "comment TEXT"
				+ ")");
		
		return tScheme;
	}
	
	/**
	 * Gibt eine Liste aller Benutzer als ArrayList zurück.
	 * 
	 * @return	Liste aller Benutzer als ArrayList.
	 */
	public ArrayList<KUser> getUserList(){
		ArrayList<KUser> userArr = new ArrayList<KUser>();
		
		try {
			Statement st = this.connection.createStatement();
			String query = "SELECT id, name, surname FROM user";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				userArr.add(new KUser(rs.getInt("id"), rs.getString("name"), rs.getString("surname")));
			}
			
			return userArr;
		} 
		catch (SQLException e){
			Util.showWarning(e);
			return userArr;
		}
	}

	/**
	 * Gibt die komplette Artikelliste als ArrayList zurück.
	 * 
	 * @return	Komplette Artikelliste als ArrayList.
	 */
	public ArrayList<KArticle> getArticleList() {
		ArrayList<KArticle> artArr = new ArrayList<KArticle>();
		
		try {
			Statement st = this.connection.createStatement();
			String query = "SELECT id, name, is_free, description FROM article";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				artArr.add(new KArticle(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_free"), rs.getString("description")));
			}
			
			return artArr;
		}
		catch (SQLException ex){
			Util.showWarning(ex);
			return artArr;
		}
	}

	/**
	 * Gibt die komplette Ausleiher-Liste als ArrayList zurück.
	 * 
	 * @return	Komplette Ausleiher-Liste als ArrayList. 	
	 */
	public ArrayList<KLender> getLenderList() {
		ArrayList<KLender> lendArr = new ArrayList<KLender>();
		
		try{
			Statement st = connection.createStatement();
			String query = "SELECT id, name, surname, student_number FROM lender";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				lendArr.add(new KLender(rs.getInt("id"), rs.getString("name"), rs.getString("surname"), rs.getInt("student_number")));
			}
			
			return lendArr;
		}
		catch(SQLException ex){
			Util.showWarning(ex);
			return lendArr;
		}
	}

	/**
	 * Gibt die komplette Liste der Ausleihen als ArrayList zurück.
	 * 
	 * @return	Die komplette Liste der Ausleihen als ArrayList.
	 */
	public ArrayList<KLending> getLendingList(){
		ArrayList<KLending> lendingArr = new ArrayList<KLending>();
		
		try {
			Statement st = connection.createStatement();
			String query = "SELECT id, user_id, lender_id, article_id, start_date, expected_end_date, end_date FROM lending";
			
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				lendingArr.add(new KLending(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("lender_id"), rs.getInt("article_id"), rs.getString("start_date"), rs.getString("expected_end_date"), rs.getString("end_date")));
			}
		}
		catch(SQLException e){
			Util.showWarning(e);
		}
		return lendingArr;
	}
	
	/**
	 * Gibt die Liste der Ausleihen für den Artikel mit der als Parameter übergebenen ID als ArrayList zurück.
	 * 
	 * @param pArtId	ID der Artikels, für den die Ausleihen zurückgegeben werden sollen.
	 * @return			Liste der Ausleihen des Artikels mit der als Parameter übergebenen ID.
	 */
	public ArrayList<KLending> getLendingListForArticle(int pArtId){
		ArrayList<KLending> lendingArr = new ArrayList<KLending>();
		
		try{
			Statement st = connection.createStatement();
			String query = "SELECT id, user_id, lender_id, start_date, expected_end_date, end_date FROM lending WHERE article_id = '"+pArtId+"' ORDER BY id DESC";
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()){
				lendingArr.add(new KLending(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("lender_id"), pArtId, rs.getString("start_date"), rs.getString("expected_end_date"), rs.getString("end_date")));
			}
			
			return lendingArr;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return lendingArr;
		}
	}

	/**
	 * Erzeugt einen neuen Benutzer mit dem übergebenen Vor- und Nachnamen.
	 * 
	 * <p> Gibt je nach Ergebnis einen anderen Statuscode als Int zurück: </p>
	 * 
	 * <ul>
	 * <li>0: Benutzer erfolgreich erzeugt.</li>
	 * <li>1: SQL-Fehler beim Erzeugen.</li>
	 * </ul>
	 * 
	 * @param	pName		Vorname.
	 * @param	pSurname	Nachname.
	 * @return  			Statuscode als Int.
	 * 	
	 */
	public int createUser(String pName, String pSurname){
		try {
			Statement st = this.connection.createStatement();
			String query = "INSERT INTO user (name, surname) VALUES ('"+pName+"', '"+pSurname+"')";
			
			st.executeUpdate(query);
			
			return 0;
			
		} catch (SQLException e) {
			Util.showWarning(e);
			return 1;
		}
	}

	/**
	 * Bearbeitet den Benutzer mit der übergebenen ID und ändert ggf. Vor- und Nachname.
	 * 
	 * <p> Gibt je nach Ergebnis einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Benutzer erfolgreich bearbeitet.</li>
	 * <li>1: SQL-Fehler beim Bearbeiten.</li>
	 * </ul>
	 * 
	 * @param	pId			ID des Benutzert, der bearbeitet werden soll.
	 * @param	pName		(Neuer) Vorname.
	 * @param	pSurname	(Neuer) Nachname.
	 * @return	Statuscode als Int.
	 */
	public int editUser(int pId, String pName, String pSurname) {
		try {
			Statement st = this.connection.createStatement();
			String query = "UPDATE user SET name = '"+pName+"', surname = '"+pSurname+"' WHERE id = '"+pId+"'";
			
			st.executeUpdate(query);
			
			return 0;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return 1;
		}
	}

	/**
	 * Löscht den Benutzer mit der als Parameter übergebenen ID.
	 * 
	 * @param	id	ID des Benutzers, der gelöscht werden soll.
	 * @return		True, wenn der Benutzer gelöscht werden konnte, andernfalls false.
	 */
	public boolean deleteUser(int id){
		try {
			Statement st = this.connection.createStatement();
			String query = "DELETE FROM user WHERE id = '"+id+"'";
			
			st.executeUpdate(query);
			
			return true;
		}
		catch (SQLException e){
			Util.showWarning(e);
			return false;
		}
	}

	/**
	 * Erstellt einen neuen Artikel mit dem übergebenen Namen und der übergebenen Beschreibung.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich erstellt.</li>
	 * <li>1: SQL-Fehler beim Erstellen.</li>
	 * </ul>
	 * 
	 * @param	pName	Name des Artikels.
	 * @param	pDesc	Beschreibung des Artikels.
	 * @return			Statuscode als Int.
	 */
	public int createArticle(String pName, String pDesc) {
		try {
			Statement st = this.connection.createStatement();
			String query = "INSERT INTO article (name, description) VALUES ('"+pName+"', '"+pDesc+"')";
			
			st.executeUpdate(query);
			
			return 0;
			
		} catch (SQLException e) {
			Util.showWarning(e);
			return 1;
		}
	}

	/**
	 * Löscht den Artikel mit der übergebenen ID.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Löschen</li>
	 * </ul>
	 * 
	 * @param	id	ID des Artikels, der gelöscht werden soll.
	 * @return		Statuscode als Int.
	 */
	public int deleteArticle(int id) {
		try {
			Statement st = connection.createStatement();
			String query = "DELETE FROM article WHERE id = '"+id+"'";
			
			st.executeUpdate(query);
			
			return 0;
		}
		catch (SQLException e){
			Util.showWarning(e);
			return 1;
		}
	}

	/**
	 * Setzt Name und Beschreibung des Artikels mit der übergebenen ID entsprechend.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Bearbeiten.</li>
	 * </ul>
	 * 
	 * @param	pId		ID des Artikels, der bearbeitet werden soll.
	 * @param	pName	(Neuer) Name des Artikels.
	 * @param	pDesc	(Neue) Beschreibung des Artikels.
	 * @return			Statuscode als Int.
	 */
	public int editArticle(int pId, String pName, String pDesc) {
		try {
			Statement st = this.connection.createStatement();
			String query = "UPDATE article SET name = '"+pName+"', description = '"+pDesc+"' WHERE id = '"+pId+"'";
			
			st.executeUpdate(query);
			
			return 0;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return 1;
		}
	}

	/**
	 * Erstellt eine neue Ausleihe.
	 * 
	 * <p>
	 * Gibt ein Int-Array der Länge 2 zurück. An erster Stelle steht der Rückgabestatus, an zweiter
	 * Stelle die ID der gerade erzeugten Tabellenzeile.
	 * </p>
	 * 
	 * <p>Die Statuscodes lauten:</p>
	 * 
	 * <ul>
	 * <li>0: Ausleihe konnte erfolgreich erzeugt werden.</li>
	 * <li>1: SQL-Fehler beim Erstellen der Ausleihe.</li>
	 * </ul>
	 * 
	 * @param	pArtId			ID des verliehenen Artikels.
	 * @param	pUId			ID des ausleihenden Benutzers.
	 * @param	pLId			ID des Ausleihers.
	 * @param	pStartDate		Startdatum der Ausleihe.
	 * @param	pEstEndDate		Voraussichtliches Enddatum der Ausleihe.
	 * @return					Statuscode als Int.
	 */
	public int[] createNewLending(int pArtId, int pUId, int pLId, String pStartDate, String pEstEndDate) {
		int[] result = new int[2];
		
		try{
			Statement st = connection.createStatement();
			String query = "INSERT INTO lending (article_id, user_id, lender_id, start_date, expected_end_date ) "
							+ "VALUES ("+pArtId+", "+pUId+", "+pLId+", '"+pStartDate+"', '"+pEstEndDate+"');"
							+ "UPDATE article SET is_free = 0 WHERE id = "+pArtId+";";
			
			st.executeUpdate(query);
			
			query = "SELECT id FROM lending ORDER BY id DESC LIMIT 1;";
			
			ResultSet rs = st.executeQuery(query);
			
			result[1] = rs.getInt("id");
			result[0] = 0;
			
			return result;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return new int[]{1,0};
		}
	}

	/**
	 * Schreibt alle Ausleihen von einem auf einen anderen Benutzer um.
	 * 
	 * @param	pOldId	ID des alten Benutzers.
	 * @param	pNewId	ID des Benutzers, auf den die Ausleihen umgeschrieben werden sollen.
	 * @return			True, wenn erfolgreich umgeschrieben werden konnte, andernfalls false.
	 * 
	 */
	public boolean rewriteToNewUser(int pOldId, int pNewId) {
		try {
			Statement st = connection.createStatement();
			String query = "UPDATE lending SET user_id = '"+pNewId+"' WHERE user_id = '"+pOldId+"'";
			
			st.executeUpdate(query);
			
			return true;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return false;
		}
	}

	/**
	 * Gibt eine Ausleihe zurück, indem ein End-Datum gesetzt wird und der Artikel wieder freigegeben wird. 
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Umtragen.</li>
	 * </ul>
	 * 
	 * @param	lendingId	ID der Ausleihe, die zurückgegeben werden soll.
	 * @param	artId		ID des Artikels, der freigegeben werden soll.
	 * @param	end_date	ID des Rückgabedatums.
	 * @return				Statuscode als Int.
	 */
	// TODO Diese Funktion kann ich auch ohne ArtId implementieren!
	public int returnLending(int lendingId, int artId, String end_date) {
		try{
			Statement st = connection.createStatement();
			String query = "UPDATE article SET is_free = 1 WHERE id = '"+artId+"';"
					+ "UPDATE lending SET end_date = '"+end_date+"' WHERE id = '"+lendingId+"';";
			
			st.executeUpdate(query);
			
			return 0;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return 1;
		}
		
	}

	/**
	 * Erzeugt einen neuen Ausleiher mit den übergebenen Daten.
	 * 
	 * <p>Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Erstellen.</li>
	 * </ul>
	 * 
	 * @param	pLName		Vorname des neuen Ausleihers.
	 * @param	pLSurname	Nachname des neuen Ausleihers.
	 * @param	pLSN		Matrikelnummer des neuen Ausleihers.
	 * @return				Statuscode als Int.
	 */
	public int createNewLender(String pLName, String pLSurname, String pLSN) {
		try{
			Statement st = connection.createStatement();
			String query = "INSERT into lender (name, surname, student_number) "
							+ "VALUES ('"+pLName+"', '"+pLSurname+"', '"+pLSN+"')";
			
			st.executeUpdate(query);
			
			return 0;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return 1;
		}
	}
	
}
