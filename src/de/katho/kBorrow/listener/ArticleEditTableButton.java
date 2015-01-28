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

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.gui.ArticlePanel;
import de.katho.kBorrow.models.ArticleTableModel;

/**
 * Erzeugt den "Bearbeiten"-Button in der Artikeltabelle.
 */
public class ArticleEditTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = -5902626427691636902L;

	/**
	 * Erzeugt den "Bearbeiten"-Button in der Artikeltabelle.
	 * 
	 * @param 	pLabel			Name des Buttons, dient als Tooltip.
	 * @param 	pTable			Referenz auf die ArticleTable.
	 * @param 	articleTab		Referenz auf das ArticlePanel.
	 * @throws	IOException		Wenn Probleme beim Einbinden des Icons auftreten.
	 */
	public ArticleEditTableButton(String pLabel, final JTable pTable, final ArticlePanel articleTab) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/accessories-text-editor.png");
		ImageIcon icon = new ImageIcon(url);
				
		this.buttonE.setIcon(icon);
		this.buttonR.setIcon(icon);
		
		this.buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((ArticleTableModel)pTable.getModel()).getIdFromRow(row);
				
				articleTab.setModeEditArticle(id);
			}
		});
	}
}
