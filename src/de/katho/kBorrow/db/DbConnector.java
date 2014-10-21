package de.katho.kBorrow.db;

import java.util.ArrayList;

import de.katho.kBorrow.data.KArticle;
import de.katho.kBorrow.data.KUser;

public interface DbConnector {

	public int createUser(String pName, String pSurname);
	public ArrayList<KUser> getUserList();
	public boolean deleteUser(int id);
	public int editUser(int pId, String pName, String pSurname);
	public ArrayList<KArticle> getArticleList();
	
}
