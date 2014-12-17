package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;

public class RewriteUserModel extends UserListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78927566018961799L;
	private int id;

	public RewriteUserModel(KDataModel pModel, int pId) {
		super(pModel);
		id = pId;
		pModel.register(this);
	}
	
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
