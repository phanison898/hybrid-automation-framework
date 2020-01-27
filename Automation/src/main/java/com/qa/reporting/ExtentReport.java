package com.qa.reporting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.base.Base;

public class ExtentReport extends Base implements ITestListener, WebDriverEventListener {

	private static final String OUTPUT_FOLDER = "./TestResults/Report";
	private static final String FILE_NAME = "index1.html";

	private static ExtentReports extent = init();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private static ExtentReports init() {

		Path path = Paths.get(OUTPUT_FOLDER);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + "/" + FILE_NAME);
		htmlReporter.config().setDocumentTitle("Automation Test Results");
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setCSS(".step-details>img{width:480px;height:240px;}");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);

		return extent;
	}

	public synchronized void onStart(ITestContext context) {
		System.out.println("Test Suite started!");
	}

	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Test Suite is ending!"));
		extent.flush();
		test.remove();
	}

	public synchronized void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String qualifiedName = result.getMethod().getQualifiedName();
		int last = qualifiedName.lastIndexOf(".");
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");
		String className = qualifiedName.substring(mid + 1, last);

		System.out.println(methodName + " started!");
		ExtentTest extentTest = extent.createTest(methodName, result.getMethod().getDescription());

		extentTest.assignCategory(result.getTestContext().getSuite().getName());
		extentTest.assignCategory(className);
		test.set(extentTest);
		test.get().getModel().setStartTime(getTime(result.getStartMillis()));
		System.out.println("Test case is started");
		test.get().log(Status.INFO, "Test case is started");
	}
	
	public static String captureSnap() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		Path path1 = Paths.get(System.getProperty("user.dir")+"/TestResults/snaps");
		if (!Files.exists(path1)) {
			try {
				Files.createDirectories(path1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String path = path1+"/"+sdf.format(new Date())+".png";
		TakesScreenshot ss = ((TakesScreenshot) driver);
		File src = ss.getScreenshotAs(OutputType.FILE);
		File dest = new File(path);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().log(Status.PASS, "Test case is passed");
		try {test.get().log(Status.PASS, "",MediaEntityBuilder
					.createScreenCaptureFromPath(captureSnap()).build());
		} catch (IOException e) {e.printStackTrace();}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	public synchronized void onTestFailure(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		test.get().log(Status.FAIL, "Test case is failed");
		try {test.get().log(Status.FAIL, "",MediaEntityBuilder
				.createScreenCaptureFromPath(captureSnap()).build());
	} catch (IOException e) {e.printStackTrace();}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().log(Status.SKIP, "Test case is skipped");
		try {test.get().log(Status.SKIP, "",MediaEntityBuilder
				.createScreenCaptureFromPath(captureSnap()).build());
	} catch (IOException e) {e.printStackTrace();}
		test.get().getModel().setEndTime(getTime(result.getEndMillis()));
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	@Override
	public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateTo(String url, WebDriver driver) {
		System.out.println("URL [" + url + "] is going to be launched");
	}

	@Override
	public void afterNavigateTo(String url, WebDriver driver) {
		System.out.println("URL [" + url + "] is launched");
	}

	@Override
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeClickOn(WebElement element, WebDriver driver) {
		System.out.println("[" + element.getText() + "] ---> is clicked");
		test.get().log(Status.INFO, "[" + element.getText().toUpperCase().replace(" ", "") + "] ---> is clicked");
		try {test.get().log(Status.INFO, "",MediaEntityBuilder
				.createScreenCaptureFromPath(captureSnap()).build());
	} catch (IOException e) {e.printStackTrace();}
	}

	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub

	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub

	}
}
