package org.betterxml.examples.natural3.particle;


import java.util.ArrayList;
import java.util.List;

import org.betterxml.natural3.annotations.Children;
import org.betterxml.natural3.annotations.Element;

@Element("Parameters")
public class Parameters {
	
	@Children(Parameter.class)
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();
	
	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
}
