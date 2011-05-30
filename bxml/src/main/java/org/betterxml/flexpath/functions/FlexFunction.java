package org.betterxml.flexpath.functions;

import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XNode;

public abstract class FlexFunction {

	protected final String[] args;
	
	public FlexFunction(String[] args) throws InvalidFunctionArguments{
		this.args = args;
		validateArgs();
	}
	
	protected abstract void validateArgs() throws InvalidFunctionArguments;
	
	public abstract List<XNode> evaluate(List<XNode> currentData);
	
}
