package org.betterxml.natural3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;
import org.betterxml.natural3.annotations.Namespace;
import org.betterxml.natural3.annotations.Singleton;
import org.betterxml.natural3.exceptions.MethodNotFoundException;

public class Metadata {
	
	public HashMap<Class<?>, ClassMetadata> classMap = new HashMap<Class<?>, ClassMetadata>(); 
	public HashMap<String, Class<?>> classNameMap = new HashMap<String, Class<?>>(); 
	
	public Metadata(Class<?> rootClass) {
		processClass(rootClass);
	}
	
	private void processClass(Class<?> klass) {
		ClassMetadata meta = new ClassMetadata(klass);
		classMap.put(klass, meta);
		classNameMap.put(meta.getElementName(), klass);
		
		for(FieldMetadata<Children> child: meta.getAllChildren()) {
			for(Class<?> childClass: child.getAnnotation().value()) {
				if(!childClass.isInterface() && !classMap.containsKey(childClass)) {
					processClass(childClass);
				}	
			}
			//Class<?> childClass = child.getAnnotation().value()[0];
			
		}
	}
	
	public ClassMetadata getMetadata(Class<?> klass) {
		return this.classMap.get(klass);
	}
	
	public ClassMetadata getMetadata(String elementName) {
		return this.classMap.get(this.classNameMap.get(elementName));
	}
	
	/**
	 * This class holds the metadata for a NaturalXML class. Important information is the  class name, 
	 * the described class, and references to the FieldMetadata instances for this particular class.
	 */
	public class ClassMetadata {
		private Class<?> klass;
		private String elementName, uri;
		
		private Map<String, FieldMetadata<Attribute>> attributes = new HashMap<String, FieldMetadata<Attribute>>();
		private FieldMetadata<CData> cdata = null;
		private Map<Class<?>, FieldMetadata<Children>> children = new HashMap<Class<?>, FieldMetadata<Children>>();
		
		public ClassMetadata(Class<?> klass) {
			this.klass = klass;
			
			this.elementName = ((Element) klass.getAnnotation(Element.class)).value();
			
			if(klass.getAnnotation(Namespace.class)!=null) {
				this.uri = ((Namespace) klass.getAnnotation(Namespace.class)).value();
			} else {
				this.uri = null;
			}
		
			
			for(Field field: getAllFields(klass)) {
				//System.out.println(field);
				this.fieldMetadataFactory(field);
			}
		}
		
		private void fieldMetadataFactory(Field field) {
			Annotation annotation = Util.checkFieldAnnotations(field);
			if(annotation instanceof Attribute) {
				Attribute attribute = (Attribute) annotation;
				attributes.put(attribute.value(), new FieldMetadata<Attribute>(klass, field, attribute, "set", "get"));
			} else if (annotation instanceof CData) {
				if(cdata==null) {
					cdata = new FieldMetadata<CData>(klass, field, (CData) annotation, "add", "get");
				} else { /* error? */ }
			} else if (annotation instanceof Children) {
				Children child = (Children) annotation;
				for(Class<?> parentClass: child.value()) {
					//should there be one and only one child? //TODO better compile time check here?
					boolean isSingleton = field.getAnnotation(Singleton.class) != null ? true : false;
					if(isSingleton) {
						//System.out.println("singleton: " + klass);
						children.put(parentClass, new FieldMetadata<Children>(klass, field, child, "set", "get"));
					} else {
						//System.out.println("non-singleton: " + klass);
						children.put(parentClass, new FieldMetadata<Children>(klass, field, child, "add", "get"));
					}
				}
			}
		}

		public Collection<FieldMetadata<Attribute>> getAllAttributes() {
			return attributes.values();
		}
		
		public Collection<FieldMetadata<Children>> getAllChildren() {
			return Collections.unmodifiableCollection(children.values());
		}
		
		public FieldMetadata<Attribute> getAttribute(String attributeName) {
			return this.attributes.get(attributeName);
		}
		
		public FieldMetadata<Children> getChild(String childElementName) {
			//could redo the mappings above and make this a bit more concise
			ClassMetadata classMetadata = classMap.get(classNameMap.get(childElementName));
			return this.children.get(classMetadata.getDescribedClass());
		}
		
		public FieldMetadata<CData> getCdata() {
			return cdata;
		}
		
		public Class<?> getDescribedClass() {
			return klass;
		}

		public String getElementName() {
			return elementName;
		}
		
		public String getUri() {
			return uri;
		}
		
		//add in genericity somehow?
		public Object getInstance() {
			try {
				return this.klass.newInstance();
			} catch(Exception e) {
				//TODO throw betterxmlexception?
				return null;
			}
		} 
	}
	
	public class FieldMetadata<A extends Annotation> {
		private Class<?> parentClass;
		private Field field;
		private A annotation;
		private Method mutator, accessor;
		
		public FieldMetadata(Class<?> parentClass, Field field, A annotation, String mutatorPrefix, String accessorPrefix) {
			this.parentClass = parentClass;
			this.field = field;
			this.annotation = annotation;
			this.mutator = resolveMethod(mutatorPrefix);
			if(this.mutator==null) {
				System.err.println("Error resolving mutator for: " + field);
			}
			this.accessor = resolveMethod(accessorPrefix);
			if(this.accessor==null) {
				System.err.println("Error resolving accessor for: " + field);
			}
		}
		
		public String getFieldName() { return field.getName(); }
		public Method getMutator() { return mutator; }
		public Method getAccessor() { return accessor; }
		public A getAnnotation() { return annotation; }
		
		public String getUri() { 
			for(Annotation annotation: field.getAnnotations()) {
				if(annotation instanceof Namespace){
					Namespace namespace = (Namespace) annotation;
					return namespace.value();
				}
			}
			ClassMetadata parentMetadata = getMetadata(parentClass);
			return parentMetadata.getUri(); 
		}
		
		
		
		private Method resolveMethod(String prefix) {
			String root = field.getName();
		    
			//if we're looking for an add method, singularize the root
			if(prefix.equals("add") ) {
				root = Inflector.singularize(root);
			}
			
			for(Method m: parentClass.getMethods()) {
				//don't be too picky about capitalization
				if(m.getName().equalsIgnoreCase(prefix+root))
					return m;
			}
			
			throw new MethodNotFoundException(prefix+root); 
		}

		public Field getField() {
			return field;
		}
	}
	
	//this should probably be somewhere else
	public static Collection<Field> getAllFields(Class<?> klass) {
		HashSet<Field> fieldSet = new HashSet<Field>();
	
		for(Field field: klass.getDeclaredFields()) {
			fieldSet.add(field);
		}
		
		if(!klass.equals(Object.class)) {
			fieldSet.addAll(getAllFields(klass.getSuperclass()));
		}
		
		return fieldSet;
		
	}
}
