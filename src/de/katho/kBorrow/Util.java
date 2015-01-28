/*
 * Copyright (C) 2015  Pascal König
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * Util enthält einige statische Hilfsfunktionen
 */
public final class Util {
	/** Referenz auf das Hauptfensterobjekt */
	private static JFrame mainwindow;
	
	/**
	 * Gibt das aktuelle Datum im Format "dd.MM.yyyy" zurück
	 * 
	 * @return Datum im Format "dd.MM.yyyy"
	 */
	public static String getCurrentDate(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		
		return dateFormat.format(date);
	}
	
	/**
	 * Generiert einen zufälligen String aus Kleinbuchstaben.
	 * 
	 * @param length	Die Länge des zu erzeugenden Strings.
	 * @return			Zufällig generierter String aus Kleinbuchstaben der Länge 'length'
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
	 * Öffnet einen neuen Dialog mit einer Warnmeldung und Informationen zur übergebenen Exception.
	 * 
	 * @param e		Exception, die im Dialog angezeigt werden soll.
	 */
	public static void showWarning(Exception e){
		KLogger.log(Level.WARNING, e.getMessage(), e);
		ErrorInfo info = new ErrorInfo("Warnung", e.getMessage(), null, null, e, Level.WARNING, null);
		JXErrorPane.showDialog(mainwindow, info);
	}
	
	/**
	 * Öffnet einen neuen Dialog mit einer Fehlermeldung und Informationen zur übergebenen Exception
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
	 * Referenz wird in den Funktionen {@link #showWarning} und {@link #showError} benötigt.
	 * 
	 * @param p		Referenz auf das Hauptfenster.
	 */
	public static void setMainWindow(JFrame p){
		mainwindow = p;
	}
	
	/**
	 * Entfernt Linebreaks aus dem übergebenen String.
	 * 
	 * @param pString	String, aus dem Linebreaks entfernt werden sollen.
	 * @return			String ohne Linebreaks.
	 */
	public static String removeLineBreaks(String pString){
		StringBuffer text = new StringBuffer(pString);
		int i = 0;
		boolean addI = true;
		
		while (i < text.length()) {
			if (text.charAt(i) == '\n') {
				text.deleteCharAt(i);
				addI = false;

			}

			if (text.charAt(i) == '\r') {
				text.deleteCharAt(i);
				addI = false;
			}

			if (text.charAt(i) == '\t') {
				text.deleteCharAt(i);
				addI = false;
			}

			if (addI) {
				i++;
			}

			addI = true;
		}

		return text.toString();
	}
}
