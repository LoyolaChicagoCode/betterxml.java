package org.betterxml.natural3;


import static org.junit.Assert.*;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAssertOneNotNull() throws Exception {
		String[] ok = new String[] {null, null, "notnull", null};
		String[] notOk = new String[] {null, "notnull", "notnull", null};
		String[] allNull = new String[] {null, null};
		String[] empty = new String[] {};
		
		assertEquals(Util.assertOneNotNull(ok), "notnull");
		//TODO fix these broken tests
		try {
			Util.assertOneNotNull(notOk);
			fail("Did not throw exception: " + notOk);
		} catch(AssertionError e) { }
		
		try {
			Util.assertOneNotNull(allNull);
			fail("Did not throw exception: " + allNull);
		} catch(AssertionError e) { }
		
		try {
			Util.assertOneNotNull(empty);
			fail("Did not throw exception: " + empty);
		} catch(AssertionError e) { }
		
	}
	
	@Test
	public void testCheckFieldAnnotations() throws Exception {
		NaturalXMLExample e = new NaturalXMLExample();
		
		assertTrue(Util.checkFieldAnnotations(e.getClass().getDeclaredField("okChildren")) instanceof Children);
		assertTrue(Util.checkFieldAnnotations(e.getClass().getDeclaredField("okCData")) instanceof CData);
		assertTrue(Util.checkFieldAnnotations(e.getClass().getDeclaredField("okAttribute")) instanceof Attribute);
		
		try {
			Util.checkFieldAnnotations(e.getClass().getDeclaredField("notOk"));
			fail("Did not throw exception");
		} catch(AssertionError error) { }
	}
	
	private class NaturalXMLExample {
		@Children(NaturalXMLExample.class)
		private String okChildren;
		
		@CData
		private String okCData;
		
		@Attribute("test")
		private String okAttribute;
		
		@Children(NaturalXMLExample.class)
		@CData
		private String notOk;
	}

}
