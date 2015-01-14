package de.katho.kBorrow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 * Util enth�lt einige statische Hilfsfunktionen
 */
public final class Util {
	/** Referenz auf das Hauptfensterobjekt */
	private static JFrame mainwindow;
	
	/**
	 * Gibt das aktuelle Datum im Format "dd.MM.yyyy" zur�ck
	 * 
	 * @return Datum im Format "dd.MM.yyyy"
	 */
	public static String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	/**
	 * Generiert einen zuf�lligen String aus Kleinbuchstaben.
	 * 
	 * @param length	Die L�nge des zu erzeugenden Strings.
	 * @return			Zuf�llig generierter String aus Kleinbuchstaben der L�nge 'length'
	 */
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
	
	/**
	 * �ffnet einen neuen Dialog mit einer Warnmeldung und Informationen zur �bergebenen Exception.
	 * 
	 * @param e		Exception, die im Dialog angezeigt werden soll.
	 */
	public static void showWarning(Exception e){
		KLogger.log(Level.WARNING, e.getMessage(), e);
		ErrorInfo info = new ErrorInfo("Warnung", e.getMessage(), null, null, e, Level.WARNING, null);
		JXErrorPane.showDialog(mainwindow, info);
	}
	
	/**
	 * �ffnet einen neuen Dialog mit einer Fehlermeldung und Informationen zur �bergebenen Exception
	 * 
	 * @param e		Exception, die im Dialog angezeigt werden soll.
	 */
	public static void showError(Exception e){
		KLogger.log(Level.SEVERE, e.getMessage(), e);
		ErrorInfo info = new ErrorInfo("Fehler", e.getMessage(), null, null, e, Level.SEVERE, null);
		JXErrorPane.showDialog(mainwindow, info);
	}
	
	/**
	 * Setzt eine Referenz auf das Hauptfenster.
	 * Referenz wird in den Funktionen {@link #showWarning} und {@link #showError} ben�tigt.
	 * 
	 * @param p		Referenz auf das Hauptfenster.
	 */
	public static void setMainWindow(JFrame p){
		mainwindow = p;
	}
}
