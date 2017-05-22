package distributedComputing;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.LinkedList;
import java.util.regex.Pattern;

import geneticAlgorithm.*;

public class cDownloadThread implements Runnable {
	
	public IPPort server;
	public IPPort serverThread;
	public TestInfo t;
	public DatagramSocket socket;
	public Socket streamSocket;
	public ObjectOutputStream out;
	public BufferedReader in;
	public ReadFile upload;
	public static WriteFile w;
	public static WriteFile s;
	
	public cDownloadThread(IPPort Server, TestInfo t, WriteFile w, WriteFile s) throws IOException{
		this.server = Server;
		this.w = w;
		this.s = s;
		//System.out.println("writefile: " + w.getPath());
		this.t = t;
		this.socket = new DatagramSocket();

		try{
			streamSocket = new Socket(server.getIPStr(), server.getPort());
			//System.out.println("started thread: " +Thread.currentThread().getId()+ " at " + streamSocket.toString());
			this.out = new ObjectOutputStream(new BufferedOutputStream(streamSocket.getOutputStream()));
			this.in = new BufferedReader(new InputStreamReader(streamSocket.getInputStream()));
			out.writeObject(t);
			out.flush();
			//System.out.println("input Stream: " + streamSocket.getLocalPort());
		}
		catch (Exception e){
			e.printStackTrace();
		};
		
	}
	
	public void run(){
		
		boolean finished = false;
		LinkedList<String> readLine = new LinkedList<String>();
		int i = 0;
		boolean waiting = true;
		while (true){
			try {
					if (in.ready()) {
						String lineRead = in.readLine();
						if (lineRead.toUpperCase().contains("EOF")) {
							String[] sA =lineRead.split(":");
							System.out.println("Test " + sA[0] + ":" + sA[1].substring(0,1) + " finished.");
							s.writeToFile(lineRead);
							continue;
						}
						writeToFile(lineRead);
						readLine.add(lineRead);
						waiting = false;
					}
					else {
						//System.out.println("waiting");
						waiting = true;
						sleep(3000);
						i++;
						//if (i >600) break;
					}
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
//		for (int j = 0; j < readLine.size(); j++){
//			System.out.println(streamSocket.getLocalPort() + ": " + readLine.get(j));
//		}
	
		
	}
	
	public static boolean writeToFile(String epoch){
		//System.out.println("writeToFile: " + epoch);
		if (epoch == null) return false;
		if (epoch.equals("EOF")) return true;
		String[]  sA = epoch.split(",");
		String testFile = w.getPath();
		char c= ' ';
		int i = testFile.length()-1;
		while (c != '/'){
			c = testFile.charAt(i);
			i--;
		}
		testFile = testFile.substring(0,i+2) + "g" + sA[0];
		//System.out.println("testFile: " + testFile);
		WriteFile write = new WriteFile(testFile,true);
		try {
			write.writeToFile(epoch);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//w.returnToken();
		return false;
	}
	
	
	public void close(){
		try {
			streamSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.close();
	}
	public void waitForServer(){
		while (!streamSocket.isConnected()){
			sleep(200);
		}
	}
	
	public static void sleep(int secs){
		try {
			Thread.sleep(secs);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
