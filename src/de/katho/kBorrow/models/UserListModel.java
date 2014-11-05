package de.katho.kBorrow.models;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import de.katho.kBorrow.data.KUser;
import de.katho.kBorrow.db.DbConnector;

public class UserListModel extends DefaultComboBoxModel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8653066929273274524L;
	private DbConnector dbCon;
	private Vector<KUser> list;
	private KUser selectedItem = null;
	
	public UserListModel(DbConnector pDbCon){		
		super();
		dbCon = pDbCon;
		updateModel();
	}
	
	
	public void updateModel() {
		list = new Vector<KUser>();
		ArrayList<KUser> data = dbCon.getUserList();
		
		for(KUser elem : data){
			list.add(elem);
		}
		
		setSelectedItem(list.firstElement());
		fireIntervalAdded(this, 0, list.size()-1);
	}
	
	public void setSelectedItem(KUser object) {
		if(selectedItem == null && object == null) return;
		if(selectedItem != null && selectedItem.equals(object)) return;
		if(object!= null && getIndexOf(object) == -1) return;
		
		selectedItem = object;
		fireContentsChanged(this, -1, -1);
	}
	
	public String getSelectedItem(){
		if(selectedItem != null ) return selectedItem.getName()+" "+selectedItem.getSurname();
		return "";
	}
	
	public String getElementAt(int index){
		if(index < 0 || index >= list.size()) return null;
		
		KUser obj = list.elementAt(index);
		return obj.getName()+" "+obj.getSurname();
	}
	
	public int getSize(){
		return list.size();
	}

	public void addElement(KUser object) {
		list.addElement(object);
		int index = list.size() - 1;
		fireIntervalAdded(this, index, index);
		
		if (list.size() == 1 && selectedItem == null)
			setSelectedItem(object);
	}
	
	public int getIndexOf(KUser obj){
		return list.indexOf(obj);
	}
}

