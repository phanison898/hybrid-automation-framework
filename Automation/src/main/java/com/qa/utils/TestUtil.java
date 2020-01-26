package com.qa.utils;

import java.io.File;
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

}
