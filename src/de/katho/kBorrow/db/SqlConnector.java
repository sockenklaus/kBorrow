package de.katho.kBorrow.db;

import java.util.ArrayList;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KUser;

public class SqlConnector implements DbConnector{

	@Override
	/**
	 * @return  0: Benutzer erfolgreich erzeugt
	 * 			1: SQL-Fehler
	 * 			2: Benutzername darf nicht leer sein.
	 */
	public int createUser(String pName, String pSurname) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KUser> getUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int editUser(int pId, String pName, String pSurname) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KArticle> getArticleList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createArticle(String pName, String pDesc) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteArticle(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int editArticle(int pId, String pName, String pDesc) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KArticle> getFreeArticleList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
