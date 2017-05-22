package geneticAlgorithm;

import java.util.*;
public class Reproduction {
	public static boolean print =false;	
	
	public static LinkedList<State> cross(Assumptions ass, State a, State b){
		String type = ass.getCrossType();
		if (print) System.out.println("CROSS()");
		LinkedList<State> l = new LinkedList<State>();
		LinkedList<Integer> p1 = a.getState();
		LinkedList<Integer> p2 = b.getState();
		if (print) System.out.println(p1.toString());
		if (print) System.out.println(p2.toString());
		
		LinkedList<Integer> c1 = new LinkedList<Integer>();
		LinkedList<Integer> c2 = new LinkedList<Integer>();
		
		Random r = new Random();
		PriorityQueue<Integer> crossPoints = new PriorityQueue<Integer>();
		boolean pmx = ass.PMX();
		if (!ass.isUniform()){
			LinkedList<Integer> gaps = new LinkedList<Integer>();
			for (int i = 0; i < p1.size()-1; i++){
				gaps.add(i+1);
			}
			for (int i = 0; i < ass.getNCross(); i++){
				if (i < p1.size()-1) crossPoints.add(gaps.remove(r.nextInt(gaps.size())));
			}
		}
		else{
			int i = 0;
			for (int n = 0; n<ass.getNCross();n++){
				if (ass.getNCross()<ass.getN()/2) i += ass.getN()/(ass.getNCross()+1);
				else {
					i += 2*(ass.getNCross() -n) + i >= ass.getN()? 1 :2;
				}
				crossPoints.add(i);
			}
		}
		int change = 0;
		LinkedList<Integer> crosses = new LinkedList<Integer>();
		int gap = crossPoints.remove();
		for (int i = 0; i < p1.size(); i++){
			if (i == gap) {
				crosses.add(gap);
				change = (change+=1)%2;
				if (!crossPoints.isEmpty()) gap = crossPoints.remove();
			}
			int toAdd1 = change == 0 ? p1.get(i) : p2.get(i);
			int toAdd2 = change == 0 ? p2.get(i) : p1.get(i);
			c1.add(toAdd1);
			c2.add(toAdd2);
		}
		if (pmx) pmx(c1,p1, crosses);
		if (pmx) pmx(c2,p2, crosses);
		
		mutate(c1, ass.getMutationProbability());
		mutate(c2, ass.getMutationProbability());
		if (print) System.out.println("c1: " + c1);
		if (print) System.out.println("c2: " + c2);
		if (print){
			System.out.println("Crosses at:");
			for (int i = 0; i < crosses.size(); i++){
				System.out.print(" " + crosses.get(i));
			}
			System.out.println();
		}
		double o = ProblemState.getObjective(c1);
		l.add(new State(c1, ProblemState.getFitness(ass,o),o));
		o = ProblemState.getObjective(c2);
		l.add(new State(c2, ProblemState.getFitness(ass,o),o));
		return l;		
	}
	
	public static void pmx(LinkedList<Integer> c, LinkedList<Integer> p, LinkedList<Integer> crosses){
		if (print) System.out.println("pmx");
		if (print) System.out.println("child: " + c.toString());
		if (print) System.out.println("parent: " + p.toString());
		if (print) System.out.println("cross: " + crosses.toString());
		LinkedList<Integer> occurred = new LinkedList<Integer>();
		boolean newC = false;
		int crossN = 0;
		for (int i = 0; i < c.size(); i++){
			boolean hasOccurred = false;
			int conflict = -1;
			if (i == crosses.get(crossN)){
				newC = newC ? false : true;
				if (crossN < crosses.size()-1) crossN++;
			}
			for (int j = 0; j < occurred.size(); j++){
				if (c.get(i) == occurred.get(j)) {
					hasOccurred = true;
					conflict = occurred.get(j);
				}
			}
			if (!hasOccurred) occurred.add(c.get(i));
			else {
				if (newC) {
					//System.out.println("newC " + i);
					for (int k = 0; k < c.size(); k++){
						if (c.get(k) == conflict) {
							c.set(k, p.get(i));
							break;
						}
					}
				}
				else {
					//System.out.println("oldC " + i);
					for (int l = 0; l < c.size(); l++){
						if (c.get(l) == conflict) {
							c.set(i, p.get(l));
							break;
						}
					}
				}
			}
		}
		if (print) System.out.println("end result: " + c.toString());
	}
	
	public static void mutate(LinkedList<Integer> ps, double mp){
		Random r = new Random();
		for (int i = 0; i < ps.size(); i ++){
			if(r.nextFloat() < mp) {
				ps.set(i, r.nextInt(ps.size()));
			}
		}
		return;
	}
	
	
}
