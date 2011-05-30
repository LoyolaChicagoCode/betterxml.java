package org.betterxml.expressiontree.natural3;

import java.io.FileReader;

import org.betterxml.natural3.Natural3ContentHandler;
import org.betterxml.parsers.XmlParser;

public class Main {
	public static void main(String[] args) throws Exception	{
		
		Natural3ContentHandler<Group> handler = new Natural3ContentHandler<Group>(Group.class);
		
		new XmlParser().setContentHandler(handler).parse(new FileReader("expressions.xml"));
		
		
		Expression expr = handler.getRoot();
		
		System.out.println("And the answer is...");
		System.out.println(expr.evaluate());
	}
}
