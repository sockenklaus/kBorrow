package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Zuständig für die Verwaltung einer Datenstruktur mit KUser-Objekten.
 * 
 * <p>
 * Verwaltet außerdem die GUI-Models, die auf die KUser-Daten zugreifen müssen und 
 * benachrichtigt die Models, wenn sich der Inhalt der Datenstruktur ändert.
 * </p>
 */
public class KUserModel implements KDataModel {

	/** Liste mit KGuiModel-Objekten, die benachrichtigt werden müssen, wenn die Datenstruktur aktualisiert wird. */
	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	
	/** Liste mit KUser-Objekten. */
	private ArrayList<KUser> data = new ArrayList<KUser>();
	
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/**
	 * Erzeugt eine neue Instanz des KUserModel.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 */
	public KUserModel(DbConnector pDbCon){
		dbCon = pDbCon;
		updateModel();
	}
	
	/**
	 * Holt Daten aus der Datenbank und benachrichtigt alle registrierten KGuiModel.
	 */
	public void updateModel(){
		data = dbCon.getUserList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
	}
	
	/**
	 * Ein KGuiModel registriert sich so am KUserModel und wird nun benachrichtigt, wenn die Datenstruktur sich ändert.
	 * 
	 * @param pModel	KGuiModel, das sich am KUserModel registriert.
	 */
	public void register(KGuiModel pModel) {
		if(!models.contains(pModel)) {
			pModel.fetchData(this);
			models.add(pModel);		
		}
	}

	/**
	 * Gibt die kompletten Daten als ArrayList zurück.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<KUser> getData() {
		return data;
	}

	/**
	 * Gibt KUser-Objekt mit der angefragten ID zurück.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public KUser getElement(int id) {
		for(KUser elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}
	
	/**
	 * Gibt die ID eines KUser-Objekts basierend auf dem vollen Namen zurück.
	 * 
	 * @param pName		Voller Name des Benutzers, nach dem gesucht wird.
	 * @return			ID des Benutzers.
	 */
	public int getIdByFullname(String pName){
		for (KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return elem.getId();
		}
		return -1;
	}

}
