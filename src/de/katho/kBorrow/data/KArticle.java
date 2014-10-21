package de.katho.kBorrow.data;

public class KArticle {
	private int id;
	private String name;
	private String description;

	public KArticle(int pId, String pName, String pDesc) {
		this.id = pId;
		this.name = pName;
		this.description = pDesc;
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
	
	public void setName(String pName){
		this.name = pName;
	}
	
	public void setDescription(String pDesc){
		this.description = pDesc;
	}

}
