package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XNode;

/**
 * @author nabicht
 *
 * Gets the last item from the current data set.  Should be given an empty array 
 * of argument strings.
 *
 */
public class Last extends FlexFunction {
	
	public Last(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//arguments are passed in then can't evaluate
		if (!(args == null || args.length == 0)) 
			throw new InvalidFunctionArguments("Invalid Arguments! Last takes no arguments.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;

		List<XNode> results = new ArrayList<XNode>();
		
		int size = currentData.size();
		if (size != 0) {
			results.add(currentData.get(size - 1));
		}
		
		return results;
	}
	
}
