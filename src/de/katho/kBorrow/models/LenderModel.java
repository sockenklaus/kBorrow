package de.katho.kBorrow.models;

import java.util.ArrayList;

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
	
	public KLender getLender(String pName, String pSurname, String pSN){
		boolean nameEmpty = pName.isEmpty();
		boolean surnameEmpty = pSurname.isEmpty();
		boolean snEmpty = pSN.isEmpty();
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
							return e;
						}
					}
				}
				//Name und Surname gegeben
				for(KLender e : data){
					if(e.getName().equals(pName) && e.getSurname().equals(pSurname)){
						return e;
					}
				}
			}
			if(!snEmpty){
				// Name und SN gegeben
				for (KLender e : data){
					if(e.getName().equals(pName) && e.getStudentnumber() == sn){
						return e;
					}
				}
			}
			// Nur Name gegeben
			for (KLender e : data){
				if(e.getName().equals(pName)){
					return e;
				}
			}
		}
		
		if(!surnameEmpty){
			if(!snEmpty){
				// Surname und SN gegeben
				for (KLender e : data){
					if(e.getSurname().equals(pSurname) && e.getStudentnumber() == sn){
						return e;
					}
				}
			}
			// Nur Surname gegeben
			for (KLender e : data){
				if(e.getSurname().equals(pSurname)){
					return e;
				}
			}
		}
		
		if(!snEmpty){
			// Nur SN gegeben
			for (KLender e : data){
				if(e.getStudentnumber() == sn){
					return e;
				}
			}
		}
		
		return null;
	}
}
