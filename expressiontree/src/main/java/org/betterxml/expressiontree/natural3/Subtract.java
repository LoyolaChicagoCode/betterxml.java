package org.betterxml.expressiontree.natural3;

import java.util.Iterator;

import org.betterxml.natural3.annotations.Element;

@Element("subtract")
public class Subtract extends ContainsExpressionList {
	
	@Override
	public int evaluate() {
		Iterator<Expression> i = expressions.iterator();
		int value = i.next().evaluate();
		while(i.hasNext()) {
			value -= i.next().evaluate();
		}
		return value;
	}
}
