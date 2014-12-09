package de.katho.kBorrow.db;

import java.util.ArrayList;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.data.KLending;
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
	public int deleteArticle(int id) {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	public ArrayList<KLender> getLenderList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createNewLending(int pArtId, int pUId, int pLId,
			String pStartDate, String pEstEndDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createNewLender(String pLName, String pLSurname, String pLSN) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KLending> getActiveLendingList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int returnLending(int id, int artId, String end_date) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<KLending> getLendingListForArticle(int pArtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<KUser> getRewriteUserList(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean rewriteToNewUser(int pOldId, int pNewId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
