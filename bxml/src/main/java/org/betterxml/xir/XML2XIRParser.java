package org.betterxml.xir;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.betterxml.handlers.Sax2BetterXmlContentHandlerAdapter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XML2XIRParser {

	@SuppressWarnings("unused")
	private final Sax2BetterXmlContentHandlerAdapter handler;
	private XMLReader xmlReader;
	
	public XML2XIRParser(Sax2BetterXmlContentHandlerAdapter handler) throws SAXException {
		xmlReader = XMLReaderFactory.createXMLReader(); 
		xmlReader.setContentHandler(handler);
		xmlReader.setErrorHandler(handler);
		
		xmlReader.setFeature("http://xml.org/sax/features/namespaces", true);
		xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
		this.handler = handler;
	}
		
	public void parse(File file) throws SAXException, IOException {
		FileReader fileReader = new FileReader(file);
		xmlReader.parse(new InputSource(fileReader));
		fileReader.close();
	}
	
	public void parse(String uri) throws SAXException, IOException {
		xmlReader.parse(uri);
	}
		
	public void parse(InputSource inputSource) throws SAXException, IOException {
		xmlReader.parse(inputSource);
	}
	

	public void parse(InputStream inputStream) throws SAXException, IOException {
		xmlReader.parse(new InputSource(inputStream));
	}
}
