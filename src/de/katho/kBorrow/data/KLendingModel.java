package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Zuständig für die Verwaltung einer Datenstruktur mit KLending-Objekten.
 * 
 * <p>
 * Verwaltet außerdem die GUI-Models, die auf die KLending-Daten zugreifen müssen und 
 * benachrichtigt die Models, wenn sich der Inhalt der Datenstruktur ändert.
 * </p>
 */
public class KLendingModel implements KDataModel {

	/** Liste mit KGuiModel-Objekten, die benachrichtigt werden müssen, wenn die Datenstruktur aktualisiert wird. */
	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	
	/** Liste mit KLender-Objekten. */
	private ArrayList<KLending> data = new ArrayList<KLending>();
	
	/** Liste mit KLender-Objekten. */
	private DbConnector dbCon;
	
	/**
	 * Erzeugt eine neue Instanz des KLendingModel.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 */
	public KLendingModel(DbConnector pDbCon) {
		dbCon = pDbCon;
		updateModel();
	}

	/**
	 * Ein KGuiModel registriert sich so am KLendingModel und wird nun benachrichtigt, wenn die Datenstruktur sich ändert.
	 * 
	 * @param pModel	KGuiModel, das sich am KLendingModel registriert.
	 */
	public void register(KGuiModel pModel) {
		if(!models.contains(pModel)){
			pModel.fetchData(this);
			models.add(pModel);
		}
		
	}

	/**
	 * Holt Daten aus der Datenbank und benachrichtigt alle registrierten KGuiModel.
	 */
	public void updateModel() {
		data = dbCon.getLendingList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
	}

	/**
	 * Gibt die kompletten Daten als ArrayList zurück.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<KLending> getData() {
		return data;
	}

	/**
	 * Gibt KLending-Objekt mit der angefragten ID zurück.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public KLending getElement(int id) {
		for(KLending elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}

}
