package org.betterxml.xelement.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.betterxml.BetterXmlException;
import org.betterxml.xelement.XAttributes;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;
import org.betterxml.xelement.XPCData;
import org.betterxml.xelement.XProcessingInstruction;
import org.junit.Test;

public class TestXElement {

	@Test
	public void testInstantiation() {
		//test instantiation with name
		XElement x = new XElement("TestElement");
		assertEquals("TestElement", x.getName());
		assertEquals(0, x.getChildren().size());
		assertEquals(0, x.getAttributes().size());
		assertEquals(null, x.getParent());
		
		//test instantiation with name and XAttributes
		XAttributes attr = new XAttributes();
		attr.setAttribute("attribute1", "value1");
		x = new XElement("TestElement", attr);
		assertEquals("TestElement", x.getName());
		assertEquals(0, x.getChildren().size());
		assertEquals(1, x.getAttributes().size());
		assertTrue(x.getAttributes().containsAttribute("attribute1"));
		assertEquals("value1",x.getAttributes().getAttributeValue("attribute1"));
		assertEquals(null, x.getParent());
		
		//test instantation with name and Children
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		x = new XElement("TestElement", children);
		assertEquals("TestElement", x.getName());
		assertEquals(2, x.getChildren().size());
		assertEquals("child1", x.getChildrenElements().get(0).getName());
		assertEquals("child2", x.getChildrenElements().get(1).getName());
		assertEquals(0, x.getAttributes().size());
		assertEquals(null, x.getParent());
		
		//test instantiation with name, children, and attributes
		child1 = new XElement("child1");
		child2 = new XElement("child2");
		children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		attr = new XAttributes();
		attr.setAttribute("attribute1", "value1");
		x = new XElement("TestElement", children, attr);
		assertEquals("TestElement", x.getName());
		assertEquals(2, x.getChildren().size());
		assertEquals("child1", x.getChildrenElements().get(0).getName());
		assertEquals("child2", x.getChildrenElements().get(1).getName());
		assertEquals(1, x.getAttributes().size());
		assertTrue(x.getAttributes().containsAttribute("attribute1"));
		assertEquals("value1",x.getAttributes().getAttributeValue("attribute1"));
		assertEquals(null, x.getParent());
	}
    
    @Test
	public void testGetChildren() {
		//test with no children
		XElement x = new XElement("TestElement");
		assertEquals(0, x.getChildren().size());
		
		//test with children
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		x = new XElement("TestElement", children);
		assertEquals(2, x.getChildren().size());
		assertEquals(children, x.getChildren());
	}

    @Test
	public void testSetChildren()  throws BetterXmlException {
		XElement x = new XElement("TestElement");
		assertEquals(0, x.getChildren().size());	//double check that no children are currently there
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		x.setChildren(children);
		assertEquals(children,x.getChildren());
		
		//now set again and it should overwrite previous setting
		LinkedList<XNode> newChildren = new LinkedList<XNode>();
		XElement child3 = new XElement("child3");
		newChildren.add(child3);
		x.setChildren(newChildren);
		assertNotSame(newChildren, children);
		assertEquals(newChildren,x.getChildren());
	}
    
	@Test
	public void testappendChildren()  throws BetterXmlException {
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		XElement x = new XElement("TestElement", children);
		assertEquals(children,x.getChildren());
		
		LinkedList<XNode> newChildren = new LinkedList<XNode>();
		XElement child3 = new XElement("child3");
		XElement child4 = new XElement("child4");
		newChildren.add(child3);
		newChildren.add(child4);
		x.appendChildren(newChildren);
		
		assertEquals(4, x.getChildren().size());
		assertEquals("child1",x.getChildren().get(0).getName());
		assertEquals("child2",x.getChildren().get(1).getName());
		assertEquals("child3",x.getChildren().get(2).getName());
		assertEquals("child4",x.getChildren().get(3).getName());
	}
	
    @Test
	public void testAppendChild()  throws BetterXmlException {
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		XElement x = new XElement("TestElement", children);
		assertEquals(children,x.getChildren());
		
		x.appendChild(new XElement("child3"));
		
		assertEquals(3, x.getChildren().size());
		assertEquals("child1",x.getChildren().get(0).getName());
		assertEquals("child2",x.getChildren().get(1).getName());
		assertEquals("child3",x.getChildren().get(2).getName());
		
	}
    
	@Test
	public void testInsertChild()  throws BetterXmlException {
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		LinkedList<XNode> children = new LinkedList<XNode>();
		children.add(child1);
		children.add(child2);
		XElement x = new XElement("TestElement", children);
		assertEquals(children,x.getChildren());
		
		x.insertChild(new XElement("child0"));
		assertEquals(3, x.getChildren().size());
		assertEquals("child0",x.getChildren().get(0).getName());
		assertEquals("child1",x.getChildren().get(1).getName());
		assertEquals("child2",x.getChildren().get(2).getName());
		
		//try inserting a child at specified index
		x.insertChild(1, new XElement("child1new"));
		assertEquals(4, x.getChildren().size());
		assertEquals("child0",x.getChildren().get(0).getName());
		assertEquals("child1new",x.getChildren().get(1).getName());
		assertEquals("child1",x.getChildren().get(2).getName());
		assertEquals("child2",x.getChildren().get(3).getName());
		
		//try inserting with an index below 0
		x.insertChild(-78, new XElement("childNegative"));
		assertEquals(5, x.getChildren().size());
		assertEquals("childNegative",x.getChildren().get(0).getName());
		assertEquals("child0",x.getChildren().get(1).getName());
		assertEquals("child1new",x.getChildren().get(2).getName());
		assertEquals("child1",x.getChildren().get(3).getName());
		assertEquals("child2",x.getChildren().get(4).getName());
		
		//try inserting with a child at element 0
		x.insertChild(0, new XElement("childNew0"));
		assertEquals(6, x.getChildren().size());
		assertEquals("childNew0",x.getChildren().get(0).getName());
		assertEquals("childNegative",x.getChildren().get(1).getName());
		assertEquals("child0",x.getChildren().get(2).getName());
		assertEquals("child1new",x.getChildren().get(3).getName());
		assertEquals("child1",x.getChildren().get(4).getName());
		assertEquals("child2",x.getChildren().get(5).getName());
		
		//try inserting with a child at index of length
		x.insertChild(x.getChildren().size(), new XElement("childLast"));
		assertEquals(7, x.getChildren().size());
		assertEquals("childNew0",x.getChildren().get(0).getName());
		assertEquals("childNegative",x.getChildren().get(1).getName());
		assertEquals("child0",x.getChildren().get(2).getName());
		assertEquals("child1new",x.getChildren().get(3).getName());
		assertEquals("child1",x.getChildren().get(4).getName());
		assertEquals("child2",x.getChildren().get(5).getName());
		assertEquals("childLast",x.getChildren().get(6).getName());
		
		//try inserting a child with an index greater than length
		x.insertChild(x.getChildren().size()+126, new XElement("childHugeIndex"));
		assertEquals(8, x.getChildren().size());
		assertEquals("childNew0",x.getChildren().get(0).getName());
		assertEquals("childNegative",x.getChildren().get(1).getName());
		assertEquals("child0",x.getChildren().get(2).getName());
		assertEquals("child1new",x.getChildren().get(3).getName());
		assertEquals("child1",x.getChildren().get(4).getName());
		assertEquals("child2",x.getChildren().get(5).getName());
		assertEquals("childLast",x.getChildren().get(6).getName());
		assertEquals("childHugeIndex",x.getChildren().get(7).getName());
	}
	
	@Test
	public void testGetAttributes() {
		XAttributes attr = new XAttributes();
		attr.setAttribute("attribute1", "value1");
		XElement x = new XElement("TestElement", attr);
		
		assertEquals(attr, x.getAttributes());
		assertEquals(1, x.getAttributes().size());
		assertTrue(x.getAttributes().containsAttribute("attribute1"));
		assertEquals("value1",x.getAttributes().getAttributeValue("attribute1"));
		
	}
	
    @Test
	public void testSetAttributes() {
		XAttributes attr = new XAttributes();
		attr.setAttribute("attribute1", "value1");
		XElement x = new XElement("TestElement");
		
		x.setAttributes(attr);
		
		assertEquals(attr, x.getAttributes());
		assertEquals(1, x.getAttributes().size());
		assertTrue(x.getAttributes().containsAttribute("attribute1"));
		assertEquals("value1",x.getAttributes().getAttributeValue("attribute1"));
		
		//if I set again, it should overwrite what is there
		XAttributes newAttrs = new XAttributes();
		newAttrs.setAttribute("attribute1", "newvalue1");
		newAttrs.setAttribute("attribute2", "value2");
		
		x.setAttributes(newAttrs);
		
		assertEquals(newAttrs, x.getAttributes());
		assertEquals(2, x.getAttributes().size());
		assertTrue(x.getAttributes().containsAttribute("attribute1"));
		assertTrue(x.getAttributes().containsAttribute("attribute2"));
		assertFalse(x.getAttributes().containsAttribute("notthere"));
		assertEquals("newvalue1",x.getAttributes().getAttributeValue("attribute1"));
		assertEquals("value2",x.getAttributes().getAttributeValue("attribute2"));

	}

    @Test
	public void testGetChildrenElements() throws BetterXmlException {
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XElement child6 = new XElement("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		List<XElement> elements = test.getChildrenElements();
		assertEquals(child1,elements.get(0));
		assertEquals(child2,elements.get(1));
		assertEquals(child4,elements.get(2));
		assertEquals(child6,elements.get(3));
		assertEquals(elements.size(),4);
	}
    
	@Test
	public void testGetChildrenElementsByName() throws BetterXmlException {
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XElement child6 = new XElement("child1");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		List<XElement> elements = test.getChildrenElements("child1");
		assertEquals(child1,elements.get(0));
		assertEquals(child6,elements.get(1));
		assertEquals(elements.size(),2);
	}
    
	@Test
	public void testRemoveChildUsingIndex() throws BetterXmlException {
		//build list
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XElement child6 = new XElement("child1");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		//make sure length of children is right (6)
		assertEquals(test.getChildren().size(), 6);
		
		//make sure that first 3 are in order I think they are in
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child2);
		assertEquals(test.getChildren().get(2), child3);
		
		//remove element at spot two, which is index 1
		test.removeChild(1);
		
		//now make sure that not only is it gone, but that stuff still is in the right order
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child4);
		
		//try removing a child with a negative index, I should get an error
		try {
			test.removeChild(-32);
			fail("Should have thrown an XElementException");
		} catch (BetterXmlException xee) {
			assertEquals(xee.getMessage(), "Cannot remove child. -32 is not a valid index. " +
					"An index must be greater than zero (0)");
		}
		
		//try removing a child with an index that is too big
		int index = 145;
		try {
			test.removeChild(index);
			fail("Should have thrown an XElementException");
		} catch (BetterXmlException xee) {
			assertEquals(xee.getMessage(), "Cannot remove child. " + index + " is not a valid index. " +
					"An index must be less than the amount of children (" + test.getChildren().size() + ").");
		}
		
		//try removing a child from an XElement with no children
		XElement noChildren = new XElement("NoChildren");
		
		//double check that noChildren has no children
		assertEquals(noChildren.getChildren().size(), 0);
		
		//try removing a child
		try {
			noChildren.removeChild(0);
			fail("Should have thrown an XElementException");
		} catch (BetterXmlException xee) {
			assertEquals(xee.getMessage(), "Cannot remove the child. This node has no children.");
		}
		
	}
	
	@Test
	public void testRemoveChildUsingXNode() throws BetterXmlException {
		//build list
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XElement child6 = new XElement("child1");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		//make sure length of children is right (6)
		assertEquals(test.getChildren().size(), 6);
		
		//make sure that first 3 are in order I think they are in
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child2);
		assertEquals(test.getChildren().get(2), child3);
		
		//remove element at spot two, which is index 1
		test.removeChild(child2);
		
		//now make sure that not only is it gone, but that stuff still is in the right order
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child4);
		
		//removing an XElement that is not in the children list, should have no affect
		XElement notAChild = new XElement("NotAChild");
		
		//double check that elemen t is not in list of children
		assertFalse(test.containsChild(notAChild));
		
		int size = test.getChildren().size();
		
		//remove notAChild, no exception should be thrown
		try {
			notAChild.removeChild(notAChild);
		} catch (Exception e) {
			fail("No Exception should have been thrown");
		}
		
		//everything should still be there and in the same order
		assertEquals(test.getChildren().size(), size);
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child4);
		assertEquals(test.getChildren().get(3), child5);
		assertEquals(test.getChildren().get(4), child6);
		
	}
	
	@Test
	public void testRemoveAllPCData() throws BetterXmlException {
		//set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XElement child2 = new XElement("child2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XPCData child6 = new XPCData("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		int childrenSize = test.getChildren().size();
		
		//test that the right amount of children are there
		assertEquals(childrenSize, 6);
		
		int pcdataSize = test.getPCData().size();
		
		//test that the right amount of pcdata are there
		assertEquals(pcdataSize, 2);
		
		//remove all pcdata
		test.removeAllPCData();
		
		//test that the right amount of children are there (should be childrensize less pcdatasize)
		assertEquals(test.getChildren().size(), childrenSize - pcdataSize);
		
		//test that the right amount of PCData are there (should be zero now)
		assertEquals(test.getPCData().size(), 0);
		
		//test that everything that remained stayed in the right order
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child2);
		assertEquals(test.getChildren().get(2), child4);
		assertEquals(test.getChildren().get(3), child5);
	}
	
	@Test
	public void testRemoveAllProcessingInstructions() throws BetterXmlException {
		//set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XProcessingInstruction child2 = new XProcessingInstruction("child", "2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XPCData child6 = new XPCData("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		int childrenSize = test.getChildren().size();
		
		//test that the right amount of children are there
		assertEquals(childrenSize, 6);
		
		int piSize = test.getProcessingInstructions().size();
		
		//test that the right amount of processing instructions are there
		assertEquals(piSize, 2);
		
		//remove all pcdata
		test.removeAllProcessingInstructions();
		
		//test that the right amount of children are there (should be childrensize less pisize)
		assertEquals(test.getChildren().size(), childrenSize - piSize);
		
		//test that the right amount of Processing Instructions are there (should be zero now)
		assertEquals(test.getProcessingInstructions().size(), 0);
		
		//test that everything that remained stayed in the right order
		assertEquals(test.getChildren().get(0), child1);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child4);
		assertEquals(test.getChildren().get(3), child6);
	}
    
	@Test
	public void testRemoveAllElements() throws BetterXmlException {
		//set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XProcessingInstruction child2 = new XProcessingInstruction("child", "2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XPCData child6 = new XPCData("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		int childrenSize = test.getChildren().size();
		
		//test that the right amount of children are there
		assertEquals(childrenSize, 6);
		
		int elementsSize = test.getChildrenElements().size();
		
		//test that the right amount of pcdata are there
		assertEquals(elementsSize, 2);
		
		//remove all pcdata
		test.removeAllElements();
		
		//test that the right amount of children are there (should be childrensize less elementsSize)
		assertEquals(test.getChildren().size(), childrenSize - elementsSize);
		
		//test that the right amount of XElements are there (should be zero now)
		assertEquals(test.getChildrenElements().size(), 0);
		
		//test that everything that remained stayed in the right order
		assertEquals(test.getChildren().get(0), child2);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child5);
		assertEquals(test.getChildren().get(3), child6);
	}
	
	@Test
	public void testRemoveAllChildren() throws BetterXmlException {
		//set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XProcessingInstruction child2 = new XProcessingInstruction("child", "2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XPCData child6 = new XPCData("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		//make sure that there are indeed children, there should be 6 of them
		assertEquals(test.getChildren().size(), 6);
		
		//remove all children
		test.removeAllChildren();
		
		//test that there are no more children
		assertEquals(test.getChildren().size(), 0);
		
	}
    
	@Test
	public void testRemoveAllElementsWithSpecifiedName() throws BetterXmlException {
		//set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XProcessingInstruction child2 = new XProcessingInstruction("child", "2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child1");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XElement child6 = new XElement("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		int childrenSize = test.getChildren().size();
		
		//test that the right amount of children are there
		assertEquals(childrenSize, 6);
		
		int elementsSize = test.getChildrenElements("child1").size();
		
		//test that the right amount of pcdata are there
		assertEquals(elementsSize, 2);
		
		//remove all pcdata
		test.removeAllElements("child1");
		
		//test that the right amount of children are there (should be childrensize less elementsSize)
		assertEquals(test.getChildren().size(), childrenSize - elementsSize);
		
		//test that the right amount of XElements named child1 are there (should be 0 now)
		assertEquals(test.getChildrenElements("child1").size(), 0);
		
		//test that the right amount of XElements are there (should be 1 now)
		assertEquals(test.getChildrenElements().size(), 1);
		
		//test that everything that remained stayed in the right order
		assertEquals(test.getChildren().get(0), child2);
		assertEquals(test.getChildren().get(1), child3);
		assertEquals(test.getChildren().get(2), child5);
		assertEquals(test.getChildren().get(3), child6);
	}
	
	@Test
	public void testContainsChild() throws BetterXmlException {
//		set up the children of an XElement
		//build list
		XElement child1 = new XElement("child1");
		XProcessingInstruction child2 = new XProcessingInstruction("child", "2");
		XPCData child3 = new XPCData("child3");
		XElement child4 = new XElement("child4");
		XProcessingInstruction child5 = new XProcessingInstruction("child","5");
		XPCData child6 = new XPCData("child6");
		XElement test = new XElement("Test");
		test.appendChild(child1);
		test.appendChild(child2);
		test.appendChild(child3);
		test.appendChild(child4);
		test.appendChild(child5);
		test.appendChild(child6);
		
		//test that it finds an XElement child that is there
		assertTrue(test.containsChild(child1));
		
		//test that it does NOT find an XElement child that is NOT there
		assertFalse(test.containsChild(new XElement("notAChild")));
		
		//test that it finds an XPCData child that is there
		assertTrue(test.containsChild(child6));
		
		//test that it does NOT find an XPCData child that is NOT there
		assertFalse(test.containsChild(new XPCData("is not a child")));
		
		//test that it finds an XProcessingInstruction child that is there
		assertTrue(test.containsChild(child5));
		
		//test that it does NOT find an XProcessingInstruction child that is NOT there
		assertFalse(test.containsChild(new XProcessingInstruction("is a child", "false")));
	}
	
}
