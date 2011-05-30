package org.betterxml.expressiontree.natural3;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.Element;

@Element("value")
public class Value implements Expression {
	
	@Attribute("value")
	private String value;

	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; }

	@Override
	public int evaluate() {
		return Integer.valueOf(value);
	}

}
