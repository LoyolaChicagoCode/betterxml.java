package org.betterxml.xir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class XIRReader {
	private BufferedReader lineOrientedReader;
	private boolean inputDone;
	
	public XIRReader(Reader input) {
		lineOrientedReader = new BufferedReader(input);
	}
	
	public XIRDataObject getNextRecord() {
		ArrayList<String> record = new ArrayList<String>(50);
		String inLine;
		
		while (!inputDone) {
			try {
				inLine = lineOrientedReader.readLine();
				if (inLine == null)
					break;
				if (inLine.length() == 0)
					break;
			} catch (IOException e) {
				inputDone = true;
				break;
			}
			record.add(inLine);
		}
		
		XIRDataObject xir = XIRDataObject.getXIRDataObject(record);
		return xir;
	}
}
