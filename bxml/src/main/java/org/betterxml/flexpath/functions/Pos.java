package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XNode;


/**
 * @author nabicht
 *
 * Gets the the item from the given position of the current data set.  The array of argument
 * strings should contain 1 item. This item should be the position you want to retrieve, which by
 * definition must be greater than or equal to 0.  The number must be in numeric form, which means 
 * "one" is invalid but "1" is valid.  The first item in the current data set has the position "0".
 */
public class Pos extends FlexFunction {
	
	private int requestedPos;
	
	public Pos(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//if too many arguments are passed in, then you can't evaluate
		//if no argument is passed in then you can't evaluate
		if (args == null || args.length != 1) 
			throw new InvalidFunctionArguments("Invalid Arguments! Pos takes exactly one argument.", args);
		
		try {
			requestedPos = Integer.valueOf(args[0]);
		} catch (NumberFormatException nfe) {
			throw new InvalidFunctionArguments("Invalid Arguments! The argument for Pos must be an integer >= 0.", args);
		}
		
		if (requestedPos < 0) {
			throw new InvalidFunctionArguments("Invalid Arguments! The argument for Pos must be an integer >= 0.", args);
		}
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;
		
		List<XNode> results = new ArrayList<XNode>();

		if (requestedPos < currentData.size()) {
			results.add(currentData.get(requestedPos));
		}
		
		return results;
	}
	
}
