package org.betterxml.expressiontree.xelement;

import java.io.FileReader;

import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.xelement.XDocument;

public class Main {
	public static void main(String[] args) throws Exception {
		
		ToXElementContentHandler handler = new ToXElementContentHandler();

		handler.registerElementClass(Add.class, "add");
		handler.registerElementClass(Subtract.class, "subtract");
		handler.registerElementClass(Group.class, "group");
		handler.registerElementClass(Value.class, "value");
		
		XDocument document = ParserUtil.getXElementFromXml(new FileReader("expressions.xml"), handler);
		
		Expression expr = (Expression) document.getRootElement();
		
		System.out.println("And the answer is...");
		System.out.println(expr.evaluate());
	}
}
