package com.qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.qa.base.Base;
import com.qa.reporting.TestManager;

public class TestUtil extends Base{
	
	public static String captureSnap() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String path = System.getProperty("user.dir")+"/TestResults/snaps/"+sdf.format(new Date())+".png";
		TakesScreenshot ss = ((TakesScreenshot) driver());
		File src = ss.getScreenshotAs(OutputType.FILE);
		File dest = new File(path);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public static void log(String log){
		TestManager.getTest().log(Status.INFO, log);
		try {
		TestManager.getTest().log(Status.INFO, "",
				MediaEntityBuilder.createScreenCaptureFromPath(captureSnap(),"screenshot-one").build());
		}catch(Exception e) {
			
		}
	}
	
	public static void generateHtmlFile(String src, String dest) {
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(src));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str = "";
		String s;
		try {
			while((s = bf.readLine())!=null) {
				str += s;
				str += "<br />";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String html = "<!DOCTYPE html><html><head></head><body>"+str+"</body></html>";
		FileWriter writter = null;
		try {
			writter = new FileWriter(new File(dest));
			writter.write(html);
			writter.close();
		} catch (IOException e) {e.printStackTrace();}
	}

}
