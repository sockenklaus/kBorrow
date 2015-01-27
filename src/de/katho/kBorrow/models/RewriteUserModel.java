package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * Enthält die Benutzer, auf die Ausleihen beim Löschen eines Benutzers umgeschrieben werden können.
 */
public class RewriteUserModel extends UserListModel {

	/** Serial Version UID */
	private static final long serialVersionUID = -78927566018961799L;
	
	/** ID des Benutzers, dessen Ausleihen umgeschrieben werden sollen. */
	private int id;

	/**
	 * Erzeugt das RewriteUserModel.
	 * 
	 * @param	pModel	Referenz auf das KUserModel.
	 * @param 	pId		ID des Benutzers, dessen Ausleihen umgeschrieben werden sollen.
	 */
	public RewriteUserModel(KUserModel pModel, int pId) {
		super(pModel);
		id = pId;
		pModel.register(this);
	}
	
	/**
	 * Gibt die ID eines Benutzers zurück, dessen voller 
	 * Name (Vor- und Nachname) per Parameter übergeben wird.
	 * 
	 * @param	pName	Vor- und Nachname als String.
	 * @return			Benutzer-ID als Int.
	 */
	public int getIdByFullname(String pName){
		for (KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return elem.getId();
		}
		return -1;
	}
	
	/**
	 * Holt die benötigten Daten aus dem als Parameter übergebenen KDataModel.
	 * 
	 * @param	pModel	KDataModel, von dem die Daten geholt werden sollen.
	 */
	public void fetchData(KDataModel pModel){
		if(pModel instanceof KUserModel){
			data = new ArrayList<KUser>();
			
			for(KUser elem : ((KUserModel)pModel).getData()){
				if(elem.getId() != id) data.add(elem);
			}
			
			if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
			
			fireIntervalAdded(this, 0, data.size()-1);
		}
		else {
			KLogger.log(Level.SEVERE, "RewriteUserModel: Typecast error!", new Exception("RewriteUserModel: Typecast error!"));
		}
	}
}
