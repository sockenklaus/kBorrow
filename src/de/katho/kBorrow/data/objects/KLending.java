package de.katho.kBorrow.data.objects;

public class KLending {
	private int id;
	private int user_id;
	private int article_id;
	private int lender_id;
	private String start_date;
	private String expected_end_date;
	private String end_date;
	
	public KLending(int pId, int pUserId, int pLenderId, int pArticleId, String pStartDate, String pExpEndDate, String pEndDate){
		id = pId;
		user_id = pUserId;
		article_id = pArticleId;
		lender_id = pLenderId;
		start_date = pStartDate;
		expected_end_date = pExpEndDate;
		end_date = pEndDate;
	}
	
	public int getId(){
		return id;
	}
	
	public int getUserId(){
		return user_id;
	}

	public int getLenderId(){
		return lender_id;
	}

	public int getArticleId(){
		return article_id;
	}
	
	public String getStartDate() {
		return start_date;
	}

	public String getExpectedEndDate() {
		return expected_end_date;
	}
	
	public String getEndDate(){
		return end_date;
	}
}
