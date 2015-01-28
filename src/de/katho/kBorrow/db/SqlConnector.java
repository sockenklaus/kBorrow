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

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;

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
	public ArrayList<KLender> getLenderList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] createNewLending(int pArtId, int pUId, int pLId,
			String pStartDate, String pEstEndDate) {
		// TODO Auto-generated method stub
		return new int[2];
	}

	@Override
	public int createNewLender(String pLName, String pLSurname, String pLSN) {
		// TODO Auto-generated method stub
		return 0;
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
	public boolean rewriteToNewUser(int pOldId, int pNewId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<KLending> getLendingList() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
