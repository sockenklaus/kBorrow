/*
 * Copyright (C) 2015  Pascal König
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.katho.kBorrow.controller;

import java.util.HashMap;

import de.katho.kBorrow.Util;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.KLendingModel;
import de.katho.kBorrow.data.objects.KLending;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.interfaces.KDataModel;

/**
 * ManageLendingsController führt sämtliche Datenbankoperationen durch, die durch {@link de.katho.kBorrow.gui.ManageLendingsPanel} angestoßen werden.
 */
public class ManageLendingsController {

	/** Referenz auf die Datenbank */
	private DbConnector dbCon;
	
	/** Referenz auf KArticleModel, wird benötigt um Tabellen und Listen zu aktualisieren. */
	private KArticleModel articleModel;
	
	/** Referenz auf KLendingModel, wird benötigt, um Tabellen und Listen zu aktualiseren. */
	private KLendingModel lendingModel;
	
	/**
	 * Erzeugt eine neue Instanz des ManageLendingsController.
	 * 
	 * @param pDbCon	Referenz auf die Datenbank.
	 * @param models	HashMap mit KDataModels.
	 */
	public ManageLendingsController(DbConnector pDbCon, HashMap<String, KDataModel> models){
		dbCon = pDbCon;
		
		articleModel = (KArticleModel)models.get("karticlemodel");
		lendingModel = (KLendingModel)models.get("klendingmodel");
	}
	
	/**
	 * Trägt die aktive Ausleihe mit der ID "pId" wieder als verfügbar ein.
	 * Das ausgeliehene Objekt wird somit zurück gegeben.
	 * 
	 * @param pId	ID der zurückzugebenden Ausleihe.
	 */
	public void returnLending(int pId) {
		KLending lending = lendingModel.getElement(pId);
		
		int artId = lending.getArticleId();
		
		dbCon.returnLending(pId, artId, Util.getCurrentDate());
	
		articleModel.updateModel();
		lendingModel.updateModel();
	}

}
