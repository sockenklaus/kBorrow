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

	public KUser getElement(int id) {
		for(KUser elem : data){
			if(elem.getId() == id) return elem;
		}
		return null;
	}
	
	public int getIdByFullname(String pName){
		for (KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return elem.getId();
		}
		return -1;
	}

}
