package org.betterxml.handlers;

import java.util.HashMap;
import java.util.Stack;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.BetterXmlException;
import org.betterxml.xelement.XAttributes;
import org.betterxml.xelement.XDocument;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNameSpace;
import org.betterxml.xelement.XPCData;

//TODO fixme
public class ToXElementContentHandler implements BetterXmlContentHandler {
	
	private final static String NO_NAMESPACE_URI = "";
	
	private final XNameSpace noNameSpaceInstance;
	
	private HashMap<String, XNameSpace> nameSpaceTable = new HashMap<String, XNameSpace>();
	private boolean ignoreWhiteSpace = true;
	private Stack<ElementMetadata> contextStack;
	private XDocument doc;
	
	/**
	 * XHandler constructor that allows you to define the default class. 
	 * @param defaultClass  Class.  The class that will be used as the default class when associating XML
	 * elements with a Java object.
	 * @throws BetterXmlException 
	 */
	public ToXElementContentHandler(Class<? extends XElement> defaultClass) {
		noNameSpaceInstance = new XNameSpace(defaultClass, NO_NAMESPACE_URI);
	}
	
	
	/**
	 * XHandler constructor.
	 */
	public ToXElementContentHandler() {
		noNameSpaceInstance = new XNameSpace(XElement.class, NO_NAMESPACE_URI);
	}
	
	/**
	 * Registers the supplied Class to the supplied element name so that when there is an element with the given
	 * name the Class is instantiated.
	 * @param klass  Class. The class to register.
	 * @param elementName  String. The element name to which to register the Class.
	 * @throws BetterXmlException  If the supplied Class is not an instance of XElement, this exception is thrown.
	 */
	public void registerElementClass(Class<? extends XElement> klass, String elementName) throws BetterXmlException {
		noNameSpaceInstance.setElementMapping(elementName, klass);
	}
	
	public void registerNameSpace(XNameSpace nameSpace) {
		nameSpaceTable.put(nameSpace.getURI(), nameSpace);
	}
	
	public void registerNameSpace(String nameSpaceURI, Class<? extends XElement> defaultClass) {
		nameSpaceTable.put(nameSpaceURI, new XNameSpace(defaultClass, nameSpaceURI));
	}
	
	public void registerNameSpace(String nameSpaceURI) {
		XNameSpace nameSpace = new XNameSpace(nameSpaceURI);
		nameSpaceTable.put(nameSpaceURI, nameSpace);
		
	}
	
	/**
	 * For the supplied element name it gets the Class that the XTreeHandler will instantiate an instance of
	 * for the element.<br>
	 * First it will look in table of element name to class mappings. If the name is not present in the table
	 * it will look at all loaded classes to see if one that is an instance of XElement matches. If there is still
	 * no match it will return XElement
	 * 
	 * @param name  String. The name of the element.
	 * @return  Class.  The Class that an instance of should be created for each occurrence of the element with the supplied name
	 */
	private Class<? extends XElement> getHandlingClass(String uri, String name) {
		if (nameSpaceTable.containsKey(uri)) {
			return nameSpaceTable.get(uri).getClass(name);
		}
		Class<? extends XElement> klass = noNameSpaceInstance.getClass(name);
		if (klass != null) return klass;
		return noNameSpaceInstance.getDefaultClass();
	}
	
	public void startDocument() {
		doc = new XDocument();
		contextStack = new Stack<ElementMetadata>();
	}
	
	public void startElement(String nsURI, String name, String qname, int numberOfAttrs) {
		XElement currentElement = null;
		try {
			currentElement = this.getHandlingClass(nsURI, name).newInstance();
		} catch(Exception ie) {
			System.err.println("Unable to handle element " + name);
			ie.printStackTrace();
			return;
		}
		
		currentElement.setNameSpaceURI(nsURI);
		currentElement.setName(name);  //TODO take care of this
		currentElement.setQName(qname); //TODO think about this
		currentElement.setAttributes(new XAttributes());
		
		contextStack.push(new ElementMetadata(currentElement, numberOfAttrs)); 
		
		//TODO should this be moved elsewhere? can it?
		if (!doc.hasRootElement()) {
			doc.setRootElement(currentElement);
		}
	}
	
	public void attribute(String nsURI, String name, String qname, String value) {
		assert(contextStack.size() > 0);
		
		XAttributes attributes = contextStack.peek().element.getAttributes();
		
		attributes.setAttribute(qname, name, nsURI, value);
	}

	public void characters(int length, String cdata) {
		//TODO differentiate between cdata and pcdata
		if (ignoreWhiteSpace==true) {
			cdata=cdata.trim();
		}
		if(cdata.equals("")) return;
		XElement element = contextStack.peek().element;
		try {
			element.appendChild(new XPCData(cdata));
		} catch (BetterXmlException e) {
			// TODO Add meaningful exception handling
			e.printStackTrace();
		}
	}
	
	public void whitespace(int length, String cdata) {
		// TODO Auto-generated method stub
	}
	
	public void endElement(String nsURI, String name, String qname) {
		ElementMetadata meta = contextStack.pop(); //TODO change to peek and pop after checks?
		XElement element = meta.element;
		if(element.getAttributes().size() != meta.attributeCount) {
			//TODO fix this
			//throw new XirTreeHandlerException("Element " + name + " expected " + meta.attributeCount + " elements but received " + element.getAttributes().size());
		} 
		/*if(!element.getNameSpaceURI().equals(nsURI)) {
			throw new XirTreeHandlerException("Expected NS URI" + element.getNameSpaceURI() + " but received " + nsURI);	
		}
		if(!element.getQName().equals(name)) {
			throw new XirTreeHandlerException("Expected element " + element.getName() + " received " + name);
		}*/
		element.finalized();
		
		//I'm postponing the add of the element to the document until we get the end
		//because this should allow use to process _most_ of a document if just one section 
		//is malformed.

		//TODO should this be here? moved the doc root test to the start element
		if (contextStack.size()!=0) {
			try {
				contextStack.peek().element.appendChild(meta.element);
			} catch (BetterXmlException xee) {
				System.err.println("Problem appending child to following Element:\n"+contextStack.toString());
				xee.printStackTrace();
				//throw new XirTreeHandlerException(xee);
			}
		}
	}

	public void endDocument() {
		if(contextStack.size()!=0);
			//throw new XirTreeHandlerException("Document ended with unprocessed elements.");
		
	}

	public void endPrefixMapping(String nsPrefix) {
		// TODO Auto-generated method stub
	}

	public void processingInstruction(String name, String target) {
		// TODO Auto-generated method stub
	}

	public void skippedEntity(String name) {
		// TODO Auto-generated method stub
	}

	public void startPrefixMapping(String nsPrefix, String nsURI) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Struct to save the element size metadata.
	 */
	private class ElementMetadata {
		public XElement element;
		public int attributeCount;
		
		public ElementMetadata(XElement element, int attributeCount) {
			this.element = element;
			this.attributeCount = attributeCount;
		}
	}

	public XDocument getDoc() {
		return doc;
	}
}
