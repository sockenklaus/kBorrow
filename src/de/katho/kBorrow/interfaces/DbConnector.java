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

package de.katho.kBorrow.interfaces;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;

/**
 * Dieses Interface definiert die Vorgaben für einen Datenbankkonnektor.
 */
public interface DbConnector {

	/**
	 * Erzeugt einen neuen Benutzer mit dem übergebenen Vor- und Nachnamen.
	 * 
	 * <p> Gibt je nach Ergebnis einen anderen Statuscode als Int zurück: </p>
	 * 
	 * <ul>
	 * <li>0: Benutzer erfolgreich erzeugt.</li>
	 * <li>1: SQL-Fehler beim Erzeugen.</li>
	 * </ul>
	 * 
	 * @param	pName		Vorname.
	 * @param	pSurname	Nachname.
	 * @return  			Statuscode als Int.
	 * 	
	 */
	public int createUser(String pName, String pSurname);
	
	/**
	 * Gibt eine Liste aller Benutzer als ArrayList zurück.
	 * 
	 * @return	Liste aller Benutzer als ArrayList.
	 */
	public ArrayList<KUser> getUserList();
	
	/**
	 * Löscht den Benutzer mit der als Parameter übergebenen ID.
	 * 
	 * @param	id	ID des Benutzers, der gelöscht werden soll.
	 * @return		True, wenn der Benutzer gelöscht werden konnte, andernfalls false.
	 */
	public boolean deleteUser(int id);
	
	/**
	 * Bearbeitet den Benutzer mit der übergebenen ID und ändert ggf. Vor- und Nachname.
	 * 
	 * <p> Gibt je nach Ergebnis einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Benutzer erfolgreich bearbeitet.</li>
	 * <li>1: SQL-Fehler beim Bearbeiten.</li>
	 * </ul>
	 * 
	 * @param	pId			ID des Benutzert, der bearbeitet werden soll.
	 * @param	pName		(Neuer) Vorname.
	 * @param	pSurname	(Neuer) Nachname.
	 * @return	Statuscode als Int.
	 */
	public int editUser(int pId, String pName, String pSurname);
	
	/**
	 * Gibt die komplette Artikelliste als ArrayList zurück.
	 * 
	 * @return	Komplette Artikelliste als ArrayList.
	 */
	public ArrayList<KArticle> getArticleList();
	
	/**
	 * Erstellt einen neuen Artikel mit dem übergebenen Namen und der übergebenen Beschreibung.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich erstellt.</li>
	 * <li>1: SQL-Fehler beim Erstellen.</li>
	 * </ul>
	 * 
	 * @param	pName	Name des Artikels.
	 * @param	pDesc	Beschreibung des Artikels.
	 * @return			Statuscode als Int.
	 */
	public int createArticle(String pName, String pDesc);
	
	/**
	 * Löscht den Artikel mit der übergebenen ID.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Löschen</li>
	 * </ul>
	 * 
	 * @param	id	ID des Artikels, der gelöscht werden soll.
	 * @return		Statuscode als Int.
	 */
	public int deleteArticle(int id);
	
	/**
	 * Setzt Name und Beschreibung des Artikels mit der übergebenen ID entsprechend.
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Bearbeiten.</li>
	 * </ul>
	 * 
	 * @param	pId		ID des Artikels, der bearbeitet werden soll.
	 * @param	pName	(Neuer) Name des Artikels.
	 * @param	pDesc	(Neue) Beschreibung des Artikels.
	 * @return			Statuscode als Int.
	 */
	public int editArticle(int pId, String pName, String pDesc);
	
	/**
	 * Gibt die komplette Ausleiher-Liste als ArrayList zurück.
	 * 
	 * @return	Komplette Ausleiher-Liste als ArrayList. 	
	 */
	public ArrayList<KLender> getLenderList();
	
	/**
	 * Erstellt eine neue Ausleihe.
	 * 
	 * <p>
	 * Gibt ein Int-Array der Länge 2 zurück. An erster Stelle steht der Rückgabestatus, an zweiter
	 * Stelle die ID der gerade erzeugten Tabellenzeile.
	 * </p>
	 * 
	 * <p>Die Statuscodes lauten:</p>
	 * 
	 * <ul>
	 * <li>0: Ausleihe konnte erfolgreich erzeugt werden.</li>
	 * <li>1: SQL-Fehler beim Erstellen der Ausleihe.</li>
	 * </ul>
	 * 
	 * @param	pArtId			ID des verliehenen Artikels.
	 * @param	pUId			ID des ausleihenden Benutzers.
	 * @param	pLId			ID des Ausleihers.
	 * @param	pStartDate		Startdatum der Ausleihe.
	 * @param	pEstEndDate		Voraussichtliches Enddatum der Ausleihe.
	 * @return					Statuscode als Int.
	 */
	public int[] createNewLending(int pArtId, int pUId, int pLId, String pStartDate, String pEstEndDate);
	
	/**
	 * Erzeugt einen neuen Ausleiher mit den übergebenen Daten.
	 * 
	 * <p>Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Erstellen.</li>
	 * </ul>
	 * 
	 * @param	pLName		Vorname des neuen Ausleihers.
	 * @param	pLSurname	Nachname des neuen Ausleihers.
	 * @param	pLSN		Matrikelnummer des neuen Ausleihers.
	 * @return				Statuscode als Int.
	 */
	public int createNewLender(String pLName, String pLSurname, String pLSN);
	
	/**
	 * Gibt eine Ausleihe zurück, indem ein End-Datum gesetzt wird und der Artikel wieder freigegeben wird. 
	 * 
	 * <p> Je nach Ergebnis gibt die Funktion einen anderen Statuscode als Int zurück:</p>
	 * 
	 * <ul>
	 * <li>0: Artikel erfolgreich gelöscht.</li>
	 * <li>1: SQL-Fehler beim Umtragen.</li>
	 * </ul>
	 * 
	 * @param	lendingId	ID der Ausleihe, die zurückgegeben werden soll.
	 * @param	artId		ID des Artikels, der freigegeben werden soll.
	 * @param	end_date	ID des Rückgabedatums.
	 * @return				Statuscode als Int.
	 */
	public int returnLending(int lendingId, int artId, String end_date);
	
	/**
	 * Gibt die komplette Liste der Ausleihen als ArrayList zurück.
	 * 
	 * @return	Die komplette Liste der Ausleihen als ArrayList.
	 */
	public ArrayList<KLending> getLendingList();
	
	/**
	 * Gibt die Liste der Ausleihen für den Artikel mit der als Parameter übergebenen ID als ArrayList zurück.
	 * 
	 * @param pArtId	ID der Artikels, für den die Ausleihen zurückgegeben werden sollen.
	 * @return			Liste der Ausleihen des Artikels mit der als Parameter übergebenen ID.
	 */
	public ArrayList<KLending> getLendingListForArticle(int pArtId);
	
	/**
	 * Schreibt alle Ausleihen von einem auf einen anderen Benutzer um.
	 * 
	 * @param	pOldId	ID des alten Benutzers.
	 * @param	pNewId	ID des Benutzers, auf den die Ausleihen umgeschrieben werden sollen.
	 * @return			True, wenn erfolgreich umgeschrieben werden konnte, andernfalls false.
	 */
	public boolean rewriteToNewUser(int pOldId, int pNewId);
}
