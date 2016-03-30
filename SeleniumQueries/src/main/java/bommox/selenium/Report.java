package bommox.selenium;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Report {
	
	private static final Logger logDescarte = LogManager.getLogger("descarte");
	private static final Logger logSeleccion = LogManager.getLogger("seleccion");

	public static void addToSelected(String msg) {
		logSeleccion.info(msg);
	}
	
	public static void addToDiscarded(String msg) {
		logDescarte.info(msg);
	}

}
