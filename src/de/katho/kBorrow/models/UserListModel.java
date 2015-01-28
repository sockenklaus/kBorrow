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

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class UserListModel extends AbstractListModel<String> implements ComboBoxModel<String>, KGuiModel  {

	/** Serial Version UID */
	private static final long serialVersionUID = -8653066929273274524L;
	
	/** Daten der Tabelle: Liste aller Benutzer. */
	protected ArrayList<KUser> data;
	
	/** Vor- und Nachname des aktuell ausgew�hlten Benutzers. */
	protected String selectedItem = null;
	
	/**
	 * Erzeugt das UserListModel.
	 * 
	 * @param	pUserModel	Referenz auf das KUserModel.
	 */
	public UserListModel(KDataModel pUserModel){		
		super();
		pUserModel.register(this);
	}
	
	/**
	 * Setzt einen Wert f�r "selectedItem".
	 * 
	 * @param	object	Ausgew�hltes Objekt aus der Liste.
	 */
	public void setSelectedItem(Object object) {
		if(selectedItem == null && object == null) return;
		if(selectedItem != null && selectedItem.equals(object)) return;
		if(object!= null && !dataContains((String)object)) return;
		
		selectedItem = (String)object;
		fireContentsChanged(this, -1, -1);
	}
	
	/**
	 * Gibt das ausgew�hlte Item zur�ck.
	 * 
	 * @return	Ausgew�hltes Item als String.	 * 
	 */
	public String getSelectedItem(){
		if(selectedItem != null ) return selectedItem;
		return "";
	}
	
	/**
	 * Gibt ein Element der Liste zur�ck.
	 * 
	 * @param	index	Index des zur�ckgegebenen Elements.
	 * @return			Element als String.
	 */
	public String getElementAt(int index){
		if(index < 0 || index >= data.size()) return null;
		
		KUser obj = data.get(index);
		return obj.getName()+" "+obj.getSurname();
	}

	/**
	 * Gibt die Gr��e der Liste zur�ck.
	 * 
	 * @return	Gr��e der Liste als Int.
	 */
	public int getSize() {
		return data.size();
	}
	
	/**
	 * Pr�ft, ob die Liste einen Benutzer mit dem �bergebenen Namen enth�lt.
	 * 
	 * @param	pName	Vor- und Nachname des Benutzers, gegen den gepr�ft wird.
	 * @return			True wenn der gesuchte Benutzer in der Liste ist, false, falls nicht.
	 */
	private boolean dataContains(String pName){
		for(KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return true;
		}
		return false;
	}

	/**
	 * Holt die ben�tigten Daten aus dem als Parameter �bergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KUserModel){
			data = ((KUserModel)pModel).getData();
		}
		
		if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
		
		fireIntervalAdded(this, 0, data.size()-1);
	}
	
	
}

