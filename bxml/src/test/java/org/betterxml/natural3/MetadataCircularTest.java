package org.betterxml.natural3;


import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.betterxml.natural3.Metadata.FieldMetadata;
import org.betterxml.natural3.annotations.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;

public class MetadataCircularTest {

	private final static String PARENT_ELEMENT_NAME = "parent";
	private final static String PARENT_ELEMENT_URI = "http://test.com/fake";
	private final static String PARENT_ELEMENT_ATTR1_NAME = ""; 
	
	private final static String CHILD_ELEMENT_ONE_NAME = "child";
	private final static String CHILD_ELEMENT_ONE_ATTR1_NAME = "child";
	private final static String CHILD_ELEMENT_ONE_ATTR1_URI = "child";
	
	private final static String CHILD_ELEMENT_TWO_NAME = "childtwo";
	private static final String CHILD_ELEMENT_THREE_NAME = "childthree";
	
	private Metadata metadataHolder;
	private Metadata.ClassMetadata parentMetadata, childMetadata;
	
	@Before
	public void setUp() throws Exception {
		metadataHolder = new Metadata(ParentElement.class);
		parentMetadata = metadataHolder.getMetadata(ParentElement.class);
		childMetadata = metadataHolder.getMetadata(ChildElementOne.class);
	}
	
	@Test
	public void testParentElementClass() throws Exception {
		assertEquals(PARENT_ELEMENT_NAME, parentMetadata.getElementName());

		//make sure the parentMetadata contains references to all of the children classes
		assertEquals(3, parentMetadata.getAllChildren().size());
		testChildrenIterator(parentMetadata.getAllChildren(), ChildElementOne.class);
		testChildrenIterator(parentMetadata.getAllChildren(), ChildElementTwo.class);
		testChildrenIterator(parentMetadata.getAllChildren(), ChildElementThree.class);
	}
	
	@Test
	public void testSubElementClass() throws Exception {
		assertEquals(CHILD_ELEMENT_ONE_NAME, childMetadata.getElementName());
		
		assertEquals(1, childMetadata.getAllChildren().size());
		testChildrenIterator(childMetadata.getAllChildren(), ParentElement.class);
		//assertIteratorContains(classes.iterator(), metadataHolder.getMetadata(ParentElement.class));
	}
	
	@Test
	public void testChildElementTwoClass() throws Exception {
		assertNotNull(metadataHolder.getMetadata(ChildElementTwo.class));
		assertEquals(CHILD_ELEMENT_TWO_NAME, metadataHolder.getMetadata(ChildElementTwo.class).getElementName());
	}
	
	@Test
	public void testChildElementThreeClass() throws Exception {
		assertNotNull(metadataHolder.getMetadata(ChildElementThree.class));
		assertEquals(CHILD_ELEMENT_THREE_NAME, metadataHolder.getMetadata(ChildElementThree.class).getElementName());	
	}
	
	public void testChildrenIterator(Collection<FieldMetadata<Children>> children, Class klass) {
		for(FieldMetadata<Children> child: children) {
			for(Class checkme: child.getAnnotation().value()) {
				if(checkme.equals(klass)) return;
			}
		}
		throw new AssertionError("class not found: " + klass);
	}
	
	@Test
	public void testParentElementChildrenMethods() throws Exception {
		Method get = ParentElement.class.getMethod("getChildElements");
		Method add = ParentElement.class.getMethod("addChildElement", ChildInterface.class);
		
		FieldMetadata<Children> fieldMetadata = parentMetadata.getChild(CHILD_ELEMENT_ONE_NAME);
		Method getActual = fieldMetadata.getAccessor();
		Method addActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(add, addActual);
	}
	
	@Test
	public void testChildElementChildrenMethods() throws Exception {
		Method get = ChildElementOne.class.getMethod("getParentElements");
		Method add = ChildElementOne.class.getMethod("addParentElement", ParentElement.class);
		
		FieldMetadata<Children> fieldMetadata = childMetadata.getChild(PARENT_ELEMENT_NAME);
		Method getActual = fieldMetadata.getAccessor();
		Method addActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(add, addActual);
	}
	
	@Test
	public void testParentElementCDataMethods() throws Exception {
		Method get = ParentElement.class.getMethod("getCData");
		Method set = ParentElement.class.getMethod("addCDatum", String.class);
		
		FieldMetadata<CData> fieldMetadata = parentMetadata.getCdata();
		Method getActual = fieldMetadata.getAccessor();
		Method setActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(set, setActual);
	}
	
	@Test
	public void testParentElementAttributeMethods() throws Exception {
		Method get = ParentElement.class.getMethod("getAttr1");
		Method set = ParentElement.class.getMethod("setAttr1", String.class);
		
		FieldMetadata<Attribute> fieldMetadata = parentMetadata.getAttribute(PARENT_ELEMENT_ATTR1_NAME);
		Method getActual = fieldMetadata.getAccessor();
		Method setActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(set, setActual);
	}
	
	@Test
	public void testChildClassAttributeMethods() throws Exception {
		//TODO add in namespace test
		Method get = ChildElementOne.class.getMethod("getAttributeWithNamespace");
		Method set = ChildElementOne.class.getMethod("setAttributeWithNamespace", String.class);
		
		FieldMetadata<Attribute> fieldMetadata = childMetadata.getAttribute(CHILD_ELEMENT_ONE_ATTR1_NAME);
		Method getActual = fieldMetadata.getAccessor();
		Method setActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(set, setActual);
	}
	
	@Test
	public void testChildElementThreeSingleton() throws Exception {
		Method get = ParentElement.class.getMethod("getChildElementThree");
		Method set = ParentElement.class.getMethod("setChildElementThree", ChildElementThree.class);
		
		FieldMetadata<Children> fieldMetadata = metadataHolder.getMetadata(ParentElement.class).getChild(CHILD_ELEMENT_THREE_NAME);
		Method getActual = fieldMetadata.getAccessor();
		Method setActual = fieldMetadata.getMutator();
		
		assertEquals(get, getActual);
		assertEquals(set, setActual);
	}
	
	@Element(PARENT_ELEMENT_NAME)
	@Namespace(PARENT_ELEMENT_URI)
	public class ParentElement {
		
		@Children({ChildElementOne.class, ChildElementTwo.class})
		private List<ChildInterface>  childElements;
		
		@Children(ChildElementThree.class)
		@Singleton
		private ChildElementThree childElementThree;
		
		@Attribute(PARENT_ELEMENT_ATTR1_NAME) 
		private String attr1;
		
		@CData 
		private List<CDataWrap> cdata;
		
		public void addChildElement(ChildInterface element) { 
			childElements.add(element); 
		}
		
		public List<ChildInterface> getChildElements() { 
			return childElements; 
		}
		
		public void setAttr1(String attr1) {
			this.attr1 = attr1;
		}
		
		public String getAttr1() {
			return attr1;
		}
		
		public void addCDatum(String cdata) {
			this.cdata.add(new CDataWrap(cdata));
		}

		public List<CDataWrap> getCData() {
			return Collections.unmodifiableList(cdata);
		}

		public ChildElementThree getChildElementThree() {
			return childElementThree;
		}

		public void setChildElementThree(ChildElementThree childElementThree) {
			this.childElementThree = childElementThree;
		}
	}
	
	public interface ChildInterface {
		
	}
	
	@Element(CHILD_ELEMENT_ONE_NAME)
	public class ChildElementOne implements ChildInterface {
		
		@Children(ParentElement.class)
		private List<ParentElement>  parentElements;
		
		@Attribute(CHILD_ELEMENT_ONE_ATTR1_NAME) 
		@Namespace(CHILD_ELEMENT_ONE_ATTR1_URI)
		private String attributeWithNamespace;
		
		public void addParentElement(ParentElement element) { 
			parentElements.add(element); 
		}
		
		public List<ParentElement> getParentElements() { 
			return parentElements; 
		}
		
		public String getAttributeWithNamespace() {
			return attributeWithNamespace;
		}

		public void setAttributeWithNamespace(String attr1) {
			this.attributeWithNamespace = attr1;
		}
	}
	
	@Element(CHILD_ELEMENT_TWO_NAME)
	public class ChildElementTwo implements ChildInterface {
		
	}
	
	@Element(CHILD_ELEMENT_THREE_NAME)
	public class ChildElementThree {
		
	}
	


}
