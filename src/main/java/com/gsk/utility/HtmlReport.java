package com.gsk.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlReport extends ReadData{
	
	FileWriter writer;
	List <Integer> cls= new ArrayList<Integer> ();
	
	public void generateHtmlReport(String paramString1) throws IOException
	{
		writer = new FileWriter(new File(suiteFolder +"\\HTML Report.html"));
		writer.write("<!DOCTYPE html>");
		writer.write("<html>");
		
		writer.write("<head>");
		writer.write("<style>");
		writer.write("table, th, td {");
		writer.write("border: 1px solid black;");
		writer.write("border-collapse: collapse;");
		writer.write("}");
		writer.write("th, td {");
		writer.write("padding: 1px;");
		writer.write("</style>");
		writer.write("</head>");
		
		writer.write("<body>");
		
		writer.write("<center>");
		writer.write("<img src=\""+System.getProperty("user.dir")+"\\MediaFiles\\Logo.png" +"\"  style=\"width:704px;height:150px;\">");
		writer.write("</center>");
		writer.write("<h1 align=\"center\">");
		writer.write("<font color=\"red\"> Summary Report </font>");
		writer.write("</h1>");
		
		writer.write("<table align=\"center\" width=\"60%\" border=\"1\">");
		writer.write("<th align=\"center\" bgcolor=\"#f5deb3\" > ");
		writer.write("<font size = \"4\"> TOTAL NO. OF TEST CASES </font>");
		writer.write("</th>"); 
		writer.write("<th align=\"center\" bgcolor=\"#f5deb3\" > ");
		writer.write("<font size = \"4\"> NO. OF PASSED TEST CASES </font>");
		writer.write("</th>"); 
		writer.write("<th align=\"center\" bgcolor=\"#f5deb3\" > ");
		writer.write("<font size = \"4\"> NO. OF FAILED TEST CASES </font>");
		writer.write("</th>"); 
		writer.write("<tr>");
		writer.write("<td align=\"center\">");
		writer.write("<font face= \"calibri\" size = \"4\">" + testCaseName1.size() +" </font>");
		writer.write(" </td>");
		
		int i=0,pass=0,fail=0;
		
		while(i<testCaseName1.size())
		{
			System.out.println(testCaseName1.get(i));
			if(statusMap.get(testCaseName1.get(i)).equalsIgnoreCase("Pass") )
			{
				pass++;
			}
			
			else if (statusMap.get(testCaseName1.get(i)).equalsIgnoreCase("Fail"))
			{
				fail++;
			}
		
			i++;
		}
		
		writer.write("<td align=\"center\">");
		writer.write("<font face= \"calibri\" size = \"4\">" + pass +" </font>");
		writer.write(" </td>");
		writer.write("<td align=\"center\">");
		writer.write("<font face= \"calibri\" size = \"4\">" + fail +" </font>");
		writer.write(" </td>");
		writer.write("</tr>");
		writer.write("</table>");
		writer.write("<br/>");
		CalculateRowSpan();
		generate(paramString1);
		writer.write("</body>");
		writer.write("</html>");
		writer.close();
		
	}
	
	public void CalculateRowSpan ()
	{
		int j,counter;
		for (int k = 0; k < testFullName.size(); k++) 
		{
			j=0;counter=0;
			while (j<classes.size())
			{
				if (classes.get(j).equals(testFullName.get(k)))
				{
					counter++;
					j++;
				}
				else
				{
					j++;
				}
			}
			
			cls.add(counter);	
		}
	}
	
	public void generate(String paramString1) throws IOException
	{
		try{
			writer.write("<table align=\"center\" width=\"60%\" border=\"1\">");
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> TC ID </font>");
			writer.write("</td>"); 
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> CLASS NAME </font>");
			writer.write("</td>"); 
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> METHOD NAME </font>");
			writer.write("</td>"); 
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> STATUS </font>");
			writer.write("</td>"); 
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> DESCRIPTION</font>");
			writer.write("</td>"); 
			writer.write("<td align=\"center\" bgcolor=\"#bdbdbd\" > ");
			writer.write("<font size = \"4\"> SCREENSHOT </font>");
			writer.write("</td>"); 
			writer.write("<p>");
				int y=0,j,x;
				for (int k = 0; k < testFullName.size(); k++) 
				{
					x=0;
					System.out.println("testFullName.get(k)  ::::::" + testFullName.get(k));
					j=0;
					while (j<classes.size())
					{						
						if (classes.get(j).equals(testFullName.get(k)))
						{
							int m = k + 1;
							
												
							if(x==0)
							{
								writer.write("<tr>");
								writer.write("<td align=\"center\" rowspan=\""+cls.get(k)+ "\">");
								System.out.println("cls.get(k)"+cls.get(k));
								writer.write("<font face= \"calibri\" size = \"4\">" + Integer.toString(m) +" </font>");
								writer.write("</td>");
							writer.write("<td align=\"center\" rowspan=\""+cls.get(k)+ "\">");
							writer.write("<font face= \"calibri\" size = \"4\">" + testCaseName1.get(k) +" </font>");
							System.out.println("testCaseName1.get(k)"+testCaseName1.get(k));
							writer.write(" </td>");
							x++;
							}
							
							writer.write("<td align=\"center\">");
							writer.write("<font face= \"calibri\" size = \"4\">" + methods.get(j) +" </font>");
							System.out.println(" methods.get(j) "+ methods.get(j));
							writer.write(" </td>");
										
								if ((methods.get(j) != null) && (((String)statusMap.get(paramString1)).trim().equalsIgnoreCase("Pass")))
								{
									writer.write("<td align=\"center\">");
									writer.write("<font face= \"calibri\" size = \"4\">" + stepStatus1.get(j) +" </font>");
									writer.write("</td>");
									
								} else if ((methods.get(j) != null) && (((String)statusMap.get(paramString1)).trim().equalsIgnoreCase("Fail")))
								{
									writer.write("<td align=\"center\">");
									writer.write("<font face= \"calibri\" size = \"4\">" + stepStatus1.get(j) +" </font>");
									writer.write("</td>");
									
								}
								
								writer.write("<td align=\"center\">");
								writer.write("<font face= \"calibri\" size = \"4\">" + errorDes.get(j) +" </font>");
								System.out.println(" methods.get(j) "+ methods.get(j));
								writer.write(" </td>");
								
								writer.write("<td align=\"center\">");
								
								writer.write("<font face= \"calibri\" size = \"4\"><a href="+ScreenShots.get(j)+ " target=_blank>ScreenShot</a> </font>");
								
								//writer.write("<font face= \"calibri\" size = \"4\">" + ScreenShots.get(j) +" </font>");
								System.out.println(" methods.get(j) "+ methods.get(j));
								writer.write(" </td>");
								
							
							writer.write("</tr>");
							j++;
							
						}
				
						else
						{
							j++;
							continue;
						}
					}
					writer.write("</p>");
		
			} 
	}
	catch (Exception localIOException3) {
		localIOException3.printStackTrace();
	}

	finally {
		testCaseNameList.clear();
		testCaseDescriptionList.clear();
		testCaseStartTimeList.clear();
		testCaseEndTimeList.clear();
		testCaseElapsedTimeList.clear();
		snapShotPathList.clear();

		testCaseName1.clear();
		testFullName.clear();
		testCaseDesc1.clear();
		testCaseStartTimeList1.clear();
		testCaseEndTimeList1.clear();	
		}
	}
}
