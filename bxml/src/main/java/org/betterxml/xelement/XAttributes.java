package org.betterxml.xelement;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XAttributes implements Iterable<String> {
	
	public class Attribute  {
		
		private String qname;
		private String localName;
		private String uri;
		private String value;
		
		public Attribute(String name, String value) {
			this(name, name, "", value);
		}
		
		public Attribute(String qname, String localName, String uri, String value) {
			this.qname 		= qname;
			this.localName 	= localName;
			this.uri 		= uri;
			this.value 		= value;
		}
		
		public String getQName() {
			return qname;
		}
		
		public String getURI() {
			return uri;
		}
		
		public String getLocalName() {
			return localName;
		}
		
		public String getValue() {
			return value;
		}
		
	}
	
	private Map<String, Attribute> qnameToAttribute 	= new HashMap<String, Attribute>();
	private Map<String, Attribute> uriLocalToAttribute	= new HashMap<String, Attribute>();
	
	public XAttributes() {}
	
	/**
	 * Set an attribute name to the given value. If the attribute already exists, the 
	 * value will be overwritten. If the attribute does not exist, it will be created 
	 * and given the supplied value.
	 * @param qname  String. The attribute's qualified name.
	 * @param value  String. The value that the attribute should be set to.
	 */
	public void setAttribute(String name, String value) {
		setAttribute(new Attribute(name, value));
	}
	
	/**
	 * Set an attribute name to the given value. If the attribute already exists, the 
	 * value will be overwritten. If the attribute does not exist, it will be created 
	 * and given the supplied value.
	 * @param qname  String. The attribute's qualified name.
	 * @param localName String. The attribute's local name.
	 * @param uri String. The attribute's uri.
	 * @param value  String. The value that the attribute should be set to.
	 */
	public void setAttribute(String qName, String localName, String uri, String value) {
		setAttribute(new Attribute(qName, localName, uri, value));
	}
	
	private void setAttribute(Attribute attr) {
		qnameToAttribute.put(attr.getQName(), attr);
		String uriLocalKey = uriLocalKey(attr.getURI(), attr.getLocalName());
		uriLocalToAttribute.put(uriLocalKey, attr);
	}
	
	
	/**
	 * Checks if an attribute with the supplied name exists in the instance of XAttributes.
	 * @param qName  String. Quanified name of the attribute to look for in instance of XAttributes.
	 * @return  boolean. True if the supplied name exists. False if it doesn't.
	 */
	public boolean containsAttribute(String qName) {
		return qnameToAttribute.containsKey(qName);
	}
	
	/**
	 * Checks if an attribute with the supplied name exists in the instance of XAttributes.
	 * @param uri.  String. The uri of the attribute. If there is no uri, this can be an empty string or null.
	 * @param localName  String. Local name of the attribute to look for in instance of XAttributes.
	 * @return  boolean. True if the supplied name exists. False if it doesn't.
	 */
	public boolean containsAttribute(String uri, String localName) {
		String uriLocalKey = uriLocalKey((uri == null ? "" : uri), localName);
		return uriLocalToAttribute.containsKey(uriLocalKey);
	}
	
	
	/**
	 * Checks if all the attributes names found in the supplied lists exist in this
	 * instance of XAttributes
	 * @param qnames A list of strings, each one an attribute's qualified name
	 * @return boolean. True if they all exist. False if at least one does not.
	 */
	public boolean containsAttributes(List<String> qnames) {
		for (String qname : qnames) {
			if (!containsAttribute(qname)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets value associated with the supplied attribute name.
	 * @param name  String. Name of the attribute to get value for in instance of XAttributes.
	 * @return  String. The value associated with the supplied attribute name.
	 */
	public String getAttributeValue(String qName) {
		return getAttributeValue(qnameToAttribute.get(qName));
	}
	
	/**
	 * Gets value associated with the supplied attribute uri and localname.
	 * @param uri  String. URI of the attribute to get value for in instance of XAttributes.
	 * @param localName String. local name of the attribute to get value for in instance of XAttributes.
	 * @return  String. The value associated with the supplied attribute name.
	 */
	public String getAttributeValue(String uri, String localName) {
		String uriLocalKey = uriLocalKey(uri, localName);
		return getAttributeValue(uriLocalToAttribute.get(uriLocalKey));
	}

	private String getAttributeValue(Attribute attr) {
		if (attr == null) return null;
		return attr.getValue();
	}
	
	/**
	 * Removes the attribute from the Map of attributes
	 * that has the same name as the supplied attribute name.
	 * @param aName  String. Qualified name of attribute to be removed.
	 */
	public void removeAttribute(String qname) {
		removeAttribute(qnameToAttribute.get(qname));
	}
	
	/**
	 * Removes the attribute from the Map of attributes
	 * that has the same name as the supplied attribute name.
	 * @param uri  String. URI of attribute to be removed.
	 * @param localName  String. Local Name of attribute to be removed.
	 */
	public void removeAttribute(String uri, String localName) {
		String uriLocalKey = uriLocalKey(uri, localName);
		removeAttribute(uriLocalToAttribute.get(uriLocalKey));
	}
	
	
	private void removeAttribute(Attribute attr) {
		if (attr == null) return;
		qnameToAttribute.remove(attr.getQName());
		String uriLocalKey = uriLocalKey(attr.getURI(), attr.getLocalName());
		uriLocalToAttribute.remove(uriLocalKey);
	}
	
	/**
	 * Gets the number of attribute name and value pairs in the instance of XAttributes
	 * @return  int. The number of attribute name and value pairs in the instance of XAttributes.
	 */
	public int size() {
		return qnameToAttribute.size();
	}

	
	private static String uriLocalKey(String uri, String local) {
		return (uri == null ? "" : uri) + ":" + local;
	}
	
	/**
	 * Generates and returns a String of all the attribute name and value pairs formatted for XML.<br>
	 * For example: attribute1:value1, attribute2:value2 will be formatted as "attribute1='value1' attribute2='value2'"<br>
	 * (sans double quotes)
	 * @return  String. The attribute name and value pairs properly formatted for XML.
	 */
	public String toXML() {
		if (size() == 0) return "";
		
		StringBuffer buf = new StringBuffer();
		for (Attribute attr : qnameToAttribute.values()) {
			buf.append(attr.getQName());
			buf.append("='");
			buf.append(attr.getValue());
			buf.append("' ");
		}
		
		return buf.toString().trim();	//trim gets rid of trailing white space after last attribute
	}

	public Iterator<String> iterator() {
		return qnameToAttribute.keySet().iterator();
	}
}
