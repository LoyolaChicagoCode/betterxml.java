package org.betterxml.natural3;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.natural3.Metadata.ClassMetadata;
import org.betterxml.natural3.Metadata.FieldMetadata;
import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Singleton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class Natural3Serializer {
	
	private Object root;
	private BetterXmlContentHandler handler;
	private Metadata metadata;
	
	public Natural3Serializer(Object root, BetterXmlContentHandler handler) {
		this.root = root;
		this.handler = handler;
		metadata = new Metadata(root.getClass());
	}
	
	public void serialize() {
		handler.startDocument();
		ClassMetadata classMetadata = metadata.getMetadata(root.getClass());
		//TODO add in first prefix mappings?
		serializeElement(root, classMetadata);
		handler.endDocument();
	}
	
	private void serializeElement(Object element, ClassMetadata classMetadata) {
		String uri = classMetadata.getUri();
		String elementName = classMetadata.getElementName();
		String qname = ""; //TODO fixme!!!
		
		Collection<FieldMetadata<Attribute>> attributes = classMetadata.getAllAttributes();
		
		handler.startElement(uri, elementName, qname, attributes.size());
		
		//loop for attributes
		for(FieldMetadata<Attribute> attribute: attributes) {
			Method m = attribute.getAccessor();
			
			try {
				String value = (String) m.invoke(element);
				String attrQName=""; //TODO fixme
				handler.attribute(attribute.getUri(), attribute.getAnnotation().value(), attrQName, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		//Cdata??
		FieldMetadata<CData> cdataMetadata = classMetadata.getCdata();
		//System.out.println(cdataMetadata);
		if(cdataMetadata!=null && cdataMetadata.getAccessor()!=null) {
			try {
				Collection<CDataWrap> cdataList = (Collection<CDataWrap>) cdataMetadata.getAccessor().invoke(element);
				for(CDataWrap cdata: cdataList) {
					handler.characters(cdata.toString().length(), cdata.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//loop for children elements
		for(FieldMetadata<Children> child: classMetadata.getAllChildren()) {
			Method m = child.getAccessor();
			boolean isSingleton = child.getField().getAnnotation(Singleton.class) != null ? true : false;

			Object childElement;
			try {
				childElement = m.invoke(element);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} 
			
			if(childElement==null) continue; //have not set the value for this bean
			
			if(isSingleton) {
				ClassMetadata childElementMetadata = metadata.getMetadata(childElement.getClass());
				serializeElement(childElement, childElementMetadata);
			} else {
				Collection<Object> childrenElements = (Collection<Object>) childElement;
				for(Object e: childrenElements) {
					ClassMetadata childElementMetadata = metadata.getMetadata(e.getClass());
					serializeElement(e, childElementMetadata);
				}

			}
		}

		handler.endElement(uri, elementName, qname);
	}
}
