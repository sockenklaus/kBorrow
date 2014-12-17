package de.katho.kBorrow.models;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.data.objects.KUser;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.interfaces.KGuiModel;

public class UserListModel extends AbstractListModel<String> implements ComboBoxModel<String>, KGuiModel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8653066929273274524L;
	protected ArrayList<KUser> data;
	protected String selectedItem = null;
	
	public UserListModel(KDataModel pUserModel){		
		super();
		pUserModel.register(this);
	}
	
	public void setSelectedItem(Object object) {
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
	
	public int getIdByFullname(String pName){
		for (KUser elem : data){
			if(pName.equals(elem.getName()+" "+elem.getSurname())) return elem.getId();
		}
		return -1;
	}
	
	public KUser getUserById(int pId){
		for (KUser elem : data){
			if(elem.getId() == pId) return elem;
		}
		return null;
	}

	public void fetchData(KDataModel pModel) {
		if(pModel instanceof KUserModel){
			data = ((KUserModel)pModel).getData();
		}
		
		if(data.size() > 0) setSelectedItem(data.get(0).getName()+" "+data.get(0).getSurname());
		
		fireIntervalAdded(this, 0, data.size()-1);
	}
	
	
}

