package de.katho.kBorrow.controller;

import de.katho.kBorrow.db.DbConnector;

public class RewriteToNewUserController {

	private DbConnector dbCon;
	
	public RewriteToNewUserController(DbConnector pDbCon){
		dbCon = pDbCon;
	}
	
	public boolean rewriteToNewUser(int pOldId, int pNewId){
		return dbCon.rewriteToNewUser(pOldId, pNewId);		
	}
}
