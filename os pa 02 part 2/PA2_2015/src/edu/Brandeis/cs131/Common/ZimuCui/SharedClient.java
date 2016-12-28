package edu.Brandeis.cs131.Common.ZimuCui;

import edu.Brandeis.cs131.Common.Abstract.Client;
import edu.Brandeis.cs131.Common.Abstract.Industry;

//Never Use MyClient
public class SharedClient extends Client {
	public SharedClient() {
		super("NOT IMPLEMENTED", Industry.TECHNOLOGY, 0, 0);
	}
	
	public SharedClient(String label, Industry industry) {
    	super(label, industry, (int)(Math.random()*10), 3);
    }
	
	public String toString() {
		String typeName = "SHARED";
		return String.format("%s %s %s", super.getIndustry(), typeName, super.getName());
	}
}
