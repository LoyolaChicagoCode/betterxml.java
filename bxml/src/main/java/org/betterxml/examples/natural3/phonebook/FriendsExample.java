package org.betterxml.examples.natural3.phonebook;


import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.betterxml.handlers.ToPrettyXMLContentHandler;
import org.betterxml.natural3.CDataWrap;
import org.betterxml.natural3.Natural3ContentHandler;
import org.betterxml.natural3.Natural3Serializer;
import org.betterxml.parsers.XmlParser;

public class FriendsExample {
	public static void main(String[] args) throws Exception {

		Natural3ContentHandler<Friends> parser = new Natural3ContentHandler<Friends>(Friends.class);

		new XmlParser().setContentHandler(parser).parse(new FileReader(new File(args[0])));
		Friends content = parser.getRoot();

		System.out.println(content);
		System.out.println(content.getFriends());
		
		System.out.println("Friends: ");
		for(Friend friend: content.getFriends()) {
			System.out.println(friend.getNickName());
			System.out.println(friend.getPhoneNumber());
			System.out.println(CDataWrap.reduce(friend.getCdataWrap()).trim());
			System.out.println();
		}

		System.out.println("Now generating XML at a theater near you...");
		Writer sysOutWriter = new OutputStreamWriter(System.out);

		ToPrettyXMLContentHandler handler = new ToPrettyXMLContentHandler(sysOutWriter);
		Natural3Serializer serializer = new Natural3Serializer(content, handler);
		serializer.serialize();
		sysOutWriter.flush();
		
	}
}


