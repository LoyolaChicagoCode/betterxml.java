package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;

import junit.framework.TestCase;

public class RangeTest extends TestCase {

	public void testValidateArgs() {
		//test too many args, ie. more than 1
		String[] args = new String[]{"1","0","3"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Invalid Arguments! Range takes exactly two arguments."));
		}
		
		//test with 1 argument, should fail
		args = new String[]{"1"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Invalid Arguments! Range takes exactly two arguments."));
		}
		
		//test with 0 arguments, should be a problem
		args = new String[]{};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Invalid Arguments! Range takes exactly two arguments."));
		}
		
		//test with null arguments, should be a problem
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("Invalid Arguments! Range takes exactly two arguments."));
		}
		
		//test with an argument that is not a valid number, exception should be thrown
		args = new String[]{"1","asdf"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The arguments for Range must be integers >= 0."));
		}
		
		//test with an argument that is less than 0, should fail
		args = new String[]{"-2","2"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The arguments for Range must be integers >= 0."));
		}
		
		//test with second argument less than first argument, should fail
		args = new String[]{"3","2"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The second argument of Range must be greater than the first argument."));
		}
		
		//test with the arguments the same, it should fail
//		test with second argument less than first argument, should fail
		args = new String[]{"3","3"};
		try {
			new Range(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("The second argument of Range must be greater than the first argument."));
		}
		
		
		//test with correct arguments, first one is zero, should work
		args = new String[]{"0","2"};
		try {
			new Range(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguements exception should have NOT been thrown by the constructor!");
		}
		
		//test with correct arguments, first one is non zero, should work
//		test with correct arguments, first one is zero, should work
		args = new String[]{"1","2"};
		try {
			new Range(args);
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
		XElement x4 = new XElement("X");
		x4.getAttributes().setAttribute("position", "3");
		XElement x5 = new XElement("X");
		x5.getAttributes().setAttribute("position", "4");
		XElement x6 = new XElement("X");
		x6.getAttributes().setAttribute("position", "5");
		
		
		
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
		String[] args = new String[]{"0", "3"};
		Range range = null;
		try {
			range = new Range(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = range.evaluate(currentResults());
		assertEquals(3, results.size());
		int x = 0;
		while (x<3) {
			XElement element = (XElement)results.get(x);
			assertEquals("X", element.getName());
			assertEquals(element.getAttributes().getAttributeValue("position"), Integer.toString(x));
			x++;
		}
				
		
	
		args = new String[]{"1", "2"};
		range = null;
		try {
			range = new Range(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		results = range.evaluate(currentResults());
		assertEquals(1, results.size());
		XElement element = (XElement)results.get(0);
		assertEquals("X", element.getName());
		assertEquals(element.getAttributes().getAttributeValue("position"), "1");
			
	}

}
