package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.List;

import de.katho.kBorrow.data.KLender;
import de.katho.kBorrow.db.DbConnector;

public class LenderModel {
	private ArrayList<KLender> data;
	private DbConnector dbCon;
	
	public LenderModel(DbConnector pDbCon){
		dbCon = pDbCon;
		updateModel();
	}
	
	public void updateModel(){
		data = dbCon.getLenderList();
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
	
	public boolean exists(String pName, String pSurname, String pSN){
		ArrayList<KLender> elems = getLenders(pName, pSurname, pSN);
		
		return elems.size() > 0 ? true : false;
	}
	
	public boolean isUnique(String pName, String pSurname, String pSN){
		ArrayList<KLender> elems = getLenders(pName, pSurname, pSN);
		
		return elems.size() == 1 ? true : false;
	}
}
