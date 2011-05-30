package org.betterxml.flexpath.exceptions;

public class CorruptedFlexPathException extends Exception {

	//TODO: it might be nice to be able to pass in the flexpath and show where it went wrong
	public CorruptedFlexPathException(String message) {
		super(message);
	}
	
}
