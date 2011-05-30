package org.betterxml.expressiontree.xelement;

import org.betterxml.xelement.XElement;

public class Group extends XElement implements Expression {
	
	public int evaluate() {
		assert(this.getChildrenElements().size() == 1);
		
		return ((Expression)this.getChildrenElements().get(0)).evaluate();
	}

}
