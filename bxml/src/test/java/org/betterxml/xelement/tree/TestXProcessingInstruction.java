package org.betterxml.xelement.tree;


import static org.junit.Assert.*;

import org.betterxml.xelement.*;
import org.junit.Test;

public class TestXProcessingInstruction {

    @Test
	public void testConstructor() {
		XProcessingInstruction xpi = new XProcessingInstruction("Target", "data='data1'");
		assertEquals(xpi.getName(), "Processing Instruction");
		assertEquals(xpi.getTarget(), "Target");
		assertEquals(xpi.getData(), "data='data1'");
		assertEquals(xpi.getParent(), null);
	}
    
	@Test
	public void testGetData() {
		XProcessingInstruction xpi = new XProcessingInstruction("Target", "data='data1'");
		assertEquals(xpi.getData(), "data='data1'");
	}

    @Test
	public void testSetData() {
		XProcessingInstruction xpi = new XProcessingInstruction("Target", "data='data1'");
		assertEquals(xpi.getData(), "data='data1'");
		xpi.setData("data is data");
		assertEquals(xpi.getData(), "data is data");
	}

    @Test
	public void testGetTarget() {
		XProcessingInstruction xpi = new XProcessingInstruction("Target", "data='data1'");
		assertEquals(xpi.getTarget(), "Target");
	}
    
	@Test
	public void testSetTarget() {
		XProcessingInstruction xpi = new XProcessingInstruction("Target", "data='data1'");
		assertEquals(xpi.getTarget(), "Target");
		xpi.setTarget("NewTarget");
		assertEquals(xpi.getTarget(), "NewTarget");
	}
}
