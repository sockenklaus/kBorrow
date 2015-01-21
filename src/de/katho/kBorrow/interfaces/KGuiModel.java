package de.katho.kBorrow.interfaces;

/**
 * Dieses Interface definiert die Eigenschaften eines KGuiModel.
 */
public interface KGuiModel {
	
	/**
	 * Holt die benötigten Daten aus dem als Parameter übergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel);
}
