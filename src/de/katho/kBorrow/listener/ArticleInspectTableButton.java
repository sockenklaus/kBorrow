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
