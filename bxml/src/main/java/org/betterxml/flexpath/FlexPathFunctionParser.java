package org.betterxml.flexpath;

import org.betterxml.flexpath.exceptions.CorruptedFlexPathException;
import org.betterxml.flexpath.exceptions.ParsingException;
import org.betterxml.flexpath.exceptions.UnknownFunctionException;

/**
 * WARNING!!! THIS DOES CLASS IS NOT FUNCTIONAL YET!!!
 * 
 * 
 * @author nabicht
 *
 */
public class FlexPathFunctionParser implements FlexPathParser {

	
	public FlexPath parse(final String functionStr) 
		throws ParsingException, CorruptedFlexPathException, UnknownFunctionException 
	{
		FlexPath path = new FlexPath();

		String[] sParts = functionStr.split("[.]");
		for (String s : sParts) {
			validFunction(s);
			/*TODO include the proper try/catch below. Will need to throw
			 * some different type of exceptions
			 */
			path.addFunction(extractFunctionName(s), extractFunctionArgs(s));
		}
		
		
		return path;
	}
	
	
	private void validFunction(String functionCall) throws ParsingException {
		/*TODO function (which may include regex) to check for validity of function
		 * which should check for *(*).
		 */
	}
	
	private String extractFunctionName(String functionCall) {
		String name = null;
		/*TODO function that extracts the function name from a 
		 * function call. This is everything up to the (
		 */
		return name;
	}
	
	private String[] extractFunctionArgs(String functionCall) {
		String[] args = null;
		/*TODO function that extracts the function arguments 
		 * from a function call. This is everything from the ( to the second 
		 * to last character. We should know by this point that the last character is 
		 * a )
		 */
		return args;
	}
	
	
}
