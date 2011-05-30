package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;
import org.betterxml.xelement.XPCData;

public class PCDataTest extends TestCase {

	private final static String pcdata1 = "This is some test PCData. I'm hoping that all " +
			"of my tests go well. They should, but until one actually runs the test you can never know.";
	
	private final static String pcdata2 = "This is the second PCData Node that I'm adding to an element " +
			"because I figure it gives me a better test.";
	
	private final static String pcdata3 = "This is some more PCData because I would like to " +
			"test with more than one XElement containing a PCData child";
	
	
	public void testValidateArgs() {
		//test any arguments then an error should be thrown
		String[] args = new String[]{"1"};
		try {
			new PCData(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("PCData takes no arguments."));
		}
		
		args = new String[]{"1","test","bob"};
		try {
			new PCData(args);
			fail("An InvalidFunctionArguements exception should have been thrown by the constructor!");
		} catch (InvalidFunctionArguments ifa) {
			assertTrue(ifa.getMessage().contains("PCData takes no arguments."));
		}
		
		//test with 0 argument, should be fine
		args = new String[]{};
		try {
			new PCData(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		//test with null as arguments, should be fine
		args = new String[]{};
		try {
			new PCData(null);
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
			x1.appendChild(new XPCData(pcdata1));
			x1.appendChild(new XPCData(pcdata2));
			
			x3.appendChild(new XPCData(pcdata3));
			
		} catch (Exception e) {
			System.err.println("Test will be thrown off because of this error!");
			e.printStackTrace();
		}
		List<XNode> cr = new ArrayList<XNode>();
		cr.add(x1);
		cr.add(x2);
		cr.add(x3);
		cr.add(new XPCData("Because this is PCData, it has no child PCData"));
		return cr;
	}
	
	public void testEvaluate() {
		String[] args = new String[]{};
		PCData pcData = null;
		try {
			pcData = new PCData(args);
		} catch (InvalidFunctionArguments ifa) {
			fail("An InvalidFunctionArguments exception should not have been thrown!");
		}
		
		List<XNode> results = pcData.evaluate(currentResults());
		assertEquals(3, results.size());
		
		assertTrue(results.get(0) instanceof XPCData);
		XPCData text = (XPCData)results.get(0);
		assertTrue(text.getText().equals(pcdata1));
		
		assertTrue(results.get(1) instanceof XPCData);
		text = (XPCData)results.get(1);
		assertTrue(text.getText().equals(pcdata2));
		
		assertTrue(results.get(2) instanceof XPCData);
		text = (XPCData)results.get(2);
		assertTrue(text.getText().equals(pcdata3));
		
	}

}
