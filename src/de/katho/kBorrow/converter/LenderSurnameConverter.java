package de.katho.kBorrow.converter;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import de.katho.kBorrow.data.objects.KLender;

/**
 * Wird benötigt, um die Nachnamen einer Liste von KLender-Objekten in einem SwingX-Widget anzuzeigen.
 */
public class LenderSurnameConverter extends ObjectToStringConverter {
	
	/**
	 * Gibt zu einem KLender-Objekt den Nachnamen zurück.
	 * 
	 * @param pItem		Beliebiges Objekt, idealerweise ein KLender-Objekt
	 * @return			Null, wenn das übergeben Objekt kein KLender-Objekt ist, ansonsten den Nachnamen des Objekts.
	 */
	public String getPreferredStringForItem(Object pItem) {
		if(pItem == null) return null;
		if(pItem instanceof KLender) return ((KLender) pItem).getSurname();
		return null;
	}

}
