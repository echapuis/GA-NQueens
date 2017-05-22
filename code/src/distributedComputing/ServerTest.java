package distributedComputing;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import geneticAlgorithm.*;

public class ServerTest implements Runnable{

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
		public static PrintWriter out;
		public Socket s;
		
		public ServerTest(int gid, int tid, int cutoff, Assumptions a, PrintWriter p, Socket s){
			this.tID = tid;
			this.gID = gid;
			//System.out.println("test " + tID);
			this.s = s;
			this.out = p;
			this.a = a;
			this.cutoff = cutoff-1;
			//System.out.println("start " +tID + ": " + this.s.toString());
			
			//out.println("test2");
			//out.println("test3");
			//out.flush();
		}
		
		public void run(){
//			try {
//				this.s = new Socket(s.getInetAddress(),s.getPort());
//				this.out = new PrintWriter(s.getOutputStream(),true);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			System.out.println("Test " + gID + ":" + tID + " started.");
			start = System.nanoTime();
			Population p = new Population(this.a,true);
			epochData.add(p);
			try {
				out.println(gID + ", "  + tID + ", " + this.epochs + ", " + epochData.get(this.epochs).toCSV());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			goalState = p.getGoal();
			boolean goal = !(goalState == null);
			while (!goal){
				Population newPop = new Population(this.a,false);
				p.sort();
				if (this.a.switcher.equals("d") && p.diversity <= this.a.getDisp()) this.a.type = this.a.switchTo;
				if (this.a.switcher.equals("p") && p.pctProductive <= this.a.getDisp()) this.a.type = this.a.switchTo;
				while (newPop.N < a.getPopulationSize()){
					LinkedList<State> parents = p.getParents();
					//System.out.println("Parent 1: " + parents.get(0).toString());
					//System.out.println("Parent 2: " + parents.get(1).toString());
					LinkedList<State> children = Reproduction.cross(a, parents.get(0),parents.get(1));
					newPop.add(children.get(0));
					newPop.add(children.get(1));
					//System.out.println("p1: " + parents.get(0).getObj() + " p2: " + parents.get(1).getObj());
					//System.out.println("c1: " + children.get(0).getObj() + " c2: " + children.get(1).getObj());
					
					double productivity = -children.get(0).getObj() - children.get(1).getObj()+parents.get(0).getObj() + parents.get(1).getObj();
					//System.out.println("prod: " + productivity);
					newPop.addProd(productivity);	
					this.crossovers++;
					if (children.get(0).isGoal() || children.get(1).isGoal()) {
						goal = true;
						this.goalState = children.get(0).isGoal() ? children.get(0) : children.get(1);
						end = System.nanoTime();
						//System.out.println("Test " + tID + " found a solution after " + epochs + " epochs.");
						try {
							finish();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
				if (goal) break;
				p.freeList();
				p = newPop;
				epochData.add(p);
				//System.out.println("Test " + tID + " completed Epoch " + this.epochs + ".");
				this.epochs++;
				if (this.epochs>cutoff) {
					end = System.nanoTime();
					break;
				}
				try {
					out.println(gID + ", "  + tID + ", " + this.epochs + ", " + epochData.get(this.epochs).toCSV());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				finish();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void finish() throws IOException{
			System.out.println(gID + ", "  + tID + " finished.");
			//System.out.println("end " + tID + ": " + this.s.toString());
			out.println(gID + ", "  + tID + ", " + this.epochs + ", " + epochData.get(this.epochs).toCSV()); 
			if (goalState == null) out.println(gID + ":" + tID + " EOF");
			else out.println(gID + ":" + tID + " EOF " + goalState.getState().toString());
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
