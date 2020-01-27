package com.qa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.qa.reporting.ExtentManager;
import com.qa.reporting.TestManager;
import com.qa.utils.TestUtil;

public class TestListener implements ITestListener{
	
	@Override
	public void onTestStart(ITestResult result) {
		TestManager.startTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		TestManager.getTest().log(Status.PASS, "Test case is passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		TestManager.getTest().log(Status.FAIL, "Test case is passed");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		TestManager.getTest().log(Status.SKIP, "Test case is passed");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
			
	}

	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getExtent();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.endExtent();
	}
	

}
