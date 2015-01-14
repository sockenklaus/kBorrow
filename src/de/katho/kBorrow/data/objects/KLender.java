package de.katho.kBorrow.data.objects;

/**
 * Bildet einen Ausleiher ab.
 */
public class KLender {
	
	/** Vorname des Ausleihers. */
	private String name;
	
	/** Nachname des Ausleihers. */
	private String surname;
	
	/** Matrikelnummer des Ausleihers. */
	private int studentnumber;
	
	/** ID des Ausleihers. */
	private int id;
	
	/**
	 * Erzeugt ein neues KLender-Objekt.
	 * 
	 * @param pId				ID des Ausleihers.
	 * @param pName				Vorname des Ausleihers.
	 * @param pSurname			Nachname des Ausleihers.
	 * @param pStudentnumber	Matrikelnummer des Ausleihers.
	 */
	public KLender(int pId, String pName, String pSurname, int pStudentnumber){
		id = pId;
		name = pName;
		surname = pSurname;
		studentnumber = pStudentnumber;
	}
	
	/**
	 * Gibt Vorname des Ausleihers zur�ck.
	 * 
	 * @return	Vorname des Ausleihers.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gibt Nachnamen des Ausleihers zur�ck.
	 * 
	 * @return	Nachname des Ausleihers.
	 */
	public String getSurname(){
		return surname;
	}
	
	/**
	 * Gibt Matrikelnummer des Ausleihers zur�ck.
	 * 
	 * @return	Matrikelnummer des Ausleihers.
	 */
	public int getStudentnumber(){
		return studentnumber;
	}
	
	/**
	 * Gibt ID des Ausleihers zur�ck.
	 * 
	 * @return	ID des Ausleihers.
	 */
	public int getId(){
		return id;
	}
}
