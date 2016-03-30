
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bommox.Util;
import bommox.selenium.core.QueryModel;

public class Main {
	
	private static final Logger logger = LogManager.getLogger("Main");

	public static void main(String[] args) {
		
		QueryModel macmodel = getModel("macbook");
		QueryModel nikonmodel = getModel("nikon");
		
		
		
	}
	
	private static QueryModel getModel(String xmlId) {
		QueryModel result = null;
		InputStream is = Util.IO.getIS("config/" + xmlId + ".xml");
		if (is == null) {
			logger.error("File not found for " + xmlId);
		}
		try {
			Unmarshaller um = JAXBContext.newInstance(QueryModel.class).createUnmarshaller();
			result = (QueryModel) um.unmarshal(is);
		} catch (JAXBException e) {
			logger.error("Error en JAX." , e);
		}
		return result;
	}

}
