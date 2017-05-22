package geneticAlgorithm;
import java.util.*;
public class ProblemState {
	
	public static boolean print = false;
	
	//returns a problem state for a given search problem
	public static State getProblemStateUnique(Assumptions a){
		LinkedList<Integer> l = new LinkedList<Integer>();
		LinkedList<Integer> ps = new LinkedList<Integer>();
		Random r = new Random();
		for (int i = 0; i<a.getN(); i++){
			l.add(i);
		}
		for (int j = a.getN(); j>0; j--){
			ps.add(l.remove(r.nextInt(j)));
		}
		if (print) System.out.println("getps: " + ps);
		double o = getObjective(ps);
		return new State(ps, getFitness(a,o), o);
	}
	
	public static State getProblemState(Assumptions a){
		LinkedList<Integer> ps = new LinkedList<Integer>();
		Random r = new Random();
		for (int i = 0; i<a.getN(); i++){
			ps.add(r.nextInt(a.getN()));
		}
		double o = getObjective(ps);
		return new State(ps, getFitness(a,o),o);
	}
	
	public static double getFitness(Assumptions a, double oF){
		double o = a.getN()*2-3-oF;
		String type = a.getCrossType();
		if (type.equals("power")){
			double fit = Math.pow(Math.max(o+a.getDisp(),0), a.getScale());
			if (fit == 0) System.out.println("zero fitness: " + o);
			if (Double.isInfinite(fit)) System.out.println("infinite fitness: " + o);
			return fit;
		}
		else {
			return Math.max(o+a.getDisp(),0)*a.getScale();
		}
	}
	
	//returns the fitness of a problemState
	//A queen on column i and row q is on the n-i+q left diagonal and
	//on the i+q-1 right diagonal
	public static double getObjective(LinkedList<Integer> problemState){
		LinkedList<Integer> l = problemState;
		LinkedList<Integer> leftD = new LinkedList<Integer>();
		LinkedList<Integer> rightD = new LinkedList<Integer>();
		LinkedList<Integer> rows = new LinkedList<Integer>();
		int n = l.size();
		if (print) System.out.println("ps: " + problemState);
		int r,c;
		int fitness = 0;
		int Diag = 0;
		int Horiz = 0;
		for(int i =0; i < (2*n-1);i++){
			leftD.add(0);
			rightD.add(0);
		}
		for (int i = 0; i < n; i++){
			rows.add(0);
		}
		for(int j = 0; j < n; j++){
			r = l.get(j);
			c = j;
			rows.set(r, rows.get(r)+1);
			leftD.set(n-c+r-1, leftD.get(n-c+r-1)+1);
			rightD.set(r+c, rightD.get(r+c)+1);
		}
		for (int k = 0; k < (2*n-1); k++){
			if (leftD.get(k)>1) {
				Diag+= leftD.get(k)-1;
				if (print) System.out.println("conflict at leftD N: " + k);
			}
			if (rightD.get(k)>1) {
				Diag+= rightD.get(k)-1;
				if (print) System.out.println("conflict at rightD N: " + k);
			}
		}
		for (int i = 0; i < n; i++){
			if (rows.get(i)>1){
				Horiz+= rows.get(i)-1;
				if (print) System.out.println("conflict on row N: " + i);
			}
		}
		fitness = Horiz+Diag;
		if (fitness < 0){
			System.out.println("fitness negative");
			fitness = 0;
		}
		return fitness;
	}
	
	//returns the relative probability of selecting a problemState
	//The sum of all probabilities must equal 1.
	public static double getProbability(Assumptions a, State s, Population population, int rank)
		{
		int n = a.getPopulationSize();
		if (a.getCrossType().equals("eRank")){
			return -Math.pow(Math.E, -a.getScale()*(rank+1)) + Math.pow(Math.E, -a.getScale()*rank) +
					Math.pow(Math.E, -a.getScale()*(n-1))/(n-1);
		}
		else if (a.getCrossType().equals("lRank")){
			return (2*(n-1)-2*rank-1)/Math.pow(n-1, 2);
		}
		else if (a.getCrossType().equals("HiLo")){
			return rank <= n*a.getScale() || rank >= n-n*a.getScale()-1 ? 1/(n*a.getScale()) : 0;
		}
		else return s.getFitness()/population.totalFitness;	
		}
	
	public static boolean isGoal(Double Fitness, int n){
		return Fitness == 0;
	}
}
