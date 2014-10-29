package de.katho.kBorrow.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

public class MyFocusTraversalPolicy extends FocusTraversalPolicy {
	
	private Vector<Component> order;
	
	public MyFocusTraversalPolicy(Vector<Component> pOrder){
		this.order = new Vector<Component>(pOrder.size());
		this.order.addAll(pOrder);
	}
	
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		int index = (order.indexOf(aComponent) + 1);
		
		if(index >= order.size()) index = 0;
		
		return order.get(index);
	}

	public Component getComponentBefore(Container aContainer, Component aComponent) {
		int index = (order.indexOf(aComponent) - 1);
		
		if(index < 0) index = order.size() -1;
		
		return order.get(index);
	}

	public Component getDefaultComponent(Container aContainer) {
		return order.firstElement();
	}

	public Component getFirstComponent(Container aContainer) {
		return order.firstElement();
	}

	public Component getLastComponent(Container aContainer) {
		return order.lastElement();
	}

}
