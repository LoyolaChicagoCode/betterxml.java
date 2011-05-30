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
 * the given name.  The array of attribute strings takes one argument: the name of the attribute
 * that you want the element to have.
 */
public class HasAttribute extends FlexFunction {
	
	public HasAttribute(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//arguments are passed in then can't evaluate
		if (args == null || args.length != 1) 
			throw new InvalidFunctionArguments("Invalid Arguments! HasAttribute takes exactly 1 argument.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;

		List<XNode> results = new ArrayList<XNode>();
		
		for (XNode node : currentData) {
			if (!(node instanceof XElement)) continue;
			
			if (((XElement)node).getAttributes().containsAttribute(args[0])) {
				results.add(node);
			}
		}
		
		return results;
	}
	
}
