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
	
	public FreeArticleTableModel(KDataModel pModel) {
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
