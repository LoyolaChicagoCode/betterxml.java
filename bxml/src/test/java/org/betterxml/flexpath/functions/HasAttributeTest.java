package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

public class HasAttributeTest extends TestCase {

	public void testValidateArgs() {
		//test too many args, ie. more than 1
		String[] args = new String[]{"name","bob","jones"};
		try {
			new HasAttribute(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttribute takes exactly 1 argument."));
		}
		
		//test with 2 argument, should fail
		args = new String[]{"name", "position"};
		try {
			new HasAttribute(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttribute takes exactly 1 argument."));
		}
		
		//test with 0 argument, should be a problem
		args = new String[]{};
		try {
			new HasAttribute(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttribute takes exactly 1 argument."));
		}
		
		//test with null arguments, should be a problem
		try {
			new HasAttribute(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasAttribute takes exactly 1 argument."));
		}
		
		//test with correct arguments, first one is zero, should work
		args = new String[]{"name"};
		try {
			new HasAttribute(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguements exception should have NOT been thrown by the constructor!");
		}
	}

	
	private static List<XNode> currentResults() {
		XElement x1 = new XElement("X");
		x1.getAttributes().setAttribute("number", "one");
		x1.getAttributes().setAttribute("pos", "0");
		x1.getAttributes().setAttribute("another", "testing1");
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
		x4.getAttributes().setAttribute("another", "testing2");
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
		String[] args = new String[]{"number"};
		HasAttribute ha = null;
		try {
			ha = new HasAttribute(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = ha.evaluate(currentResults());
		assertEquals(6, results.size());
		
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(0));

		element = (XElement)results.get(1);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(1));
		
		element = (XElement)results.get(2);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(2));
		
		element = (XElement)results.get(3);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(3));
		
		element = (XElement)results.get(4);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(4));
		
		element = (XElement)results.get(5);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(5));
		
		//doesn't exist in structure so, should get empty results
		args = new String[]{"truck"};
		ha = null;
		try {
			ha = new HasAttribute(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = ha.evaluate(currentResults());
		assertEquals(0, results.size());
		
		
		args = new String[]{"another"};
		ha = null;
		try {
			ha = new HasAttribute(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = ha.evaluate(currentResults());
		assertEquals(2, results.size());
		
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(0));
		assertEquals(element.getAttributes().getAttributeValue("another"), "testing1");
		
		element = (XElement)results.get(1);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("pos"), Integer.toString(3));
		assertEquals(element.getAttributes().getAttributeValue("another"), "testing2");		
		
	}

}
