package de.katho.kBorrow.db;

import java.util.ArrayList;

import de.katho.kBorrow.data.KUser;

public interface DbConnector {

	public boolean createUser(String pName, String pSurname);
	public ArrayList<KUser> getUserList();
	
}
