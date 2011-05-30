package org.betterxml.xir;

public class XIRParser {
	private XIRReader reader;
	private XIRContentHandler handler;

	public XIRParser(XIRReader reader, XIRContentHandler handler) {
		this.reader = reader;
		this.handler = handler;
	}
	
	public void parse() {
		XIRDataObject xir;
		while (true) {
			xir = reader.getNextRecord();
			if (xir == null) break;
			XIRDataObjectTypes type = xir.getType();
			if (type == XIRDataObjectTypes.none)
				continue;
			XIRDataObjectSubtypes subtype = xir.getSubtype();
			switch (type) {
				case document: {
					if (subtype == XIRDataObjectSubtypes.begin) 
						handler.startDocument();
					else {
						handler.endDocument();
						return;
					}
					break;
				}
				case prefix_mapping: {
					String prefix = xir.getValue("prefix");
					String uri = xir.getValue("uri");
					if (subtype == XIRDataObjectSubtypes.begin) 
						handler.startPrefixMapping(prefix, uri);
					else
						handler.endPrefixMapping(prefix);
					break;
				}
				case element: {
					if (subtype == XIRDataObjectSubtypes.begin) {
						String attributes = xir.getValue("attributes");
						String ns = xir.getValue("ns");
						String name = xir.getValue("name");
						handler.startElement(ns, name, Integer.parseInt(attributes));
					} else {
						String ns = xir.getValue("ns");
						String name = xir.getValue("name");
						handler.endElement(ns, name);
					}	
					break;
				}
				case attribute: {
					String ns = xir.getValue("ns");
					String name = xir.getValue("name");
					String value = xir.getValue("value");
					handler.attribute(ns, name, value);
					break;
				}
				case characters: {
					String cdata = xir.getValue("cdata");
					handler.characters(cdata.length(), cdata);
					break;
				}
				case whitespace: {
					String cdata = xir.getValue("cdata");
					handler.whitespace(cdata.length(), cdata);
					break;
				}
				case skipped_entity: {
					String name = xir.getValue("name");
					handler.skippedEntity(name);
					break;
				} 
				case processing_instruction: {
					String name = xir.getValue("name");
					String target = xir.getValue("target");
					handler.processingInstruction(name, target);
					break;
				}
			}
			
		}
	}
}

