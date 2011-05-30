package org.betterxml.flexpath.exceptions;

public class InvalidFunctionArguments extends Exception {

	public InvalidFunctionArguments(String message) {
		super(message);
	}
	
	public InvalidFunctionArguments(String message, String[] args) {
		super(createMessage(message, args));
	}
	
	private static String createMessage(String message, String[] args) {
		StringBuffer sb = new StringBuffer(message + "\nArguments are:");
		if (args != null) {
			for (String arg : args) {
				sb.append("\n\t" + arg);
			}
		}
		return sb.toString();
	}
	
}
