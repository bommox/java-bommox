package bommox.selenium.core.search;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bommox.selenium.core.Environment;
import bommox.selenium.core.QueryModel;

public abstract class SearchFactory {
	
	private static final Logger log = LogManager.getLogger(SearchFactory.class);
	
	
	public static Search getSearch(Environment env, QueryModel model, List<String> discards) {
		Search result = null;
		switch (env) {
		case MILANUNCIOS:
			result = new MilanunciosSearch(model, discards);
			break;

		default:
			result = new DummySearch(model, discards);
			break;
		}
		return result;
	}
	
	public static class DummySearch extends Search {

		public DummySearch(QueryModel model, List<String> discardList) {
			super(model, discardList);
		}

		@Override
		public Environment getEnvironment() {
			return null;
		}

		@Override
		public void run() {
			log.warn("Dummy Search!");
			
		}
		
	}
		
	 
	 

}
