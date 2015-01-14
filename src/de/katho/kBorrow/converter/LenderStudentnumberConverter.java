package de.katho.kBorrow.converter;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import de.katho.kBorrow.data.objects.KLender;

/**
 * Wird benötigt, um die Matrikelnummer einer Liste von KLender-Objekten in einem SwingX-Widget anzuzeigen.
 */
public class LenderStudentnumberConverter extends ObjectToStringConverter {

	/**
	 * Gibt zu einem KLender-Objekt die Matrikelnummer zurück.
	 * 
	 * @param pItem		Beliebiges Objekt, idealerweise ein KLender-Objekt
	 * @return			Null, wenn das übergeben Objekt kein KLender-Objekt ist, ansonsten die Matrikelnummer des Objekts.
	 */
	public String getPreferredStringForItem(Object pItem) {
		if(pItem == null) return null;
		if(pItem instanceof KLender) return String.valueOf(((KLender) pItem).getStudentnumber());
		return null;
	}
}
