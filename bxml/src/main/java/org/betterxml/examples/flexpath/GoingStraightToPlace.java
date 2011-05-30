package org.betterxml.examples.flexpath;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.List;

import org.betterxml.flexpath.FlexPath;
import org.betterxml.flexpath.exceptions.CorruptedFlexPathException;
import org.betterxml.flexpath.exceptions.UnknownFunctionException;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.handlers.ToXMLContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.xelement.XDocument;
import org.betterxml.xelement.XNode;

/**
 * @author nabicht
 *
 * A demonstration of jumping directly to a point in an XDocument. This is the 
 * basic intended use of a FlexPath with an XML Document: to be able to specify a path
 * to a node or group of nodes and be able to jump to the node(s) automatically.
 * 
 * This example will use an XML representation of an ebook. In an attempt to use
 * real world examples, this will suppose that a teacher is directing a class 
 * to pay particular attention to various excerpts from Franz Kafka's
 * Metamorphosis.
 *
 */
public class GoingStraightToPlace {
	
	
	public static void main (String[] args) {
		
		XDocument doc = getXDocument();
		
		
		System.out.println("FIRST EXAMPLE: Go Straight to first paragraph of firt chapter.\n");
		
		/**
		 * If the teacher wanted to directly point out the first paragraph
		 * of the first chapter, which is truly stunning.
		 */
		
		/*First, build the FlexPath*/
		FlexPath fpb = new FlexPath();
		try {
			fpb.addFunction("child", new String[]{"content"});
			fpb.addFunction("pos", new String[]{"0"});	//optional, sense there is only 1 in doc, but might as well be safe
			fpb.addFunction("child", new String[]{"chapter"});
			fpb.addFunction("pos", new String[]{"0"});
			fpb.addFunction("child", new String[]{"paragraph"});
			fpb.addFunction("pos", new String[]{"0"});
		} catch (UnknownFunctionException ufe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			ufe.printStackTrace();
		} catch (CorruptedFlexPathException cfpe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			cfpe.printStackTrace();
		}
		
		/*Second, run the evaluation on the FlexPath with the XDocument to which
		 * you are applying the FlexPath. Even though you should only be expecting 
		 * one node back, you still get an entire list of XNodes back as the result
		 * because that is the way the evaluation needs to return a general structure
		 * for one item and for 1 million items.
		 */ 
		List<XNode> result = fpb.evaluatePath(doc);
		
		/*Lastly, to prove it worked, print the result to the screen.*/
		printXMLToScreen(result);
		
		System.out.println("\n\nSECOND EXAMPLE: Go Straight to first paragraph of the last chapter. Use first and last instead of pos.\n");
		
		/**
		 * If the teacher wanted to point out the first paragraph of the last chapter.
		 * This time, instead of using the 'pos' function, the 'first' and 'last' functions
		 * are used.
		 */
		/*First, build the FlexPath*/
		FlexPath fpb2 = new FlexPath();
		try {
			fpb2.addFunction("child", new String[]{"content"});
			fpb2.addFunction("pos", new String[]{"0"});	//optional, sense there is only 1 in doc, but might as well be safe
			fpb2.addFunction("child", new String[]{"chapter"});
			fpb2.addFunction("last", new String[]{});
			fpb2.addFunction("child", new String[]{"paragraph"});
			fpb2.addFunction("first", new String[]{});
		} catch (UnknownFunctionException ufe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			ufe.printStackTrace();
		} catch (CorruptedFlexPathException cfpe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			cfpe.printStackTrace();
		}
		
		/*Second, run the evaluation on the FlexPath with the XDocument to which
		 * you are applying the FlexPath.
		 */ 
		List<XNode> result2 = fpb2.evaluatePath(doc);
		
		/*Lastly, to prove it worked, print the result to the screen.*/
		printXMLToScreen(result2);
		
		
		System.out.println("\n\nTHIRD EXAMPLE: Go Straight to third through seventh (inclusive) paragraphs " +
				"of the second chapter. Use first and last instead of pos.\n");
		
		/**
		 * If the teacher wanted to point out paragraphs 3,4,5,6, and 7 of chapter 2.
		 * To accomplish this the range function is used.
		 */
		/*First, build the FlexPath*/
		FlexPath fpb3 = new FlexPath();
		try {
			fpb3.addFunction("child", new String[]{"content"});
			fpb3.addFunction("pos", new String[]{"0"});	//optional, sense there is only 1 in doc, but might as well be safe
			fpb3.addFunction("child", new String[]{"chapter"});
			fpb3.addFunction("pos", new String[]{"1"});
			fpb3.addFunction("child", new String[]{"paragraph"});
			fpb3.addFunction("range", new String[]{"2","7"});
		} catch (UnknownFunctionException ufe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			ufe.printStackTrace();
		} catch (CorruptedFlexPathException cfpe) {
			System.err.println("Building the FlexPath failed! This exmample is not turning out so well.");
			cfpe.printStackTrace();
		}
		
		/*Second, run the evaluation on the FlexPath with the XDocument to which
		 * you are applying the FlexPath.
		 */ 
		List<XNode> result3 = fpb3.evaluatePath(doc);
		
		/*Lastly, to prove it worked, print the result to the screen.*/
		printXMLToScreen(result3);
		
	}
	
	/**Helper function for printing List of XNodes to the screen*/
	public static void printXMLToScreen(List<XNode> data) {
		for (XNode item : data) {
			item.acceptContentHandler(new ToXMLContentHandler(new OutputStreamWriter(System.out)));
		}
		System.out.flush();
	}
	
	/**Helper function to load the XML we'll be using FlexPath on*/
	public static XDocument getXDocument() {
		String fileName = "book.xml";
		try {
			return ParserUtil.getXElementFromXml(new FileReader(fileName), new ToXElementContentHandler());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
