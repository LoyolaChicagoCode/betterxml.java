package org.betterxml.natural3;

import java.util.List;

/**
 * CData wrapper class.
 *
 * TODO I'm not sure I'm entirely on board with this wrapper class idea. --mbone
 */
public class CDataWrap {
	
	private String cdata;
	
	public CDataWrap(String cdata) { this.cdata = cdata; }
	public String toString() { return this.cdata; }
	
	public static String reduce(List<CDataWrap> cdataList) {
		StringBuffer buffer = new StringBuffer();
		for(CDataWrap cdata: cdataList) {
			buffer.append(cdata);
		}
		return buffer.toString();
	}

}
