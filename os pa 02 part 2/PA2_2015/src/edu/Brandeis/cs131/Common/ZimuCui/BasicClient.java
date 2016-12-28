package edu.Brandeis.cs131.Common.ZimuCui;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Industry;


// Never Use MyClient
public class BasicClient extends Client {
	public BasicClient() {
		super("NOT IMPLEMENTED", Industry.TECHNOLOGY, 0, 0);
	}
	
	public BasicClient(String label, Industry industry) {
    	super(label, industry, (int)(Math.random()*10), 3);
    }
	
	public String toString() {
		String typeName = "BASIC";
		return String.format("%s %s %s", super.getIndustry(), typeName, super.getName());
	}
}
