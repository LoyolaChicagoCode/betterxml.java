package org.betterxml.xir.bin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.betterxml.handlers.Sax2BetterXmlContentHandlerAdapter;
import org.betterxml.handlers.ToXirContentHandler;
import org.betterxml.xir.XML2XIRParser;
import org.xml.sax.SAXException;

public class XIRGen {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) {
		Writer output = new OutputStreamWriter(System.out);
		Sax2BetterXmlContentHandlerAdapter handler = new Sax2BetterXmlContentHandlerAdapter(new ToXirContentHandler(output));
		try {
			XML2XIRParser xp = new XML2XIRParser(handler);
			if (args[0].startsWith("http://"))
				xp.parse(args[0]);
			else {
				File fileObject = new File(args[0]);
				xp.parse(fileObject);
			}
			output.flush();
		} catch (IOException e1) {
			System.out.println(e1);
		} catch (SAXException e2) {
			System.out.println(e2);
		}
		
	}
}
