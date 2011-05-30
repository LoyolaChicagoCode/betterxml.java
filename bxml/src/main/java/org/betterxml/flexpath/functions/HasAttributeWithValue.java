package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

/**
 * @author nabicht
 *
 * Gets the the items from the current data set that have an instance of an attribute with 
 * the given name and value.  The array of attribute strings takes two arguments: the name 
 * of the attribute and the value that it must have in order for it be in the returned list 
 * of XNodes.
 */
public class HasAttributeWithValue extends FlexFunction {
	
	public HasAttributeWithValue(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//arguments are passed in then can't evaluate
		if (args == null || args.length != 2) 
			throw new InvalidFunctionArguments("Invalid Arguments! HasAttributeWithValue takes exactly 2 arguments.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;

		List<XNode> results = new ArrayList<XNode>();
		
		for (XNode node : currentData) {
			if (!(node instanceof XElement)) continue;
			
			String value = ((XElement)node).getAttributes().getAttributeValue(args[0]);
			
			if (value != null && value.equals(args[1])) {
				results.add(node);
			}
		}
		
		return results;
	}
	
}
