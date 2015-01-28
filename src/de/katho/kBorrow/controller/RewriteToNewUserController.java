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
