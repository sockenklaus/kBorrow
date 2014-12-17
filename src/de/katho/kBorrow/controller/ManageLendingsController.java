package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.models.ArticleTableModel;
import de.katho.kBorrow.models.FreeArticleTableModel;
import de.katho.kBorrow.models.LendingTableModel;

public class ManageLendingsController {

	private DbConnector dbCon;
	private FreeArticleTableModel freeArticleTableModel;
	private ArticleTableModel articleTableModel;
	private LendingTableModel lendingTableModel;
	
	public ManageLendingsController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		
		freeArticleTableModel = (FreeArticleTableModel)models.get("freearticletablemodel");
		articleTableModel = (ArticleTableModel)models.get("articletablemodel");
		lendingTableModel = (LendingTableModel)models.get("lendingtablemodel");
	}
	
	public void returnLending(int pRow) {
		KLending lending = lendingTableModel.getLendingByRow(pRow);
		
		int artId = lending.getArticleId();
		int lendingId = lending.getId();
		
		dbCon.returnLending(lendingId, artId, Util.getCurrentDate());
		
		freeArticleTableModel.updateModel();
		articleTableModel.updateModel();
		lendingTableModel.updateModel();
	}

}
