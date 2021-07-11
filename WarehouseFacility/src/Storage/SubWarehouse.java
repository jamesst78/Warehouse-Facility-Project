import java.util.ArrayList;
//T11_37_4158_Ahmed_Hossam
public class LeftRecursionElimination {
	
    static ArrayList<CFG> cfg = new ArrayList<>();
    
    public static void main(String[] args) {
    	
        String input = "S,BC,C;B,Bb,b;C,SC,a";
        String output = LRE(input);
        System.out.print(output);
	    
    }
    public static String LRE(String input) {
    	
        String[] splitted = input.split(";");

        for (int i = 0;i<splitted.length;i++) {
            CFG x = new CFG(splitted[i]);
            cfg.add(x);
        }

        for (int i = 0; i < cfg.size(); i++) {
            CFG newCFG= direct(i);
            updatePosition(newCFG,i);
            
        }
        filterWrong();
	    return printFinalOutput();
    }

    public static CFG direct(int index) {
        String beforeArrow = cfg.get(index).beforeArrow;
        ArrayList<String> leftRec = new ArrayList<>();
        ArrayList<String> good = new ArrayList<>();

        for (int i = 0; i < cfg.get(index).afterArrow.size(); i++) {
            if (cfg.get(index).beforeArrow.charAt(0) == cfg.get(index).afterArrow.get(i).charAt(0))
                leftRec.add(cfg.get(index).afterArrow.get(i));
            else
                good.add(cfg.get(index).afterArrow.get(i));
        }
        CFG G = new CFG();
        if (leftRec.size() != 0) {
            cfg.get(index).afterArrow = new ArrayList<>();
            for (int i = 0 ;i<good.size();i++)
                cfg.get(index).afterArrow.add(good.get(i) + cfg.get(index).beforeArrow + "\'");

            ArrayList<String> remainingG = new ArrayList<>();
            for (int i = 0; i < leftRec.size(); i++)
                remainingG.add(leftRec.get(i).substring(1) + beforeArrow + "\'");

            G = new CFG(cfg.get(index).beforeArrow + "\'", remainingG);
        }
        return G;


    }
    public static void filterWrong() {
    	String prob = "";
    	for(int i = 0 ;i<cfg.size();i++) {
    		
    		if(!((cfg.get(i).beforeArrow.equals(cfg.get(i).beforeArrow.charAt(0)+""))||(cfg.get(i).beforeArrow.equals(cfg.get(i).beforeArrow.charAt(0)+"'")))) {
        		prob = cfg.get(i).beforeArrow.charAt(0)+"";
    			cfg.remove(i);
        		i--;
    		}
    	}
    	for(int i = 0;i<cfg.size();i++) {
			CFG update = cfg.get(i);
    		if(!prob.equals("")){
    			if(cfg.get(i).beforeArrow.equals(prob+"'")) {
        			update.afterArrow.add(prob+prob+"'");
        		}
    		}
    		cfg.set(i, update);
    		
    	}
    	for(int i=0;i<cfg.size();i++) {
			CFG update = cfg.get(i);
    		for(int j = 0;j<cfg.get(i).afterArrow.size();j++) {
    			update.afterArrow.set(j,cfg.get(i).afterArrow.get(j).replace(update.beforeArrow.charAt(0)+"''", ""));
    			update.afterArrow.set(j,cfg.get(i).afterArrow.get(j).replace(update.beforeArrow.charAt(0)+"'''", ""));
    			update.afterArrow.set(j,cfg.get(i).afterArrow.get(j).replace(update.beforeArrow.charAt(0)+"''''", ""));
    			update.afterArrow.set(j,cfg.get(i).afterArrow.get(j).replace(update.beforeArrow.charAt(0)+"'''''", ""));

    		}
    		cfg.set(i, update);
    	}
    	
    }
    public static void updatePosition(CFG newRule,int index) {
        ArrayList<CFG> updatecfg = new ArrayList<>();
        for (int i = 0; i < cfg.size(); i++) {
            if (i <= index) {
                updatecfg.add(cfg.get(i));
                if (i == index && newRule.beforeArrow != "")
                    updatecfg.add(newRule);
            } else {
                ArrayList<String> newafterArrow = new ArrayList<>();
                for (int j = 0; j < cfg.get(i).afterArrow.size(); j++) {
                    if (cfg.get(i).afterArrow.get(j).charAt(0) == cfg.get(index).beforeArrow.charAt(0)) {
                        for (int x = 0; x < cfg.get(index).afterArrow.size(); x++) {
                            newafterArrow.add(cfg.get(index).afterArrow.get(x) + cfg.get(i).afterArrow.get(j).substring(1));
                        }
                    } else
                        newafterArrow.add(cfg.get(i).afterArrow.get(j));

                }
                cfg.get(i).afterArrow = newafterArrow;
                updatecfg.add(cfg.get(i));
            }
        }
        cfg = updatecfg;
    }
    public static String printFinalOutput() {
    	String out ="";
        for (int i = 0;i<cfg.size();i++) {
//            System.out.println(cfg.get(i).getString());
            out+=cfg.get(i).getString();
        }
        return out;
    }
    public static class CFG {
        public String beforeArrow;
        public ArrayList<String> afterArrow = new ArrayList<String>();

        CFG(){
            beforeArrow = "";
            afterArrow = new ArrayList<String>();
        }
        CFG(String rule){
        	
            String[] splitted = rule.split(",");
            beforeArrow = splitted[0];
            for (int i = 1;i<splitted.length;i++) {
                afterArrow.add(splitted[i]);
            }
        }
        public CFG(String beforeArrow, ArrayList<String> afterArrow) {
            this.beforeArrow = beforeArrow;
            this.afterArrow = afterArrow;
        }

     
        public String getString(){
        	
        	String finalS = "";
            finalS+=beforeArrow;
            for (int i = 0;i<afterArrow.size();i++) {
                finalS += (","+afterArrow.get(i));
        }
            if(beforeArrow.equals(beforeArrow.charAt(0)+"'")) {
            	finalS+=",";
            }
            finalS+=";";
            return finalS;
        }
    }
}
