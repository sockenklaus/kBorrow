package de.katho.kBorrow.db;

import java.util.ArrayList;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.data.KLending;
import de.katho.kBorrow.data.KUser;

public interface DbConnector {

	public int createUser(String pName, String pSurname);
	public ArrayList<KUser> getUserList();
	public boolean deleteUser(int id);
	public int editUser(int pId, String pName, String pSurname);
	public ArrayList<KArticle> getArticleList();
	public int createArticle(String pName, String pDesc);
	public int deleteArticle(int id);
	public int editArticle(int pId, String pName, String pDesc);
	public ArrayList<KArticle> getFreeArticleList();
	public ArrayList<KLender> getLenderList();
	public int createNewLending(int pArtId, int pUId, int pLId, String pStartDate, String pEstEndDate);
	public int createNewLender(String pLName, String pLSurname, String pLSN);
	public ArrayList<KLending> getActiveLendingList();
	public int returnLending(int lendingId, int artId, String string);
	public ArrayList<KLending> getLendingListForArticle(int pArtId);
	public ArrayList<KUser> getRewriteUserList(int id);
	public boolean rewriteToNewUser(int pOldId, int pNewId);
}
