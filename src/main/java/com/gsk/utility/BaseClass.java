	package com.gsk.utility;
	
	import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


//import org.apache.commons.el.parser.ParseException;
	import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
	
public class BaseClass {
		
	static boolean b = true;
	public String TestReportspath;
	public String suiteName;
	public String TestName = null;
	public String TestFullName = null;
	public  String winHandleBefore = null;
	public List<String> desc = new ArrayList<>();
	int r;
	public Map<String, String> reportDetails = new HashMap();
	public Logger log=Logger.getLogger(BaseClass.class);
	public static ReadData readData=new ReadData();
	public HtmlReport htmlrep = new HtmlReport();
	public static  WebDriver driver = null;
	
	
	public void openBrowser(String browserName) {
        if (browserName.equalsIgnoreCase("firefox")) {
        	WebDriverManager.firefoxdriver().setup();
        	driver=new FirefoxDriver();
        }
        else if (browserName.equalsIgnoreCase("ie")) {
        	WebDriverManager.iedriver().setup();
        	driver = new InternetExplorerDriver();

        }
        else if (browserName.equalsIgnoreCase("chrome")) {
        	WebDriverManager.chromedriver().setup();
        	driver = new ChromeDriver();	
        }
    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
    }
	
	public void openURL(String url) throws Exception {
		try {
			driver.get(url);
			log.info("Opened the URL :"+url);
		} catch (RuntimeException e) {
			log.fatal("Unable to Open the URL :"+e.getMessage());
		}
	
	}

	public void click(By locator) throws Exception {
		try {
			driver.findElement(locator).click();
			log.info("Clicked on "+locator);
		} catch (Exception e) {
			log.error("Unable to click on "+locator);
		}
	}
	public void click(String locator) throws Exception {
		try {
			driver.findElement(By.linkText(locator)).click();
			log.info("Clicked on "+locator);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Unable to click on "+locator);
		}
	}
	
		
	public void type(By locator, String data) throws Exception {
		try {
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(data);
			log.info("Entered the value in the Textbox :"+locator);
		} catch (Exception e) {
			log.fatal("Unable to Enter the value in the Textbox :"+locator);		}
	}
	
	public void select(By locator, String data) throws Exception {
		try {
			new Select(driver.findElement(locator)).selectByVisibleText(data);
		//	Select s = new Select(driver.findElement(locator));
		//	s.selectByVisibleText(data);
			log.info("Selected the value form the field :"+locator);
		} catch (RuntimeException e) {
			log.fatal("Unable to select from the field "+e.getMessage());
		}
	}
	public void select(By locator, int no) throws Exception {
		try {
			new Select(driver.findElement(locator)).selectByIndex(no);
		//Select dropdown = new Select(driver.findElement(locator));
		//dropdown.selectByIndex(no);
			log.info("Selected the value form the field :"+locator);
		} catch (RuntimeException e) {
		log.fatal("Unable to select from the field "+e.getMessage());
		}
	}

	
	public void switchwindow_TAB(int index) throws Exception {
		try {
			Set<String> str = driver.getWindowHandles();
			Object[] windowName = str.toArray();
			driver.switchTo().window(windowName[index].toString());
			driver.manage().window().maximize();
		} catch (RuntimeException localRuntimeException) {
			System.out.println("Error in Switching the window:" + index + "Fail");
			readData.addStepDetails("Switch to Window", "Switch to window should be done", "Error in Switching to the Window: " + localRuntimeException.getMessage(), "FAIL","N");
			localRuntimeException.getMessage();
		}
	
	}
	
	public void switchframe(WebElement elem) throws Exception {
		try {
			driver.switchTo().frame(elem);
			log.info("Switched to frame");
		} catch (Exception e) {
			System.out.println("Error in Switching the Frame:" + e.getMessage() + "Fail");
			
		}
	
	}
	public void switchToDefaultFrame() throws Exception {
		try {
			driver.switchTo().defaultContent();
			log.info("Navigated back to window from frame");
		} catch (Exception e) {
			System.out.println("Error in Switching the Frame:" + e.getMessage() + "Fail");
		}
	
	}
	

	public void js_type(By loc, String data) throws Throwable{
		try {
			WebElement location=driver.findElement(loc);
			((JavascriptExecutor)driver).executeScript("arguments[0].value='"+data+"'", location);
		} catch (Exception e) {
			
		}

	}

	public  void jsClick(By loc) throws Exception {
		try {
			WebElement element = driver.findElement(loc);
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
		//	JavascriptExecutor executor = (JavascriptExecutor)driver;
		//	executor.executeScript("arguments[0].click();", element);
			
		}
		catch (Exception e) {
		} 	
	
	
	}

	
	public  void highlight(By loc) throws Exception 
	{
		try{
			WebElement elem = driver.findElement(loc);
			JavascriptExecutor je=(JavascriptExecutor)driver;
			je.executeScript("arguments[0].style.border='3px solid blue'", elem);
	
		} catch (RuntimeException localRuntimeException)
		{
			System.out.println("Error in Highlighting the element :" + localRuntimeException.getMessage() + "Fail");
			readData.addStepDetails("highlight the element", "Element should be highlighted ", "Error in Highlighting the element : " + localRuntimeException.getMessage(), "FAIL","N");
			localRuntimeException.getMessage();
		}
	}
	
	
	
	public void waitForElement(By locator, int timer) throws Exception{
		try{
			for (int i = 0; i < timer; i++) {
				try{
					driver.findElement(locator).isDisplayed();
					System.out.println("Element is available :"+locator);
					break;
				}catch (RuntimeException localRuntimeException) { 
					Thread.sleep(1000);
					System.out.println("Waiting for........"+locator);
				} 
			}
		}catch (RuntimeException localRuntimeException) {
			System.out.println("Error in performing Wait:" + localRuntimeException.getMessage() + "Fail");
			readData.addStepDetails("Error in performing Wait:", "It should wait for the element", "Error in performing Wait:: " + localRuntimeException.getMessage(), "FAIL","N");
			localRuntimeException.getMessage();
		}
	}
	
	public void verifyField(String loc)  {
		try{
			driver.findElement(By.xpath(loc)).isDisplayed();
			log.info("Element is available :"+loc);
		}catch(Exception e){
			log.error("Element is not available :"+loc);
		}
		
	}
	
	public int totalitemsdropdownlist(WebElement elem)  {
		List<WebElement> dropdown_values = null;
		try {
			Select dropdownfield = new Select(elem);
			dropdown_values = dropdownfield.getOptions();
			
		} catch (RuntimeException localRuntimeException) {
			System.out.println("Error in finding total no. of elements in dropdown: " + localRuntimeException.getMessage() + "Fail");
			// readData.addStepDetails("List box size", "Get the no of items available in the dropdown", "Error in finding total no. of elements in dropdown: " + localRuntimeException.getMessage(), "FAIL","N");
			localRuntimeException.getMessage();
		}
		return dropdown_values.size();
	}

	public static void verifyElementIsEnabled(WebElement elem, boolean paramBoolean) 
	{
		try
		{
			boolean bool = elem.isEnabled();
			if (bool == paramBoolean)
				System.out.println("Element is present in expected state"+elem+"Pass");
			else
				System.out.println("Element is not present in expected state"+elem+"Fail");
		}
		catch (RuntimeException localRuntimeException)
		{
			System.out.println("Element not found:"+elem+"Fail");
			//    readData.addStepDetails("Verify Element", "Element should be present", "Element not found: " + localRuntimeException.getMessage(), "FAIL","N");
			localRuntimeException.getMessage();
		}
	}

	public  String getAlertMessageText()
	{
		String str1 = null;
		str1 = driver.switchTo().alert().getText();
		return str1;
	}

	
	public void pressEnterKey()
	{
		try
		{
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	public void VerifyText(WebElement elem, String paramString2)
	{
		try
		{
			String selectedOption = new Select(elem).getFirstSelectedOption().getText();
			if (selectedOption.trim().equalsIgnoreCase(paramString2))
			{
				System.out.println("Text was found :"+paramString2+"Pass");
				//  readData.addStepDetails("Verify Element", "Element "+paramString2+" should be available", "Element "+paramString2+" was found in DOM", "PASS","N");
			}
			else
			{
				System.out.println("Text was not found :"+paramString2+"Fail");
				//  readData.addStepDetails("Verify Element", "Element "+paramString2+" should be available", "Element "+paramString2+" is not found", "FAIL","N");
			}
		}
		catch (RuntimeException localRuntimeException)
		{
			System.out.println("Element was not found :"+elem+"Fail");
			//   readData.addStepDetails("Verify Element", "Element "+paramString2+" should be available", "Error in finding the element", "FAIL","N");
			localRuntimeException.getMessage();
		}
	}
	
	public String getToolTipText(WebElement elem,String paramString1) {
		String tooltip = null;
		try{
			if (elem != null)
			{
				tooltip = elem.getAttribute(paramString1);  
			}
		}
		catch (RuntimeException localRuntimeException) {
			System.out.println("Error in Getting tool tip text:"+localRuntimeException.getMessage()+"Fail");
			localRuntimeException.getMessage();
		}
		return tooltip;
	}
	public static void verifyListItems(WebElement elem){
		try{
			Select listBox = new Select(elem);
			List<WebElement> allItems = listBox.getOptions();
			for (WebElement item:allItems){
				System.out.println("Item is available in list:"+item);
			}
		}catch (Exception e){
			System.out.println("Issue While Selecting Value in Drop Down Object :"+elem);
		}
	}
	
	public static By getLocators(String paramString1, String paramString2)
	{
		if (paramString1.trim().equalsIgnoreCase("xpath"))
			return By.xpath(paramString2);
		if (paramString1.trim().equalsIgnoreCase("cssselector"))
			return By.cssSelector(paramString2);
		if (paramString1.trim().equalsIgnoreCase("tagname"))
			return By.tagName(paramString2);
		if (paramString1.trim().equalsIgnoreCase("id"))
			return By.id(paramString2);
		if (paramString1.trim().equalsIgnoreCase("name"))
			return By.name(paramString2);
		if (paramString1.trim().equalsIgnoreCase("linktext"))
			return By.linkText(paramString2);
		return null;
	}
	public static String defaultdropdownselecteditem(WebElement elem) {
	
		Select dropdownfield = new Select(elem);
		String text = dropdownfield.getFirstSelectedOption().getText();
		System.out.println(text.trim());
		return dropdownfield.getFirstSelectedOption().getText().trim();
	}
	
	public String alldropdownlistvalues(WebElement elem) {
		Select dropdownfield = new Select(elem);
		List<WebElement> dropdownfield_values = dropdownfield.getOptions();
	
		String allvalues = "";
		for (int i = 0; i < dropdownfield_values.size(); i++) {
			String currentvalue = dropdownfield_values.get(i).getText();
			String concatvalue = allvalues + currentvalue + ",";
			allvalues = concatvalue;
		}
	
		return allvalues.substring(0, allvalues.length() - 1);
	}
	
	public String getdate(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date d=new Date();
		String date = df.format(d);
		System.out.println(date);
		return date;
	}
	
	public static String getattributevalue(WebElement elem, String requiredattribute) throws Exception {
		String attribute = null;
		try{
			attribute = elem.getAttribute(requiredattribute);
		}catch(RuntimeException localRuntimeException){
			// readData.addStepDetails("Get Element Attribute", "Element attribute should able to get", "Error in getting the Element attribute" + elem, "FAIL","N");
		}
		return attribute;
	}
	
	public void alertaction(String action) {
	
		try {
			if (action.equals("ok")) {
				driver.switchTo().alert().accept();
			} else if (action.equals("cancel")) {
				driver.switchTo().alert().dismiss();
			}
		} catch (Exception e) {
			System.out.println("Error in performing action on Alert box:" + action + "Fail");
		}
	
	}
	
	public  String printText(By locator) {
		String text= driver.findElement(locator).getText();
		System.out.println("The text is :"+text);
		return text;
	}
	
	public int totallinnks(WebElement elem) {
		return elem.findElements(By.tagName("a")).size();
	}
	
	public void capturesnapshot(String destinationPath) throws IOException {
		try {
			File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(f, new File(destinationPath));
		}
		catch (Exception e) {
			System.out.println("Error in Capturing Screenshot:Fail");
		}
	
	}
	
	public void dragAndDrop(By question, By target){
		WebElement e1=driver.findElement(question);
		WebElement e2=driver.findElement(target);
		Actions a=new Actions(driver);
		a.dragAndDrop(e1, e2).build().perform();
	}
	
	public boolean verifyElementExist(WebElement elem)
	{
		boolean blnStatus = false;
		WebDriverWait localWebDriverWait = new WebDriverWait(driver, 60L);
		try
		{
			localWebDriverWait.until(ExpectedConditions.presenceOfElementLocated((By) elem));
			System.out.println("Element is available:"+elem+"Pass");
			blnStatus = true;
	
		}
		catch (RuntimeException localRuntimeException)
		{
			System.out.println("Error in finding Element:"+localRuntimeException.getMessage() +"Pass");
		}
		return blnStatus;
	}
	
	public void Mousehover(WebElement elem)
	{
		try
		{
			Actions action = new Actions(driver);
			action.moveToElement(elem).build().perform();
		}
		catch (RuntimeException localRuntimeException)
		{
			System.out.println("Error in Hover on element"+localRuntimeException.getMessage()+"Pass");
	
		}
	}
	
	public void selectListItem(WebElement elem, String paramString)
	{
		int i = 0;
		try
		{
			List localList = driver.findElements(By.tagName("option"));
			Iterator localIterator = localList.iterator();
			while (localIterator.hasNext())
			{
				WebElement localWebElement2 = (WebElement)localIterator.next();
				if (paramString.trim().equalsIgnoreCase(localWebElement2.getText().trim()))
				{
					i = 1;
					localWebElement2.click();
					break;
				}
			}
			System.out.println("Selected option:"+paramString+"Successfully"+"Pass");
			if (i == 0)
			{
				System.out.println("Selected option:"+paramString+"is not present"+"Fail");
			}
		}
		catch (RuntimeException localRuntimeException)
		{
			System.out.println("Issue while Selected value:"+localRuntimeException.getMessage()+"is not present"+"Fail");
		}
	}
	
	public void waitSD(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());;
		}
	}
	
	
	
	@BeforeSuite
	public void setUpSuiteDetails(ITestContext ctx) throws Exception{
		readData.suiteName = ctx.getCurrentXmlTest().getSuite().getName();
		System.out.println("readData.suiteName "+readData.suiteName);
		readData.generateSuiteResultFolder();
		TestReportspath = System.getProperty("user.dir") + "\\TestReports\\" + 
				readData.suiteName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		readData.generateSuiteResultFolder();
	//	readData.DefaultReport("SummaryReport");
	}
	@BeforeMethod
	public void captureDesc(Method method){
		Test test = method.getAnnotation(Test.class);
		if(!test.description().toString().equals("")){
			desc.add(test.description());
		}
	} 
	@AfterSuite
	public void afterSuite() throws Exception{
		try{
			File local=readData.suiteFolder;
			htmlrep.generateHtmlReport(TestName);
			
			} catch (Exception ex){
			System.out.println("Result Suite file is not being generated : "+ex.getMessage());
		}
	}

	@BeforeClass
	public void BeforeClass() throws Exception
	{ 
		Thread.sleep(1000);
		TestName = this.getClass().getSimpleName();
		TestFullName = this.getClass().getName();
		System.out.println("TestName ::::::::::::::::"+TestName);
		System.out.println(System.getProperty("user.dir")+"\\Resources\\TestData.xlsx");
		//readData.captureTestCaseStartTime();
		Thread.sleep(2000);
	}

	@AfterClass 
	public void afterClass() throws Exception
	{
		readData.addSummaryReport(TestName,desc.get(r), TestFullName);
		r++;
		readData.knowTestCaseStatus(TestName);
		//flush();
	} 
	
	}
