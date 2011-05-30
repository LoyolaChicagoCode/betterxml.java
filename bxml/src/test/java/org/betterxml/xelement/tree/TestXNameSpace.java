package org.betterxml.xelement.tree;


import static org.junit.Assert.*;

import org.betterxml.BetterXmlException;
import org.betterxml.xelement.*;
import org.junit.Test;

public class TestXNameSpace {

	class MyXElement extends XElement { }
	
	class MyXNameSpace extends XNameSpace {
		
		public final static String URI ="http://www.betterxml.org/doesnotexist"; 
		
		public MyXNameSpace() {
			super(URI);
		}
	}
	
	@Test
	public void testConstructors() {
		final String uri = "http://www.betterxml.org/doesnotexist";
		
		XNameSpace test1 = new XNameSpace(uri);
		assertEquals(test1.getURI(), uri);
		assertEquals(test1.getDefaultClass(), XElement.class);
		assertEquals(test1.getMappings().size(), 0);
		
		XNameSpace test2 = new XNameSpace(MyXElement.class, uri);
		assertFalse(test2 == null);
		assertEquals(test2.getDefaultClass(), MyXElement.class);
		assertEquals(test2.getURI(), uri);
		assertEquals(test2.getMappings().size(), 0);
		
		//Checked at compile time:
		//Class klass = String.class;
		//XNameSpace test3 = new XNameSpace(uri, klass);;
		//assertTrue(test3 == null);
	}
    
	@Test
	public void testGetURI() {
		assertEquals(new MyXNameSpace().getURI(), MyXNameSpace.URI);
	}
    
	@Test
	public void testSetElementMapping() {
		XNameSpace ns = new XNameSpace("http://www.betterxml.org/doesnotexist");
		assertEquals(ns.getMappings().size(), 0);
		try {
			ns.setElementMapping("Element", MyXElement.class);
		} catch (BetterXmlException xee) {
			fail("An XElementException should NOT have been thrown.");
		}
		assertEquals(ns.getMappings().size(), 1);
		assertEquals(ns.getClass("Element"), MyXElement.class);
		
		try {
			ns.setElementMapping("Element", XElement.class);
		} catch (BetterXmlException xee) {
			fail("An XElementException should NOT have been thrown.");
		}
		assertEquals(ns.getMappings().size(), 1);
		assertEquals(ns.getClass("Element"), XElement.class);
	}
	
	//TODO We need to think about this case carefully, because I was hoping
	//to eliminate the need for tests like this with the generics 
	//tricks at compile time, but below shows an example of where it can
	//fail (though the compiler should give a warning)
	/*public void testSetElementMappingInValidClass() {
		XNameSpace ns = new XNameSpace("http://www.betterxml.org/doesnotexist");
		assertEquals(ns.getMappings().size(), 0);
		Class klass = Integer.class;
		try {
			ns.setElementMapping("Element", klass);
			fail("An XElementException should have been thrown.");
		} catch (XElementException xee) {
			assertEquals(xee.getMessage(), "Passed in Class (" + klass.getName() + ") is not of instance XElement");
		}
	}*/
	
}
