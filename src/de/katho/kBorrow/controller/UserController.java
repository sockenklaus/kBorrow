package de.katho.kBorrow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.gui.RewriteToNewUserDialog;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class UserController {

	private DbConnector dbCon;
	private KDataModel userModel;
	private KDataModel lendingModel;
	
	public UserController(DbConnector pDbCon, HashMap<String, KDataModel> models) {
		dbCon = pDbCon;
		userModel = models.get("kusermodel");
		lendingModel = models.get("klendingmodel");
	}

	public int createUser(String pName, String pSurname){
		int status = dbCon.createUser(pName, pSurname);
		
		userModel.updateModel();
		
		return status;
	}
	
	public int editUser(int pId, String pName, String pSurname) {
		int status = dbCon.editUser(pId, pName, pSurname);
		
		if(status == 0){
			userModel.updateModel();
		}
		
		return status;
	}
	
	public boolean deleteUser(int pId) {
		if(!(lendingModel instanceof KLendingModel)) {
			KLogger.log(Level.SEVERE, "UserController: lendingModel type error!", new Exception("UserController: lendingModel type error!"));
			return false;
		}
		
		boolean isOccupied = false;
		
		ArrayList<KLending> lendingList = ((KLendingModel)lendingModel).getData();
		for(KLending elem : lendingList){
			if(elem.getUserId() == pId){
				isOccupied = true;
				break;
			}
		}
		
		if(isOccupied){
			RewriteToNewUserDialog dialog = new RewriteToNewUserDialog(pId, dbCon);
			if(dialog.getResult() == 0){
				lendingModel.updateModel();
				userModel.updateModel();
				
				return deleteUser(pId);
			}
			else return false;
				
		}
		else {
			if(dbCon.deleteUser(pId)){
				userModel.updateModel();
				lendingModel.updateModel();
			
				return true;
			}
			return false;
		}
	}
}
