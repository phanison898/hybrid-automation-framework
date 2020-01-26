package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {
	
	static String configFilePath = System.getProperty("user.dir")+"/src/main/resources/config.properties";

	public static String config(String key) {
		Properties  config = new Properties();
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(configFilePath));
		} catch(Exception e) {
			e.printStackTrace();
		} try {
			config.load(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return config.getProperty(key);
	}
	
}
