package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.models.UserModel;
import de.katho.kBorrow.models.UserListModel;

public class UserController {

	private DbConnector dbCon;
	private UserModel userModel;
	private UserListModel userListModel;
	
	public UserController(DbConnector pDbCon, HashMap<String, Object> pModels) {
		dbCon = pDbCon;
		userModel = (UserModel)pModels.get("usermodel");
		userListModel = (UserListModel)pModels.get("userlistmodel");
	}

	public int createUser(String pName, String pSurname){
		int status = dbCon.createUser(pName, pSurname);
		
		userModel.updateModel();
		userListModel.updateModel();
		
		return status;
	}
	
	public int editUser(int pId, String pName, String pSurname) {
		int status = dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			userModel.updateModel();
			userListModel.updateModel();
		}
		
		return status;
	}
	
	public boolean deleteUser(int id){
		if(dbCon.deleteUser(id)){
			userModel.updateModel();
			userListModel.updateModel();
			
			return true;
		}
		return false;
	}

}
