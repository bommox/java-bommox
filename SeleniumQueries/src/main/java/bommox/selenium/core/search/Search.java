package bommox.selenium.core.search;

import java.util.ArrayList;
import java.util.List;

import bommox.selenium.core.Environment;
import bommox.selenium.core.QueryModel;

public abstract class Search {
	
	QueryModel model;
	List<String> discardList = new ArrayList<String>();
	
	public Search(QueryModel model, List<String> discardList) {
		this.model = model;
		if (discardList != null) {
			this.discardList = discardList;
		}		
	}
	
	public abstract Environment getEnvironment();
	
	public abstract void run();
	
}
