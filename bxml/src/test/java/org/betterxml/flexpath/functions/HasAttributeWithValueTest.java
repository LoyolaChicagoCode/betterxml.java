package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

public class HasAttributeWithValueTest extends TestCase {

	public void testValidateArgs() {
		//test too many args, ie. more than 2
		String[] args = new String[]{"name","bob","jones"};
		try {
			new HasAttributeWithValue(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttributeWithValue takes exactly 2 arguments."));
		}
		
		//test with 1 argument, should fail
		args = new String[]{"name"};
		try {
			new HasAttributeWithValue(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttributeWithValue takes exactly 2 arguments."));
		}
		
		//test with 0 arguments, should be a problem
		args = new String[]{};
		try {
			new HasAttributeWithValue(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttributeWithValue takes exactly 2 arguments."));
		}
		
		//test with null arguments, should be a problem
		try {
			new HasAttributeWithValue(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttributeWithValue takes exactly 2 arguments."));
		}
		
		//test with correct arguments, first one is zero, should work
		args = new String[]{"name","bob"};
		try {
			new HasAttributeWithValue(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguements exception should have NOT been thrown by the constructor!");
		}
	}

	
	private static List<XNode> currentResults() {
		XElement x1 = new XElement("X");
		x1.getAttributes().setAttribute("number", "one");
		x1.getAttributes().setAttribute("pos", "0");
		XElement x2 = new XElement("X");
		x2.getAttributes().setAttribute("number", "two");
		x2.getAttributes().setAttribute("pos", "1");
		XElement x3 = new XElement("X");
		x3.getAttributes().setAttribute("number", "three");
		x3.getAttributes().setAttribute("pos", "2");
		XElement y = new XElement("Y");
		XElement x4 = new XElement("X");
		x4.getAttributes().setAttribute("number", "one");
		x4.getAttributes().setAttribute("pos", "3");
		XElement x5 = new XElement("X");
		x5.getAttributes().setAttribute("number", "one");
		x5.getAttributes().setAttribute("pos", "4");
		XElement x6 = new XElement("X");
		x6.getAttributes().setAttribute("number", "one");
		x6.getAttributes().setAttribute("pos", "5");
		
		try {
			x1.appendChild(y);
		} catch (Exception e) {
			System.err.println("Test will be thrown off because of this error!");
			e.printStackTrace();
		}
		List<XNode> cr = new ArrayList<XNode>();
		cr.add(x1);
		cr.add(x2);
		cr.add(x3);
		cr.add(x4);
		cr.add(x5);
		cr.add(x6);
		return cr;
	}
	
	public void testEvaluate() {
		String[] args = new String[]{"number", "one"};
		HasAttributeWithValue hawv = null;
		try {
			hawv = new HasAttributeWithValue(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = hawv.evaluate(currentResults());
		assertEquals(4, results.size());
		
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(0));

		element = (XElement)results.get(1);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(3));
		
		element = (XElement)results.get(2);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(4));
		
		element = (XElement)results.get(3);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(5));
		

		
		args = new String[]{"number", "two"};
		hawv = null;
		try {
			hawv = new HasAttributeWithValue(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = hawv.evaluate(currentResults());
		assertEquals(1, results.size());
		
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(1));

			
	}

}
