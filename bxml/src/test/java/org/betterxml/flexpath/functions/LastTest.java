package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

import junit.framework.TestCase;

public class LastTest extends TestCase {

	public void testValidateArgs() {
		//test any arguments then an error should be thrown
		String[] args = new String[]{"1"};
		try {
			new Last(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Last takes no arguments."));
		}
		
		//test with 0 argument, should be fine
		args = new String[]{};
		try {
			new Last(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		//test with null as arguments, should be fine
		args = new String[]{};
		try {
			new Last(null);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
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
		String[] args = new String[]{};
		Last last = null;
		try {
			last = new Last(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = last.evaluate(currentResults());
		assertEquals(1, results.size());
		assertTrue(results.get(0) instanceof XNode);
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "2");
		
		//same test as above but with null arguments
		last = null;
		try {
			last = new Last(null);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = last.evaluate(currentResults());
		assertEquals(1, results.size());
		assertTrue(results.get(0) instanceof XNode);
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "2");
		
	}

}
