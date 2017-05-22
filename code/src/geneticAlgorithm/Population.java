package geneticAlgorithm;

import java.util.*;

public class Population {
	
	public State maxFit;
	public State minFit;
	public double totalFitness = 0;
	public double totalObjective = 0;
	public double productivity = 0;
	public int prodCount = 0;
	public double diversity = 1;
	public double pctProductive = 1;
	public int N = 0;
	public LinkedList<State> list = new LinkedList<State>();
	public boolean print = false;
	public Assumptions a;
	
	
	public Population(Assumptions a, boolean populate){
		this.a = a;
		if (populate){
			for (int i = 0; i < a.getPopulationSize(); i++){
				if (a.isUnique()) this.add(ProblemState.getProblemStateUnique(a));
				else this.add(ProblemState.getProblemState(a));
			}		
		}
	}
	
	public void addProd(double p){
		this.productivity+=p;
		if (p>0) this.prodCount++;
		
	}
	public void add(State newState){
		this.list.add(newState);
		if (N == 0 || newState.getFitness() > this.maxFit.getFitness() ){
			maxFit = newState;
		}
		if (N == 0 || newState.getFitness() < this.minFit.getFitness() ){
			minFit = newState;
		}
		this.totalFitness+=newState.getFitness();
		this.totalObjective+=newState.getObj();
		N++;
	}
	public void sort(){
		Collections.sort(this.list);
	}
	
	public LinkedList<State> getParents(){
		LinkedList<State> l = new LinkedList<State>();
		double totalprob = 0;
		Random r = new Random();
		for (int x = 0; x < 2; x ++){
			double p = r.nextFloat();
			int i = 0;
			if (this.a.getCrossType().equals("HiLo") && x == 1) i = this.size()-1; 
			totalprob = 0;
			while (totalprob < p){
				totalprob += ProblemState.getProbability(this.a, this.get(i), this,i);
				if (this.a.getCrossType().equals("HiLo") && x == 1) i--;
				else i++;
				if (i < 0 || i >= this.size()-1) break;
			}
			if (i <0) {
				//System.out.println("parent index < 0");
				l.add(this.get(0));
			}
			else if (i >= this.size()-1) {
				//System.out.println("parent index > size");
				l.add(this.get(this.size()-1));
			}
			else {
				//System.out.println("rank " + (this.size()-(i+1)) + " chosen.");
				l.add(this.get(i-1));
			}
			
		}
		return l;		
	}
	
	public void freeList(){
//		int nU =numberUnique();
//		int size = size();
		
//		System.out.println("diversity: " + diversity);
//		System.out.println("nU: " + nU);
//		System.out.println("size: " + size);
		list = null;
	}
	
	public int size(){
		return N;
	}
	
	public State get(int index){
		return this.list.get(index);
	}
	
	public State random(){
		return this.list.get((int) (Math.random()*this.N));
	}
	
	public String toString(){
		String s = "population: {\n";
		for (int i = 0; i < N; i++){
			s += this.get(i).toString() + "\n";
		}
		s+="}";
		return s;
	}
	
	public LinkedList<Count> toSet(){
		LinkedList<Count> l = new LinkedList<Count>();
		if (this.size() == 0) return l;
		else {
			l.add(new Count(1,this.get(0)));
			for (int i = 1; i < this.size();i++){
				int n = 0;
				State curState = this.get(i);
				while (n < l.size()){
					if (l.get(n).getState().equals(curState)){
						l.get(n).addCount();
						break;}
					n++;
				}
				if (n == l.size()) l.addLast(new Count(1,curState));
			}
		}
		return l;
	}
	
	public int numberUnique(){
		Set<State> s = new HashSet<State>();
		for (int i = 0; i < this.size(); i++){
			s.add(this.get(i));
		}
		return s.size();
	}
	
	public void printSet(LinkedList<Count> l, boolean printContents){
		System.out.println("POPULATION CONTENTS");
		System.out.println("Number Unique: " + l.size() + " out of " + this.N);
		System.out.println("Percent Unique: " + (double) ((double)l.size()/(double)this.N*100));
		if (printContents){
		for (int i = 0; i < l.size(); i++){
			System.out.println("State:  " + l.get(i).getState().toString() + "\tCount:  " + l.get(i).getCount());
		}
		}
	}
	
	
	public String toCSV(){
		this.diversity = (double) this.numberUnique()/ (double) this.size();
		double avgFit = this.totalFitness/this.size();
		double avgObj = this.totalObjective/this.size();
		double avgProd = prodCount == 0 ? 0 : this.productivity/this.size();
		pctProductive = (double)((double) this.prodCount/((double) this.size()/ (double) 2));
		return avgFit + ", " + avgObj + ", " + this.diversity + ", " + avgProd + ", " + pctProductive;
	}
	
	public State getGoal(){
		for (int i = 0; i < this.N; i++){
			if (this.get(i).isGoal()) return this.get(i);
		}
		return null;
	}
}
