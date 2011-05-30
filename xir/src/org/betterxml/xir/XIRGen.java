package org.betterxml.xir;

import java.io.File;
import java.io.IOException;
import org.xml.sax.SAXException;

public class XIRGen {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] args) {
		Sax2XIR handler = new Sax2XIR(System.out);
		try {
			XML2XIRParser xp = new XML2XIRParser(handler);
			if (args[0].startsWith("http://"))
				xp.parse(args[0]);
			else {
				File fileObject = new File(args[0]);
				xp.parse(fileObject);
			}
		} catch (IOException e1) {
			System.out.println(e1);
		} catch (SAXException e2) {
			System.out.println(e2);
		}
	}
}
