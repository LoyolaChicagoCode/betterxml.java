package org.betterxml.xir;

public interface XIRContentHandler {
    public void startDocument();
    
    public void endDocument();
    
    public void startPrefixMapping(String nsPrefix, String nsURI);
    
    public void endPrefixMapping(String nsPrefix);

    public void startElement(String nsURI,String name, int numberOfAttrs);
    
    public void endElement(String nsURI, String name);
    
    public void attribute(String nsURI, String name, String value);

    public void characters(int length, String cdata);

    public void whitespace(int length, String cdata);

    public void skippedEntity(String name);
    
    public void processingInstruction(String name, String target); 
}
