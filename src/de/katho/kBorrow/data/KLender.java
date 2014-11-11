package de.katho.kBorrow.data;

public class KLender {
	private String name;
	private String surname;
	private int studentnumber;
	private int id;
	
	public KLender(int pId, String pName, String pSurname, int pStudentnumber){
		id = pId;
		name = pName;
		surname = pSurname;
		studentnumber = pStudentnumber;
	}
	
	public String getName(){
		return name;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public int getStudentnumber(){
		return studentnumber;
	}
	
	public int getId(){
		return id;
	}
}
