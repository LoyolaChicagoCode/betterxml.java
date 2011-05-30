package org.betterxml.xelement;

import org.betterxml.BetterXmlContentHandler;


public class XProcessingInstruction extends XNode {
	
	private String target = new String();
	private String data = new String();
	
	/**
	 * Constructor. Name is set by default to "Processing Instruction"
	 * @param target  String.  The target.
	 * @param data  String.  The data.
	 */
	public XProcessingInstruction(String target, String data) {
		this.target = target;
		this.data = data;
		name = "Processing Instruction";
		initialized();
	}
	
	
	/**
	 * Set the target for the instance of XProcessingInstruction
	 * @param target  String. The target.
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	
	
	/**
	 * Get the target for the instance of XProcessingInstruction
	 * @return  String. The target.
	 */
	public String getTarget() {
		return target;
	}

	
	/**
	 * Set the data for the instance of XProcessingInstruction
	 * @param data  String. The data.
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	
	/**
	 * Get the data for the instance of XProcessingInstruction
	 * @return  String. The data.
	 */
	public String getData() {
		return data;
	}
	
	/*public String toXML(int indentLevel) {
		return XNode.Indent(indentLevel) + "<?" + target + " " + data + "?>" + "\n";
	}*/
	
	public String toString() {
		return "Processing Instruction:  target = '" + target + "'   data = '" + data +"'";
	}

	public void acceptContentHandler(BetterXmlContentHandler contentHandler) {
		contentHandler.processingInstruction(name, target);
	}
	
	
}
