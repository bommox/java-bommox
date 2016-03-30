package bommox.selenium.core.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import bommox.Util;
import bommox.selenium.Report;
import bommox.selenium.core.Environment;
import bommox.selenium.core.QueryModel;
import bommox.selenium.utils.SeUtils;

public class MilanunciosSearch extends Search {
	
	private static final Logger logger = LogManager.getLogger(MilanunciosSearch.class);
	
	public MilanunciosSearch(QueryModel model, List<String> discardList) {
		super(model, discardList);
	}

	@Override
	public Environment getEnvironment() {
		return Environment.MILANUNCIOS;
	}

	@Override
	public void run() {
		Scanner fileScanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream("descartados.txt"));
		List<String> descartados = new ArrayList<String>();
		while(fileScanner.hasNext()) {
			descartados.add(fileScanner.nextLine());
		}
		fileScanner.close();
		logger.debug("Cargados " + descartados.size() + " referencias");
		WebDriver driver = SeUtils.open("http://www.milanuncios.com");
		getElement("tb-busqueda").sendKeys(model.getSearch());
		getElement("bt-busqueda").click();
		if (model.getMinPrice() != null) {
			getElement("tb-desde").sendKeys(String.valueOf(model.getMinPrice()));
		}
		new Select(getElement("combo-demanda")).selectByValue("n"); //Solo oferta
		getElement("bt-buscar").click();
		final AtomicBoolean paginar = new AtomicBoolean(true);
		Report.addToSelected("----------");
		while (paginar.get()) {
			//Busqueda entre resultados
			getElements("dv-resultado").forEach((element) -> {
				if (paginar.get() == false) return;
				
				String referencia = getElement("item-ref", element).getText();
				String titulo = getElement("item-title", element).getText();
				String descripcion = getElement("item-desc", element).getText();
				String fecha = getElement("item-fecha", element).getText();
				
				if (fecha.contains("día")) {
					logger.debug("Fin de la paginaci�n. Fecha : " + fecha);
					paginar.set(false);;
				} else {
					if (descartados.contains(referencia)) {
						logger.info(referencia + " -> en lista de descarte");
					} else if (!Util.StringUtil.containsEveryWord(titulo, model.getTitlelistArray()) || 
						 Util.StringUtil.containsSomeWord(titulo, model.getBlacklistArray()) || 
						 Util.StringUtil.containsSomeWord(descripcion, model.getBlacklistArray())) {
					} else {
						Report.addToSelected(referencia + " [" + model.getId() + "] --> " + titulo + ":::" + descripcion);
					}
				}
			});
			
			getElement("bt-siguiente").click();
			logger.info("Pasamos de pagina");
		}
		
		//Pasar de pagina
		SeUtils.sleep(1000);
		driver.quit();		
	}
	
	
	public static List<WebElement> getElements(String key) {
		return getElements(key, null);
	}
	
	public static WebElement getElement(String key) {
		return getElement(key, null);
	}
	
	
	public static List<WebElement> getElements(String key, WebElement root) {
		List<WebElement> result = new ArrayList<WebElement>();
		//SeUtils.sleep(100);
		switch (key) {
		case "dv-resultado":
			result = SeUtils.getElements(By.className("x1"));
			break;

		default:
			logger.error("No hay elemento registrado con key " + key);
			break;
		}
		return result;
	}
	
	public static WebElement getElement(String key, WebElement root) {
		WebElement result = null;
		//SeUtils.sleep(100);
		switch (key) {
		case "tb-busqueda":
			result = SeUtils.getElement(By.id("q"));
			break;
		case "bt-busqueda":
			result = SeUtils.getElement(By.className("bt"));
			break;
		case "combo-demanda":
			result = SeUtils.getElement(By.id("demanda"));
			break;
			
		case "tb-desde":
			result = SeUtils.getElement(By.id("desde"));
			break;
		case "bt-buscar":
			result = SeUtils.getElement(By.className("f1"));
			break;
		case "bt-siguiente":
			result = SeUtils.getElement(By.linkText("Siguiente"));
			break;

		case "item-ref":
			result = SeUtils.getElement(By.cssSelector(".x2 .x5"), root);
			break;
		case "item-fecha":
			result = SeUtils.getElement(By.cssSelector(".x2 .x6"), root);
			break;
		case "item-title":
			result = SeUtils.getElement(By.cssSelector(".cti"), root);
			break;

		case "item-desc":
			result = SeUtils.getElement(By.cssSelector(".tx"), root);
			break;

		default:
			logger.error("No hay elemento registrado con key " + key);
			break;
		}
		
		
		if (result == null) {
			logger.warn("El item con key: " + key + " ha devuelto null.");
		}
		
		return result;
	}

}
