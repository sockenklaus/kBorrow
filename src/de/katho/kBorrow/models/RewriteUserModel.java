package de.katho.kBorrow.models;

import de.katho.kBorrow.db.DbConnector;

public class RewriteUserModel extends UserListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -78927566018961799L;
	private int id;

	public RewriteUserModel(DbConnector pDbCon, int pId) {
		super(pDbCon);
		id = pId;
		updateModel();
	}
	
	public void updateModel(){
		data = dbCon.getRewriteUserList(id);
		
		if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
		
		fireIntervalAdded(this, 0, data.size()-1);
	}

}
