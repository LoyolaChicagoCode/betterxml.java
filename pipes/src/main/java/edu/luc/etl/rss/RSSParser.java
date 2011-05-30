package edu.luc.etl.rss;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.betterxml.BetterXmlException;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.xelement.XDocument;

public class RSSParser {

	private final ToXElementContentHandler handler;
	
	public RSSParser() {
		this(new ToRSSXElementContentHandler());
	}
	
	public RSSParser(ToXElementContentHandler contentHandler) {
		this.handler = contentHandler;
	}
	
	public XDocument parse(File file) throws BetterXmlException, FileNotFoundException {
		XDocument document = null;
		document = ParserUtil.getXElementFromXml(new FileReader(file), handler);
		return document;
	}
	
}
