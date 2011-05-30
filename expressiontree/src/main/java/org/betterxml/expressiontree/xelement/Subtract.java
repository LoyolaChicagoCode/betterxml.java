package org.betterxml.expressiontree.xelement;

import java.util.Iterator;

import org.betterxml.xelement.XElement;

public class Subtract extends XElement implements Expression{
	public int evaluate() {
		
		Iterator<XElement> i = this.getChildrenElements().iterator();
		
		int result = ((Expression) i.next()).evaluate();
		
		while(i.hasNext()) {
			result -= ((Expression) i.next()).evaluate();
		}
		
		return result;
	}
}
