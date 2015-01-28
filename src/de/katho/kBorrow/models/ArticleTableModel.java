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

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Enth�lt die f�r die ArticleTable ben�tigten Daten.
 */
public class ArticleTableModel extends AbstractTableModel implements KGuiModel {

	/** Serial Version UID */
	private static final long serialVersionUID = -1860949772989116745L;
	
	/** Enth�lt die �berschriften der Tabellenspalten */
	protected String[] header;
	
	/** Daten der Tabelle: Komplette Liste der Artikel */
	protected ArrayList<KArticle> data = new ArrayList<KArticle>();

	/**
	 * Erzeugt das ArticleTableModel.
	 * 
	 * @param pModel	Referenz auf das KArticleModel
	 */
	public ArticleTableModel(KArticleModel pModel){
		header = new String [] {"ID", "Artikelname", "Artikelbeschreibung", "", "", ""};
		pModel.register(this);
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
	 * @param	pRow	Zeile der angefragten Tabellenzelle.
	 * @param	pCol	Spalte der angefragten Tabellenzelle.
	 * @return			Inhalt der angefragten Tabellenzelle.
	 */
	public Object getValueAt(int pRow, int pCol) {
		switch(pCol){
		case 0:
			return String.valueOf(data.get(pRow).getId());
			
		case 1:
			return data.get(pRow).getName();
			
		case 2:
			return data.get(pRow).getDescription();
			
		default:
			return null;
		}	
	}
	
	/**
	 * Gibt zur�ck, ob eine Tabellenzelle editierbar ist.
	 * 
	 * <p>Alle Spalten gr��er 2 sind editierbar.</p>
	 * 
	 * @param	pRow	Zeile der angefragten Zelle.
	 * @param	pCol	Spalte der angefragten Zelle.
	 * @return	 		Editierbarkeit einer Tabellenzelle (true, falls pCol gr��er 2).
	 */
	public boolean isCellEditable(int pRow, int pCol){
		if (pCol > 2) return true;
		return false;
	}
		
	/**
	 * Gibt die Artikel-ID in der �bergebenen Zeile zur�ck.
	 * 
	 * @param	pRow	Zeile, deren Artikel-ID ermittelt werden soll.
	 * @return			Artikel-ID als Int.
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
		if(pModel instanceof KArticleModel){
			data = ((KArticleModel)pModel).getData();
			
			fireTableDataChanged();
		}
		
	}

}
