package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;

public class UserListModel extends AbstractListModel<String> implements ComboBoxModel<String>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8653066929273274524L;
	private DbConnector dbCon;
	private ArrayList<KUser> data;
	private String selectedItem = null;
	
	public UserListModel(DbConnector pDbCon){		
		super();
		dbCon = pDbCon;
		updateModel();
	}
	
	
	public void updateModel() {
		data = dbCon.getUserList();
		
		if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
		
		fireIntervalAdded(this, 0, data.size()-1);
	}
	
	public void setSelectedItem(Object object) {
		System.out.println(object);
		if(selectedItem == null && object == null) return;
		if(selectedItem != null && selectedItem.equals(object)) return;
		if(object!= null && !dataContains((String)object)) return;
		
		selectedItem = (String)object;
		fireContentsChanged(this, -1, -1);
	}
	
	public String getSelectedItem(){
		if(selectedItem != null ) return selectedItem;
		return "";
	}
	
	public String getElementAt(int index){
		if(index < 0 || index >= data.size()) return null;
		
		KUser obj = data.get(index);
		return obj.getName()+" "+obj.getSurname();
	}

	public int getSize() {
		return data.size();
	}
	
	private boolean dataContains(String pName){
		for(KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return true;
		}
		return false;
	}
}

