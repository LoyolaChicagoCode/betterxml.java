package org.betterxml.xelement.tree;


import static org.junit.Assert.*;

import org.betterxml.xelement.*;
import org.junit.Test;

public class TestXPCData {
	
	public class XPCDataExtended extends XPCData {
		public XPCDataExtended(String text) {
			super(text);
		}
		
		public String convertEntitites() {
			return super.convertEntities();
		}
	}
	
    @Test
	public void testConstructors() {
		//test the empty constructor
		XPCData pcdata1 = new XPCData();
		assertEquals(pcdata1.getName(), "PCDATA");
		assertEquals(pcdata1.getText(), "");
		
		//test the argument constructor
		XPCData pcdata2 = new XPCData("test");
		assertEquals(pcdata2.getName(), "PCDATA");
		assertEquals(pcdata2.getText(), "test");
		
	}
	
	@Test
	public void testGetText() {
		String textStr = "Test of getText() function";
		XPCData pcdata = new XPCData( textStr );
		assertEquals(textStr, pcdata.getText());
	}
	
	@Test
	public void testTrimWhiteSpace() {
		XPCData pcdata = new XPCData( "   Testing removing whitespace  \n  " );
		assertEquals("   Testing removing whitespace  \n  ", pcdata.getText());
		pcdata.trimWhiteSpace();
		assertEquals("Testing removing whitespace", pcdata.getText());
	}
	
	@Test
	public void testAppendText() {
		XPCData pcdata = new XPCData( "First some text" );
		assertEquals("First some text", pcdata.getText());
		pcdata.appendText(", and then some more.");
		assertEquals("First some text, and then some more.", pcdata.getText());
	}
	
    @Test
	public void testSetText() {
		XPCData pcdata = new XPCData("starts with this text");
		assertEquals("starts with this text", pcdata.getText());
		pcdata.setText("and it becomes this text");
		assertEquals("and it becomes this text", pcdata.getText());
	}
	
	@Test
	public void testConvertEntities() {
		XPCDataExtended pcdata = new XPCDataExtended("this has a >, a < and a &");
		assertEquals("this has a >, a < and a &", pcdata.getText());
		assertEquals("this has a &gt;, a &lt; and a &amp;", pcdata.convertEntitites());
	}	
}
