package de.katho.kBorrow.controller;

import de.katho.kBorrow.interfaces.DbConnector;

/**
 * RewriteToNewUserController führt sämtliche Datenbankoperationen durch, die durch {@link de.katho.kBorrow.gui.RewriteToNewUserDialog} angestoßen werden.
 */
public class RewriteToNewUserController {

	/** Referenz auf die Datenbank. */
	private DbConnector dbCon;
	
	/**
	 * Erzeugt eine neue Instanz des RewriteToNewUserController.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 */
	public RewriteToNewUserController(DbConnector pDbCon){
		dbCon = pDbCon;
	}
	
	/**
	 * Schreibt sämtliche Ausleihen vom Benutzer mit der ID 'pOldId' auf den Benutzer mit der ID 'pNewId' um.
	 * 
	 * @param pOldId	ID des bisherigen Benutzers.
	 * @param pNewId	ID des neuen Benutzers.
	 * @return			true, wenn die Operation erfolgreich war, false, wenn sie nicht erfolgreich war.
	 */
	public boolean rewriteToNewUser(int pOldId, int pNewId){
		return dbCon.rewriteToNewUser(pOldId, pNewId);		
	}
}
