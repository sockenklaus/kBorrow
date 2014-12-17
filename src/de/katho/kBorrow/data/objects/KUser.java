package de.katho.kBorrow.data.objects;

public class KUser {
	private String name;
	private String surname;
	private int id;
	
	public KUser(int pId, String pName, String pSurname){
		this.name = pName;
		this.surname = pSurname;
		this.id = pId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setName(String pName){
		this.name = pName;
	}
	
	public void setSurname(String pSurname){
		this.surname = pSurname;
	}
}
