package org.betterxml;

public interface BetterXmlContentHandler {
    public void startDocument();
    
    public void endDocument();
    
    public void startPrefixMapping(String nsPrefix, String nsURI);
    
    public void endPrefixMapping(String nsPrefix);
    
    public void startElement(String nsURI,String name, String qname, int numberOfAttrs);
    
    public void endElement(String nsURI, String name, String qname);
    
    public void attribute(String nsURI, String name, String qname, String value);

    public void characters(int length, String cdata);

    public void whitespace(int length, String cdata);

    public void skippedEntity(String name);
    
    public void processingInstruction(String name, String target); 
}
