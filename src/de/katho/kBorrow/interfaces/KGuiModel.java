package de.katho.kBorrow.interfaces;

/**
 * Dieses Interface definiert die Eigenschaften eines KGuiModel.
 */
public interface KGuiModel {
	
	/**
	 * Holt die ben�tigten Daten aus dem als Parameter �bergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel);
}
