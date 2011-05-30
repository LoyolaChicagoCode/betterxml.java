package org.betterxml.expressiontree.xelement;

import org.betterxml.xelement.XElement;

public class Value extends XElement implements Expression {
	public int evaluate() {
		if (attributes.containsAttribute("value")) {
			return Integer.parseInt(attributes.getAttributeValue("value"));
		}
		return Integer.MIN_VALUE;
	}
}
