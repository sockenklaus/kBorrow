package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class KLenderModel implements KDataModel {

	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	private ArrayList<KLender> data = new ArrayList<KLender>();
	private DbConnector dbCon;
	
	public KLenderModel(DbConnector pDbCon) {
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
		data = dbCon.getLenderList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
		
	}

	public ArrayList<KLender> getData() {
		return data;
	}

	public Object get(int id) {
		if(data.size() > id) return data.get(id);
		return null;
	}

}
