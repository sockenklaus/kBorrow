/*
 * Copyright (C) 2015  Pascal K�nig
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

package de.katho.kBorrow.converter;

import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import de.katho.kBorrow.data.objects.KLender;

/**
 * Wird ben�tigt, um die Matrikelnummer einer Liste von KLender-Objekten in einem SwingX-Widget anzuzeigen.
 */
public class LenderStudentnumberConverter extends ObjectToStringConverter {

	/**
	 * Gibt zu einem KLender-Objekt die Matrikelnummer zur�ck.
	 * 
	 * @param pItem		Beliebiges Objekt, idealerweise ein KLender-Objekt
	 * @return			Null, wenn das �bergeben Objekt kein KLender-Objekt ist, ansonsten die Matrikelnummer des Objekts.
	 */
	public String getPreferredStringForItem(Object pItem) {
		if(pItem == null) return null;
		if(pItem instanceof KLender) return String.valueOf(((KLender) pItem).getStudentnumber());
		return null;
	}
}
