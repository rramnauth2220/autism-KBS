import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.net.URL; // for criteria records online
import java.util.Scanner;
import java.util.Date;
import java.util.List; 
import java.util.ArrayList;


/**
 * ASD KBDS V1
 * @imports Apache POI, Apache Commons, W3C libraries
 * @author Rebecca Ramnauth
 * Date: 11-25-2017
 */
 
public class Diagnosis {
		
    private final static int EXAM = 0;
    private final static int QUESTION_NUM = 1;
    private final static int QUESTION = 2;
    private final static int RESPONSE = 3;
    private final static int B = 4;				//Social Interaction
    private final static int C = 5; 			//Communication 
    private final static int D = 6;				//Behavior/Interests
    private final static int E = 7;				//Spatial Intelligence
    private final static int F = 8;				//Perception
    private final static int G = 9;				//Bodily-Kinesthetic Intelligence
    private final static int EMPATHY = 10;
    private final static int SYSTEMIZATION = 11;
    private final static int BODY = 12;
    

    public static void main(String[] args) throws Exception {
    	XSSFWorkbook raw = new XSSFWorkbook ("raw.xlsx");
    	//XSSFWorkbook given = new XSSFWorkbook ("given.xlsx");
    	System.out.println("-----------------------------------------");
    	System.out.println("  AUTISM KNOWLEDGE-BASED EXPERT SYSTEM   ");
    	System.out.println("-----------------------------------------");
    	System.out.println("Client Information");
    	questioning(raw);
    }

    /**
     * Initiates questions, interprets and stores inputs in a client-specific excel file
     * @throws Exception
     */
    private static void questioning(XSSFWorkbook raw) throws Exception{
        FileInputStream file = new FileInputStream("output.xlsx");
        XSSFWorkbook given = new XSSFWorkbook(file);
        
        Sheet opt = given.getSheetAt(0);     
        Sheet sheet1 = raw.getSheetAt(0);
		
		int qcount = 0;	
		Cell optCell;
		
		int total = sheet1.getLastRowNum();
		System.out.println("Knowledge database successfully processed: " + total + " rules found");		
		
		//INTRODUCTORY QUESTIONS
		String level = "	"; 
		System.out.print(level + "Client's full name: ");
		Scanner namesc = new Scanner(System.in);
		String name = namesc.nextLine();
		
		System.out.print(level + "Client's Age (in years): ");
		Scanner agesc = new Scanner(System.in);
		int age = agesc.nextInt();
		List<String> exams = new ArrayList<>();
		if (age < 2)
			exams.add("CHAT");
		if (age > 2 && age < 12)
			exams.add("AESQ 12-15");
		if (age < 6)
			exams.add("ASQ");
		String tests = "";	
		for (int k = 0; k < exams.size(); k++)
			tests+=exams.get(k) + " ";
		System.out.println("Need to perform : " + tests);
		
		String timeStamp = new Date().toString();
		
		try {
			optCell = opt.getRow(0).getCell(1);
			optCell.setCellValue(name);
			optCell = opt.getRow(1).getCell(1);
			optCell.setCellValue(age);
			optCell = opt.getRow(2).getCell(1);
			optCell.setCellValue(timeStamp);
		}
		catch(NullPointerException e){	}
		
		//INSTRUCTIONS
		System.out.println("Answer the following questions by denoting severity on a scale from 1 - 5 where");
		System.out.println(" 1 = Strongly disagree ");
		System.out.println(" 2 = Disagree ");
		System.out.println(" 3 = Neutral ");
		System.out.println(" 4 = Agree ");
		System.out.println(" 5 = Strongly agree ");
		System.out.println(" ");
		
		int rowNum = 5;
		Cell pointer;
		for (int i = 1; i <= total; i++){
			if (!exams.contains(sheet1.getRow(i).getCell(EXAM).toString())){
				i += 1;
			}
			else {
				pointer = sheet1.getRow(i).getCell(QUESTION);
				String value = pointer.toString();					//get raw questions
				qcount++;
				System.out.print(level + qcount + ": " + value + " "); 	//ask question
				Scanner input = new Scanner(System.in);				//read & store input
				int res = input.nextInt();
				
				//determine weights
				pointer = sheet1.getRow(i).getCell(RESPONSE);
				String sympt = pointer.toString();
				int weight = res;
				if (res <= 3)
					weight = res * -1;
				if (sympt.equals("NEGATE"))
					weight = res * -1;
				double b_val;
				try{
					pointer = sheet1.getRow(i).getCell(B);
					String b = pointer.toString();
					b_val = Double.parseDouble(b) * weight;
				}
				catch (NullPointerException e){ b_val = 0; }
				double c_val;
				try {
					pointer = sheet1.getRow(i).getCell(C);
					String c = pointer.toString();
					c_val = Double.parseDouble(c) * weight;
				}
				catch (NullPointerException e) { c_val = 0; }
				double d_val;
				try {
					pointer = sheet1.getRow(i).getCell(D);
					String d = pointer.toString();
					d_val = Double.parseDouble(d) * weight;
				}
				catch (NullPointerException e) { d_val = 0; }
				double e_val;
				try {
					pointer = sheet1.getRow(i).getCell(E);
					String ee = pointer.toString();
					e_val = Double.parseDouble(ee) * weight;
				}
				catch (NullPointerException e) { e_val = 0; }
				double f_val;
				try {
					pointer = sheet1.getRow(i).getCell(F);
					String f = pointer.toString();
					f_val = Double.parseDouble(f) * weight;
				}
				catch (NullPointerException e) { f_val = 0; }
				double g_val;
				try {
					pointer = sheet1.getRow(i).getCell(G);
					String g = pointer.toString();
					g_val = Double.parseDouble(g) * weight;
				}
				catch (NullPointerException e) { g_val = 0; }
				String exam = sheet1.getRow(i).getCell(EXAM).toString();
				
				double emp_val;
				try {
					pointer = sheet1.getRow(i).getCell(EMPATHY);
					String emp = pointer.toString();
					emp_val = Double.parseDouble(emp) * weight;
				}
				catch (NullPointerException e) { emp_val = 0; }
				double sys_val;
				try {
					pointer = sheet1.getRow(i).getCell(SYSTEMIZATION);
					String sys = pointer.toString();
					sys_val = Double.parseDouble(sys) * weight;
				}
				catch (NullPointerException e) { sys_val = 0; }
				double body_val;
				try {
					pointer = sheet1.getRow(i).getCell(BODY);
					String bodily = pointer.toString();
					body_val = Double.parseDouble(bodily) * weight;
				}
				catch (NullPointerException e) { body_val = 0; }
				
				try{ //update question
					optCell = opt.getRow(rowNum).getCell(EXAM);
					optCell.setCellValue(exam);
				}
				catch (NullPointerException e){
					//optCell.setCellValue("");
				}
				try{ //update question number
					optCell = opt.getRow(rowNum).getCell(QUESTION_NUM);
					optCell.setCellValue(i);
				}
				catch (NullPointerException e){	}
				try{ //update question text
					optCell = opt.getRow(rowNum).getCell(QUESTION);
					optCell.setCellValue(value);
				}
				catch (NullPointerException e){	}
				try{ //update response
					optCell = opt.getRow(rowNum).getCell(RESPONSE);
					optCell.setCellValue(res);
				}
				catch (NullPointerException e){	}
				try{ //update parent categories
					optCell = opt.getRow(rowNum).getCell(B);
					optCell.setCellValue(b_val);
					optCell = opt.getRow(rowNum).getCell(C);
					optCell.setCellValue(c_val);
					optCell = opt.getRow(rowNum).getCell(D);
					optCell.setCellValue(d_val);
					optCell = opt.getRow(rowNum).getCell(E);
					optCell.setCellValue(e_val);
					optCell = opt.getRow(rowNum).getCell(F);
					optCell.setCellValue(f_val);
					optCell = opt.getRow(rowNum).getCell(G);
					optCell.setCellValue(g_val);
				}
				catch (NullPointerException e){	}
				try{ //update child categories
					optCell = opt.getRow(rowNum).getCell(EMPATHY);
					optCell.setCellValue(emp_val);
					optCell = opt.getRow(rowNum).getCell(SYSTEMIZATION);
					optCell.setCellValue(sys_val);
					optCell = opt.getRow(rowNum).getCell(BODY);
					optCell.setCellValue(body_val);
				}
				catch (NullPointerException e){	}
				rowNum++;
			}
		}
		//given.getCreationHelper().createFormulaEvaluator().evaluateAll();
		file.close();
		
		FileOutputStream fileOut = new FileOutputStream("Results for " + name + ".xlsx");
        given.write(fileOut);
        //opt.close();
        fileOut.close();
    }
}