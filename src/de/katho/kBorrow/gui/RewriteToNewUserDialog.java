package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import de.katho.kBorrow.controller.RewriteToNewUserController;
import de.katho.kBorrow.data.KUserModel;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.models.RewriteUserModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

/**
 * Erzeugt den Auswahldialog, der es beim Löschen eines Benutzers ermöglicht, alle seine Ausleihen auf einen anderen Benutzer umzuschreiben.
 */
public class RewriteToNewUserDialog extends JDialog implements ActionListener {

	/** Serial Version UID */
	private static final long serialVersionUID = -6002073589194176368L;
	
	/** Button: OK */
	private JButton okButton;
	
	/** Button: Abbrechen */
	private JButton cancelButton;
	
	/** Referenz auf das RewriteUserModel */
	private RewriteUserModel rwusermodel;
	
	/** Referenz auf den RewriteToNewUserController */
	private RewriteToNewUserController rwcontroller;
	
	/** Resultat, das nach dem Schließen des Dialogs zurück an den UserController gegeben wird. */
	private int result = 1;
	
	/** Benutzer-ID des Benutzers, der gelöscht werden soll. */
	private int oldId;

	/**
	 * Erzeugt den Dialog.
	 * 
	 * @param	pOldId		ID des Benutzers, der gelöscht werden soll.
	 * @param	pDbCon		Referenz auf die Datenbankverbindung.
	 * @param	userModel	Referenz auf das KUserModel.
	 */
	public RewriteToNewUserDialog(int pOldId, DbConnector pDbCon, KUserModel userModel) {
		JPanel contentPanel = new JPanel();
		
		setTitle("Ausleihe umschreiben");
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setBounds(100, 100, 229, 156);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		oldId = pOldId;
		rwusermodel = new RewriteUserModel(userModel, oldId);
		rwcontroller = new RewriteToNewUserController(pDbCon);
		
		// Content Panel
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<String>(rwusermodel);
		comboBox.setBounds(11, 52, 200, 30);
		
		contentPanel.add(comboBox);
		
		// Button Panel
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblBenutzerAuswhlenAuf = new JLabel("<html>Benutzer ausw\u00E4hlen, auf den <br /> Ausleihen umgeschrieben werden sollen.</html>");
		lblBenutzerAuswhlenAuf.setBounds(11, 11, 200, 30);
		contentPanel.add(lblBenutzerAuswhlenAuf);
		setVisible(true);
	}

	/**
	 * ActionListener für gedrückte Buttons.
	 * 
	 * @param	e	ActionEvent, das diesen Listener auslöst. 
	 */
	public void actionPerformed(ActionEvent e) {
		
		// OK Button pressed
		if(e.getSource() == okButton ) { 
			int newId = rwusermodel.getIdByFullname(rwusermodel.getSelectedItem());
			
			if(rwcontroller.rewriteToNewUser(oldId, newId)){
				result = 0;
			}
			dispose();
		}
		
		// Cancel Button pressed
		if(e.getSource() == cancelButton ) {
			dispose();
		}
	}

	/**
	 * Gibt Resultat des Umschreibens zurück.
	 * 
	 * @return	Resultat des Umschreibens als Int.
	 */
	public int getResult(){
		return result;
	}
}

