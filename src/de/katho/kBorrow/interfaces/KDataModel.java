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

package de.katho.kBorrow.interfaces;

import java.util.ArrayList;

/**
 * Dieses Interface definiert die Eigenschaften eines KDataModel.
 */
public interface KDataModel {
	
	/**
	 * Ein KGuiModel registriert sich so am KDataModel und wird nun benachrichtigt, wenn die Datenstruktur sich �ndert.
	 * 
	 * @param pModel	KGuiModel, das sich am KDataModel registriert.
	 */
	public void register(KGuiModel pModel);
	
	/**
	 * Holt Daten aus der Datenbank und benachrichtigt alle registrierten KGuiModel.
	 */
	public void updateModel();
	
	/**
	 * Gibt die kompletten Daten als ArrayList zur�ck.
	 * 
	 * @return 	Die kompletten Daten als ArrayList.
	 */
	public ArrayList<?> getData();
	
	/**
	 * Gibt Daten-Objekt mit der angefragten ID zur�ck.
	 * 
	 * @param id	ID des angefragten Elements.
	 * @return		Das angefragte Element.
	 */
	public Object getElement(int id);
}
