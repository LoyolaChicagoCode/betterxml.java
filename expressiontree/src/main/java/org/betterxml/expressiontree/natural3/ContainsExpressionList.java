package org.betterxml.expressiontree.natural3;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;

public abstract class ContainsExpressionList implements Expression {
	
	@Children({Group.class, Add.class, Subtract.class, Value.class})
	protected List<Expression> expressions = new ArrayList<Expression>();
	
	public List<Expression> getExpressions() {
		return expressions;
	}

	public void addExpression(Expression expression) {
		this.expressions.add(expression);
	}
}
