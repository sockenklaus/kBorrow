package de.katho.kBorrow.interfaces;

import java.util.ArrayList;

/**
 * Dieses Interface definiert die Eigenschaften eines KDataModel.
 */
public interface KDataModel {
	
	/**
	 * Ein KGuiModel registriert sich so am KDataModel und wird nun benachrichtigt, wenn die Datenstruktur sich ändert.
	 * 
	 * @param pModel	KGuiModel, das sich am KDataModel registriert.
	 */
	public void register(KGuiModel pModel);
	
	/**
	 * Holt Daten aus der Datenbank und benachrichtigt alle registrierten KGuiModel.
	 */
	public void updateModel();
	
	/**
	 * Gibt die kompletten Daten als ArrayList zurück.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<?> getData();
	
	/**
	 * Gibt Daten-Objekt mit der angefragten ID zurück.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public Object getElement(int id);
}
