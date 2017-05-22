package geneticAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class test3 {
	
	public static void main(String[] args){
		HashMap<String, Object> m = new HashMap<String,Object>(){{
			put("nCross",1);
			put("N", 20);
			put("pSize", 1000);
			put("pmx", "false");
			put("unique", "false");
			put("type", "linear");
			put("scale", "195");
			put("disp", 0);
			put("uniform","true");
		}};
		Assumptions a = new Assumptions(m);
		String tI = "testInfo";
		String dta = "data.csv";
		WriteFile testInfo = (new File("logs/" + tI)).exists() ? newFile("logs/" + tI) : new WriteFile("logs/" + tI, true);
		WriteFile data = (new File("logs/" + dta)).exists() ? newFile("logs/" + dta) : new WriteFile("logs/" + dta, true);
		try {
			data.writeToFile(0, "Test ID, Epoch, Average Fitness, Average oF, Diversity");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Test t;
		Thread tt = null;
		int s = 0;
		String fileName = "";
		for (int x = 0; x< 2; x++){
			s = 4;
			int id =0;
			m.put("scale", Integer.toString(s));
			if (x==0) m.put("type", "eRank"); else m.put("type", "lRank");
			m.put("unique", "false");
			for (int n = 0; n < 4; n++){
				int crossNum = n ==0 ? 1: n==1? 2 : n==2 ? 10 : 19;
				m.put("nCross", crossNum);
				for (int u = 0; u < 1; u++){
					if (u == 0) m.put("uniform", "false"); else m.put("uniform", "true");
					for (int zz = 0; zz < 1; zz++){
						String pmx = zz == 0 ? "" : "PMX";
						if (zz == 0) m.put("pmx", "false"); else m.put("pmx", "true");
						if (zz == 0) m.put("unique","false"); else m.put("unique","true");
						a = new Assumptions(m);
						System.out.println(a.toString());
						fileName = u==0 ? "u" : "";
						fileName += x==0 ? "eR" : "lR";
						fileName += "nC" + crossNum + pmx;
						data = newFile("logs/" + fileName);
						try {
							data.writeToFile(0, "Test ID, Epoch, Average Fitness, Average oF, Diversity, Productivity, % Productive");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						testInfo = newFile("logs/test" + fileName);
						for (int i = 0; i < 2; i++){
							for (int ii = 0; ii < 5; ii ++){
								t = new Test(id, 100, a, testInfo, data);
								tt = new Thread(t);
								tt.start();
								id++;
							}
							try {
								tt.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
			}
		}
		}
	}
	
	
	public static Set<State> getSet(LinkedList<State> l){
		Set<State> s = new HashSet<State>();
		for (int i = 0; i < l.size(); i++){
			s.add(l.get(i));
		}
		return s;
	}
	public static WriteFile newFile(String fileName){
		String fileStr = fileName;
		File newFile = new File(fileName);
		if (newFile.exists()){
			newFile.delete();
		}
		return new WriteFile(fileStr,true);
//		while (newFile.exists()){
//			String[] split = newFile.getName().split("-");
//			String[] dot = newFile.getName().split("\\.");
//			if (split.length>1){
//				int index = dot.length == 1 ? Integer.valueOf(split[1])+1 : Integer.valueOf(dot[0].substring(dot[0].length()-1));
//				fileStr = split[0] + "-"  + index;
//				if (dot.length>1) fileStr += "." + dot[1];
//				newFile = new File(fileStr);
//			}
//			else {
//				fileStr = dot[0] + "-1";
//				if (dot.length>1) fileStr += "." + dot[1];
//				newFile = new File(fileStr);
//			}
//		}
//		System.out.println("newfile " +fileStr);
//		return new WriteFile(fileStr, true);
	}
}
