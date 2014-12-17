package de.katho.kBorrow.data;

import java.util.ArrayList;

import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class KUserModel implements KDataModel {

	private ArrayList<KGuiModel> models = new ArrayList<KGuiModel>();
	private ArrayList<KUser> data = new ArrayList<KUser>();
	private DbConnector dbCon;
	
	public KUserModel(DbConnector pDbCon){
		dbCon = pDbCon;
		updateModel();
	}
	
	public void updateModel(){
		data = dbCon.getUserList();
		
		for(KGuiModel model : models){
			model.fetchData(this);
		}
	}
	
	public void register(KGuiModel pModel) {
		if(!models.contains(pModel)) {
			pModel.fetchData(this);
			models.add(pModel);		
		}
	}

	public ArrayList<KUser> getData() {
		return data;
	}

	public Object get(int id) {
		if(data.size() > id) return data.get(id);
		return null;
	}

}
