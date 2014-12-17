package de.katho.kBorrow.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;

import de.katho.kBorrow.controller.RewriteToNewUserController;
import de.katho.kBorrow.interfaces.DbConnector;
import de.katho.kBorrow.models.RewriteUserModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class RewriteToNewUserDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6002073589194176368L;
	private final JPanel contentPanel = new JPanel();
	
	private JButton okButton;
	private JButton cancelButton;
	private RewriteUserModel rwusermodel;
	private RewriteToNewUserController rwcontroller;
	
	private int result = 1;
	private int oldId;

	/**
	 * Create the dialog.
	 */
	public RewriteToNewUserDialog(int pOldId, DbConnector pDbCon) {
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
		rwusermodel = new RewriteUserModel(pDbCon, oldId);
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

	public int getResult(){
		return result;
	}
}

