package com.qa.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.qa.utils.TestUtil;

public class Tests extends TestUtil{
	static Logger logger = LogManager.getLogger(Tests.class);

	@Test
	public void test1() {
		WebElement el =driver.findElement(By.xpath("//input[@title='Search']"));
		el.clear();
		el.sendKeys("java latest version");
		el.submit();
	}
	
}
