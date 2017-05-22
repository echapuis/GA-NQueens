package geneticAlgorithm;

import java.io.IOException;
import java.util.LinkedList;

public class Test implements Runnable{

		public int crossovers = 0;
		public int epochs = 0;
		public Assumptions a;
		public boolean print = true;
		public WriteFile testInfo;
		public WriteFile data;
		public State goalState = null;
		public long start = 0;
		public long end = 0;
		public int tID;
		public int gID;
		public LinkedList<Population> epochData = new LinkedList<Population>();
		public int cutoff = 500;
		
		public Test(int gid, int tid, int cutoff, Assumptions a,  WriteFile testInfo, WriteFile data){
			this.tID = tid;
			this.gID = gid;
			this.a = a;
			this.testInfo = testInfo;
			this.data = data;
			this.cutoff = cutoff;
		}
		
		public void run(){
			System.out.println("Test " + gID + ":" + tID + " started.");
			start = System.nanoTime();
			Population p = new Population(this.a,true);
			epochData.add(p);
//			try {
//				data.writeToFile(ID, ID + ", " + this.epochs + ", " + epochData.get(this.epochs).toCSV());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			goalState = p.getGoal();
			boolean goal = !(goalState == null);
			while (!goal){
				Population newPop = new Population(this.a,false);
				p.sort();
				while (newPop.N < a.getPopulationSize()){
					LinkedList<State> parents = p.getParents();
					LinkedList<State> children = Reproduction.cross(a, parents.get(0),parents.get(1));
					newPop.add(children.get(0));
					newPop.add(children.get(1));
					double productivity = Math.max(-children.get(0).getObj() - children.get(1).getObj()
												   +parents.get(0).getObj() + parents.get(1).getObj(),0);
					newPop.addProd(productivity);						
					this.crossovers++;
					if (children.get(0).isGoal() || children.get(1).isGoal()) {
						goal = true;
						this.goalState = children.get(0).isGoal() ? children.get(0) : children.get(1);
						end = System.nanoTime();
						System.out.println("Test " + tID + " found a solution after " + epochs + " epochs.");
						break;
					}
				}
				if (goal) break;
				p.freeList();
				p = newPop;
				epochData.add(p);
				if (tID == 0) System.out.println("Test " + tID + " complete Epoch " + this.epochs + ".");
				this.epochs++;
				if (this.epochs>cutoff) {
					end = System.nanoTime();
					break;
				}
//				try {
//					data.writeToFile(ID, ID + ", " + this.epochs + ", " + epochData.get(this.epochs).toCSV());
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			try {
				printResults();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void printResults() throws IOException{
			System.out.println("printing results for Test: " + gID + ":"  + tID);
			testInfo.getToken();
			testInfo.write("Test: " + gID + ":" + tID);
			testInfo.write(a.toString());
			if (goalState == null) testInfo.write("GoalState not found."); else testInfo.write("GoalState: " + this.goalState.toString());
			testInfo.write("crossovers: " + this.crossovers);
			testInfo.write("epochs: " + this.epochs);	
			testInfo.write("time: " + ((double) (this.end-this.start))/1000000000);
			testInfo.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
			testInfo.returnToken();
			data.getToken();
			for (int i = 0; i < epochData.size(); i ++){
				data.write(gID + ", " + tID + ", " + i + ", " + epochData.get(i).toCSV());
			}
			data.returnToken();
			//epochData.getLast().printSet(epochData.getLast().toSet(),true);
		}
		
		public int getCrossovers(){
			return this.crossovers;
		}
		
		public int getEpochs(){
			return this.epochs;
		}
		
		public double getTime(){
			return ((double) (this.end-this.start))/1000000000;
		}
		
		public State getGoalState(){
			return this.goalState;
		}
		
		public Assumptions getAssumptions(){
			return this.a;
		}
}
