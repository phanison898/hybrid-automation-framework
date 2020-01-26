package com.qa.reporting;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.Platform;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.base.Configuration;

public class ExtentManager extends Configuration{
	
	private static ExtentReports extent;
	private static ExtentHtmlReporter html;
	private static String file = System.getProperty("user.dir")+"/TestResults/Report/"+config("reportName")+".html";
	
	public static ExtentReports getExtent() {
		if(extent==null) {
			extent = new ExtentReports();
			extentConfig();
			html = new ExtentHtmlReporter(file);
			htmlConfig();
			extent.attachReporter(html);
		}
		return extent;
	}
	
	public static void endExtent() {
		getExtent().flush();
	}
	
	private static void extentConfig() {
		getExtent().setSystemInfo("Client", config("client"));
		getExtent().setSystemInfo("Domain", config("url"));
		getExtent().setSystemInfo("Environment", config("env"));
		getExtent().setSystemInfo("JDK version", System.getProperty("java.version"));
		getExtent().setSystemInfo("Platform", Platform.getCurrent().toString());
		getExtent().setSystemInfo("Operating System", System.getProperty("os.name"));
		getExtent().setSystemInfo("Automation-Tester", config("admin"));
		getExtent().setSystemInfo("Email_ID", config("emailId"));
		getExtent().setSystemInfo("Date", new Date().toString());
	}
	
	private static void htmlConfig() {
		html.config().enableTimeline(true);
		html.config().setDocumentTitle(config("reportName"));
		html.config().setCSS(".r-img{width:450px;height:300px;}");
		html.config().setReportName("HtmlReport");
		if(config("theme").equalsIgnoreCase("dark"))
			html.config().setTheme(Theme.DARK);
		else
			html.config().setTheme(Theme.STANDARD);
		html.config().setTimeStampFormat("yyyy:MM:dd hh:mm:ss SSS a");
	}
	
	private static String getCss() {
		String path = System.getProperty("user.dir")+"/src/main/resources/style.css";
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str = "";
		String s;
		try {
			while((s = bf.readLine())!=null) {
				str += s;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
		
	}
	public static void main(String[] args) {
		String str = getCss();
		System.out.println(str);
	}
	

}
