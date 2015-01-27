package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Enthält die für die ArticleTable benötigten Daten.
 */
public class ArticleTableModel extends AbstractTableModel implements KGuiModel {

	/** Serial Version UID */
	private static final long serialVersionUID = -1860949772989116745L;
	
	/** Enthält die Überschriften der Tabellenspalten */
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
	 * Gibt Anzahl der Tabellenspalten zurück.
	 * 
	 * @return	Anzahl der Tabellenspalten.
	 */
	public int getColumnCount() {
		return header.length;
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
	 * Gibt zurück, ob eine Tabellenzelle editierbar ist.
	 * 
	 * <p>Alle Spalten größer 2 sind editierbar.</p>
	 * 
	 * @param	pRow	Zeile der angefragten Zelle.
	 * @param	pCol	Spalte der angefragten Zelle.
	 * @return	 		Editierbarkeit einer Tabellenzelle (true, falls pCol größer 2).
	 */
	public boolean isCellEditable(int pRow, int pCol){
		if (pCol > 2) return true;
		return false;
	}
		
	/**
	 * Gibt die Artikel-ID in der übergebenen Zeile zurück.
	 * 
	 * @param	pRow	Zeile, deren Artikel-ID ermittelt werden soll.
	 * @return			Artikel-ID als Int.
	 */
	public int getIdFromRow(int pRow){
		return data.get(pRow).getId();
	}

	/**
	 * Holt die benötigten Daten aus dem als Parameter übergebenen KDataModel.
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
