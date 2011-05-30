package org.betterxml;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;

import org.betterxml.xelement.XNode;


public class BetterXmlException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BetterXmlException(String message) {
		super(message);
	}
	
	public BetterXmlException(Exception se) {
		super(se);
	}

	public static BetterXmlException CannotLoadFile(File file) {
		return new BetterXmlException("Cannot load file for parsing: " + 
						file.getAbsolutePath());
	}
	
	public static BetterXmlException ParserInitializationError(ParserConfigurationException pce){
		return new BetterXmlException("ParserConfigurationError at Parser Initialization;" +
						pce.getMessage());
	}
	
	public static BetterXmlException SettingSelfAsAncestor(XNode node) {
		return new BetterXmlException("Data Structure Error: making an element its own ancestor.");
	}
	
}
