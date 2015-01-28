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

package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.logging.Level;

import de.katho.kBorrow.KLogger;
import de.katho.kBorrow.data.KArticleModel;
import de.katho.kBorrow.data.objects.KArticle;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class FreeArticleTableModel extends ArticleTableModel implements KGuiModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908669541941834593L;
	private String[] header;
	
	public FreeArticleTableModel(KArticleModel pModel) {
		super(pModel);
		header = new String[] {"ID", "Artikelname", "Artikelbeschreibung", ""};
		pModel.register(this);
	}
	
	public int getColumnCount(){
		return header.length;
	}
	
	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KArticleModel){
			data = new ArrayList<KArticle>();
			
			for(KArticle elem : ((KArticleModel)pModel).getData()){
				if (elem.getIsFree()) data.add(elem);
			}
			
			fireTableDataChanged();
		}
		else {
			KLogger.log(Level.SEVERE, "FreeArticleModel: Typecast error!", new Exception("FreeArticleModel: Typecast error!"));
		}
		
	}
}
