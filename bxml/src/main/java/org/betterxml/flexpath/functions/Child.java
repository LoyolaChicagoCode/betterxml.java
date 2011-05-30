package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

/**
 * @author nabicht
 *
 * The Child FlexFunction gets all children Elements from the data set given to the evaluate function.
 * This accepts the 0 or 1 items in the passed in array of arguments.  0 items means that all children
 * elements of each element in the current data set will be returned.  If 1 item is in args then that item 
 * is the name of the desired children. Then, evaluate will return all children of the elements in the 
 * current data set that match the name.
 *
 */
public class Child extends FlexFunction {
	
	
	public Child(String[] args) throws InvalidFunctionArguments { 
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		if (args == null) return;
		
		if (args.length > 1) 
			throw new InvalidFunctionArguments("Invalid Arguments! Child only takes 0 or 1 argument.", args);
		
	}
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;
		
		List<XNode> results = new ArrayList<XNode>();

		if (currentData.size() == 0)
			return results;
		
		//if no child name is specified, then all children are wanted
		if (args == null || args.length == 0) {
			for (XNode node : currentData) {
				if (!(node instanceof XElement))
					continue;
				XElement element = (XElement)node;
				results.addAll(element.getChildrenElements());
			}
		} else if (args.length == 1) {
			for (XNode node : currentData) {
				if (!(node instanceof XElement))
					continue;
				XElement element = (XElement)node;
				results.addAll(element.getChildrenElements(args[0]));
			}
		}

		return results;
	}
	
}
