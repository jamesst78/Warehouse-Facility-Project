package Storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class ReportParser {
	
	
	public static void main(String[] args) {
		
		parseReportToVec("Reports/ykersha.txt");
	}
	
	
	
	

	
	
	public static  void parseReportToVec(String filename)
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
	      
	      
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
	}

}
