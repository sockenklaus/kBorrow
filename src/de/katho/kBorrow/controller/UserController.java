package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.UserTableModel;
import de.katho.kBorrow.models.UserListModel;

public class UserController {

	private DbConnector dbCon;
	private UserTableModel userTableModel;
	private UserListModel userListModel;
	
	public UserController(DbConnector pDbCon, HashMap<String, Object> pModels) {
		dbCon = pDbCon;
		userTableModel = (UserTableModel)pModels.get("usertablemodel");
		userListModel = (UserListModel)pModels.get("userlistmodel");
	}

	public int createUser(String pName, String pSurname){
		int status = dbCon.createUser(pName, pSurname);
		
		userTableModel.updateModel();
		userListModel.updateModel();
		
		return status;
	}
	
	public int editUser(int pId, String pName, String pSurname) {
		int status = dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			userTableModel.updateModel();
			userListModel.updateModel();
		}
		
		return status;
	}
	
	public boolean deleteUser(int pRow){
		int id = userTableModel.getUserByRow(pRow).getId();
		
		if(dbCon.deleteUser(id)){
			userTableModel.updateModel();
			userListModel.updateModel();
			
			return true;
		}
		return false;
	}

}
