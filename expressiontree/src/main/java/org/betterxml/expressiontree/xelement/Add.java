package org.betterxml.expressiontree.xelement;

import org.betterxml.xelement.XElement;

public class Add extends XElement implements Expression{
	public int evaluate() {
		int result=0;
		
		for (XElement element: this.getChildrenElements()) {
			if (element instanceof Expression) {
				result += ((Expression) element).evaluate();
			}
		}
		return result;
	}
}
