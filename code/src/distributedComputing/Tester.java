package distributedComputing;

import geneticAlgorithm.Assumptions;
import geneticAlgorithm.TestInfo;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Tester {
	
	public static String clientIP = "136.167.101.220";
	public static int clientPort = 6000;
	public static Socket socket;
	public static ObjectOutputStream out;
	
	public static void main(String[] args) throws Exception{
		
		HashMap<String, Object> m = new HashMap<String,Object>(){{
			put("nCross",99);
			put("N", 100);
			put("pSize", 1000);
			put("pmx", "true");
			put("type", "power");
			put("scale", "4");
			put("disp", ".1");
		}};
		Assumptions a = new Assumptions(m);
		TestInfo test = new TestInfo(0,0,10, a, "l19");
		int g = 2;
		test = new TestInfo(g,-1,500,a,"n = 100", "logs/PTestPMXN/g" + g);
		sendTest(test, 20);
	}
	
	public static void sendTest(TestInfo t, int iter) throws IOException{
		//System.out.println("sentTest " + t.toString());
		for (int i = 0; i < iter; i ++){
			t.addToID();
			socket = new Socket(clientIP, clientPort);
			out = new ObjectOutputStream((socket.getOutputStream()));
			out.writeObject(t);
			socket.close();
		}
	}
}
