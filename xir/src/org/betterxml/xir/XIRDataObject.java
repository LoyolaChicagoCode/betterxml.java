package org.betterxml.xir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.iharder.b64.*;


public class XIRDataObject {
	/**
	 * Every XIR record has a type, which corresponds to the kind of XML
	 * "thing" being encoded. Constants that may be used begin with the
	 * XML_ prefix.
	 */
	private static final String XIR_TYPE = "xir.type";
	/*
	 * The subtype is used to qualify a record type (often begin/end). An
	 * XIR record (presently) is fully distinguished by its type and subtype
	 * pair.
	 */
	private static final String XIR_SUBTYPE = "xir.subtype";
	
	/*
	 * XIR records may use any desired key, except one that begins with
	 * the reserved prefix "xir."
	 */
	private static final String XIR = "xir.";
	
	/*
	 * XIR_VERBATIM and XIR_B64 are the only two encodings supported by 
	 * the present implementation. XIR_VERBATIM means the character data is
	 * encoded "as is", while XIR_B64 is used to wrap the data in Base64.
	 * We use B64 whenever there is the possibility of data being encoded
	 * across newline boundaries. (XIR is a line-oriented record format!)
	 */

	private Map<String, EncodedString> data = new HashMap<String, EncodedString>();
	
	public XIRDataObject(XIRDataObjectTypes type, XIRDataObjectSubtypes subtype) {
		data.put(XIR_TYPE, new EncodedString(XIREncodings.verbatim, type.toString()));
		data.put(XIR_SUBTYPE, new EncodedString(XIREncodings.verbatim, subtype.toString()));
	}

	public XIRDataObject(XIRDataObjectTypes type) {
		this(type, XIRDataObjectSubtypes.none);
	}

	public XIRDataObject() {
		this(XIRDataObjectTypes.none, XIRDataObjectSubtypes.none);
	}
	
	public void setType(XIRDataObjectTypes type) {
		data.put(XIR_TYPE, new EncodedString(XIREncodings.verbatim, type.toString()));
		
	}

	public void setSubtype(XIRDataObjectSubtypes subtype) {
		data.put(XIR_SUBTYPE, new EncodedString(XIREncodings.verbatim, subtype.toString()));
	}
		
	public void setVerbatim(String key, String value) throws XIRDataObjectException {
		//if (key.startsWith(XIR))
		//	throw new XIRDataObjectException("Illegal Key", key);
		data.put(key, new EncodedString(XIREncodings.verbatim, value));
	}
	
	public void setBase64(String key, String value) throws XIRDataObjectException {
		//if (key.startsWith(XIR))
		//	throw new XIRDataObjectException("Illegal Key", key);
		String value64 = Base64.encodeBytes(value.getBytes(), Base64.DONT_BREAK_LINES);
		EncodedString encodedData = new EncodedString(XIREncodings.base64, value64);
		data.put(key, encodedData);
	}
	
	public XIREncodings getEncoding(String key) {
		return data.get(key).getEncoding();
	}
	
	public String getValue(String key) {
		XIREncodings encoding = data.get(key).getEncoding();
		if (encoding == XIREncodings.verbatim)
			return data.get(key).getValue();
		else {
			// TODO: We need to think about whether "locale" infomration should be saved when doing the b64 encoding.
			return new String(Base64.decode(data.get(key).getValue(), Base64.DONT_BREAK_LINES));
		}
	}
	
	public XIRDataObjectTypes getType() {
		String typeAsText = data.get(XIR_TYPE).getValue();
		return XIRDataObjectTypes.valueOf(typeAsText);
	}
	
	public XIRDataObjectSubtypes getSubtype() {
		String subtypeAsText = data.get(XIR_SUBTYPE).getValue();
		return XIRDataObjectSubtypes.valueOf(subtypeAsText);
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for ( String key : data.keySet()) {
			EncodedString value = data.get(key);
			buffer.append(key);
			buffer.append(":");
			buffer.append(value.getEncoding());
			buffer.append("=");
			buffer.append(value.getValue());
			buffer.append("\n");
		}
		return buffer.toString();
	}

	public static XIRDataObject getXIRDataObject(ArrayList<String> record) {
		XIRDataObject xdo = new XIRDataObject();
		for (String field : record) {
			int eqPos = field.indexOf('=');
			if (eqPos < 0)
				break; // TODO: Add exception code here.		
			String var = field.substring(0, eqPos);
			String value = field.substring(eqPos+1);
			int colonPos = var.indexOf(':');
			if (colonPos < 0)
				break; // TODO: Add exception code here.
			String encoding = var.substring(colonPos+1);
			var = var.substring(0, colonPos);
			if (var == XIR_TYPE)
				xdo.setType(XIRDataObjectTypes.valueOf(value));
			else if (var == XIR_SUBTYPE) 
				xdo.setSubtype(XIRDataObjectSubtypes.valueOf(value));
			else 
				xdo.data.put(var, new EncodedString(XIREncodings.valueOf(encoding), value));
		}
		return xdo;
	}
}

class EncodedString {
	private XIREncodings encoding;
	private String value;
	
	public EncodedString(XIREncodings encoding, String value) {
		this.encoding = encoding;
		this.value = value;
	}

	public XIREncodings getEncoding() {
		return encoding;
	}

	public String getValue() {
		return value;
	}
}