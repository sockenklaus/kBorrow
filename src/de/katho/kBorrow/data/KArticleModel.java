package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class KArticleModel implements KDataModel {

	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	private ArrayList<KArticle> data = new ArrayList<KArticle>();
	private DbConnector dbCon;
	
	public KArticleModel(DbConnector pDbCon) {
		dbCon = pDbCon;
		updateModel();
	}

	public void register(KGuiModel pModel) {
		if(!models.contains(pModel)){
			pModel.fetchData(this);
			models.add(pModel);
		}
	}

	public void updateModel() {
		data = dbCon.getArticleList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
	}

	public ArrayList<KArticle> getData() {
		return data;
	}

	public KArticle getElement(int id) {
		for (KArticle elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}

}
