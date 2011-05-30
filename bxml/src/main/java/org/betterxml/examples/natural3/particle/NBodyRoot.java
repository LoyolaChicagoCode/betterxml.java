package org.betterxml.examples.natural3.particle;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.betterxml.handlers.ToXMLContentHandler;
import org.betterxml.natural3.Natural3ContentHandler;
import org.betterxml.natural3.Natural3Serializer;
import org.betterxml.parsers.XmlParser;



public class NBodyRoot  {
	public static void main(String[] args) throws Exception {
		
		Natural3ContentHandler<NBody> parser = new Natural3ContentHandler<NBody>(NBody.class);

		new XmlParser().setContentHandler(parser).parse(new FileReader(new File(args[0])));
		NBody nbody = parser.getRoot();

		// content.doSimulation() goes here.
		Writer sysOutWriter = new OutputStreamWriter(System.out);

		ToXMLContentHandler handler = new ToXMLContentHandler(sysOutWriter);
		Natural3Serializer serializer = new Natural3Serializer(nbody, handler);
		serializer.serialize();
		sysOutWriter.flush();
	}

}
