package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base extends Configuration{
	
	static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	static long PAGELOAD_TIME = 60;
	static long IMPLICIT_TIME = 5;
	static long SCRIPTLOAD_TIME = 5;
	static Logger logger = LogManager.getLogger(Base.class);
	
	public Base() {
		
	}
	
	@BeforeMethod public void start() {
		initBrowser(config("browser"));
		System.out.println(config("reportName"));
	}
	
	@AfterMethod public void end() {
		driver().quit();
	}
	
	public static WebDriver driver() {
		return driver.get();
	}
	
	public static void initBrowser(String browserName) {
		switch(browserName) {
		case BrowserType.CHROME:
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			break;
		case BrowserType.FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			break;
		case BrowserType.EDGE:
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
			break;
		case BrowserType.IE:
			WebDriverManager.iedriver().setup();
			driver.set(new InternetExplorerDriver());
			break;
		default:
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
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
