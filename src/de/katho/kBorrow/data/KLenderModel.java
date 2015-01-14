package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Zuständig für die Verwaltung einer Datenstruktur mit KLender-Objekten.
 * 
 * <p>
 * Verwaltet außerdem die GUI-Models, die auf die KLender-Daten zugreifen müssen und 
 * benachrichtigt die Models, wenn sich der Inhalt der Datenstruktur ändert.
 * </p>
 */
public class KLenderModel implements KDataModel {

	/** Liste mit KGuiModel-Objekten, die benachrichtigt werden müssen, wenn die Datenstruktur aktualisiert wird. */
	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	
	/** Liste mit KLender-Objekten. */
	private ArrayList<KLender> data = new ArrayList<KLender>();
	
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/**
	 * Erzeugt eine neue Instanz des KLenderModel.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 */
	public KLenderModel(DbConnector pDbCon) {
		dbCon = pDbCon;
		updateModel();
	}

	/**
	 * Ein KGuiModel registriert sich so am KLenderModel und wird nun benachrichtigt, wenn die Datenstruktur sich ändert.
	 * 
	 * @param pModel	KGuiModel, das sich am KLenderModel registriert.
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
		data = dbCon.getLenderList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
		
	}

	/**
	 * Gibt die kompletten Daten als ArrayList zurück.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<KLender> getData() {
		return data;
	}
	
	/**
	 * Gibt KLender-Objekt mit der angefragten ID zurück.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public KLender getElement(int id) {
		for(KLender elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}
	
	/**
	 * Gibt eine ArrayList mit KLender-Objekten zurück, auf die alle als Parameter übergebenen Suchkriterien zutreffen.
	 * 
	 * @param pName		Vorname, nach dem gesucht werden soll.
	 * @param pSurname	Nachname, nach dem gesucht werden soll.
	 * @param pSN		Matrikelnummer, nach der gesucht werden soll.
	 * @return			ArrayList mit KLender-Objekten.
	 */
	public ArrayList<KLender> getLenders(String pName, String pSurname, String pSN){
		boolean nameEmpty = pName.isEmpty();
		boolean surnameEmpty = pSurname.isEmpty();
		boolean snEmpty = pSN.isEmpty();
		ArrayList<KLender> elems = new ArrayList<KLender>();
		int sn;
		
		if(pSN.matches("[0-9]+")){
			sn = Integer.parseInt(pSN);
		}
		else {
			sn = -1;
		}
				
		if(!nameEmpty){
			if(!surnameEmpty){
				if(!snEmpty){
					// Alles gegeben
					for(KLender e : data){
						if(e.getName().equals(pName) && e.getSurname().equals(pSurname) && e.getStudentnumber() == sn){
							elems.add(e);
						}
					}
					return elems;
				}
				//Name und Surname gegeben
				for(KLender e : data){
					if(e.getName().equals(pName) && e.getSurname().equals(pSurname)){
						elems.add(e);
					}
				}
				return elems;
			}
			if(!snEmpty){
				// Name und SN gegeben
				for (KLender e : data){
					if(e.getName().equals(pName) && e.getStudentnumber() == sn){
						elems.add(e);
					}
				}
				return elems;
			}
			// Nur Name gegeben
			for (KLender e : data){
				if(e.getName().equals(pName)){
					elems.add(e);
				}
			}
			return elems;
		}
		
		if(!surnameEmpty){
			if(!snEmpty){
				// Surname und SN gegeben
				for (KLender e : data){
					if(e.getSurname().equals(pSurname) && e.getStudentnumber() == sn){
						elems.add(e);
					}
				}
				return elems;
			}
			// Nur Surname gegeben
			for (KLender e : data){
				if(e.getSurname().equals(pSurname)){
					elems.add(e);
				}
			}
			return elems;
		}
		
		if(!snEmpty){
			// Nur SN gegeben
			for (KLender e : data){
				if(e.getStudentnumber() == sn){
					elems.add(e);
				}
			}
			return elems;
		}
		
		return elems;
	}

}
