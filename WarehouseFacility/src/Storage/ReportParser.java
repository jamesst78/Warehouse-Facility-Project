package Storage;

import static org.junit.Assert.assertTrue;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportParser {
	
	ArrayList<String[]> dataLines ;
	
	public static void main(String[] args) throws IOException {
	       
		ReportParser rp = new ReportParser();
		rp.dataLines = new ArrayList<>();
		rp.parseReportToVec("Reports/AutomatedReport.txt");
	}
	
	
	
	

	
	
	public   void parseReportToVec(String filename) throws IOException
	{
		Hashtable<String, Integer> codevec = new Hashtable<>();
		Hashtable<String, Integer> codemetrics = new Hashtable<>();
	    int ForLoopVariableCount =0;
        int UnusedAssignment =0;
        int UnusedFormalParameter =0;
        int UnusedLocalVariable =0;
        int ControlStatementBraces =0;
        int LocalVariableCouldBeFinal =0;
        int LongVariable =0;
        int PrematureDeclaration =0;
        int ShortVariable =0;
        int AvoidDeeplyNestedIfStmts =0;
        int CollapsibleIfStatements =0;
        int AddEmptyString =0;
        int AvoidInstantiatingObjectsInLoops =0;
        int MethodArgumentCouldBeFinal =0;
        int CyclomaticComplexity = 0;
		try {
	      File myObj = new File(filename);
	      Scanner myReader = new Scanner(myObj);

	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        System.out.println(data);
	        
	    



	        
	        if(data.contains("ForLoopVariableCount")) ForLoopVariableCount++;
	        	codevec.put("ForLoopVariableCount", ForLoopVariableCount);
	        
	        if(data.contains("UnusedAssignment")) UnusedAssignment++;
	        	codevec.put("UnusedAssignment", UnusedAssignment);
	        
	        if(data.contains("UnusedFormalParameter")) UnusedFormalParameter++;
	        	codevec.put("UnusedFormalParameter", UnusedFormalParameter);
	        
	        
	        if(data.contains("UnusedLocalVariable")) UnusedLocalVariable++;
	        	codevec.put("UnusedLocalVariable", UnusedLocalVariable);
	        
	        
	        if(data.contains("ControlStatementBraces")) ControlStatementBraces++;
	        	codevec.put("ControlStatementBraces", ControlStatementBraces);
	        
	        
	        if(data.contains("LocalVariableCouldBeFinal")) LocalVariableCouldBeFinal++;
	        	codevec.put("LocalVariableCouldBeFinal", LocalVariableCouldBeFinal);
	        
	        
	        if(data.contains("LongVariable")) LongVariable++;
	        	codevec.put("LongVariable", LongVariable);
	        
	        
	        if(data.contains("PrematureDeclaration")) PrematureDeclaration++;
	        	codevec.put("PrematureDeclaration", PrematureDeclaration);
	        
	        
	        if(data.contains("ShortVariable")) ShortVariable++;
	        	codevec.put("ShortVariable", ShortVariable);
	        
	        
	        if(data.contains("AvoidDeeplyNestedIfStmts")) AvoidDeeplyNestedIfStmts++;
	        	codevec.put("AvoidDeeplyNestedIfStmts", AvoidDeeplyNestedIfStmts);
	        
	        
	        if(data.contains("CollapsibleIfStatements")) CollapsibleIfStatements++;
	        	codevec.put("CollapsibleIfStatements", CollapsibleIfStatements);
	        
	        
	        if(data.contains("AddEmptyString")) AddEmptyString++;
	        	codevec.put("AddEmptyString", AddEmptyString);
	        
	        
	        if(data.contains("AvoidInstantiatingObjectsInLoops")) AvoidInstantiatingObjectsInLoops++;
	        	codevec.put("AvoidInstantiatingObjectsInLoops", AvoidInstantiatingObjectsInLoops);
	        
	        
	        
	        if(data.contains("MethodArgumentCouldBeFinal")) MethodArgumentCouldBeFinal++;
	        	codevec.put("MethodArgumentCouldBeFinal", MethodArgumentCouldBeFinal);
	        
	        
	        if(data.contains("CyclomaticComplexity")) CyclomaticComplexity++;
	        	codevec.put("CyclomaticComplexity", CyclomaticComplexity);
	        
	        
	        
	        
	        
	        
	        
	      }
	      myReader.close();
	      
	      
	      //open the csv file and assign data to it
	      String [] csvString = new String[17];
	      csvString[0] = "1";
	      csvString[1] = "1";
	      

	      
	      
	      //Enumerate thru hash
	      
	      // create enumeration to store keys
	        Enumeration<String> e = codevec.keys();
	  
	        // while keys are present
	        while (e.hasMoreElements()) {
	  
	            // get key
	            String key = e.nextElement();
	  
	            // print key and value corresponding to that key
	            System.out.println(key + ":" + codevec.get(key));
	        }
	      
	        csvString[2] = codevec.get("CollapsibleIfStatements") + "";
	        csvString[3] = codevec.get("LongVariable") + "";
	        csvString[4] = codevec.get("ForLoopVariableCount") + "";
	        csvString[5] = codevec.get("UnusedLocalVariable") + "";
	        csvString[6] = codevec.get("AddEmptyString") + "";
	        csvString[7] = codevec.get("CyclomaticComplexity") + "";
	        csvString[8] = codevec.get("AvoidDeeplyNestedIfStmts") + "";
	        csvString[9] = codevec.get("UnusedAssignment") + "";
	        csvString[10] = codevec.get("MethodArgumentCouldBeFinal") + "";
	        csvString[11] = codevec.get("AvoidInstantiatingObjectsInLoops") + "";
	        csvString[12] = codevec.get("PrematureDeclaration") + "";
	        csvString[13] = codevec.get("ShortVariable") + "";
	        csvString[14] = codevec.get("ControlStatementBraces") + "";
	        csvString[15] = codevec.get("UnusedFormalParameter") + "";
	        csvString[16] = codevec.get("LocalVariableCouldBeFinal") + "";

	        this.dataLines.add(csvString);


	        this.givenDataArray_whenConvertToCSV_thenOutputCreated();


	        



	        
	      
	      
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
	}
	
	
	public String convertToCSV(String[] data) {
	    return Stream.of(data).map(this::escapeSpecialCharacters).collect(Collectors.joining(","));
	}
	
	public String escapeSpecialCharacters(String data) {
	    String escapedData = data.replaceAll("\\R", " ");
	    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
	        data = data.replace("\"", "\"\"");
	        escapedData = "\"" + data + "\"";
	    }
	    return escapedData;
	}
	
	public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
	    File csvOutputFile = new File("./vecs.data");
	    FileWriter fr = new FileWriter(csvOutputFile, true);
	    BufferedWriter br = new BufferedWriter(fr);

	    try (PrintWriter pw = new PrintWriter(br)) {
	        dataLines.stream()
	          .map(this::convertToCSV)
	          .forEach(pw::println);
	    }
	    assertTrue(csvOutputFile.exists());
	}

}
