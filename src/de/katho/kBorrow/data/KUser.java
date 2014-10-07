package de.katho.kBorrow.data;

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
}
