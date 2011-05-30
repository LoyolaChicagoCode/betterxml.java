package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XNode;

/**
 * @author nabicht
 *
 * Gets the the items from the given range from the current data set.  The array of argument
 * strings should contain 2 items. These items should be the starting position (inclusive) and the
 * ending position (exclusive) for the range you want to retrieve. By definition these 2 arguments 
 * must be greater than or equal to 0. Furthermore, the second argument must be greater than the first
 * argument. The arguments must be in numeric form, which means "one" is invalid but "1" is valid. 
 * The first item in the current data set has the position "0".
 */
public class Range extends FlexFunction {
	
	private int requestedStart;
	private int requestedStop;
	
	public Range(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		if (args == null || args.length != 2) 
			throw new InvalidFunctionArguments("Invalid Arguments! Range takes exactly two arguments.", args);
		
		try {
			requestedStart 	= Integer.valueOf(args[0]);
			requestedStop 	= Integer.valueOf(args[1]);
		} catch (NumberFormatException nfe) {
			throw new InvalidFunctionArguments("Invalid Arguments! The arguments for Range must be integers >= 0.", args);
		}
		
		if (requestedStart < 0 || requestedStop < 0) {
			throw new InvalidFunctionArguments("Invalid Arguments! The arguments for Range must be integers >= 0.", args);
		}
		
		if (requestedStart >= requestedStop) {
			throw new InvalidFunctionArguments("Invalid Arguments! The second argument of Range must be greater than the first argument.", args);
		}
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;
		
		List<XNode> results = new ArrayList<XNode>();

		if (requestedStart < currentData.size()) {
			int pos = requestedStart;
			int stop = Math.min(requestedStop, currentData.size());
			while (pos < stop) {
				results.add(currentData.get(pos));
				pos++;
			}
		}
		
		return results;
	}
	
}
