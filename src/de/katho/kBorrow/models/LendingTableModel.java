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

package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class LendingTableModel extends AbstractTableModel implements KGuiModel {

	/**	Serial Version UID */
	private static final long serialVersionUID = 1375465648631587292L;
	
	/** Enthält die Überschriften der Tabellenspalten. */
	private String[] header = {"ID", "Artikel", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", ""};
	
	/** Daten der Tabelle: Sämtliche aktiven Ausleihen. */
	private ArrayList<KLending> data;
	
	/** Referenz auf das ArticleModel */
	private KDataModel articleModel;
	
	/** Referenz auf das LenderModel */
	private KDataModel lenderModel;
	
	/** Referenz auf das UserModel */
	private KDataModel userModel;
	
	/** Referenz auf das LendingModel */
	private KDataModel lendingModel;
	
	/**
	 * Erzeugt das LendingTableModel.
	 * 
	 * @param	pModels		HashMap mit Referenzen auf alle KDataModels
	 */
	public LendingTableModel(HashMap<String, KDataModel> pModels ) {
		articleModel = pModels.get("karticlemodel");
		lenderModel = pModels.get("klendermodel");
		userModel = pModels.get("kusermodel");
		lendingModel = pModels.get("klendingmodel");
		
		lendingModel.register(this);
	}

	/**
	 * Gibt Anzahl der Tabellenspalten zurück.
	 * 
	 * @return	Anzahl der Tabellenspalten.
	 */
	public int getColumnCount() {
		return header.length;
	}
	
	/**
	 * Gibt Namen der per Parameter angegebenen Tabellenspalte zurück.
	 * 
	 * @param	col		Spaltennummer, deren Name zurückgegeben werden soll.
	 * @return			Spaltenname als String.
	 */
	public String getColumnName(int col){
		if(col < header.length) return header[col];
		return "";
	}
	
	/**
	 * Gibt Anzahl der Tabellenzeilen zurück.
	 * 
	 * @return	Anzahl der Tabellenzeilen als Int.
	 */
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Gibt den Wert der per Parameter angegebenen Tabellenzelle zurück.
	 * 
	 * @param	row		Zeile der angefragten Tabellenzelle.
	 * @param	col		Spalte der angefragten Tabellenzelle.
	 * @return			Inhalt der angefragten Tabellenzelle.
	 */
	public Object getValueAt(int row, int col) {
		switch (col){
		case 0:
			return data.get(row).getId();
			
		case 1:
			int artId = data.get(row).getArticleId();
			KArticle art = (KArticle) articleModel.getElement(artId);
			
			return art.getName();
			
		case 2:
			int uId = data.get(row).getUserId();
			KUser user = (KUser) userModel.getElement(uId);
			
			return user.getName()+" "+user.getSurname();
			
		case 3:
			int lenderId = data.get(row).getLenderId();
			KLender lender = (KLender) lenderModel.getElement(lenderId);
			
			return lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")";
		
		case 4:
			return data.get(row).getStartDate();
			
		case 5:
			return data.get(row).getExpectedEndDate();
		
		default:
			return null;
		}
	}
	
	/**
	 * Gibt zurück, ob eine Tabellenzelle editierbar ist.
	 * 
	 * <p>Alle Spalten größer 4 sind editierbar.</p>
	 * 
	 * @param	row		Zeile der angefragten Zelle.
	 * @param	col		Spalte der angefragten Zelle.
	 * @return	 		Editierbarkeit einer Tabellenzelle (true, falls col größer 4).
	 */
	public boolean isCellEditable(int row, int col){
		if (col > 4) return true;
		return false;
	}
	
	/**
	 * Holt die benötigten Daten aus dem als Parameter übergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel) {
		data = new ArrayList<KLending>();
		
		if(pModel instanceof KLendingModel){
			for(KLending elem : ((KLendingModel)pModel).getData()){
				if(elem.getEndDate() == null || elem.getEndDate().equals("")) data.add(elem);
			}
			fireTableDataChanged();
		}		
	}
	
	/**
	 * Gibt die Ausleihen-ID in der übergebenen Zeile zurück.
	 * 
	 * @param	pRow	Zeile, deren Ausleihen-ID ermittelt werden soll.
	 * @return			Ausleihen-ID als Int.
	 */
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}

}
