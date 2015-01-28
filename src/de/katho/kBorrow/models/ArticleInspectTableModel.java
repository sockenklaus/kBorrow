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

import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Enthält die für die ArticleInspectTable nötigen Daten.
 */
public class ArticleInspectTableModel extends AbstractTableModel implements KGuiModel {

	/** Serial Version UID */
	private static final long serialVersionUID = 2293157709447086998L;
	
	/** Enthält die Überschriften der Tabellenspalten */
	private String[] header;
	
	/** ID des Artikels, für den alle Ausleihen in der Tabelle angezeigt werden sollen. */
	private int articleId;
	
	/** Daten der Tabelle: Alle Ausleihen für einen Artikel. */
	private ArrayList<KLending> data;
	
	/** Referenz auf das benötigte UserModel. */
	private KUserModel userModel;
	
	/** Referenz auf das benötigte LenderModel. */
	private KLenderModel lenderModel;
	
	/**
	 * Erzeugt das ArticleInspectTableModel.
	 * 
	 * @param pId		ID des Artikel, dessen Ausleihen angezeigt werden sollen.
	 * @param models	HashMap mit Referenzen auf alle KDataModels.
	 */
	public ArticleInspectTableModel(int pId, HashMap<String, KDataModel> models){
		header = new String[] {"ID", "Verliehen von:", "Ausgeliehen an:", "Ausleihdatum", "Vor. Rückgabe", "Rückgabe"};
		articleId = pId;
		userModel = (KUserModel)models.get("kusermodel");
		lenderModel = (KLenderModel)models.get("klendermodel");
		
		fetchData(((KLendingModel)models.get("klendingmodel")));
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
	 * Gibt zurück, ob eine Tabellenzelle editierbar ist.
	 * 
	 * <p>Alle Tabellenzellen sind NICHT editierbar!</p>
	 * 
	 * @param	pRow	Zeile der angefragten Zelle.
	 * @param	pCol	Spalte der angefragten Zelle.
	 * @return	 		Editierbarkeit einer Tabellenzelle (Immer false!)
	 */
	public boolean isCellEditable(int pRow, int pCol){
		return false;
	}
	
	/**
	 * Gibt den Wert der per Parameter angegebenen Tabellenzelle zurück.
	 * 
	 * @param	row		Zeile der angefragten Tabellenzelle.
	 * @param	col		Spalte der angefragten Tabellenzelle.
	 * @return			Inhalt der angefragten Tabellenzelle.
	 */
	public Object getValueAt(int row, int col) {
		switch(col){
		case 0:
			return data.get(row).getId();
		
		case 1:
			int uid = data.get(row).getUserId();
			KUser user = userModel.getElement(uid);
			
			return user.getName()+" "+user.getSurname();
			
		case 2:
			int lid = data.get(row).getLenderId();
			KLender lender = lenderModel.getElement(lid);
			
			return lender.getName()+" "+lender.getSurname()+" ("+lender.getStudentnumber()+")";
			
		case 3:
			return data.get(row).getStartDate();
			
		case 4:
			return data.get(row).getExpectedEndDate();
		
		case 5:
			return data.get(row).getEndDate();
			
		default:
			return null;
		}
	}
	
	/**
	 * Holt die benötigten Daten aus dem als Parameter übergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KLendingModel){
			data = new ArrayList<KLending>();
			
			for(KLending elem : ((KLendingModel)pModel).getData()){
				if(elem.getArticleId() == articleId) data.add(elem);
			}
			
			fireTableDataChanged();
		}
	}

}
