package de.katho.kBorrow.models;

import java.util.ArrayList;

import de.katho.kBorrow.data.KLenderModel;
import de.katho.kBorrow.data.objects.KLender;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class LenderModel implements KGuiModel {
	private ArrayList<KLender> data;
	
	public LenderModel(KDataModel pModel){
		pModel.register(this);
	}
	
	public ArrayList<KLender> getList(){
		return data;
	}
	
	public ArrayList<KLender> getLenders(String pName, String pSurname, String pSN){
		boolean nameEmpty = pName.isEmpty();
		boolean surnameEmpty = pSurname.isEmpty();
		boolean snEmpty = pSN.isEmpty();
		ArrayList<KLender> elems = new ArrayList<KLender>();
		int sn;
		
		if(pSN.matches("[0-9]+")){
			sn = Integer.parseInt(pSN);
		}
		else {
			sn = -1;
		}
				
		if(!nameEmpty){
			if(!surnameEmpty){
				if(!snEmpty){
					// Alles gegeben
					for(KLender e : data){
						if(e.getName().equals(pName) && e.getSurname().equals(pSurname) && e.getStudentnumber() == sn){
							elems.add(e);
						}
					}
					return elems;
				}
				//Name und Surname gegeben
				for(KLender e : data){
					if(e.getName().equals(pName) && e.getSurname().equals(pSurname)){
						elems.add(e);
					}
				}
				return elems;
			}
			if(!snEmpty){
				// Name und SN gegeben
				for (KLender e : data){
					if(e.getName().equals(pName) && e.getStudentnumber() == sn){
						elems.add(e);
					}
				}
				return elems;
			}
			// Nur Name gegeben
			for (KLender e : data){
				if(e.getName().equals(pName)){
					elems.add(e);
				}
			}
			return elems;
		}
		
		if(!surnameEmpty){
			if(!snEmpty){
				// Surname und SN gegeben
				for (KLender e : data){
					if(e.getSurname().equals(pSurname) && e.getStudentnumber() == sn){
						elems.add(e);
					}
				}
				return elems;
			}
			// Nur Surname gegeben
			for (KLender e : data){
				if(e.getSurname().equals(pSurname)){
					elems.add(e);
				}
			}
			return elems;
		}
		
		if(!snEmpty){
			// Nur SN gegeben
			for (KLender e : data){
				if(e.getStudentnumber() == sn){
					elems.add(e);
				}
			}
			return elems;
		}
		
		return elems;
	}
	
	public KLender getLenderById(int pId){
		for(KLender elem : data){
			if(elem.getId() == pId) return elem;
		}
		return null;
	}
	
	public KLender getLenderByRow(int pRow){
		return data.get(pRow);
	}

	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KLenderModel){
			data = ((KLenderModel)pModel).getData();
		}
	}
}
