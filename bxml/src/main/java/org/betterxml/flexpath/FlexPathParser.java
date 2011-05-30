package org.betterxml.flexpath;

import org.betterxml.flexpath.exceptions.CorruptedFlexPathException;
import org.betterxml.flexpath.exceptions.ParsingException;
import org.betterxml.flexpath.exceptions.UnknownFunctionException;

public interface FlexPathParser {

	public FlexPath parse(String path) throws ParsingException, CorruptedFlexPathException, UnknownFunctionException ;
	
}
