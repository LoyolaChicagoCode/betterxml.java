package org.betterxml.examples.xelement.friends_namespaces;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.betterxml.BetterXmlException;
import org.betterxml.handlers.ToPrettyXMLContentHandler;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.xelement.XDocument;
import org.betterxml.xelement.XNameSpace;

public class Main {

	public static void main(String[] args) {
		String fileName = args[0];
		XDocument document = null;
		try {
			ToXElementContentHandler handler = new ToXElementContentHandler();
			XNameSpace f = new XNameSpace("http://mydomain.com/friends");
			f.setElementMapping("Friend", Friend.class);
			handler.registerNameSpace(f);
			document = ParserUtil.getXElementFromXml(new FileReader(fileName), handler);
		} catch (BetterXmlException xe) {
			System.out.println("XElement Exception while loading/parsing file: " + fileName);
			xe.printStackTrace();
			System.exit(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Writer outputStream = new OutputStreamWriter(System.out);
		document.acceptContentHandler(new ToPrettyXMLContentHandler(outputStream));
		//document.acceptContentHandler(new ToXirContentHandler(outputStream));
		try {
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
