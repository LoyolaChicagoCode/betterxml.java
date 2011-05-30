package edu.luc.etl.rss.elements;

import org.betterxml.xelement.XElement;


public class Cloud extends XElement {

	private final static String DOMAIN_ATTR_KEY = "domain";
	private final static String PORT_ATTR_KEY = "port";
	private final static String PATH_ATTR_KEY = "path";
	private final static String REGISTERPROCEDURE_ATTR_KEY = "registerProcedure";
	private final static String PROTOCOL_ATTR_KEY = "protocol";
	
	
	public String getDomain() {
		return getAttributes().getAttributeValue(DOMAIN_ATTR_KEY);
	}
	
	public void setDomain(String domain) {
		getAttributes().setAttribute(DOMAIN_ATTR_KEY, domain);
	}
	
	public String getPort() {
		return getAttributes().getAttributeValue(PORT_ATTR_KEY);
	}
	
	public void setPort(String port) {
		getAttributes().setAttribute(PORT_ATTR_KEY, port);
	}
	
	public void setPort(int port) {
		getAttributes().setAttribute(PORT_ATTR_KEY, Integer.toString(port));
	}
	
	public String getPath() {
		return getAttributes().getAttributeValue(PATH_ATTR_KEY);
	}
	
	public void setPath(String path) {
		getAttributes().setAttribute(PATH_ATTR_KEY, path);
	}
	
	public String getRegisterProcedure() {
		return getAttributes().getAttributeValue(REGISTERPROCEDURE_ATTR_KEY);
	}
	
	public void setRegisterProcedure(String registerProcedure) {
		getAttributes().setAttribute(REGISTERPROCEDURE_ATTR_KEY, registerProcedure);
	}
	
	public String getProtocol() {
		return getAttributes().getAttributeValue(PROTOCOL_ATTR_KEY);
	}
	
	public void setProtocol(String protocol) {
		getAttributes().setAttribute(PROTOCOL_ATTR_KEY, protocol);
	}
}
