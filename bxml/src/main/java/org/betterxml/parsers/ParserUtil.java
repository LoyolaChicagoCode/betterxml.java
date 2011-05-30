package org.betterxml.parsers;

import java.io.Reader;

import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.xelement.XDocument;

public class ParserUtil {
	public static XDocument getXElementFromXml(Reader reader, ToXElementContentHandler handler) {
		new XmlParser().setContentHandler(handler).parse(reader);
		return handler.getDoc();
	}
	
	public static XDocument getXElementFromXir(Reader reader, ToXElementContentHandler handler) {
		new XIRParser().setContentHandler(handler).parse(reader);
		return handler.getDoc();
	}
	
}
