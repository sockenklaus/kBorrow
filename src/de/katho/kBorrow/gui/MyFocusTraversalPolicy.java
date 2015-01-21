package de.katho.kBorrow.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

/**
 * Definiert, nach welchem Prinzip der Cursor in einem Formular wandert.
 */
public class MyFocusTraversalPolicy extends FocusTraversalPolicy {
	
	/** Reihenfolge der Formularkomponenten */
	private Vector<Component> order;
	
	/**
	 * Gibt ein neues MyFocusTraversalPolicy-Objekt zurück.
	 * 
	 * @param	pOrder	Vector mit der Reihenfolge der Formularkomponenten.
	 */
	public MyFocusTraversalPolicy(Vector<Component> pOrder){
		order = new Vector<Component>(pOrder.size());
		order.addAll(pOrder);
	}
	
	/**
	 * Gibt die nächste Komponente nach der übergebenen Komponente oder die erste Komponente in der Reihenfolge zurück.
	 * 
	 * @param	aContainer	Swing-Container ohne Funktion
	 * @param	aComponent	Swing-Component, dessen Nachfolger in der Reihenfolge zurückgegeben werden soll.
	 * @return				Swing-Component
	 */
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		if(order.contains(aComponent)){
			int index = (order.indexOf(aComponent) + 1);
			
			if(index >= order.size()) index = 0;
			
			return order.get(index);
		}
		return order.firstElement();		
	}

	/**
	 * Gibt die Swing-Component vor der übergebenen Komponente oder die erste in der Reihenfolge zurück.
	 * 
	 * @param	aContainer	Swing-Container ohne Funktion.
	 * @param	aComponent	Swing-Component, dessen Vorgänger in der Reihenfolge zurückgegeben werden soll.
	 * @return				Swing-Component.
	 */
	public Component getComponentBefore(Container aContainer, Component aComponent) {
		if(order.contains(aComponent)){
			int index = (order.indexOf(aComponent) - 1);
			
			if(index < 0) index = order.size() -1;
			
			return order.get(index);
		}
		return order.firstElement();		
	}

	/**
	 * Gibt das Standardelement der Reihenfolge zurück.
	 * 
	 * @param	aContainer	Swing-Container ohne Funktion.
	 * @return				Standard-Swing-Component in der Reihenfolge.
	 */
	public Component getDefaultComponent(Container aContainer) {
		return order.firstElement();
	}

	/**
	 * Gibt die erste Component der Reihenfolge zurück.
	 * 
	 * @param	aContainer	Swing-Container ohne Funktion.
	 * @return				Erste Swing-Component in der Reihenfolge.
	 */
	public Component getFirstComponent(Container aContainer) {
		return order.firstElement();
	}

	/**
	 * Gibt die letzte Swing-Component in der Reihenfolge zurück.
	 * 
	 * @param	aContainer	Swing-Container ohne Funktion.
	 * @return				Letzte Swing-Componentn in der Reihenfolge.
	 */
	public Component getLastComponent(Container aContainer) {
		return order.lastElement();
	}

}
