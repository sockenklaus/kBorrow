package de.katho.kBorrow.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import sun.tools.jar.Main;
import de.katho.kBorrow.controller.ArticleController;
import de.katho.kBorrow.gui.ArticlePanel;
import de.katho.kBorrow.models.ArticleTableModel;

/**
 * Erzeugt den Löschbutton in der Artikeltabelle.
 */
public class ArticleDeleteTableButton extends TableButton {

	/** Serial Version UID */
	private static final long serialVersionUID = 7701712368979056068L;

	/**
	 * Erzeugt den Löschbutton in der Artikeltabelle.
	 * 
	 * @param 	pLabel			Name des Buttons, dient als Tooltip.
	 * @param 	pTable			Referenz auf die ArticleTable.
	 * @param 	pPanel			Referenz auf das ArticlePanel.
	 * @param 	pController		Referenz auf den ArticleController.
	 * @throws	IOException		Wenn Probleme beim Einbinden des Icons auftreten.
	 */
	public ArticleDeleteTableButton(String pLabel, final JTable pTable, final ArticlePanel pPanel, final ArticleController pController) throws IOException {
		super(pLabel);
		URL url = Main.class.getResource("/icons/edit-delete.png");
		ImageIcon icon = new ImageIcon(url);
		
		buttonE.setIcon(icon);
		buttonR.setIcon(icon);
		
		buttonE.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fireEditingStopped();
				
				int row = pTable.getSelectedRow();
				int id = ((ArticleTableModel)pTable.getModel()).getIdFromRow(row);
				
				pPanel.setDeleteStatusLabel(pController.deleteArticle(id));		
				pPanel.resetModeEditArticle();
			}
		});
	}
}
