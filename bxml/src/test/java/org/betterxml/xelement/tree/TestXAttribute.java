package org.betterxml.xelement.tree;

import static org.junit.Assert.*;

import org.betterxml.xelement.XAttributes;
import org.junit.Test;

public class TestXAttribute  {

	@Test
	public void testSetAttribute() {
		XAttributes xattr = new XAttributes();
		assertEquals(xattr.size(), 0);
		
		String test1name = "test1name";
		String test1value = "test1value";
		xattr.setAttribute(test1name, test1value);
		
		assertEquals(xattr.size(), 1);
		
		String test2qname = "qname";
		String test2local = "local";
		String test2value = "value";
		String test2uri   = "uri";
		
		xattr.setAttribute(test2qname, test2local, test2uri, test2value);
		
		//length should now be 2
		assertEquals(xattr.size(), 2);
		
		//reset the second one and length should still be 2
		xattr.setAttribute(test2qname, test2local, test2uri, "newvalue");
		assertEquals(xattr.size(), 2);
	}
	
    @Test
	public void testGetAttributeValue() {
		XAttributes xattr = new XAttributes();
		assertEquals(xattr.size(), 0);
		
		String test1name = "test1name";
		String test1value = "test1value";
		xattr.setAttribute(test1name, test1value);
		
		String test2qname = "qname";
		String test2local = "local";
		String test2value = "value";
		String test2uri   = "uri";
		
		xattr.setAttribute(test2qname, test2local, test2uri, test2value);
		
		assertEquals(2, xattr.size());
		
		assertEquals(xattr.getAttributeValue(test1name), test1value);
		assertEquals(xattr.getAttributeValue(null, test1name), test1value);
		assertEquals(xattr.getAttributeValue(test2uri, test2local), test2value);
		assertEquals(xattr.getAttributeValue(test2qname), test2value);
		
		//reset the second one and length should still be 2
		String newValue = "newvalue";
		xattr.setAttribute(test2qname, test2local, test2uri, newValue);
		
		//first one should be the same, second one changed
		assertEquals(xattr.getAttributeValue(test1name), test1value);
		assertEquals(xattr.getAttributeValue(null, test1name), test1value);
		assertEquals(xattr.getAttributeValue(test2uri, test2local), newValue);
		assertEquals(xattr.getAttributeValue(test2qname), newValue);
	}
	
	@Test
	public void testContainsAttribute() {
		XAttributes xattr = new XAttributes();
		assertEquals(xattr.size(), 0);
		
		String test1name = "test1name";
		String test1value = "test1value";
		xattr.setAttribute(test1name, test1value);
		
		String test2qname = "qname";
		String test2local = "local";
		String test2value = "value";
		String test2uri   = "uri";
		
		xattr.setAttribute(test2qname, test2local, test2uri, test2value);
		
		assertTrue(xattr.containsAttribute(test2qname));
		assertTrue(xattr.containsAttribute(test2uri, test2local));
		assertTrue(xattr.containsAttribute(test1name));
		assertTrue(xattr.containsAttribute("",test1name));
		assertTrue(xattr.containsAttribute(null,test1name));
	}
	
    @Test
	public void testRemoveAttribute() {
		XAttributes xattr = new XAttributes();
		assertEquals(xattr.size(), 0);
		
		String test1name = "test1name";
		String test1value = "test1value";
		xattr.setAttribute(test1name, test1value);
		
		String test3name = "test3name";
		String test3value = "test3value";
		xattr.setAttribute(test3name, test3value);
		
		String test4name = "test4name";
		String test4value = "test4value";
		xattr.setAttribute(test4name, test4value);
		
		String test2qname = "qname";
		String test2local = "local";
		String test2value = "value";
		String test2uri   = "uri";
		
		xattr.setAttribute(test2qname, test2local, test2uri, test2value);
		
		assertTrue(xattr.containsAttribute(test2qname));
		assertTrue(xattr.containsAttribute(test1name));
		assertTrue(xattr.containsAttribute(test3name));
		assertTrue(xattr.containsAttribute(test4name));
		assertEquals(xattr.size(), 4);
		
		xattr.removeAttribute(test1name);
		assertEquals(xattr.size(), 3);
		assertFalse(xattr.containsAttribute(test1name));
		
		xattr.removeAttribute("", test3name);
		assertEquals(xattr.size(), 2);
		assertFalse(xattr.containsAttribute(test3name));
		
		xattr.removeAttribute(null, test4name);
		assertEquals(xattr.size(), 1);
		assertFalse(xattr.containsAttribute(test4name));
		
		xattr.removeAttribute(test2uri, test2local);
		assertEquals(xattr.size(), 0);
		assertFalse(xattr.containsAttribute(test2uri, test2local));
	}	
}
