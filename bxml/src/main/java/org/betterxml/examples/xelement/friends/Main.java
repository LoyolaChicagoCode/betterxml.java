package org.betterxml.examples.xelement.friends;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.betterxml.BetterXmlException;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.xelement.XDocument;

public class Main {

	public static void main(String[] args) {
		String fileName = args[0];
		XDocument document = null;
		ToXElementContentHandler handler = new ToXElementContentHandler();
		try {
			handler.registerElementClass(Friend.class, "Friend");
			handler.registerElementClass(Friends.class, "Friends");
		} catch (BetterXmlException xee) {
			System.err.println("XElement Exception while regsitering Element/Class");
			xee.printStackTrace();
			System.exit(1);
		}
			
		try {
			document = ParserUtil.getXElementFromXml(new FileReader(fileName), handler);
		} catch (BetterXmlException xe) {
			System.err.println("XElement Exception while loading/parsing file: " + fileName);
			xe.printStackTrace();
			System.exit(1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (document.rootElementHasName("Friends")) {
			System.out.print("Phone Number for W: ");
			System.out.println(((FriendsProcessor)document.getRootElement()).getPhoneNumber("W"));
			
			System.out.print("Email for nabicht: ");
			System.out.println(((FriendsProcessor)document.getRootElement()).getEmail("nabicht"));
			
			System.out.print("Email for Bob: ");
			System.out.println(((FriendsProcessor)document.getRootElement()).getEmail("Bob"));
			
			System.out.print("Phone for GKT: ");
			System.out.println(((FriendsProcessor)document.getRootElement()).getPhoneNumber("GKT"));
		}
		
	}
	
}
