package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;


public class ChildTest extends TestCase {

	public void testValidateArgs() {
		//test too many args, ie. more than 1
		String[] args = new String[]{"X","Y"};
		try {
			new Child(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Child only takes 0 or 1 argument."));
		}
		
		//test with 1 argument, should be fine
		args = new String[]{"X"};
		try {
			new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		//test with 0 arguments, should be fine
		args = new String[]{};
		try {
			new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		//test with null arguments, should be fine
		try {
			new Child(null);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
	}
	
	private static List<XNode> currentResults() {
		XElement test = new XElement("test");
		XElement x1 = new XElement("X");
		XElement x2 = new XElement("X");
		XElement x3 = new XElement("X");
		XElement y = new XElement("Y");
		
		try {
			test.appendChild(x1);
			test.appendChild(y);
			test.appendChild(x2);
			test.appendChild(x3);
		} catch (Exception e) {
			System.err.println("Test will be thrown off because of this error!");
			e.printStackTrace();
		}
		List<XNode> cr = new ArrayList<XNode>();
		cr.add(test);
		return cr;
	}

	public void testEvaluate() {
		//just get children elements X
		String[] args = new String[]{"X"};
		Child child = null;
		try {
			child = new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = child.evaluate(currentResults());
		assertEquals(3, results.size());
		for (XNode node : results) {
			assertTrue(node instanceof XElement);
			assertEquals("X", node.getName());
		}
		
		//Just get children elements Y
		args = new String[]{"Y"};
		child = null;
		try {
			child = new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = child.evaluate(currentResults());
		assertEquals(1, results.size());
		for (XNode node : results) {
			assertTrue(node instanceof XElement);
			assertEquals("Y", node.getName());
		}
		
		//get children of elements Z, there should be none of these
		args = new String[]{"Z"};
		child = null;
		try {
			child = new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = child.evaluate(currentResults());
		assertEquals(0, results.size());
		
		//get children of element Y with a null currentResults -- should return null
		child = null;
		args = new String[]{};
		try {
			child = new Child(null);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = child.evaluate(null);
		assertEquals(null, results);
		
		//get all children by passing in an empty args string at construction, should be 4 and in order
		args = new String[]{};
		child = null;
		try {
			child = new Child(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = child.evaluate(currentResults());
		assertEquals(4, results.size());
		
		assertEquals("X", results.get(0).getName());
		assertEquals("Y", results.get(1).getName());
		assertEquals("X", results.get(2).getName());
		assertEquals("X", results.get(3).getName());
		
//		get all children by passing in a null args string at construction, should be 4 and in order
		child = null;
		try {
			child = new Child(null);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = child.evaluate(currentResults());
		assertEquals(4, results.size());
		
		assertEquals("X", results.get(0).getName());
		assertEquals("Y", results.get(1).getName());
		assertEquals("X", results.get(2).getName());
		assertEquals("X", results.get(3).getName());
	}
	
}
