package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

public class HasAttributeWithValueContains extends FlexFunction {
	
	public HasAttributeWithValueContains(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//arguments are passed in then can't evaluate
		if (args == null || args.length != 2) 
			throw new InvalidFunctionArguments("Invalid Arguments! HasAttributeWithValueContains takes " +
					"exactly 2 arguments.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;

		List<XNode> results = new ArrayList<XNode>();
		
		for (XNode node : currentData) {
			if (!(node instanceof XElement)) continue;
			
			String value = ((XElement)node).getAttributes().getAttributeValue(args[0]);
			
			if (value != null && value.contains(args[1])) {
				results.add(node);
			}
		}
		
		return results;
	}
	
}
