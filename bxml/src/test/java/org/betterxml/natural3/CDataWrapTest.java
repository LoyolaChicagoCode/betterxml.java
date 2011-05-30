package org.betterxml.natural3;


import static org.junit.Assert.*;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CDataWrapTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCDataWrap() {
		String test = "this is a test";
		CDataWrap wrap = new CDataWrap(test);
		
		assertEquals(test, wrap.toString());
	}
	
	@Test
	public void testCDataWrapReduce() {
		List<CDataWrap> cdataList = new ArrayList<CDataWrap>();
		cdataList.add(new CDataWrap("one"));
		cdataList.add(new CDataWrap(" two "));
		cdataList.add(new CDataWrap("three "));
		
		assertEquals("one two three ", CDataWrap.reduce(cdataList));
	}

}
