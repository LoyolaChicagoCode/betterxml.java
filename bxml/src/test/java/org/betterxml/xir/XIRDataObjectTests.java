package org.betterxml.xir;

import org.betterxml.xir.XIRDataObject;
import org.betterxml.xir.XIRDataObjectException;
import org.betterxml.xir.XIRDataObjectSubtypes;
import org.betterxml.xir.XIRDataObjectTypes;
import static org.junit.Assert.*;
import org.junit.Test;

public class XIRDataObjectTests {
	
    @Test
	public void testConstructor() {
		XIRDataObject xdo = new XIRDataObject();
		assertEquals(xdo.getType(), XIRDataObjectTypes.none);
		assertEquals(xdo.getSubtype(), XIRDataObjectSubtypes.none);
	}

    @Test
	public void testBasic() throws XIRDataObjectException {
		XIRDataObject xdo = new XIRDataObject();
		xdo.setVerbatim("x", "25");
		xdo.setVerbatim("y", "30");
		String text;
		text = xdo.getValue("x");
		assertEquals("25", text);
		text = xdo.getValue("y");
		assertEquals("30", text);
	}
    
	@Test
	public void testB64() throws XIRDataObjectException {
		XIRDataObject xdo = new XIRDataObject();
		xdo.setBase64("x", "25");
		xdo.setBase64("y", "30");
		String text;
		text = xdo.getValue("x");
		assertEquals("25", text);
		text = xdo.getValue("y");
		assertEquals("30", text);
	}
    
    @Test
	public void testMix() throws XIRDataObjectException {
		XIRDataObject xdo = new XIRDataObject();
		xdo.setVerbatim("x", "25");
		xdo.setVerbatim("y", "30");
		String text;
		text = xdo.getValue("x");
		assertEquals("25", text);
		text = xdo.getValue("y");
		assertEquals("30", text);
		xdo.setBase64("a", "25");
		xdo.setBase64("b", "30");
		text = xdo.getValue("a");
		assertEquals("25", text);
		text = xdo.getValue("b");
		assertEquals("30", text);
	}
}
