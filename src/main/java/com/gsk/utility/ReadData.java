	package com.gsk.utility;
	
	import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
	/*import com.itextpdf.text.Document;
	import com.itextpdf.text.Font;
	import com.itextpdf.text.PageSize;
	import com.itextpdf.text.pdf.PdfWriter;*/
	import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	
	public class ReadData{
		public Map<String, String> testData = new HashMap<String, String>();
		public Map<String, String> testDataValue = new HashMap<String, String>();;
		public  static File suiteFolder;
		private static File suiteFile;
		public static String suiteFilePath;
		public static String suiteName;
		public static String testCaseReportsFolder = "Test";
		public static String snapShotsFolder = "Snapshots";
		public  String filePath;
		public  List<String> testCaseStartTimeList = new ArrayList();
		public  List<Integer> failCount = new ArrayList();
		public  List<Integer> passCount = new ArrayList();
		public  List<String> testCaseEndTimeList = new ArrayList();
		public  List<String> testCaseNameList = new ArrayList();
		public  List<String> testCaseDescriptionList = new ArrayList();
		public static  final SimpleDateFormat DATE_FORMAT_NOW = new SimpleDateFormat("yyyyMMddHHmmss");
		public static long testStepTempTime;
		public  List<String> testStepElapsedTimeList = new ArrayList();
		public static List<String> testCaseElapsedTimeList = new ArrayList();
		public static File snapShotFolder;
		public  List<String> snapShotPathList = new ArrayList();
		public static  Map<String, String> statusMap = new HashMap();
		public  List<String> stepCount = new ArrayList();
		public  List<String> stepDescription = new ArrayList();
		public  List<String> stepDescription1 = new ArrayList();
	
		public  List<String> stepPrequisite = new ArrayList();
		public  List<String> stepExpected = new ArrayList();
		public  List<String> stepActual = new ArrayList();
		public  List<String> stepStatus = new ArrayList();
		public  static List<String> stepStatus1 = new ArrayList();
	
		public  List<String> objectEvidence = new ArrayList();
		public List<String> testCaseName = new ArrayList();
		public  List<String> testCaseDesc = new ArrayList();
		public static List<String> testCaseName1 = new ArrayList();
		public static List<String> testFullName= new ArrayList();
		public static List<String> testmethod1 = new ArrayList();
		public static List<String> testCaseDesc1 = new ArrayList();
		public static List<String> testCaseStartTimeList1 = new ArrayList();
		public static List<String> testCaseEndTimeList1 = new ArrayList();
		public static List<String> classes= new ArrayList <String>();
		public static List<String> methods= new ArrayList <String>();
		public static List<String> errorDes= new ArrayList <String>();
		public static List<String> ScreenShots= new ArrayList <String>();
		public  String screenShotPath = null;
		
	
		public void addStepDetails(String Param2,String Param3,String Param4,String Param5,String Param6) throws Exception {
	
			stepDescription.add(Param2);
			stepDescription1.add(Param2);
			stepExpected.add(Param3);
			stepActual.add(Param4);
			stepStatus.add(Param5);
			stepStatus1.add(Param5);
			
			String className=null;
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			className=stackTraceElements[3].getClassName();
			
			System.out.println("className is:            " + className);
			classes.add(className);
			methods.add(Param2);
			errorDes.add(Param4);
			//ScreenShots.add(screenShotPath);
			objectEvidence.add(Param6);
/*			long l = System.currentTimeMillis() - testStepTempTime;
			int i = 0;
			int j = (int)(l / 1000L);
			String str = String.valueOf(j);
			if (j >= 60)
			{
				i = j / 60;
				j %= 60;
				str = String.valueOf(j);
			}
			if (j <= 9)
				str = "0" + String.valueOf(j);*/
			testStepTempTime = System.currentTimeMillis();
			fullScreenCapture(Param6,Param5);
		}

		public void knowTestCaseStatus(String paramString1){
			try{
				int i = 0;
				int m = 0;
				int j = 0;
				for (int n = 0; n < stepStatus.size(); n++){
					if(((String)stepStatus.get(n)).trim().equalsIgnoreCase("pass")){
						m++;
						passCount.add(m);
					}else{
						j++;
						i++;
						failCount.add(j);
					}
				}
				if (i > 0){
					statusMap.put(paramString1, "Fail");
				}else{ 
					statusMap.put(paramString1, "Pass");
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	
		public void addSummaryReport(String param2,String param3, String param4){
			try{
				testCaseName.add(param2);
				testCaseDesc.add(param3);
				testCaseName1.add(param2);
				testFullName.add(param4);
				testCaseDesc1.add(param3);
				System.out.println("testCaseName :"+testCaseName);
				System.out.println("testCaseDesc :"+testCaseDesc);
				System.out.println("testCaseName1 :"+testCaseName1);
				System.out.println("testCaseDesc1 :"+testCaseDesc1);
				/*long l = System.currentTimeMillis() - testStepTempTime;
				int i = 0;
				int j = (int)(l / 1000L);
				String str = String.valueOf(j);
				if (j >= 60)
				{
					i = j / 60;
					j %= 60;
					str = String.valueOf(j);
				}
				if (j <= 9)
					str = "0" + String.valueOf(j);
				testCaseElapsedTimeList.add(i + ":" + str);
				testStepTempTime = System.currentTimeMillis();*/
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	
		public void fullScreenCapture(String Parameter,String Parameter1) throws Exception{
            try {
          Robot robot = new Robot();
          String format = "png";
          String fileName = "SnapShot_" + DATE_FORMAT_NOW.format(new Date()) + ".png";
         
         Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
          BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
          if(((Parameter == "Y") || (Parameter == "N")) && (Parameter1.equalsIgnoreCase("Fail"))){
           File f=((TakesScreenshot)BaseClass.driver).getScreenshotAs(OutputType.FILE);
           FileUtils.copyFile(f, new File(snapShotFolder+"/"+fileName));
           
            screenShotPath=snapShotFolder+"/"+fileName;
           ScreenShots.add(screenShotPath);
           
       //      ImageIO.write(screenFullImage, format, new File(snapShotFolder+"//"+ fileName));
              snapShotPathList.add(fileName);
 
          }else if((Parameter == "Y") && (Parameter1.equalsIgnoreCase("Pass"))){
       //   ImageIO.write(screenFullImage, format, new File(snapShotFolder+"//"+ fileName));
            File f=((TakesScreenshot)BaseClass.driver).getScreenshotAs(OutputType.FILE);
              FileUtils.copyFile(f, new File(snapShotFolder+"\\"+fileName));
             snapShotPathList.add(fileName);
           
             screenShotPath=snapShotFolder+"\\"+fileName;
             ScreenShots.add(screenShotPath);
             
          }else{
          snapShotPathList.add("");
          }
      } catch (IOException ex) {
          System.err.println(ex);
      }
     }
	
		public void generateSuiteResultFolder()
		{
			File localFile = new File(System.getProperty("user.dir") + "\\" + "TestReports");
			System.out.println("localFile ::::::::"+localFile);
			if (!localFile.exists())
				localFile.mkdir();
			suiteFolder = new File(localFile + "\\" + suiteName + "_" + DATE_FORMAT_NOW.format(new Date()));
			System.out.println("suiteFolder ::::::::"+suiteFolder);
			if (!suiteFolder.exists())
				suiteFolder.mkdir();
		//	testFolder = new File(suiteFolder + "/" + testCaseReportsFolder);
			snapShotFolder = new File(suiteFolder + "\\" + snapShotsFolder);
	/*		if (!testFolder.exists())
				testFolder.mkdir();*/
			if (!snapShotFolder.exists())
				snapShotFolder.mkdir();
			suiteFile = new File(suiteFolder + "\\" + suiteName + ".html");
			suiteFilePath = suiteName + ".html";
			System.out.println("suiteFilePath   ----"+suiteFilePath);
		}
	
		@SuppressWarnings("resource")
		public  void readTestDataFile(String filePath, String tcID) throws Exception{
			int s = 0;
			try{
				FileInputStream testRunReport = new FileInputStream(filePath);
				Workbook workbook ;
				Sheet  worksheet;
				int n=filePath.indexOf(".");
				String extension=filePath.substring(n);
				if (extension.equalsIgnoreCase(".xlsx"))
				{
					workbook=new XSSFWorkbook(testRunReport);
				} else {
					workbook=new HSSFWorkbook(testRunReport);
	
				}
				int sheets=workbook.getNumberOfSheets();
				System.out.println("sheets "+sheets);
				int j=0;
				List<String> arrayOfString = new ArrayList<>();
				// for(s=0; s<sheets; s++ ){
				// XSSFSheet worksheet = workbook.getSheetAt(s);
				String str=workbook.getSheetName(0);
				System.out.println(str);
				if(str.contains("PreCondition"))
				{
					worksheet=workbook.getSheet(str);
					Row Headertrow = worksheet.getRow(0);
					// for(int i=1;i<=totalrows;i++){
					Row currentrowww = worksheet.getRow(1);
					int columns=currentrowww.getLastCellNum();
					for (int k = 0; k < columns; k++) { 
						if(currentrowww !=null){
							Cell cellkey = Headertrow.getCell(k);
							System.out.println("cellkey "+cellkey); 
							Cell cellValue = currentrowww.getCell(k);
							System.out.println("cellValue "+cellValue);
							System.out.println("-----------------------------");
							if(cellkey.toString() != ""){
								if(!arrayOfString.contains(cellkey.toString())){
									arrayOfString.add(cellkey.toString());
									testData.put(cellkey.toString(), cellValue.toString());
								}else{
									System.out.println("Duplicate Key In Test Data File : "+cellkey.toString());
								//	throw new AutomationException("Duplicate Key In Test Data File : "+cellkey.toString());
								}
							}else{
								System.out.println("Row is empty in Test Data file : ");
							}
						}
					}
					//}
				} 
				String str1=workbook.getSheetName(1); 
				System.out.println(str1);
				if(str1.contains("data"))
				{
					worksheet=workbook.getSheet(str1);
					Row Headertrow = worksheet.getRow(0);
					// int totalrows = worksheet.getLastRowNum()-worksheet.getFirstRowNum();
					int startValue=Integer.parseInt(tcID);
					int endValue=startValue; 
					for(int i=startValue; i<=endValue;i++){
						Row currentrow = worksheet.getRow(i);
						int columns=currentrow.getLastCellNum();
						// if(currentrow.getCell(1).getStringCellValue().equalsIgnoreCase(BaseClass.tcno)){ 
						for (int k = 0; k < columns; k++) { 
							if(currentrow !=null){
								Cell cellkey = Headertrow.getCell(k);
								System.out.println("cellkey "+cellkey); 
								Cell cellValue = currentrow.getCell(k);
								System.out.println("cellValue "+cellValue);
								System.out.println("-----------------------------");
								if(cellkey.toString() != ""){
									if(!arrayOfString.contains(cellkey.toString())){
										arrayOfString.add(cellkey.toString());
										testData.put(cellkey.toString(), cellValue.toString());
										testDataValue.put(cellkey.toString(), cellValue.toString());
									}else{
										System.out.println("Duplicate Key In Test Data File : "+cellkey.toString());
										//throw new AutomationException("Duplicate Key In Test Data File : "+cellkey.toString());
									}
								}else{
									System.out.println("Row is empty in Test Data file : "+(i+1));
								}
							}
						}
						//}
					}
				}
			}catch (IOException localIOException){
				System.out.println("Unable to read test data file : " + s);
			}
			catch (Exception localAutomationException){
				throw localAutomationException;
			}  
		}}
	
