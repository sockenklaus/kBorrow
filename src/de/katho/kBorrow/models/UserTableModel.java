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

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Enth�lt die f�r die UserTable ben�tigten Daten.
 */
public class UserTableModel extends AbstractTableModel implements KGuiModel {

	/**	Serial Version UID */
	private static final long serialVersionUID = 435829735305533728L;
	
	/** Enth�lt die �berschriften der Tabellenspalten. */
	private String[] header = {"ID", "Vorname", "Nachname", "", ""};
	
	/** Daten der Tabelle: Komplette Liste aller Benutzer. */
	private ArrayList<KUser> data;
	
	/**
	 * Erzeugt das UserTableModel.
	 * 
	 * @param pModel	Referenz auf das KUserModel.
	 */
	public UserTableModel(KDataModel pModel){
		pModel.register(this);
	}
	
	/**
	 * Gibt Anzahl der Tabellenspalten zur�ck.
	 * 
	 * @return	Anzahl der Tabellenspalten.
	 */
	public int getColumnCount() {
		return header.length;
	}

	/**
	 * Gibt Anzahl der Tabellenzeilen zur�ck.
	 * 
	 * @return	Anzahl der Tabellenzeilen als Int.
	 */
	public int getRowCount() {		
		return data.size();
	}

	/**
	 * Gibt den Wert der per Parameter angegebenen Tabellenzelle zur�ck.
	 * 
	 * @param	row		Zeile der angefragten Tabellenzelle.
	 * @param	col		Spalte der angefragten Tabellenzelle.
	 * @return			Inhalt der angefragten Tabellenzelle.
	 */
	public String getValueAt(int row, int col) {
		switch(col){
		case 0:
			return String.valueOf(this.data.get(row).getId());
		
		case 1:
			return this.data.get(row).getName();
			
		case 2:
			return this.data.get(row).getSurname();
			
		default:
			return null;
		}
	}

	/**
	 * Gibt Namen der per Parameter angegebenen Tabellenspalte zur�ck.
	 * 
	 * @param	col		Spaltennummer, deren Name zur�ckgegeben werden soll.
	 * @return			Spaltenname als String.
	 */
	public String getColumnName(int col){
		if(col < header.length) return header[col];
		return "";
	}
	
	/**
	 * Gibt zur�ck, ob eine Tabellenzelle editierbar ist.
	 * 
	 * <p>Alle Spalten gr��er 2 sind editierbar.</p>
	 * 
	 * @param	row		Zeile der angefragten Zelle.
	 * @param	col		Spalte der angefragten Zelle.
	 * @return	 		Editierbarkeit einer Tabellenzelle (true, falls pCol gr��er 2).
	 */
	public boolean isCellEditable(int row, int col){
		if (col > 2) return true;
		return false;
	}
	
	/**
	 * Gibt die User-ID in der �bergebenen Zeile zur�ck.
	 * 
	 * @param	pRow	Zeile, deren User-ID ermittelt werden soll.
	 * @return			User-ID als Int.
	 */
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}
	
	/**
	 * Holt die ben�tigten Daten aus dem als Parameter �bergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KUserModel){
			data = ((KUserModel)pModel).getData();
			
			fireTableDataChanged();
		}
		
	}
}
