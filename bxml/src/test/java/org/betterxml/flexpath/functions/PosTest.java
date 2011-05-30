package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

import junit.framework.TestCase;

public class PosTest extends TestCase {

	public void testValidateArgs() {
		//test too many args, ie. more than 1
		String[] args = new String[]{"1","0"};
		try {
			new Pos(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Pos takes exactly one argument."));
		}
		
		//test with 1 argument, should be fine
		args = new String[]{"1"};
		try {
			new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		//test with 0 arguments, should be a problem
		args = new String[]{};
		try {
			new Pos(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Pos takes exactly one argument."));
		}
		
		//test with null arguments, should be a problem
		try {
			new Pos(null);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Pos takes exactly one argument."));
		}
		
		//test with an argument that is not a valid number, exception should be thrown
		args = new String[]{"asdfwe"};
		try {
			new Pos(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The argument for Pos must be an integer >= 0"));
		}
		
		//test with an argument that is less than 0
		args = new String[]{"-2"};
		try {
			new Pos(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The argument for Pos must be an integer >= 0"));
		}
		
		//test with an argument of 0, should be fine
		args = new String[]{"0"};
		try {
			new Pos(args);
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
		//get position 0
		String pos = "0";
		String[] args = new String[]{pos};
		Pos Pos = null;
		try {
			Pos = new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = Pos.evaluate(currentResults());
		assertEquals(1, results.size());
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), pos);
		
//		get position 1
		pos = "1";
		args = new String[]{pos};
		Pos = null;
		try {
			Pos = new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = Pos.evaluate(currentResults());
		assertEquals(1, results.size());
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), pos);

//		get position 2
		pos = "2";
		args = new String[]{pos};
		Pos = null;
		try {
			Pos = new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = Pos.evaluate(currentResults());
		assertEquals(1, results.size());
		element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), pos);

	
//		get position 3, should get nothing back.
		pos = "3";
		args = new String[]{pos};
		Pos = null;
		try {
			Pos = new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = Pos.evaluate(currentResults());
		assertEquals(0, results.size());
		
		//test with current results as null
		pos = "3";
		args = new String[]{pos};
		Pos = null;
		try {
			Pos = new Pos(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = Pos.evaluate(null);
		assertEquals(null, results);
		
	
	}

}
