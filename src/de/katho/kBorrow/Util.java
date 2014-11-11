package de.katho.kBorrow;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {
	public static String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
}
