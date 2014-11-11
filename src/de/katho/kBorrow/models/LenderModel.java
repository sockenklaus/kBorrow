package de.katho.kBorrow.models;

import java.util.ArrayList;

import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.db.DbConnector;

public class LenderModel {
	private ArrayList<KLender> data;
	private DbConnector dbCon;
	
	public LenderModel(DbConnector pDbCon){
		dbCon = pDbCon;
		updateModel();
	}
	
	public void updateModel(){
		data = dbCon.getLenderList();
	}
}
