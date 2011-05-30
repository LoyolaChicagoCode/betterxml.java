package org.betterxml.expressiontree.natural3;

import org.betterxml.natural3.annotations.Element;

@Element("group")
public class Group extends ContainsExpressionList {
	
	@Override
	public int evaluate() {
		assert(expressions.size() == 1);
		return expressions.get(0).evaluate();
	}

}
