package com.gsk.components;

import com.gsk.pages.HomePageLocaters;
import com.gsk.utility.BaseClass;

public class OrangeHrmLoginComponents extends BaseClass {
	
	HomePageLocaters loc = new HomePageLocaters();
	
	public void launchApp() throws Exception {
		try {
		openBrowser("chrome");
		
		openURL(readData.testDataValue.get("AppURL"));
		readData.addStepDetails("launchApp", "Application should open the url", "successfully opened the url", "Pass", "Y");
		}catch(Exception e) {
	      		
			readData.addStepDetails("launchApp","Application should open the url", "Unable to open the application", "Fail", "N");
		}
	}
	
	public void login() throws Exception {
		
		try {
			type(loc.username,readData.testDataValue.get("Username"));
			type(loc.password,readData.testDataValue.get("Password"));
			click(loc.login);
			readData.addStepDetails("login", "User should be able to login the application", "successfully login", "Pass", "Y");
		}catch(Exception e) {
			readData.addStepDetails("login", "User should be able to login the application", "Unable to login", "Fail", "N");
		}
	}

}
