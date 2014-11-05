package de.katho.kBorrow.controller;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.UserModel;

public class UserController {

	private DbConnector dbCon;
	private UserModel userModel;
	
	public UserController(DbConnector pDbCon, UserModel pModel) {
		dbCon = pDbCon;
		userModel = pModel;
	}

	public int createUser(String pName, String pSurname){
		int status = dbCon.createUser(pName, pSurname);
		
		userModel.updateTable();
		
		return status;
	}
	
	public int editUser(int pId, String pName, String pSurname) {
		int status = dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			int row = userModel.getRowFromId(pId);
			
			userModel.getUserByRow(row).setName(pName);
			userModel.getUserByRow(row).setSurname(pSurname);
			userModel.fireTableRowsUpdated(row, row);
		}
		
		return status;
	}
	
	public boolean deleteUser(int id){
		if(dbCon.deleteUser(id)){
			int row = userModel.getRowFromId(id);
			
			userModel.removeRow(row);
			
			return true;
		}
		return false;
	}

}
