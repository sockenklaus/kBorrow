/*
 * Copyright (C) 2015  Pascal K�nig
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

package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * Enth�lt die Benutzer, auf die Ausleihen beim L�schen eines Benutzers umgeschrieben werden k�nnen.
 */
public class RewriteUserModel extends UserListModel {

	/** Serial Version UID */
	private static final long serialVersionUID = -78927566018961799L;
	
	/** ID des Benutzers, dessen Ausleihen umgeschrieben werden sollen. */
	private int id;

	/**
	 * Erzeugt das RewriteUserModel.
	 * 
	 * @param	pModel	Referenz auf das KUserModel.
	 * @param 	pId		ID des Benutzers, dessen Ausleihen umgeschrieben werden sollen.
	 */
	public RewriteUserModel(KUserModel pModel, int pId) {
		super(pModel);
		id = pId;
		pModel.register(this);
	}
	
	/**
	 * Gibt die ID eines Benutzers zur�ck, dessen voller 
	 * Name (Vor- und Nachname) per Parameter �bergeben wird.
	 * 
	 * @param	pName	Vor- und Nachname als String.
	 * @return			Benutzer-ID als Int.
	 */
	public int getIdByFullname(String pName){
		for (KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return elem.getId();
		}
		return -1;
	}
	
	/**
	 * Holt die ben�tigten Daten aus dem als Parameter �bergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel){
		if(pModel instanceof KUserModel){
			data = new ArrayList<KUser>();
			
			for(KUser elem : ((KUserModel)pModel).getData()){
				if(elem.getId() != id) data.add(elem);
			}
			
			if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
			
			fireIntervalAdded(this, 0, data.size()-1);
		}
		else {
			KLogger.log(Level.SEVERE, "RewriteUserModel: Typecast error!", new Exception("RewriteUserModel: Typecast error!"));
		}
	}
}
