package org.betterxml.flexpath;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.CorruptedFlexPathException;
import org.betterxml.flexpath.exceptions.UnknownFunctionException;
import org.betterxml.flexpath.functions.FlexFunction;
import org.betterxml.xelement.XDocument;
import org.betterxml.xelement.XNode;

public class FlexPath {

	private static boolean IsInstanceOfFlexFunction(Class c) {
		Class testClass = c;
		while (testClass != null) {
			if (testClass.getName().equalsIgnoreCase(FlexFunction.class.getName())) return true;
			testClass = testClass.getSuperclass();
		}
		return false;
	}
	
	class FlexPathClassLoader extends ClassLoader {
		
		/**
		 * XHandlerClassLoader constructor.
		 */
		public FlexPathClassLoader(){
			super();
			try {
				super.loadClass("org.betterxml.flexpath.functions.FlexFunction");
		
			} catch (ClassNotFoundException cnfe) {
				System.err.println("FlexFunction.class is not found. We have some serious problems here!");
				cnfe.printStackTrace();
			}
		}
		
		/**
		 * Loads/gets the Class that has the same name as that passed in. If there are more than two classes 
		 * with the same name (they could have the same name with different packages) the first one that is come
		 * across is returned.
		 * @param name  String. Name of the Class that you want returned.
		 * @return  The class that has the same name as that which is supplied.
		 */
		public Class load(String functionName) {
			//change element name so that it works for java class naming rules
			String name = functionName.replaceAll(":","_");	//for namespaces, since : isn't allowed in java class names
			
			Package[] packages = super.getPackages();
			for (int i=0;i<packages.length;i++) {
				//FIXME I don't like that it is okay to fail here, not a good use of try/catch
				try {
					Class c = super.loadClass(packages[i].getName()+"."+name);
					//check if proper type
					//TODO don't know if this will work on classes extending a class that implements FlexPathBuilder
					if (IsInstanceOfFlexFunction(c)) return c;
				} catch (Exception e) {
					//System.err.println("Problem finding "+packages[i].getName()+"."+name);
				}	
			}
			return null;
		}
		
		
	}
	
	private FlexPathClassLoader fpcl = new FlexPathClassLoader();
	private ArrayList<FlexFunction> functions = new ArrayList<FlexFunction>();
	
	public FlexPath() { 	}

	public void addFunction(String functionName, String[] args) 
			throws UnknownFunctionException, CorruptedFlexPathException
	{  
		/*TODO to save time I could have a map where I look up functions I've already loaded, 
		so I don't have to find them and load them again -- do this by overriding class
		loader's getClass() and keeping the map in the FlexPathClassLoader*/
		
		Class[] argTypes = new Class[]{String[].class};
		
		Class c = fpcl.load(functionName);
		if (c == null) 
			throw new UnknownFunctionException(functionName + " is not a known function, therefore it cannot be handled.");
		
		Constructor constructor;
		try {
			constructor = c.getConstructor(argTypes);
		} catch (NoSuchMethodException nsme) {
			throw new UnknownFunctionException("Don't know how to handle function " +
					functionName + " even though the FlexFunction exists, it does not have " +
					"a valid constructor.");
		}
		
		/*TODO I probably need to rethink the exceptions I'm throwing and condense them some
		 * or rename them and whatnot
		 */
		
		Object[] initArgs = new Object[]{args};
		
		try {
			Object function = constructor.newInstance(initArgs);
			functions.add((FlexFunction)function);
		} catch (InvocationTargetException ite) {
			throw new CorruptedFlexPathException("There was an InvocationTargetException when creating a new " + 
					functionName + ". This will corrupt your FlexPath.");
		} catch (IllegalAccessException iae) {
			throw new CorruptedFlexPathException("There was an IllegalAccessException when creating a new " + 
					functionName + ". This will corrupt your FlexPath.");
		} catch (InstantiationException ie) {
			throw new CorruptedFlexPathException("There was an InstantiationException when creating a new " + 
					functionName + ". This will corrupt your FlexPath.");
		}
	}
	
	public List<XNode> evaluatePath(XDocument doc) {
		//TODO if root then start at root element. If document then do document wide
		List<XNode> currentData = new ArrayList<XNode>();
		currentData.add(doc.getRootElement());
		for (FlexFunction function : functions) {
			currentData = function.evaluate(currentData);
		}
		return currentData;
	}
		
}
