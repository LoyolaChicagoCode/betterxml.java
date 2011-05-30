package org.betterxml.flexpath.functions;

import java.util.ArrayList;
import java.util.List;

import org.betterxml.flexpath.exceptions.InvalidFunctionArguments;
import org.betterxml.xelement.XElement;
import org.betterxml.xelement.XNode;
import org.betterxml.xelement.XPCData;

public class PCDataContains extends FlexFunction {
	
	public PCDataContains(String[] args) throws InvalidFunctionArguments {
		super(args);
	}
	
	protected void validateArgs() throws InvalidFunctionArguments {
		//if too many arguments are passed in, then you can't evaluate
		//if no argument is passed in then you can't evaluate
		if (args == null || args.length != 1) 
			throw new InvalidFunctionArguments("Invalid Arguments! PCDataContains takes exactly one argument.", args);
	}
	
	
	public List<XNode> evaluate(List<XNode> currentData) {
		if (currentData == null)
			return null;
		
		List<XNode> results = new ArrayList<XNode>();

		for (XNode node : currentData) {
			if (!(node instanceof XElement)) {
				continue;
			}
			
			for (XPCData pcdata : ((XElement)node).getPCData()) {
				if (pcdata.getText().contains(args[0])) {
					results.add(pcdata);
				}
			}
		}
		
		return results;
	}
	
}
