package org.betterxml.xir;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.handlers.ToXirContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.parsers.XIRParser;
import org.betterxml.parsers.XmlParser;

public class XirXmlBenchmark {

	private static final String fileLocation = "http://www.w3.org/TR/2001/REC-xsl-20011015/xslspec.xml";
	
	public static void main(String[] args) throws Exception {
		long start, stop;
		
		//download
		File xml = downloadFile(fileLocation);
		System.out.println("XML File Size: " + xml.length() + " bytes."); 
		
		//conver to xir
		File xir = File.createTempFile("xir_benchmark", ".xir");
		start = System.currentTimeMillis();
		new XmlParser().setContentHandler(new ToXirContentHandler(new FileWriter(xir))).parse(new FileReader(xml));
		stop = System.currentTimeMillis();
		System.out.println("Convert XML to XIR: " + (stop-start) + " milliseconds.");
		System.out.println("XIR File Size: " + xir.length() + " bytes.");
		
		parse(xml, xir);

		

	}
        
        public static void parse(File xml, File xir) throws Exception{
                long start, stop;
            	//parse xml
		start = System.currentTimeMillis();
                new XmlParser().setContentHandler(new DummyContentHandler()).parse(new FileReader(xml));
		//ParserUtil.getXElementFromXml(new FileReader(xml), new DummyContentHandler());
		stop = System.currentTimeMillis();
		System.out.println("Parse Raw XML: " + (stop-start) + " milliseconds.");
                
                System.gc();
		System.gc();
		System.gc();
		
		//parse xir
		start = System.currentTimeMillis();
                new XIRParser().setContentHandler(new DummyContentHandler()).parse(new FileReader(xir));
		//ParserUtil.getXElementFromXir(new FileReader(xir), new DummyContentHandler());
		stop = System.currentTimeMillis();
		System.out.println("Parse Raw XIR: " + (stop-start) + " milliseconds.");
        }
	
	public static File downloadFile(String filename) throws Exception {
		int size = 1024;
		byte[] buffer = new byte[size];

		URL url = new URL(filename);
		File tempXmlFile = File.createTempFile("xir_benchmark", ".xml");

		URLConnection connection = url.openConnection();
		InputStream in = new BufferedInputStream(connection.getInputStream());
		OutputStream out = new FileOutputStream(tempXmlFile);

		int available = in.read(buffer, 0, size);
		while(available > 0) {
			out.write(buffer, 0, available);
			available = in.read(buffer, 0, size);
		}

		in.close();
		out.close();
		
		return tempXmlFile;
	}
        
        private static class DummyContentHandler implements BetterXmlContentHandler {

        public void startDocument() {
        }
        public void endDocument() {
        }
        public void startPrefixMapping(String nsPrefix, String nsURI) {
        }
        public void endPrefixMapping(String nsPrefix) {
        }
        public void startElement(String nsURI, String name, String qname, int numberOfAttrs) {
        }
        public void endElement(String nsURI, String name, String qname) {
        }
        public void attribute(String nsURI, String name, String qname, String value) {
        }
        public void characters(int length, String cdata) {
        }
        public void whitespace(int length, String cdata) {
        }
        public void skippedEntity(String name) {
        }
        public void processingInstruction(String name, String target) {
        }
            
        }

}
