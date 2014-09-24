package de.katho.kBorrow;

import java.awt.EventQueue;

import de.katho.kBorrow.gui.MainWindow;

public class Main {
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		
		System.out.println(System.getProperty("user.home"));
		
		
		/*
		 * Create the apps main window.
		 */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
