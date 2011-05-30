package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;
import org.betterxml.xelement.XPCData;

public class HasPCDataContainsTest extends TestCase {
	private final static String contains1 = "PCData";
	private final static String contains2 = "more PCData because I would like to test with more";
	
	
	public void testValidateArgs() {
		//test too many args, ie. more than 1
		String[] args = new String[]{"name","bob","jones"};
		try {
			new HasPCDataContains(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasPCDataContains takes exactly one argument."));
		}
		
		//test with 2 argument, should fail
		args = new String[]{"name", "position"};
		try {
			new HasPCDataContains(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasPCDataContains takes exactly one argument."));
		}
		
		//test with 0 argument, should be a problem
		args = new String[]{};
		try {
			new HasPCDataContains(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasPCDataContains takes exactly one argument."));
		}
		
		//test with null arguments, should be a problem
		try {
			new HasPCDataContains(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("HasPCDataContains takes exactly one argument."));
		}
		
		//test with correct arguments, first one is zero, should work
		args = new String[]{"some text to look for in PCData"};
		try {
			new HasPCDataContains(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguements exception should have NOT been thrown by the constructor!");
		}
	}

	
	private static List<XNode> currentResults() {
		XElement x1 = new XElement("X");
		x1.getAttributes().setAttribute("position", "0");
		XElement x2 = new XElement("X");
		x2.getAttributes().setAttribute("position", "1");
		XElement x3 = new XElement("X");
		x3.getAttributes().setAttribute("position", "2");
		XElement y = new XElement("Y");
		
		try {
			x1.appendChild(y);
			x1.appendChild(new XPCData("This is some test " + contains1 + ". I'm hoping that all of my tests go well." +
					" They should, but until one actually runs the test you can never know."));
			x1.appendChild(new XPCData("This is the second " + contains1 + " Node that I'm adding to an element " +
					"because I figure it gives me a better test."));
			
			x3.appendChild(new XPCData("This is some " + contains2 + " than " +
					"one XElement containing a " + contains1 + " child"));
			
		} catch (Exception e) {
			System.err.println("Test will be thrown off because of this error!");
			e.printStackTrace();
		}
		List<XNode> cr = new ArrayList<XNode>();
		cr.add(x1);
		cr.add(x2);
		cr.add(x3);
		return cr;
	}
	
	public void testEvaluate() {
		String[] args = new String[]{contains1};
		HasPCDataContains hasPCDataContains = null;
		try {
			hasPCDataContains = new HasPCDataContains(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = hasPCDataContains.evaluate(currentResults());
		assertEquals(2, results.size());
		
		assertTrue(results.get(0) instanceof XElement);
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "0");
		
		assertTrue(results.get(1) instanceof XElement);
		element = (XElement)results.get(1);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "2");
		
		//should get none in the results here
		args = new String[]{"The Zebras are not animatronic robots!"};
		hasPCDataContains = null;
		try {
			hasPCDataContains = new HasPCDataContains(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = hasPCDataContains.evaluate(currentResults());
		assertEquals(0, results.size());
		
		//should get one in the results
		args = new String[]{contains2};
		hasPCDataContains = null;
		try {
			hasPCDataContains = new HasPCDataContains(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = hasPCDataContains.evaluate(currentResults());
		assertEquals(1, results.size());
		
		assertTrue(results.get(0) instanceof XElement);
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "2");
		
	}

}
