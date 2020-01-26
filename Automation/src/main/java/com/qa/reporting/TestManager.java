package com.qa.reporting;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestManager {

		static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
		static ExtentReports extent = ExtentManager.getExtent();

		public static synchronized ExtentTest getTest() {
			return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
		}

		public static synchronized void endTest() {
			extent.removeTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
		}

		public static synchronized void startTest(String testName) {
			ExtentTest test = extent.createTest(testName);
			extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		}
}