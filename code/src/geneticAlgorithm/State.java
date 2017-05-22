package geneticAlgorithm;

import java.util.LinkedList;

public class State implements Comparable<State>{
	
	public LinkedList<Integer> ps;
	public double fitness;
	public double oF;
	
	public State(LinkedList<Integer> ps, double fitness, double oF){
		this.ps = ps;
		this.fitness = fitness;
		this.oF = oF;
	}
	
	public LinkedList<Integer> getState(){
		return this.ps;
	}
	
	public String printStateString(){
		String s  = "";
		for (int i = 0; i < this.ps.size()-2;i++){
			s+= this.ps.get(i) + "-";
		}
		s+= this.ps.get(this.ps.size()-1);
		return s;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ps == null) ? 0 : ps.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (ps == null) {
			if (other.ps != null)
				return false;
		} else if (!ps.equals(other.ps))
			return false;
		return true;
	}
	
	public double getObj(){
		return this.oF;
	}
	
	public double getFitness(){
		return this.fitness;
	}
	
	public void setFitness(double fit){
		this.fitness = fit;
	}
	
	public String stateString(){
		String s = "{";
		for (int i = 0; i < this.ps.size(); i++){
			if (i < this.ps.size()-1) s+= this.ps.get(i)+" ";
			else s+= this.ps.get(i);
		}
		s+= "}";
		return s;
	}
	
	public String toString(){
		return this.stateString() + " " + "Fitness:  " + this.fitness + " " + "Obj. Function: " + this.oF;
	}
	
	public String toCSV(){
		return this.stateString() + ", " + this.oF + ", " + this.fitness;
	}
	public boolean isGoal(){
		return ProblemState.isGoal(this.oF, this.ps.size());
	}

	

	public int compareTo(State o) {
		return  ((Double) this.oF).compareTo(o.getObj());
	}
}
