package de.katho.kBorrow.converter;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import de.katho.kBorrow.data.objects.KLender;

public class LenderSurnameConverter extends ObjectToStringConverter {

	public String getPreferredStringForItem(Object pItem) {
		if(pItem == null) return null;
		if(pItem instanceof KLender) return ((KLender) pItem).getSurname();
		return null;
	}

}
