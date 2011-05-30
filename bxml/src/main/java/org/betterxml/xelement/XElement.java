package org.betterxml.xelement;

import java.util.LinkedList;
import java.util.List;

import org.betterxml.BetterXmlContentHandler;
import org.betterxml.BetterXmlException;
import org.betterxml.xelement.XAttributes;
import org.betterxml.xelement.XNode;
import org.betterxml.xelement.XPCData;
import org.betterxml.xelement.XProcessingInstruction;



public class XElement extends XNode {

	protected List<XNode> children = null;
	protected XAttributes attributes;
	
	private String nameSpaceURI;
	private String qName;
	
	/***********************************************************************
	 * CONSTRUCTORS ********************************************************
	 **********************************************************************/
	
	/**
	 * Constructor. The name is set to an empty string, the children of the 
	 * element are set to an empty LinkedList of XNode, and the attributes of the 
	 * element are set to an empty instance of XAttributes by default.
	 */
	public XElement() {
		this("","","");
	}
	
	/**
	 * Constructor. The local name is set to the name you pass in, but the URI of the NameSpace
	 * and the qName are set to empty strings.
	 * @param name
	 */
	public XElement(String name) {
		this("", "", name);
	}
	
	
	/**
	 * Constructor. The children of the element are set to an empty LinkedList of XNode and
	 * the attributes of the element are set to an empty instance of XAttributes by default.
	 * @param nameSpaceURI  String. The URI of the Element's namespace.
	 * @param qName  String.  The qName of the Element.
	 * @param name  String. Local name of the Element.
	 */
	public XElement(String nameSpaceURI, String qName, String name) {
		this(nameSpaceURI, qName, name, new LinkedList<XNode>(), new XAttributes());
	}
	
	/**
	 * Constructor.  The children of the element are set to an empty LinkedList of XNode by default.
	 * @param name  String. Name of the Element.
	 * @param attributes  XAttributes. The attributes of the Element.
	 */
	public XElement(String name, XAttributes attributes) {
		this("", "", name, new LinkedList<XNode>(), attributes);
	}
	
	/**
	 * Constructor.  The children of the element are set to an empty LinkedList of XNode by default.
	 * @param nameSpaceURI  String. The URI of the Element's namespace.
	 * @param qName  String.  The qName of the Element.
	 * @param name  String. Local name of the Element.
	 * @param attributes  XAttributes. The attributes of the Element.
	 */
	public XElement(String nameSpaceURI, String qName ,String name, XAttributes attributes) {
		this(nameSpaceURI, qName, name, new LinkedList<XNode>(), attributes);
	}
	
	/**
	 * Constructor. The attributes of the element are set to an empty instance of XAttributes by default.
	 * @param name  String. Name of the Element.
	 * @param children  LinkedList of XNode. The children of the Element.
	 */
	public XElement(String name, List<XNode> children) {
		this("", "", name, children, new XAttributes());
	}
	
	/**
	 * Constructor. The attributes of the element are set to an empty instance of XAttributes by default.
	 * @param nameSpaceURI  String. The URI of the Element's namespace.
	 * @param qName  String.  The qName of the Element.
	 * @param name  String. Local name of the Element.
	 * @param children  LinkedList of XNode. The children of the Element.
	 */
	public XElement(String nameSpaceURI, String qName, String name, List<XNode> children) {
		this(nameSpaceURI, qName, name, children, new XAttributes());
	}
	
	/**
	 * Constructor.
	 * @param name  String. Local name of the Element.
	 * @param children  LinkedList of XNode.  The children of the Element
	 * @param attributes  XAttributes. The attributes of the Element.
	 */
	public XElement(String name/*localname*/, List<XNode> children, XAttributes attributes) {
		this("", "", name, children, attributes);
	}
	
	/**
	 * Constructor.
	 * @param nameSpaceURI  String. The URI of the Element's namespace.
	 * @param qName  String.  The qName of the Element.
	 * @param name  String. Local name of the Element.
	 * @param children  LinkedList of XNode.  The children of the Element
	 * @param attributes  XAttributes. The attributes of the Element.
	 */
	public XElement(String nameSpaceURI, String qName, String name/*localname*/, List<XNode> children, XAttributes attributes) {
		this.nameSpaceURI = nameSpaceURI;
		this.qName = qName;
		this.name = name;
		this.children = children;
		this.attributes = attributes;
		initialized();
	}
	
	
	/***********************************************************************
	 * CODE THAT DEALS WITH CHILDREN ***************************************
	 **********************************************************************/
	
	
	/**
	 * Get all the children of the instance of XElement.
	 * @return LinkedList<XNode> All the children of the instance of XElement
	 */
	public List<? extends XNode> getChildren() {
		return children;
	}
	
	/**
	 * Checks to make sure that the supplied XNode is not equal to this instance of XElement or any of
	 * this instance's ancestors. If it is an XElementException is thrown. If it isn't then nothing 
	 * happens.
	 * @param newNode  XNode to check to this and it's ancestors
	 */
	private void checkForAncestorLoop(XNode newNode) throws BetterXmlException {
		XNode node = this;
		while (node!=null) {
			if (node.equals(newNode))
				throw BetterXmlException.SettingSelfAsAncestor(newNode);
			node = node.getParent();
		}
	}
	
	/**
	 * Set the children of the instance of XElement. If children already exist they are<br>
	 * are overwritten.
	 * @param children List<XNode>. To set as the children of the instance of XElement
	 */
	public void setChildren(List<? extends XNode> children) throws BetterXmlException {
		//make sure none of the children would become its own ancestor
		for (XNode child : children ) {
			checkForAncestorLoop(child);
		}
		
		this.children.clear();
		
		//set parent in the children to this
		for (XNode child : children ) {
			child.setParent(this);
			this.children.add(child);
		}	
	}
	
	/**
	 * Add new supplied list of children to the end of the list of children that already 
	 * exist in the instance of XElement.
	 * @param children LinkedList<XNode> children to add to the children in the instance of XElement.
	 */
	public void appendChildren(List<? extends XNode> children) throws BetterXmlException {
		//make sure none of the children would become its own ancestor
		for (XNode child : children ) {
			checkForAncestorLoop(child);
		}
		
		//set parent in the children to this
		for (XNode child : children ) {
			child.setParent(this);
			this.children.add(child);
		}
		
	}
	
	/**
	 * Add a supplied XNode to the children of the instance of XElement. The XNode is added to the end of the list.
	 * @param node  XNode.  A child to add to the end of the list of children in the instance of XElement.
	 */
	public void appendChild(XNode node) throws BetterXmlException {
		//make sure the new child would become its own ancestor
		checkForAncestorLoop(node);
		
		//set parent in new child to this
		node.setParent(this);
		
		children.add(node);
	}
	

	/**
	 * Insert a supplied XNode to the beginning of the list of children for the instance of XElement.
	 * @param node  XNode. The child to insert at the beginning of the list of children of the instance of XElement. 
	 */
	public void insertChild(XNode node)  throws BetterXmlException {
		//make sure the new child would become its own ancestor
		checkForAncestorLoop(node);
		
		//set parent in new child to this
		node.setParent(this);
		
		insertChild(0, node);
	}
	
	
	/**
	 * Insert the supplied XNode into the list of children of the instance of XElement at the given index.
	 * @param index  int.  The index where the XNode should be inserted.
	 * @param node  XNode.  The XNode that is to be inserted into the list of children for the instance of XElement.
	 */
	public void insertChild(int index, XNode node) throws BetterXmlException {
		//make sure the new child would become its own ancestor
		checkForAncestorLoop(node);
		
		//set parent in new child to this
		node.setParent(this);
		
		if (index < 0) index = 0;
		if (index >= children.size()) {
			appendChild(node);
		} else {
			children.add(index, node);
		}
	}
	
	/**
	 * Remove the child at the specified location in the collection of children.
	 * @param index  int. The index of the child to be removed.
	 * @throws BetterXmlException
	 */
	public void removeChild(int index) throws BetterXmlException {
		//see if there are any children to remove
		if (children.size() == 0) 
			throw new BetterXmlException("Cannot remove the child. This node has no children.");
		
		//make sure the index is valid
		if (index < 0) 
			throw new BetterXmlException("Cannot remove child. " + index + " is not a valid index. " +
					"An index must be greater than zero (0)");
		
		if (index >= children.size())
			throw new BetterXmlException("Cannot remove child. " + index + " is not a valid index. " +
					"An index must be less than the amount of children (" + children.size() + ")." );
		
		//if it made it this far, you are good to remove the child
		children.remove(index);
	}
	
	
	/**
	 * Remove the given XNode from the collection of children. No action is taken if the given XNode
	 * is not in the collection of children. If the given XNode is in the collection of children more than once
	 * then only the first one is removed
	 * @param node  XNode. The node to remove from the collection of children.
	 */
	public void removeChild(XNode node) {
		children.remove(node);
	}
	
	/**
	 * Removes all pcdata from the element.
	 */
	public void removeAllPCData() {
		List<XPCData> pcdata = getPCData();
		children.removeAll(pcdata);
	}
	
	/**
	 * Removes all children elements from the element.
	 */
	public void removeAllElements() {
		List<XElement> elements = getChildrenElements();
		children.removeAll(elements);
	}
	
	/**
	 * Removes all children elements with the given name
	 * @param name  String. The name of the element that you want to remove all of.
	 */
	public void removeAllElements(String name) {
		List<XElement> elements = getChildrenElements(name);
		children.removeAll(elements);
	}
	
	/**
	 * Removes all processing instructions from the element.
	 */
	public void removeAllProcessingInstructions() {
		List<XProcessingInstruction> processingInstructions = getProcessingInstructions();
		children.removeAll(processingInstructions);
	}
	
	
	/**
	 * Resets the collection of children, which effectively empties the collection and leaves you with 
	 * no children.
	 */
	public void removeAllChildren() {
		children = new LinkedList<XNode>();
	}
	
	/**
	 * Compares the given XNode to each child in the list and returns true if 
	 * a match is found.
	 * @param node  XNode.  The node to look for
	 * @return  boolean.  True if the given XNode is in the collection of children; false if it is not.
	 */
	public boolean containsChild(XNode node) {
		return children.contains(node);		
	}
	
	
	/**
	 * Gets all children of the instance of XElement that are of type XElement
	 * @return  LinkedList.  all the XElement children of the instance of XElement.
	 */
	public List<XElement> getChildrenElements() {
		LinkedList<XElement> elements = new LinkedList<XElement>();
		for (XNode node : getChildren()) {
			if (node instanceof XElement) {
				elements.add((XElement)node);
			}
		}
		return elements;
	}
	
	/**
	 * Gets all children of the instance of XElement that are the type XElement and 
	 * have the same name as that which is supplied.
	 * @param name  String.  The name of the XElement children that you want
	 * @return  LinkedList. All the XElement children of the instance of XElement with the supplied name. 
	 */
	public List<XElement> getChildrenElements(String name) {
		LinkedList<XElement> elements = new LinkedList<XElement>();
		for (XNode node : getChildren()) {
			if (node instanceof XElement && ((XElement)node).nameEquals(name)) {
				elements.add((XElement)node);
			}
		}
		return elements;
	}
	
	/**
	 * Get a list of all children of the instance of XElement that are of instance PCData.
	 * @return  LinkedList.  A list of XPCData children of the instance of XElement.
	 */
	public List<XPCData> getPCData() {
		List<XPCData> elements = new LinkedList<XPCData>();
		for (XNode node : getChildren()) {
			if (node instanceof XPCData) {
				elements.add((XPCData)node);
			}
		}
		return elements;
	}
	
	/**
	 * Get a list of all children of the instance of XElement that are of instance XProcessingInstruction.
	 * @return  LinkedList.  A list of XProcessingInstruction children of the instance of XElement.
	 */
	public List<XProcessingInstruction> getProcessingInstructions() {
		List<XProcessingInstruction> pi = new LinkedList<XProcessingInstruction>();
		for (XNode node : getChildren()) {
			if (node instanceof XProcessingInstruction) {
				pi.add((XProcessingInstruction)node);
			}
		}
		return pi;
	}
	
	
	/***********************************************************************
	 * CODE THAT DEALS WITH NAME AND NAMESPACE *****************************
	 **********************************************************************/
	
	
	/**
	 * Set the name of the instance of XElement to that which is supplied.
	 * @param name  String. What the name of the instance of XElement will be set to.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Gets the name of the element. In namespace speak, this is the local name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the qName of the element. This is the name of the element that includes the 
	 * @param qName
	 */
	public void setQName(String qName) {
		this.qName = qName;
	}
	
	
	/**
	 * Returns the qName of the element. If the element does not have a namespace then an
	 * empty string is returned
	 * @return  String.
	 */
	public String getQName() {
		return qName;
	}
	
	/**
	 * Sets the URI of the Element's namespace.
	 * @param nameSpaceURI  String. The URI of the Element's namespace.
	 */
	public void setNameSpaceURI(String nameSpaceURI) {
		this.nameSpaceURI = nameSpaceURI;
	}
	
	/**
	 * Returns the URI of the Element's namespace. If the Element does not have a namespace
	 * then an empty string is returned.
	 * @return  String.
	 */
	public String getNameSpaceURI() {
		return nameSpaceURI;
	}
	
	
	/**
	 * Check if the name of the instance of XElement is equal to that which is supplied
	 * @param checkName String. The name to compare to the name of the instance of XElement.
	 * @return  boolean.  True if the supplied name is equal to the instance of XElement's name. False if it is not.
	 */
	public boolean nameEquals(String checkName) {
		return name.equals(checkName);
	}
	
	
	/***********************************************************************
	 * CODE THAT DEALS WITH ATTRIBUTES *************************************
	 **********************************************************************/
	
	/**
	 * Get the XAttributes of the XElement instance.
	 * @return  XAttributes. The XAttributes of the instance of XElement.
	 */
	public XAttributes getAttributes() {
		return attributes;
	}
	
	
	
	/**
	 * Set the XAttributes of the XElement instance to that which is supplied. If the instance of 
	 * XElement already has attributes, they are overwritten.
	 * @param attrs  XAttribugtes.  The XAttributes to add to XElement.
	 */
	public void setAttributes(XAttributes attrs) {
		this.attributes = attrs;
	}
	
	
	/***********************************************************************
	 * CODE THAT DEALS WITH OUTPUT *****************************************
	 **********************************************************************/
	
	public String toString() {
		return "ELEMENT\n\tName: " + name + "\n\tParent: " + parent.getName() + "\n\t" + 
				"Children Count: " + children.size();
	}

	@Override
	public void acceptContentHandler(BetterXmlContentHandler contentHandler) {
		contentHandler.startElement(nameSpaceURI, name, qName, attributes.size());
		for(String attribute: this.getAttributes()) {
			String value = attributes.getAttributeValue(attribute);
			contentHandler.attribute(nameSpaceURI, attribute, attribute, value);
		}
		for(XNode child: this.getChildren()) {
			child.acceptContentHandler(contentHandler);
		}
		contentHandler.endElement(nameSpaceURI, name, qName);
		
	}
	
}
