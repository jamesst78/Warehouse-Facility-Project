package compilers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CFG {
	
	
	
	public static String LRE(String x) {
		String [] v=x.split(";");
		List<String> states = new ArrayList<String>();
		List<List> nodes = new ArrayList<List>();
		for (int i =0 ;i<v.length;i++) {
			states.add(""+v[i].charAt(0));
			nodes.add(Arrays.asList((v[i].substring(2)).split(",")));
		}
		
		//System.out.print(states.toString());

		List<List> to= new ArrayList<List>();
		to=run(states,nodes);	
		String nlr="";
		for (int i=0;i<to.size();i++) {
			for(int j =0;j<to.get(i).size();j++) {
				nlr+=to.get(i).get(j);
				nlr+=",";
			}
			nlr= nlr.substring(0, nlr.length()-1);
			nlr+=";";
			
		}
		nlr= nlr.substring(0, nlr.length()-1);
		//System.out.println(nlr);
		//System.out.println(nodes.toString());
		return nlr;

	}
	public static List<List> run(List<String> s,List<List> n){
		List<String> states = new ArrayList<String>();
		List<List> nodes = new ArrayList<List>();
		boolean b = false;
		String t = null;
		for (int i=0;i<s.size();i++) {
			states.add(s.get(i));
			 List<String> w = new ArrayList<String>();
			if(i==0) {
				w=n.get(i);
			}
			else {
			 w=sub(states,nodes,n.get(i),s.get(i));
			}
			 for (int j=0;j<w.size();j++) {
			
				String m =((String) w.get(j)).charAt(0)+"";
				 
				if(s.get(i).charAt(0)==m.charAt(0)) {
				//	System.out.println(true);
					t= (char)(((String) w.get(j)).charAt(0))+"'";
					if(!states.contains(t))
					states.add(t);
					b = true;
				}
				
				
			}
			if(b) {
				//System.out.println(true);
				List<String> snew = new ArrayList<String>();
				List<String> snw = new ArrayList<String>();
				for (int j=0;j<w.size();j++) {
					if(s.get(i).charAt(0)==(char)(((String) w.get(j)).charAt(0))) {
			//			System.out.println(true);
						snew.add(((String) (w.get(j))).substring(1)+t);
						
					}
					else {
						snw.add(((String) (w.get(j)))+t);
					}
					
				}
				snew.add("");
				nodes.add(snw);
				nodes.add(snew);
			
				b=false;
				
			}
			else {
				nodes.add(w);
			}
			//if(s.get(i)
		}
		//System.out.println(states.toString());
	//	System.out.println(nodes.toString());
		List<List> ot= new ArrayList<List>();
		for (int i=0;i<states.size();i++) {
			List<String> tt= new ArrayList<String>();
			List<String> to= new ArrayList<String>();
			tt.add(states.get(i));
			to=nodes.get(i);
			for(int j =0;j<to.size();j++)
				tt.add(to.get(j));
			ot.add(tt);
		}
		//System.out.println(ot.toString());
		return ot;
		
	}
	public static List<String> sub(List<String> s, List <List>n,List<String> a,String x){
		List<String> ot= new ArrayList<String>();
		//ot= a;System.out.print("ehhhh");
		//System.out.println("ehhhh1");
		boolean check = false;
		List<String> p = new ArrayList<String>();
		for(int i =0;i<a.size();i++) {
			String m =((String) a.get(i)).charAt(0)+"";
			//System.out.println(m);
			if(s.contains(m)&&(s.indexOf(m)<s.indexOf(x))) {
				//System.out.println(n.get(s.indexOf(m)));
				check =  true;
				
				p=sub(s,n,n.get(s.indexOf(m)),x);
				//System.out.println(p);
				for(int o=0;o<p.size();o++) {
					String r = a.get(i).substring(1);
					
					ot.add(p.get(o)+r);
				}
			}
			else {
				ot.add(a.get(i));
			}
		
		}	
		if(!check) {
			ot=a;
		}
	return ot;
		
		
	}
	
	public static void main(String[]args) {
		
		//String input= "X,YZ,Y";
		//String input= "A,Ac,b";
		//String x= "E,E*T,T";
		//String input= "S,S0S1S,01";
	//	String x= "E,E+T,T";
		//String x= "S,Sab,cd";
		//String x= "A,0,T1;T,1,A0";
//String x= "A,BC;B,Bb"
	//String x= "S,Aa,b,a;A,Ac,Sd,c";
//	String x= "S,SUS,SS,S*,(S),a";
		//String input= "E,EUT,T;T,TF,F;F,F*,P;P,a,b";
		//String input= "A,BC,C;B,Bb,b;C,AC,a";

		String input= "S,ScT,T;T,aSb,iaLb,i;L,SdL,S";
		String output = LRE(input);
		System.out.println(output);
	}
}
