package org.betterxml.xir;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

// TODO: Implement the XIREcho class.

public class XIREcho {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FileReader inFile;
		try {
			inFile = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			System.err.println("Cannot open " + args[0]);
			e.printStackTrace();
			return;
		}
		XIRReader reader = new XIRReader(inFile);
		OutputStreamWriter out = new OutputStreamWriter(System.out);
		XIRContentHandler handler = new XIREchoHandler(out);
		XIRParser xp = new XIRParser(reader, handler);
		xp.parse();
		out.flush();
	}
}
