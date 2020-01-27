package com.qa.base;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qa.reporting.WebDriverEvents;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base extends Configuration {

	private static WebDriver e_driver;
	static long PAGELOAD_TIME = 60;
	static long IMPLICIT_TIME = 5;
	static long SCRIPTLOAD_TIME = 5;
	private static Logger logger = LogManager.getLogger(Base.class);
	public static EventFiringWebDriver driver;
	private static WebDriverEventListener eventListener;
	
	public Base() {
		
		

	}

	@BeforeMethod
	public void start() {
		initBrowser(config("browser"));
		System.out.println(config("reportName"));
		logger.info("this is a log");
	}

	@AfterMethod
	public void end() {
		driver.quit();
	}

	
	public static void initBrowser(String browserName) {
		switch (browserName) {
		case BrowserType.CHROME:
			WebDriverManager.chromedriver().setup();
			e_driver = new ChromeDriver();
			driver = new EventFiringWebDriver(e_driver);
			eventListener = new WebDriverEvents();
			driver.register(eventListener);
			break;
		case BrowserType.FIREFOX:
			WebDriverManager.chromedriver().setup();
			e_driver = new ChromeDriver();
			driver = new EventFiringWebDriver(e_driver);
			eventListener = new WebDriverEvents();
			driver.register(eventListener);
			break;
		case BrowserType.EDGE:
			WebDriverManager.chromedriver().setup();
			e_driver = new ChromeDriver();
			driver = new EventFiringWebDriver(e_driver);
			eventListener = new WebDriverEvents();
			driver.register(eventListener);
			break;
		case BrowserType.IE:
			WebDriverManager.chromedriver().setup();
			e_driver = new ChromeDriver();
			driver = new EventFiringWebDriver(e_driver);
			eventListener = new WebDriverEvents();
			driver.register(eventListener);
			break;
		default:
			WebDriverManager.chromedriver().setup();
			e_driver = new ChromeDriver();
			driver = new EventFiringWebDriver(e_driver);
			eventListener = new WebDriverEvents();
			driver.register(eventListener);
			break;
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(PAGELOAD_TIME, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_TIME, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(SCRIPTLOAD_TIME, TimeUnit.SECONDS);
		driver.navigate().to(config("url"));
	}
}
