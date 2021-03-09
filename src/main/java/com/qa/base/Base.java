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
import com.qa.reporting.ExtentReportListener;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base extends Configuration {

	protected static ThreadLocal<EventFiringWebDriver> driver = new ThreadLocal<>();

	private static long PAGELOAD_TIME = 60;
	private static long IMPLICIT_TIME = 5;
	private static long SCRIPTLOAD_TIME = 5;

	private static Logger logger = LogManager.getLogger(Base.class);
	
	public Base() {
		
	}

	@BeforeMethod
	public void start() {
		initBrowser(config("browser"));
	}

	@AfterMethod
	public void end() {
		driver().quit();
	}

	public static EventFiringWebDriver driver() {
		return driver.get().register(new ExtentReportListener());
	}
	
	private static void initBrowser(String browserName) {
		switch (browserName) {
		case BrowserType.CHROME:
			WebDriverManager.chromedriver().setup();
			driver.set(new EventFiringWebDriver(new ChromeDriver()));
			break;
		case BrowserType.FIREFOX:
			WebDriverManager.chromedriver().setup();
			driver.set(new EventFiringWebDriver(new FirefoxDriver()));
			break;
		case BrowserType.EDGE:
			WebDriverManager.edgedriver().setup();
			driver.set(new EventFiringWebDriver(new EdgeDriver()));
			break;
		case BrowserType.IE:
			WebDriverManager.iedriver().setup();;
			driver.set(new EventFiringWebDriver(new InternetExplorerDriver()));
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver.set(new EventFiringWebDriver(new ChromeDriver()));
			break;
		}
		driver().manage().window().maximize();
		driver().manage().deleteAllCookies();
		driver().manage().timeouts().pageLoadTimeout(PAGELOAD_TIME, TimeUnit.SECONDS);
		driver().manage().timeouts().implicitlyWait(IMPLICIT_TIME, TimeUnit.SECONDS);
		driver().manage().timeouts().setScriptTimeout(SCRIPTLOAD_TIME, TimeUnit.SECONDS);
		driver().navigate().to(config("url"));
	}
}
