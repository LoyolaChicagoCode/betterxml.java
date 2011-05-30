package org.betterxml.natural3;

import java.util.ArrayDeque;
import java.util.Deque;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.BetterXmlException;
import org.betterxml.natural3.Metadata.ClassMetadata;
import org.betterxml.natural3.Metadata.FieldMetadata;
import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;

public class Natural3ContentHandler<T> implements BetterXmlContentHandler {
	
	private T root =  null; //only should be able to set once
	private Deque<Object> contextDeque = new ArrayDeque<Object>();
	
	private Metadata metadataHolder;

	public Natural3ContentHandler(Class<T> rootClass) {
		this.metadataHolder = new Metadata(rootClass);
	}
	
	/**
	 * Returns the root element of the document.  This should only be called
	 * after the entire document has been parsed.
	 * @return
	 */
	public T getRoot() {
		return root;
	}
	
	public void startDocument() { 
		//The deque should be empty
		assert(contextDeque.size()==0); 
	}
	
	public void startElement(String nsURI, String name, String qname, int numberOfAttrs) {
		ClassMetadata elementClassMetadata = this.metadataHolder.getMetadata(name);
		//Class<?> elementClass = elementClassMetadata.getDescribedClass();
		System.out.println(name);
		
		//TODO fail silently if elementClassMetadata not found??
		Object element = elementClassMetadata.getInstance();
		
		if(!contextDeque.isEmpty()) {
			Object parent = contextDeque.peek();
			ClassMetadata parentMetadata = this.metadataHolder.getMetadata(parent.getClass());
			String childName = elementClassMetadata.getElementName();
			
			FieldMetadata<Children> childField = parentMetadata.getChild(childName);
			invokeFieldMutator(childField, parent, element);
		} else {
			root = (T) element;
		}
		this.contextDeque.push(element);
		
	}
	
	public void attribute(String nsURI, String name, String qname, String value) {
		Object parent = this.contextDeque.peek();
		ClassMetadata parentMetadata = this.metadataHolder.getMetadata(parent.getClass());
		
		FieldMetadata<Attribute> attributeField = parentMetadata.getAttribute(name);
		invokeFieldMutator(attributeField, parent, value);
	}
	
	public void characters(int length, String cdata) {
		Object parent = this.contextDeque.peek();
		
		ClassMetadata parentMetadata = this.metadataHolder.getMetadata(parent.getClass()); 
		
		FieldMetadata<CData> cdataField = parentMetadata.getCdata();
		invokeFieldMutator(cdataField, parent, cdata);
	}
	
	public void whitespace(int length, String cdata) { characters(length, cdata); }
	
	public void endElement(String nsURI, String name, String qname) {
		//TODO add in check
		this.contextDeque.pop();
	}

	public void endPrefixMapping(String nsPrefix) { }
	public void processingInstruction(String name, String target) { }
	public void skippedEntity(String name) { }
	
	public void startPrefixMapping(String nsPrefix, String nsURI) { }
	
	public void endDocument() {
		//only one element, the root, on the deque
		assert(this.contextDeque.size()==1);
	}
	
	/**
	 * Invoke the method on the specified object, catch all exceptions
	 * and rethrow as runtime BetterXMLExceptions.
	 * @param method
	 * @param object
	 * @param args
	 */
	private void invokeFieldMutator(FieldMetadata<?> fieldMetadata, Object object, Object...args) {
		if(fieldMetadata==null) return;
		
		try {
			fieldMetadata.getMutator().invoke(object, args);
		} catch (Exception e) {
			throw new BetterXmlException(e);
		}
	}
}
