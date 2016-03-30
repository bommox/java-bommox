

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bommox.Util;
import bommox.selenium.core.Environment;
import bommox.selenium.core.QueryModel;
import bommox.selenium.core.search.Search;
import bommox.selenium.core.search.SearchFactory;

public class MilanunciosMain {
	
	private static final Logger logger = LogManager.getLogger(MilanunciosMain.class);

	private static final String DISCARD_FILE = "descartados.txt";
	
	
	public static void main(String[] args) {
		logger.debug("Application starts!");
		
		List<String> discards = new ArrayList<String>();
		try(Stream<String> stream = Files.lines(Paths.get(Util.IO.getURI(DISCARD_FILE)))) {
			stream.forEach(s -> discards.add(s));
			logger.debug(String.format("Loaded %d from discard file.", discards.size()));
		} catch (Exception e) {
			logger.error("Error reading discard file");
		}
		
				
		Search macbook = SearchFactory.getSearch(Environment.MILANUNCIOS, getModel("macbook") , discards);
		macbook.run();
		
		logger.debug("Application ends");
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