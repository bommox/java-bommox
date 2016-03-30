package bommox.selenium.utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeUtils {
	
	static {
		System.setProperty("webdriver.chrome.driver", "C:/programas/selenium/chromedriver.exe");
	}
	
	private static WebDriver currentDriver;
	
	public static WebDriver getCurrentDriver() {
		if (currentDriver == null) {
			currentDriver = getDriver(null);
		} 
		return currentDriver;
	}
	
	public static WebElement getElement(By by) {
		return getElement(by, null);
	}
	public static WebElement getElement(By by, WebElement root) {
		WebElement result;
		try {

			if (root == null) {
				result = getCurrentDriver().findElement(by);
			} else {
				result = root.findElement(by);
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	public static List<WebElement> getElements(By by) {		
		return getElements(by, null);
	}
	

	
	public static List<WebElement> getElements(By by, WebElement root) {
		List<WebElement> result;
		try {
			if (root == null) {
				result = getCurrentDriver().findElements(by);
			} else {
				result = root.findElements(by);
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	
	public static WebDriver getDriver(Browser browser) {
		WebDriver result;
		if (browser == null) {
			browser = Browser.CHROME;
		}
		switch (browser) {
		case FIREFOX:
			result = new FirefoxDriver();
			break;

		default:
			result = new ChromeDriver();
			break;
		}
		new WebDriverWait(result, 30);
		currentDriver = result;
		return result;
		
	}
	
	public static WebDriver open(String url, Browser browser) {
		WebDriver result = getDriver(browser);
		result.get(url);
		return result;
	}
	
	public static WebDriver open(String url) {
		return open(url, null);
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}catch(Exception e) {}
	}
	
	public enum Browser {
		CHROME, FIREFOX;
	}

}