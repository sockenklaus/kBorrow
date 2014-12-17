package de.katho.kBorrow.data.objects;

public class KArticle {
	private int id;
	private String name;
	private String description;
	private boolean isFree;

	public KArticle(int pId, String pName, boolean pFree, String pDesc) {
		id = pId;
		name = pName;
		description = pDesc;
		isFree = pFree;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean getIsFree(){
		return isFree;
	}
	
	public void setName(String pName){
		name = pName;
	}
	
	public void setDescription(String pDesc){
		description = pDesc;
	}
	
	public void setIsFree(boolean pFree){
		isFree = pFree;
	}

}
