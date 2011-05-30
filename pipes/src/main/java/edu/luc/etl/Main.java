package edu.luc.etl;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.betterxml.handlers.ToPrettyXMLContentHandler;
import org.betterxml.natural3.Natural3ContentHandler;
import org.betterxml.natural3.Natural3Serializer;
import org.betterxml.parsers.XmlParser;

import edu.luc.etl.atom.Feed;

public class Main {

	public static void main(String[] args) throws Exception {
		Natural3ContentHandler<Feed> parser = new Natural3ContentHandler<Feed>(Feed.class);
		System.out.println("Created handler.");
		new XmlParser().setContentHandler(parser).parse(new FileReader(new File("weblog.xml")));
		System.out.println("Completed Parse.");
		Feed feed = parser.getRoot();
		System.out.println("Root: " + feed);

		//Writer sysOutWriter = new StringWriter();
		Writer sysOutWriter = new OutputStreamWriter(System.out);

		ToPrettyXMLContentHandler handler = new ToPrettyXMLContentHandler(sysOutWriter);
		Natural3Serializer serializer = new Natural3Serializer(feed, handler);
		serializer.serialize();
		sysOutWriter.flush();
		System.out.println("Finished XML.");
	}

}
