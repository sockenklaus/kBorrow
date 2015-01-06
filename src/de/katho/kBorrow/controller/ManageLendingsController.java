package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

public class ManageLendingsController {

	private DbConnector dbCon;
	private KArticleModel articleModel;
	private KLendingModel lendingModel;
	
	public ManageLendingsController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		
		articleModel = (KArticleModel)models.get("karticlemodel");
		lendingModel = (KLendingModel)models.get("klendingmodel");
	}
	
	public void returnLending(int pId) {
		KLending lending = lendingModel.getElement(pId);
		
		int artId = lending.getArticleId();
		
		dbCon.returnLending(pId, artId, Util.getCurrentDate());
	
		articleModel.updateModel();
		lendingModel.updateModel();
	}

}
