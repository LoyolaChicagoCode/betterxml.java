package org.betterxml.natural3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.betterxml.natural3.annotations.Attribute;
import org.betterxml.natural3.annotations.CData;
import org.betterxml.natural3.annotations.Children;

public class Util {
	
	/**
	 * Check that a field has only one and only one exclusive field annotation
	 * and return that annotation.
	 */
	public static Annotation checkFieldAnnotations(Field f) {
		//TODO bad, hardcoded:
		return assertOneNotNull(f.getAnnotation(Attribute.class), 
								f.getAnnotation(CData.class), 
								f.getAnnotation(Children.class));
	}
	
	/**
	 * Returns the one non-null item in the array or throws an AssertionError if the array
	 * contains more than one non-null item.
	 * 
	 * @param <T>
	 * @param objects
	 * @return
	 */
	public static <T> T assertOneNotNull(T...objects) {
		T returnMe = null;
		for(T t: objects) {
			if(t!=null) {
				if(returnMe == null) {
					returnMe = t;
				} else {
					throw new AssertionError("More than one object in " + objects + " is not null.");
				}
			}
		}
		//if(returnMe==null) throw new AssertionError("All objects in " + objects + " are null.");
		return returnMe;
	}
}
