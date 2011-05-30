package org.betterxml.expressiontree.natural3;

import org.betterxml.natural3.annotations.Element;

@Element("add")
public class Add extends ContainsExpressionList {
	@Override
	public int evaluate() {
		int sum = 0;
		for(Expression expression: expressions) {
			sum += expression.evaluate();
		}
		return sum;
	}

}
