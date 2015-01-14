package de.katho.kBorrow.data.objects;

/**
 * Bildet eine Ausleihe ab.
 */
public class KLending {
	/** ID der Ausleihe */
	private int id;
	
	/** ID des Benutzers, der die Ausleihe erstellt hat. */
	private int user_id;
	
	/** ID des verliehenen Artikels. */
	private int article_id;
	
	/** ID des Ausleihers. */
	private int lender_id;
	
	/** Start der Ausleihe. */
	private String start_date;
	
	/** Voraussichtliches Ende der Ausleihe. */
	private String expected_end_date;
	
	/** Ende der Ausleihe. */
	private String end_date;
	
	/**
	 * Erzeugt ein neues KLending-Objekt.
	 * 
	 * @param pId			ID der Ausleihe.
	 * @param pUserId		ID des Benutzers, der die Ausleihe erstellt hat.
	 * @param pLenderId		ID des Ausleihers.
	 * @param pArticleId	ID des verliehenen Artikels
	 * @param pStartDate	Startdatum der Ausleihe.
	 * @param pExpEndDate	Voraussichtliches Enddatum der Ausleihe.
	 * @param pEndDate		Enddatum der Ausleihe.
	 */
	public KLending(int pId, int pUserId, int pLenderId, int pArticleId, String pStartDate, String pExpEndDate, String pEndDate){
		id = pId;
		user_id = pUserId;
		article_id = pArticleId;
		lender_id = pLenderId;
		start_date = pStartDate;
		expected_end_date = pExpEndDate;
		end_date = pEndDate;
	}
	
	/**
	 * Gibt ID der Ausleihe zurück.
	 * 
	 * @return	ID der Ausleihe.
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Gibt ID des Benutzers, der die Ausleihe erstellt hat, zurück.
	 * 
	 * @return	ID des Benutzers, der die Ausleihe erstellt hat.
	 */
	public int getUserId(){
		return user_id;
	}

	/**
	 * Gibt ID des Ausleihers zurück.
	 * 
	 * @return	ID des Ausleihers.
	 */
	public int getLenderId(){
		return lender_id;
	}

	/**
	 * Gibt die ID des Artikels, der verliehen wurde, zurück.
	 * 
	 * @return	ID des Artikels, der verliehen wurde.
	 */
	public int getArticleId(){
		return article_id;
	}
	
	/**
	 * Gibt das Startdatum der Ausleihe zurück.
	 * 
	 * @return	Startdatum der Ausleihe.
	 */
	public String getStartDate() {
		return start_date;
	}

	/**
	 * Gibt das voraussichtliche Enddatum der Ausleihe zurück.
	 * 
	 * @return	Voraussichtliches Enddatum der Ausleihe.
	 */
	public String getExpectedEndDate() {
		return expected_end_date;
	}
	
	/**
	 * Gibt das Enddatum der Ausleihe zurück.
	 * 
	 * @return	Enddatum der Ausleihe.
	 */
	public String getEndDate(){
		return end_date;
	}
}
