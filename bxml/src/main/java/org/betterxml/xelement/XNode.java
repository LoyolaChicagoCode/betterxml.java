package org.betterxml.xelement;

import org.betterxml.BetterXmlContentHandler;



public abstract class XNode {
	protected XNode parent = null;
	protected String name = "";
	
	/**
	 * This function is so that you don't have to overwrite the constructor. Anything you would like 
	 * implemented at construction you should put in <code>initialize()</code>. It is always called at 
	 * construction.
	 */
	public void initialized() {  }
	
	/**
	 * This function is that allows the developer to define behavior that should occur when the
	 * closing tag of the node is parsed. In other words, actions that occur when the node has 
	 * been initialized and all known data has been loaded.  
	 */
	public void finalized() {  }
	
	/**
	 * Set the parent XNode of the XNode instance
	 * @param parent  XNode. The XNode to set as the parent.
	 */
	public void setParent(XNode parent) {
		this.parent = parent; 
	}
	
	/**
	 * Get the XNode isntance's parent node.
	 * @return  The parent XNode.
	 */
	public XNode getParent() {
		return parent;
	}
	
	/**
	 * Gets the root XNode of the XML document. THe root is determined by
	 * recursively getting the parent XNode until an XNode is found with 
	 * <code>null</code> as the value for the parent.
	 * @return  XNode. The root of the Document.
	 */
	public XNode getRoot() {
		if (parent == null) return this;
		return parent.getRoot();
	}
	
	/**
	 * Get the name of the XNode instance.
	 * @return String.  The XNode instance's name.
	 */
	public String getName() {
		return name;
	}
	
	public abstract void acceptContentHandler(BetterXmlContentHandler contentHandler);
	
}
