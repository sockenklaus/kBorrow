package de.katho.kBorrow.controller;

import java.util.ArrayList;
import java.util.HashMap;

import de.katho.kBorrow.data.KLending;
import de.katho.kBorrow.db.DbConnector;
import de.katho.kBorrow.gui.RewriteToNewUserDialog;
import de.katho.kBorrow.models.LendingTableModel;
import de.katho.kBorrow.models.UserTableModel;
import de.katho.kBorrow.models.UserListModel;

public class UserController {

	private DbConnector dbCon;
	private UserTableModel userTableModel;
	private UserListModel userListModel;
	private LendingTableModel lendingTableModel;
	
	public UserController(DbConnector pDbCon, HashMap<String, Object> pModels) {
		dbCon = pDbCon;
		userTableModel = (UserTableModel)pModels.get("usertablemodel");
		userListModel = (UserListModel)pModels.get("userlistmodel");
		lendingTableModel = (LendingTableModel)pModels.get("lendingtablemodel");
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
	
	public boolean deleteUser(int pRow) {
		int id = userTableModel.getUserByRow(pRow).getId();
		
		boolean isOccupied = false;
		ArrayList<KLending> lendingList = lendingTableModel.getLendingList();
		for(KLending elem : lendingList){
			if(elem.getUserId() == id){
				isOccupied = true;
				break;
			}
		}
		
		if(isOccupied){
			RewriteToNewUserDialog dialog = new RewriteToNewUserDialog(id, dbCon);
			if(dialog.getResult() == 0){
				lendingTableModel.updateModel();
				userTableModel.updateModel();
				userListModel.updateModel();
				
				return deleteUser(pRow);
			}
			else return false;
				
		}
		else {
			if(dbCon.deleteUser(id)){
				userTableModel.updateModel();
				userListModel.updateModel();
				lendingTableModel.updateModel();
			
				return true;
			}
			return false;
		}
	}
}
