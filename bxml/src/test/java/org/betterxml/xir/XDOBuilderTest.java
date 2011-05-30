package org.betterxml.xir;

import org.junit.Test;
import static org.junit.Assert.*;

import org.betterxml.xir.XDOBuilder;
import org.betterxml.xir.XIRDataObject;
import org.betterxml.xir.XIRDataObjectTypes;
import org.betterxml.xir.XIRDataObjectSubtypes;



public class XDOBuilderTest {
	
	XIRDataObject xdo;
	
	@Test
	public void testBuildCharacters() throws Exception {
		String characters = "this is my test data";
		xdo = XDOBuilder.buildCharacters(characters);
		
		assertEquals(XIRDataObjectTypes.characters, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(String.valueOf(characters.length()), xdo.getValue("length"));
		assertEquals(characters, xdo.getValue("cdata"));
	}
	
	@Test
	public void testBuildWhitespace() throws Exception {
		String whitespace = " \n\t\r     ";
		xdo = XDOBuilder.buildWhitespace(whitespace);
		
		assertEquals(XIRDataObjectTypes.whitespace, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(String.valueOf(whitespace.length()), xdo.getValue("length"));
		assertEquals(whitespace, xdo.getValue("cdata"));
	}
	
	
	@Test
	public void testBuildStartDocument() throws Exception {
		xdo = XDOBuilder.buildStartDocument();
		assertEquals(XIRDataObjectTypes.document, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.begin, xdo.getSubtype());
	}
	
	@Test
	public void testBuildEndDocument() throws Exception {
		xdo = XDOBuilder.buildEndDocument();
		assertEquals(XIRDataObjectTypes.document, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.end, xdo.getSubtype());
	}
	
	@Test
	public void testBuildStartElement() throws Exception {
		String uri = "http://fakeuri.com/test";
		String elementName = "myelementname";
		String qname = "ns:myelementname";
		int numOfAttributes = 3;
		xdo = XDOBuilder.buildStartElement(uri, elementName, qname, numOfAttributes);
		
		assertEquals(XIRDataObjectTypes.element, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.begin, xdo.getSubtype());
		
		assertEquals(elementName, xdo.getValue("name"));
		assertEquals(qname, xdo.getValue("qname"));
		assertEquals(uri, xdo.getValue("ns"));
		assertEquals(String.valueOf(numOfAttributes), xdo.getValue("attributes"));
	}
	
	@Test
	public void testBuildStartElementQName() throws Exception {
		String uri = "http://fakeuri.com/test";
		String elementName = "myelementname";
		String qName = "myprefix:myelementname";
		int numOfAttributes = 3;
		xdo = XDOBuilder.buildStartElement(uri, elementName, qName, numOfAttributes);
		
		assertEquals(XIRDataObjectTypes.element, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.begin, xdo.getSubtype());
		
		assertEquals(elementName, xdo.getValue("name"));
		assertEquals(qName, xdo.getValue("qname"));
		assertEquals(uri, xdo.getValue("ns"));
		assertEquals(String.valueOf(numOfAttributes), xdo.getValue("attributes"));
	}
	
	@Test
	public void testBuildEndElement()  throws Exception {
		String uri = "http://fakeuri.com/test";
		String elementName = "myelementname";
		String qname = "ns:myelementname";
		xdo = XDOBuilder.buildEndElement(uri, elementName, qname);	
		
		assertEquals(XIRDataObjectTypes.element, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.end, xdo.getSubtype());
		
		assertEquals(elementName, xdo.getValue("name"));
		assertEquals(uri, xdo.getValue("ns"));
	}
	
	@Test
	public void testBuildAttribute() throws Exception {
		String uri = "http://fakeuri.com/test";
		String name = "myattrname";
		String qname = "ns:myattrname";
		String value = "myvalue";
		xdo = XDOBuilder.buildAttribute(uri, name, qname, value);
		
		assertEquals(XIRDataObjectTypes.attribute, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(name, xdo.getValue("name"));
		assertEquals(qname, xdo.getValue("qname"));
		assertEquals(value, xdo.getValue("value"));
		assertEquals(uri, xdo.getValue("ns"));
	}
	
	@Test
	public void testBuildAttributeQName() throws Exception {
		String uri = "http://fakeuri.com/test";
		String name = "myattrname";
		String qname = "myprefix:myattrname";
		String value = "myvalue";
		xdo = XDOBuilder.buildAttribute(uri, name, qname, value);
		
		assertEquals(XIRDataObjectTypes.attribute, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(name, xdo.getValue("name"));
		assertEquals(qname, xdo.getValue("qname"));
		assertEquals(value, xdo.getValue("value"));
		assertEquals(uri, xdo.getValue("ns"));
	}
	
	@Test
	public void testBuildStartPrefixMapping() throws Exception {
		String prefix = "asdf";
		String uri = "http://fakeuri.com/test";
		
		xdo = XDOBuilder.buildStartPrefixMapping(prefix, uri);
		
		assertEquals(XIRDataObjectTypes.prefix_mapping, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.begin, xdo.getSubtype());
		
		assertEquals(prefix, xdo.getValue("prefix"));
		assertEquals(uri, xdo.getValue("uri"));
	}
	
	@Test
	public void testBuildEndPrefixMapping() throws Exception {
		String prefix = "asdf";
		String uri = "http://fakeuri.com/test";
		
		xdo = XDOBuilder.buildEndPrefixMapping(prefix, uri);
		
		assertEquals(XIRDataObjectTypes.prefix_mapping, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.end, xdo.getSubtype());
		
		assertEquals(prefix, xdo.getValue("prefix"));
		assertEquals(uri, xdo.getValue("uri"));
	}
	
	@Test
	public void testProcessingInstruction() throws Exception {
		String target = "sometarget";
		String data = "somedata";
		xdo = XDOBuilder.buildProcessingInstruction(target, data);
		
		assertEquals(XIRDataObjectTypes.processing_instruction, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(target, xdo.getValue("target"));
		assertEquals(data, xdo.getValue("data"));
	}
	
	@Test 
	public void testBuildSkippedEntity() throws Exception {
		String entity = "skipme";
		
		xdo = XDOBuilder.buildSkippedEntity(entity);
		
		assertEquals(XIRDataObjectTypes.skipped_entity, xdo.getType());
		assertEquals(XIRDataObjectSubtypes.none, xdo.getSubtype());
		
		assertEquals(entity, xdo.getValue("name"));
	}
}
