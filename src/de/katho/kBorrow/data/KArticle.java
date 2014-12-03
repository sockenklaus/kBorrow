package de.katho.kBorrow.data;

public class KArticle {
	private int id;
	private String name;
	private String description;
	private boolean isFree;

	public KArticle(int pId, String pName, boolean pFree, String pDesc) {
		this.id = pId;
		this.name = pName;
		this.description = pDesc;
		isFree = pFree;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}
	
	public boolean getIsFree(){
		return isFree;
	}
	
	public void setName(String pName){
		this.name = pName;
	}
	
	public void setDescription(String pDesc){
		this.description = pDesc;
	}
	
	public void setIsFree(boolean pFree){
		isFree = pFree;
	}

}
