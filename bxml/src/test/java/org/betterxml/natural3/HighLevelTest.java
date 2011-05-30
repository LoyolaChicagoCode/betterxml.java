package org.betterxml.natural3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;

import org.betterxml.natural3.elementClasses.Myroot;
import org.betterxml.natural3.elementClasses.SubElement1;
import org.betterxml.natural3.elementClasses.SubElement2;
import org.betterxml.natural3.elementClasses.XElement1;
import org.betterxml.parsers.XmlParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HighLevelTest {

    public static final String defaultNs = "http://betterxml.org/default";
    public static final String nsOne = "http://betterxml.org/one";
    public static final String nsTwo = "http://betterxml.org/two";
    public static final String xml = 
            "<myroot xmlns=\"" + defaultNs + "\">" + "\n" + 
              "<xelement1 xmlns:nsone=\"" + nsOne + "\" xmlns:nstwo=\"" + nsTwo + "\">" + "\n" +
                "<subelement1 nsone:a=\"hello\" nstwo:b=\"world\"/>" + "\n" + 
                "<nstwo:subelement2 a=\"hello again\"/>" + "\n" +
              "</xelement1>" + "\n" + 
            "</myroot>";
    
    private Myroot root;
    
    @Before
    public void setup() {
    	Natural3ContentHandler<Myroot> parser = new Natural3ContentHandler<Myroot>(Myroot.class);
		new XmlParser().setContentHandler(parser).parse(new StringReader(xml));
		root = parser.getRoot();
    }
    
    @After
    public void teardown() {
    	root = null;
    }
    
    @Test
    public void testRoot() {
        assertNotNull(root);
        assertTrue(root instanceof Myroot);
        assertEquals(1, root.getXelement1s().size());
    }

    @Test
    public void testXElement1() {
    	XElement1 element = root.getXelement1s().get(0);
    	
        assertNotNull(element);
        assertTrue(element instanceof XElement1);
    }

    @Test
    public void testSubElementOne() {
    	SubElement1 element = root.getXelement1s().get(0).getSubelement1();
    	
        assertNotNull(element);
        assertTrue(element instanceof SubElement1);
    }

    @Test
    public void testSubElementTwo() {
    	SubElement2 element = root.getXelement1s().get(0).getSubelement2();
    	
        assertNotNull(element);
        assertTrue(element instanceof SubElement2);
    }
}