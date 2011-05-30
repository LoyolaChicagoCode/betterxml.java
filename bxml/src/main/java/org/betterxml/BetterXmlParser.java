package org.betterxml;

import java.io.Reader;

public interface BetterXmlParser {

	public BetterXmlParser setContentHandler(BetterXmlContentHandler handler);
	
	public BetterXmlContentHandler getContentHandler();
	
	public void parse(Reader reader);
}
