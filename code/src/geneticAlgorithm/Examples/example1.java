package geneticAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class test2 {

	public static void main(String[] args){
		
//		LinkedList<Test> tests = new LinkedList<Test>();
//		for (int i = 0; i < 20; i++){
//			tests.add(new Test(a, false));
//			System.out.println("Finished Test " + i + ".");
//			System.out.println("\tResult: " + tests.get(i).getGoalState().stateString());
//		}
//		double avgTime = 0;
//		double avgCross = 0;
//		double avgEpoch = 0;
//		for (int i = 0; i < tests.size(); i++){
//			Test t = tests.get(i);
//			avgTime+= t.getTime();
//			avgCross += t.getCrossovers();
//			avgEpoch += t.getEpochs();
//		}
//		avgTime = avgTime/tests.size();
//		avgCross = avgCross/tests.size();
//		avgEpoch = avgEpoch/tests.size();
//		System.out.println("avgTime: " + avgTime);
//		System.out.println("avgCross: " + avgCross);
//		System.out.println("avgEpoch: " + avgEpoch);
		//Test t= new Test(a,true);
		int id = 1;
		HashMap<String, Object> m = new HashMap<String,Object>(){{
			put("nCross",5);
			put("N", 10);
			put("pSize", 100);
			put("pmx", true);
			put("unique", true);
			put("mProb", "0.01");
		}};
		Assumptions a = new Assumptions(m);
		String tI = "testInfo";
		String dta = "data";
		WriteFile testInfo = (new File("logs/" + tI)).exists() ? newFile("logs/" + tI) : new WriteFile("logs/" + tI, true);
		WriteFile data = (new File("logs/" + dta)).exists() ? newFile("logs/" + dta) : new WriteFile("logs/" + dta, true);
		Test t;
		Thread tt;
		for (int i = 0; i < 9; i++){
			int crossNum = i+1;
			m.put("nCross", crossNum);
			a = new Assumptions(m);
			dta = "data" + crossNum + ".csv";
			tI = "testInfo" + crossNum + ".csv";
			testInfo = (new File("logs/" + tI)).exists() ? newFile("logs/" + tI) : new WriteFile("logs/" + tI, true);
			data = (new File("logs/" + dta)).exists() ? newFile("logs/" + dta) : new WriteFile("logs/" + dta, true);
			for (int z = 0; z < 30; z++){
				t = new Test(id, a, testInfo, data);
				tt = new Thread(t);
				tt.start();
				id++;
			}
		}
		System.out.println("started test");
	}
	
	public static WriteFile newFile(String fileName){
		String fileStr = fileName;
		File newFile = new File(fileName);
		while (newFile.exists()){
			String[] split = newFile.getName().split("-");
			String[] dot = newFile.getName().split("\\.");
			if (split.length>1){
				int index = dot.length == 1 ? Integer.valueOf(split[1])+1 : Integer.valueOf(dot[0].substring(dot[0].length()-1));
				fileStr = split[0] + "-"  + index;
				if (dot.length>1) fileStr += "." + dot[1];
				newFile = new File(fileStr);
			}
			else {
				fileStr = dot[0] + "-1";
				if (dot.length>1) fileStr += "." + dot[1];
				newFile = new File(fileStr);
			}
		}
		System.out.println("newfile " +fileStr);
		return new WriteFile(fileStr, true);
	}

	
	public static void pmxTest(){
		HashMap<String, Object> m = new HashMap<String,Object>(){{
			put("nCross",2);
			put("N", 9);
			put("pSize", 50);
			put("pmx", true);
			put("unique", true);
			put("mProb", "0");
		}};
		Assumptions a = new Assumptions(m);
		LinkedList<Integer> ps1 = new LinkedList<Integer>();
		ps1.add(2);
		ps1.add(5);
		ps1.add(1);
		ps1.add(3);
		ps1.add(8);
		ps1.add(4);
		ps1.add(7);
		ps1.add(6);
		ps1.add(0);
		LinkedList<Integer> ps2 = new LinkedList<Integer>();
		ps2.add(8);
		ps2.add(4);
		ps2.add(7);
		ps2.add(2);
		ps2.add(6);
		ps2.add(1);
		ps2.add(3);
		ps2.add(5);
		ps2.add(0);
//		
//		State s1 = new State(ps1, ProblemState.getFitness(ps1));
//		State s2 = new State(ps2, ProblemState.getFitness(ps2));
//		
//			LinkedList<State> children = Reproduction.cross(a, s1,s2);
	}
}
