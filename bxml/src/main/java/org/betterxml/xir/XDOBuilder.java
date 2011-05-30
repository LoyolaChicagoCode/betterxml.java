package org.betterxml.xir;


public class XDOBuilder {
	
	public static XIRDataObject buildCharacters(String characters) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.characters, XIRDataObjectSubtypes.none);
		xdo.setBase64("cdata", characters);
		xdo.setVerbatim("length", String.valueOf(characters.length()));
		return xdo;
	}
	
	public static XIRDataObject buildWhitespace(String whitespace) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.whitespace, XIRDataObjectSubtypes.none);
		xdo.setBase64("cdata", whitespace);
		xdo.setVerbatim("length", String.valueOf(whitespace.length()));
		return xdo;
	}
	
	public static XIRDataObject buildStartDocument() {
		return new XIRDataObject(XIRDataObjectTypes.document, XIRDataObjectSubtypes.begin);
	}
	
	public static XIRDataObject buildEndDocument() {
		return new XIRDataObject(XIRDataObjectTypes.document, XIRDataObjectSubtypes.end);
	}
	
	public static XIRDataObject buildStartElement(String nsURI, String elementName, String qualifiedName, int numOfAttributes) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.element, XIRDataObjectSubtypes.begin);
		xdo.setVerbatim("ns", nsURI);
		xdo.setVerbatim("name", elementName);
		xdo.setVerbatim("qname", qualifiedName);
		xdo.setVerbatim("attributes", String.valueOf(numOfAttributes));
		return xdo;
	}
	
	public static XIRDataObject buildEndElement(String nsURI, String elementName, String qname) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.element, XIRDataObjectSubtypes.end);
		xdo.setVerbatim("ns", nsURI);
		xdo.setVerbatim("name", elementName);
		xdo.setVerbatim("qname", qname);
		return xdo;
	}
	
	public static XIRDataObject buildAttribute(String nsURI, String name, String qualifiedName, String value) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.attribute, XIRDataObjectSubtypes.none);
		xdo.setVerbatim("ns", nsURI);
		xdo.setVerbatim("name", name);
		xdo.setVerbatim("qname", qualifiedName);
		xdo.setVerbatim("value", value);
		return xdo;
	}
	
	public static XIRDataObject buildStartPrefixMapping(String prefix, String uri) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.prefix_mapping, XIRDataObjectSubtypes.begin);
		xdo.setVerbatim("prefix", prefix);
		xdo.setVerbatim("uri", uri);
		return xdo;
	}
	
	public static XIRDataObject buildEndPrefixMapping(String prefix, String uri) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.prefix_mapping, XIRDataObjectSubtypes.end);
		xdo.setVerbatim("prefix", prefix);
		xdo.setVerbatim("uri", uri);
		return xdo;
	}
	
	public static XIRDataObject buildProcessingInstruction(String target, String data) {
		//TODO I think something may be hosed here.
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.processing_instruction, XIRDataObjectSubtypes.none);
		xdo.setVerbatim("target", target);
		xdo.setVerbatim("data", data);
		return xdo;
	}
	
	public static XIRDataObject buildSkippedEntity(String name) {
		XIRDataObject xdo = new XIRDataObject(XIRDataObjectTypes.skipped_entity, XIRDataObjectSubtypes.none);
		xdo.setVerbatim("name", name);
		return xdo;
	}
}
