package geneticAlgorithm;

import java.io.Serializable;
import java.util.Map;

public class Assumptions implements Serializable{
	
	public int NQueens = 8;
	public int pSize = 100;
	public double mutationProb = 0.01;
	public String type = "linear"; //linear, power, eRank, lRank, HiLo
	public String switchTo;//which method to switch to after displacement
	public String switcher = "d";
	public int nCross = 2;
	public boolean unique = false;
	public boolean pmx = false;
	public boolean uniform = false;
	public double scaling = 2;
	public double displacement = 0;
	//public double percentile = .25;
	
	public Assumptions(){};
	
	public Assumptions(Map<String,Object> parameters){
		String a = "";
		int i = 0;
		double d = 0;
		String s = "";
		if (parameters.containsKey("N")){
			if (!(parameters.get("N") instanceof Integer)) { 
	            throw new IllegalArgumentException("...");
	        }
	        i = (Integer) parameters.get("N");
	        this.NQueens = i;
		}
		if (parameters.containsKey("pSize")){
			if (!(parameters.get("pSize") instanceof Integer)) { 
	            throw new IllegalArgumentException("...");
	        }
	        i = (Integer) parameters.get("pSize");
	        this.pSize = i;
		}
		if (parameters.containsKey("mProb")){
			if (!(parameters.get("mProb") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        d = Double.valueOf((String) parameters.get("mProb"));
	        this.mutationProb = d;
		}
		if (parameters.containsKey("scale")){
			if (!(parameters.get("scale") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        d = Double.valueOf((String) parameters.get("scale"));
	        this.scaling = d;
		}
		if (parameters.containsKey("nCross")){
			if (!(parameters.get("nCross") instanceof Integer)) { 
	            throw new IllegalArgumentException("...");
	        }
	        i = (Integer) parameters.get("nCross");
	        this.nCross = i;
		}
		if (parameters.containsKey("pmx")){
			if (!(parameters.get("pmx") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("pmx");
	        this.pmx = Boolean.valueOf(a);
		}
		if (parameters.containsKey("unique")){
			if (!(parameters.get("unique") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("unique");
	        this.unique = Boolean.valueOf(a);
		}
		if (parameters.containsKey("uniform")){
			if (!(parameters.get("uniform") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("uniform");
	        this.uniform = Boolean.valueOf(a);
		}
		if (parameters.containsKey("type")){
			if (!(parameters.get("type") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("type");
	        this.type = a;
		}
		if (parameters.containsKey("switchTo")){
			if (!(parameters.get("switchTo") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("switchTo");
	        this.switchTo = a;
		}
		else this.switchTo = this.type;
		if (parameters.containsKey("switcher")){
			if (!(parameters.get("switcher") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("switcher");
	        this.switcher = a;
		}
		
		if (parameters.containsKey("disp")){
			if (!(parameters.get("disp") instanceof String)) { 
	            throw new IllegalArgumentException("...");
	        }
	        a = (String) parameters.get("disp");
	        this.displacement = Double.valueOf(a);
		}
	}
	
	public String toString(){
		return "Assumptions: \n" + 
			   "Number of Queens: " + this.NQueens + "\n" +
			   "Population Size: " + this.pSize + "\n" + 
			   "Mutation Probability: " + this.mutationProb + "\n" + 
			   "Crossover Method: " + this.type + "\n" +
			   "Crossover Points: " + this.nCross + "\n" +
			   "Scaling: " + this.scaling + "\n" + 
			   "Displacement: " + this.displacement + "\n" + 
			   "(PMX, Unique, Uniform): (" + this.pmx + ", " + this.unique + ", " + this.uniform + ")\n";
	}
	
	public String getCrossType() { return this.type;}
	
	public int getN() { return this.NQueens; }
	
	public boolean isUnique() { return this.unique;}
	
	//returns the desired population size
	public int getPopulationSize(){ return this.pSize; }
	
	//returns the mutation probability of the population
	//Must be a value between 0 and 1
	public double getMutationProbability(){ return this.mutationProb;}
	
	public boolean PMX(){ return this.pmx;}
	
	public int getNCross() { return this.nCross;}
	
	public double getScale() { return this.scaling;}
	
	public double getDisp() { return this.displacement;}
	
	public boolean isUniform(){return this.uniform;}
	//returns the crossover diversity value
	//Must be a value between 0 and 1 
	//Values closer to 0 are more likely to crossover in half
	//Values closer to 1 are more likely to crossover unequally
	public double getCDV(){ return 0; }
	
	//returns the fitness dominance value
	//Must be a value between 0 and 1
	//The higher the fitness dominance value, the more likely
	//high-fitness samples will be selected and qualities retained
	public double getFDV(){ return 0; }
	
	//returns the cooling factor
	//Must be a value between 0 and 1
	//Impacts the intensity of reproductive change until cooling
	//has been achieved.
	public double getCoolingFactor(){ return 0; }
}
