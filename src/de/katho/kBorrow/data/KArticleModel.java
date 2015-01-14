package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

/**
 * Zust�ndig f�r die Verwaltung einer Datenstruktur mit KArticle-Objekten.
 * 
 * <p>
 * Verwaltet au�erdem die GUI-Models, die auf die KArticle-Daten zugreifen m�ssen und 
 * benachrichtigt die Models, wenn sich der Inhalt der Datenstruktur �ndert.
 * </p>
 */
public class KArticleModel implements KDataModel {

	/** Liste mit KGuiModel-Objekten, die benachrichtigt werden m�ssen, wenn die Datenstruktur aktualisiert wird. */
	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	
	/** Liste mit KArticle-Objekten. */
	private ArrayList<KArticle> data = new ArrayList<KArticle>();
	
	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/**
	 * Erzeugt eine neue Instanz des KArticleModel.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 */
	public KArticleModel(DbConnector pDbCon) {
		dbCon = pDbCon;
		updateModel();
	}

	/**
	 * Ein KGuiModel registriert sich so am KArticleModel und wird nun benachrichtigt, wenn die Datenstruktur sich �ndert.
	 * 
	 * @param pModel	KGuiModel, das sich am KArticleModel registriert.
	 */
	public void register(KGuiModel pModel) {
		if(!models.contains(pModel)){
			pModel.fetchData(this);
			models.add(pModel);
		}
	}
	
	/**
	 * Holt Daten au sder Datenbank und benachrichtigt alle registrierten KGuiModel.
	 */
	public void updateModel() {
		data = dbCon.getArticleList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
	}

	/**
	 * Gibt die kompletten Daten als ArrayList zur�ck.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<KArticle> getData() {
		return data;
	}

	/**
	 * Gibt KArticle-Objekt mit der angefragten ID zur�ck.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public KArticle getElement(int id) {
		for (KArticle elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}

}
