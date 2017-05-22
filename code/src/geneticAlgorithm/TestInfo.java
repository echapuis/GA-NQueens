package geneticAlgorithm;

import java.io.Serializable;



public class TestInfo implements Serializable{
	
	public int tID;
	public int gID;
	public Assumptions a;
	public int cutoff;
	public String title;
	public String fileLoc = "logs/test";
	
	public TestInfo(int gID, int tID, int cutoff, Assumptions a){
		this.tID = tID;
		this.gID = gID;
		this.cutoff = cutoff;
		this.a = a;
		this.title = "";
	}
	
	public TestInfo(int gID, int tID, int cutoff, Assumptions a, String t){
		this.tID = tID;
		this.gID = gID;
		this.cutoff = cutoff;
		this.a = a;
		this.title = t;
		this.fileLoc = "logs/g" + gID;
	}
	
	public TestInfo(int gID, int tID, int cutoff, Assumptions a, String t, String f){
		this.tID = tID;
		this.gID = gID;
		this.cutoff = cutoff;
		this.a = a;
		this.title = t;
		this.fileLoc = f;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getFilename(){
		return this.fileLoc;
	}
	
	public int getGroupID(){
		return this.gID;
	}
	
	public int getTestID(){
		return this.tID;
	}
	
	public void addToID(){
		this.tID++;
	}
	
	public Assumptions getAssumptions(){
		return this.a;
	}
	
	public int getCutoff(){
		return this.cutoff;
	}
	
	public String toString(){
		return "group: " + gID + "\tID: " + tID + "\n" +a.toString();
	}
}
