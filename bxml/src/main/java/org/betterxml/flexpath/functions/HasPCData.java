package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

public class HasPCData extends FlexFunction {
	
	public HasPCData(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//arguments are passed in then can't evaluate
		if (!(args == null || args.length == 0)) 
			throw new InvalidFunctionArguments("Invalid Arguments! HasPCData takes no arguments.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;

		List<XNode> results = new ArrayList<XNode>();
		
		for (XNode node : currentData) {
			if (!(node instanceof XElement)) {
				continue;
			}
			
			if (((XElement)node).getPCData().size()>0) {
				results.add(node);
			}
		}
		
		return results;
	}
	
}
