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

package de.katho.kBorrow.data.objects;

/**
 * Bildet einen Artikel ab.
 */
public class KArticle {
	
	/** ID des Artikels */
	private int id;
	
	/** Name des Artikels */
	private String name;
	
	/** Beschreibung des Artikels */
	private String description;
	
	/** Ist der Artikel im Moment ausgeliehen oder nicht? */
	private boolean isFree;

	/**
	 * Erzeugt ein neues Artikelobjekt.
	 * 
	 * @param pId		ID des Artikels.
	 * @param pName		Name des Artikels.
	 * @param pFree		Ist der Artikel im Moment ausgeliehen oder nicht?
	 * @param pDesc		Beschreibung des Artikels.
	 */
	public KArticle(int pId, String pName, boolean pFree, String pDesc) {
		id = pId;
		name = pName;
		description = pDesc;
		isFree = pFree;
	}

	/**
	 * Gibt die ID des Artikels zurück.
	 * 
	 * @return	ID des Artikels.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gibt den Namen des Artikels zurück.
	 * 
	 * @return	Name des Artikels.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt die Beschreibung des Artikels zurück.
	 * 
	 * @return	Beschreibung des Artikels.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Gibt zurück, ob der Artikel im Moment ausgeliehen ist oder nicht.
	 * 
	 * @return	Ist der Artikel im Moment ausgeliehen oder nicht?
	 */
	public boolean getIsFree(){
		return isFree;
	}
	
	/**
	 * Setzt den Namen des Artikels.
	 * 
	 * @param pName		(Neuer) Name des Artikels.
	 */
	public void setName(String pName){
		name = pName;
	}
	
	/**
	 * Setzt die Beschreibung des Artikels.
	 * 
	 * @param pDesc		(Neue) Beschreibung des Artikels.
	 */
	public void setDescription(String pDesc){
		description = pDesc;
	}
	
	/**
	 * Setzt, ob der Artikel ausgeliehen ist, oder nicht.
	 * 
	 * @param pFree		Ist der Artikel ausliehen oder nicht?
	 */
	public void setIsFree(boolean pFree){
		isFree = pFree;
	}

}
