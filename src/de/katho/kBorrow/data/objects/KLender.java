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
 * Bildet einen Ausleiher ab.
 */
public class KLender {
	
	/** Vorname des Ausleihers. */
	private String name;
	
	/** Nachname des Ausleihers. */
	private String surname;
	
	/** Matrikelnummer des Ausleihers. */
	private int studentnumber;
	
	/** ID des Ausleihers. */
	private int id;
	
	/**
	 * Erzeugt ein neues KLender-Objekt.
	 * 
	 * @param pId				ID des Ausleihers.
	 * @param pName				Vorname des Ausleihers.
	 * @param pSurname			Nachname des Ausleihers.
	 * @param pStudentnumber	Matrikelnummer des Ausleihers.
	 */
	public KLender(int pId, String pName, String pSurname, int pStudentnumber){
		id = pId;
		name = pName;
		surname = pSurname;
		studentnumber = pStudentnumber;
	}
	
	/**
	 * Gibt Vorname des Ausleihers zurück.
	 * 
	 * @return	Vorname des Ausleihers.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gibt Nachnamen des Ausleihers zurück.
	 * 
	 * @return	Nachname des Ausleihers.
	 */
	public String getSurname(){
		return surname;
	}
	
	/**
	 * Gibt Matrikelnummer des Ausleihers zurück.
	 * 
	 * @return	Matrikelnummer des Ausleihers.
	 */
	public int getStudentnumber(){
		return studentnumber;
	}
	
	/**
	 * Gibt ID des Ausleihers zurück.
	 * 
	 * @return	ID des Ausleihers.
	 */
	public int getId(){
		return id;
	}
}
