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

package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.ArticleInspectFrame;
import de.katho.kBorrow.interfaces.KDataModel;
import de.katho.kBorrow.models.ArticleTableModel;

/**
 * Erzeugt den "Details"-Button in der Artikeltabelle.
 */
public class ArticleInspectTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = -2591133864537097893L;

	/**
	 * Erzeugt den "Details"-Button in der Artikeltabelle.
	 * 
	 * @param 	pLabel			Name des Buttons, dient als Tooltip.
	 * @param 	pTable			Referenz auf die ArticleTable.
	 * @param 	models			HashMap mit allen KDataModels
	 * @throws	IOException		Wenn Probleme beim Einbinden des Icons auftreten.
	 */
	public ArticleInspectTableButton(String pLabel, final JTable pTable, final HashMap<String, KDataModel> models) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/system-search.png");
		ImageIcon icon = new ImageIcon(url);
	
		buttonE.setIcon(icon);
		buttonR.setIcon(icon);
		
		buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((ArticleTableModel)pTable.getModel()).getIdFromRow(row);
				
				new ArticleInspectFrame(id, models);
			}
		});
	}

}
