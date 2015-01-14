package de.katho.kBorrow.db;

import java.io.File;
import java.io.FileNotFoundException;
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
	private Connection connection;
	private String dbHandle;
	private Hashtable<String, String> sqlScheme;
	
	/**
	 * @param pHandle This string contains the path to database file the connector has to use
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public SqliteConnector(String pHandle) throws ClassNotFoundException, SQLException, IOException {
			
		this.dbHandle = pHandle;
		this.sqlScheme = this.loadScheme();
		
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
			Util.showWarning(e);
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
			Util.showWarning(e);
			return false;
		}
	}
	
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
			return null;
		}
	}

	public ArrayList<KUser> getRewriteUserList(int id) {
		ArrayList<KUser> userArr = new ArrayList<KUser>();
		
		try {
			Statement st = this.connection.createStatement();
			String query = "SELECT id, name, surname FROM user WHERE id != "+id;
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				userArr.add(new KUser(rs.getInt("id"), rs.getString("name"), rs.getString("surname")));
			}
			
			return userArr;
		} 
		catch (SQLException e){
			Util.showWarning(e);
			return null;
		}
	}

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
			return null;
		}
	}

	public ArrayList<KArticle> getFreeArticleList() {
		ArrayList<KArticle> artArr = new ArrayList<KArticle>();
		
		try {
			Statement st = this.connection.createStatement();
			String query = "SELECT id, name, is_free, description FROM article WHERE is_free = 1;";
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				artArr.add(new KArticle(rs.getInt("id"), rs.getString("name"), rs.getBoolean("is_free"), rs.getString("description")));
			}
			
			return artArr;
		}
		catch(SQLException ex){
			Util.showWarning(ex);
			return null;
		}
	}

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
			return null;
		}
	}

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
	
	public ArrayList<KLending> getActiveLendingList() {
		ArrayList<KLending> lendingArr = new ArrayList<KLending>();
		
		try {
			Statement st = connection.createStatement();
			String query = 	"SELECT id, user_id, lender_id, article_id, start_date, expected_end_date, end_date FROM lending WHERE end_date IS NULL";
			
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()){
				lendingArr.add(new KLending(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("lender_id"), rs.getInt("article_id"), rs.getString("start_date"), rs.getString("expected_end_date"), rs.getString("end_date")));
			}
			
			return lendingArr;
		}
		catch(SQLException e){
			Util.showWarning(e);
			return lendingArr;
		}
	}
	
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
	 * 
	 * @return  0: Benutzer erfolgreich erzeugt
	 * 			1: SQL-Fehler beim Erzeugen
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

	public boolean deleteUser(int id){
		// @TODO: Ausleihen auf einen anderen User umschreiben!
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
	 * 
	 * @return	Rückgabewert ist ein Array mit zwei Werten.
	 * 
	 * 			Index		0:	Enthält den Rückgabestatus:
	 * 			- Status 	0:	Alles in Ordnung
	 * 			- Status	1:	SQL-Fehler
	 * 
	 * 			Index 1: Enthält die ID der gerade erzeugten Tabellenzeile
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
