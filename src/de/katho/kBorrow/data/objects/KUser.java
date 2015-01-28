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
 * Bildet einen Benutzer ab.
 */
public class KUser {
	/** Vorname des Benutzers */
	private String name;
	
	/** Nachname des Benutzers */
	private String surname;
	
	/** ID des Benutzers */
	private int id;
	
	/**
	 * Erstellt ein neues KUser-Objekt.
	 * 
	 * @param pId		ID des Benutzers.
	 * @param pName		Vorname des Benutzers.
	 * @param pSurname	Nachname des Benutzers.
	 */
	public KUser(int pId, String pName, String pSurname){
		this.name = pName;
		this.surname = pSurname;
		this.id = pId;
	}
	
	/**
	 * Gibt Vorname des Benutzers zurück.
	 * 
	 * @return	Vorname des Benutzers.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gibt Nachname des Benutzers zurück.
	 * 
	 * @return	Nachname des Benutzers.
	 */
	public String getSurname(){
		return this.surname;
	}
	
	/**
	 * Gibt ID des Benutzers zurück.
	 * 
	 * @return	ID des Benutzers.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Setzt den Vornamen des Benutzers auf 'pName'.
	 * 
	 * @param pName	(Neuer) Vorname des Benutzers.
	 */
	public void setName(String pName){
		this.name = pName;
	}
	
	/**
	 * Setzt den Nachnamen des Benutzers auf 'pSurname'.
	 * 
	 * @param pSurname	(Neuer) Nachname des Benutzers.
	 */
	public void setSurname(String pSurname){
		this.surname = pSurname;
	}
}
