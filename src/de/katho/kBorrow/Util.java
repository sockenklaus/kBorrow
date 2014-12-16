package de.katho.kBorrow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

public final class Util {
	private static JFrame mainwindow;
	
	public static String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	public static String generateRandomString(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < length; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
	
		return sb.toString();
	  }
	
	public static void showWarning(Exception e){
		KLogger.log(Level.WARNING, e.getMessage(), e);
		ErrorInfo info = new ErrorInfo("Warnung", e.getMessage(), null, null, e, Level.WARNING, null);
		JXErrorPane.showDialog(mainwindow, info);
	}
	
	public static void showError(Exception e){
		KLogger.log(Level.SEVERE, e.getMessage(), e);
		ErrorInfo info = new ErrorInfo("Fehler", e.getMessage(), null, null, e, Level.SEVERE, null);
		JXErrorPane.showDialog(mainwindow, info);
	}
	
	public static void setMainWindow(JFrame p){
		mainwindow = p;
	}
}
