package org.betterxml.xelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.betterxml.BetterXmlException;
import org.betterxml.handlers.ToXElementContentHandler;
import org.betterxml.handlers.ToXirContentHandler;
import org.betterxml.parsers.ParserUtil;
import org.betterxml.parsers.XmlParser;
import org.betterxml.xelement.XAttributes;
import org.betterxml.xelement.XDocument;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNameSpace;
import org.betterxml.xelement.elementClasses.DefaultElement;
import org.betterxml.xelement.elementClasses.SubElementOne;
import org.betterxml.xelement.elementClasses.SubElementTwo;
import org.betterxml.xelement.elementClasses.XElementOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HighLevelTest {

    private static final String defaultNs = "http://betterxml.org/default";
    private static final String nsOne = "http://betterxml.org/one";
    private static final String nsTwo = "http://betterxml.org/one";
    private static final String xml = 
            "<myroot xmlns=\"" + defaultNs + "\">" + "\n" + 
              "<xelement1 xmlns:nsone=\"" + nsOne + "\" xmlns:nstwo=\"" + nsTwo + "\">" + "\n" +
                "<subelement1 nsone:a=\"hello\" nstwo:b=\"world\"/>" + "\n" + 
                "<nstwo:subelement2 a=\"hello again\"/>" + "\n" +
              "</xelement1>" + "\n" + 
            "</myroot>";
    
    
    private XDocument doc;
    
    @SuppressWarnings("unchecked")
	@Parameters
    public static Collection parameters() throws Exception {
      return Arrays.asList(new Object[][] { {getDocumentFromXir()}, {getDocumentFromXml()} } );
    }
    
    public HighLevelTest(XDocument doc) {
    	this.doc = doc;
    }
    
    @Test
    public void testRoot() {
    	
    	XElement element = doc.getRootElement();
    	
        assertNotNull(element);
        assertTrue(element instanceof DefaultElement);
        assertEquals("myroot", element.getQName());
        assertEquals("myroot", element.getName());
        assertEquals(defaultNs, element.getNameSpaceURI());
        
        XAttributes attributes = element.getAttributes();
        assertEquals(defaultNs, attributes.getAttributeValue("xmlns"));
    }

    @Test
    public void testXElement1() {
    	XElement element = doc.getRootElement().getChildrenElements().get(0);
    	
        assertNotNull(element);
        assertTrue(element instanceof XElementOne);
        assertEquals(defaultNs, element.getNameSpaceURI());
        assertEquals("xelement1", element.getName());
        assertEquals("xelement1", element.getQName());
    }

    @Test
    public void testSubElementOne() {
    	XElement element = doc.getRootElement().getChildrenElements().get(0).getChildrenElements().get(0);
    	
        assertNotNull(element);
        assertTrue(element instanceof SubElementOne);
        assertEquals(defaultNs, element.getNameSpaceURI());
        assertEquals("subelement1", element.getName());
        assertEquals("subelement1", element.getQName());
    }

    @Test
    public void testSubElementTwo() {
    	//change to 0
    	XElement element = doc.getRootElement().getChildrenElements().get(0).getChildrenElements().get(1);
    	
    	System.out.println(element.getClass());
        assertNotNull(element);
        assertTrue(element instanceof SubElementTwo);
        assertEquals(nsTwo, element.getNameSpaceURI());
        assertEquals("subelement2", element.getName());
        assertEquals("nstwo:subelement2", element.getQName());
    }
    
    /**
     * Generate an xir document from the xml string with the xir tools.  Run the resulting xir string
     * through the xir input to xelement.  Return the resulting xdocument.
     * @return
     * @throws Exception
     */
    private static XDocument getDocumentFromXir() throws Exception {
        //TODO create some sort of 'chaining parser' so that we dont' have to write out the xir
        //to a string
    	
        //convert the xml to XIR with the XIR tools and then run xelement on the resulting xir.
        StringWriter outputWriter = new StringWriter();
        ToXirContentHandler xirHandler = new ToXirContentHandler(outputWriter);
        new XmlParser().setContentHandler(xirHandler).parse(new StringReader(xml));
        outputWriter.flush();

        return ParserUtil.getXElementFromXir(new StringReader(outputWriter.toString()),
        		registerNameSpaceMappings(new ToXElementContentHandler()));
		
    }
    
    /**
     * Run xelement on the xml string directly.
     * @return
     * @throws Exception
     */
    private static XDocument getDocumentFromXml() throws Exception {
        return ParserUtil.getXElementFromXml(new StringReader(xml), 
        		registerNameSpaceMappings(new ToXElementContentHandler()));
    }
    
    private static ToXElementContentHandler registerNameSpaceMappings(ToXElementContentHandler handler) throws BetterXmlException {
        XNameSpace defaultNamespace = new XNameSpace(DefaultElement.class, defaultNs);
        defaultNamespace.setElementMapping("xelement1", XElementOne.class);
        defaultNamespace.setElementMapping("subelement1", SubElementOne.class);
        XNameSpace nsOneNamespace = new XNameSpace(nsOne);
        XNameSpace nsTwoNamespace = new XNameSpace(nsTwo);
        nsTwoNamespace.setElementMapping("subelement2", SubElementTwo.class);
        //TODO remove this
        nsTwoNamespace.setElementMapping("nstwo:subelement2", SubElementTwo.class);
        
        handler.registerNameSpace(defaultNamespace);
        handler.registerNameSpace(nsOneNamespace);
        handler.registerNameSpace(nsTwoNamespace);
        return handler;
        
    }

    /*TODO there is some bug where we can't use inner classes?
    public class DefaultElement extends XElement { }
    public class ElementOne extends XElement { }
    public class ElementTwo extends XElement { }
    public class Subelement extends XElement { }*/
    
}