package com.qa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.utils.TestUtil;

public class Tests extends TestUtil{
	
	
	@Test
	public void test1() {
		Assert.assertTrue(true);
		log("test case passed");
	}
	
	@Test
	public void test2() {
		Assert.assertTrue(false);
		log("test case failed");
	}

}
