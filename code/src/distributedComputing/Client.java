package distributedComputing;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.net.*;

import geneticAlgorithm.*;

public class Client {
	
	public static DatagramSocket socket;
	public static ServerSocket ServerSocket;
	public static IPPort Server;
	public static String folder = "test";
	public static int numServers =5;
	protected static LinkedList<IPCount> servers = new LinkedList<IPCount>();
	
	public static void main(String[] args) throws Exception{
		socket = new DatagramSocket(5000);
		ServerSocket = new ServerSocket(6000,999, InetAddress.getLocalHost());
		byte[] buf = new byte[256];  //needs to be big enough to hold entire Serializable object
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		int s = 0;
		System.out.println(InetAddress.getLocalHost() + ":" + socket.getLocalPort());
		while (s<numServers){
			socket.receive(packet);
			IPPort newServer = getServerInfo(packet);
			servers.add(new IPCount(newServer));
			System.out.println("Server " + numServers + " [" + newServer.getIPStr() + ":" + newServer.getPort() + "] connected.");
			s++;
		}
		System.out.println("ready to execute");
		int g = 0;
		while (true) {
			//newSock = Server.accept();
			Socket sock = ServerSocket.accept();
			//System.out.println("new connection: " + sock.toString());
		    ObjectInputStream in = new ObjectInputStream((sock.getInputStream()));
		    TestInfo t = (TestInfo) in.readObject();
		    //System.out.println(t.toString());
		    String testFile = t.getFilename();
			boolean newFile = Helpers.createFile(testFile,false);
			WriteFile write = new WriteFile(testFile,true);
			if (newFile) {
				write.writeToFile(t.getTitle());
				write.writeToFile(t.getAssumptions().toString());
			}
			sendTest(t,write);			
		}
		
	}
	
	
	public static void sendTest(TestInfo t, WriteFile writer) throws IOException{
		Helpers.createFile("logs/solutions", false);
		WriteFile sols = new WriteFile("logs/solutions",true);
		IPPort server = getServer();
		cDownloadThread s = new cDownloadThread(server, t, writer,sols);
		Thread sT = new Thread(s);
		sT.start();	
	}
	
	public static IPPort getServer(){
		IPCount minServer = servers.get(0);
		int serverID = 0;
		for (int i = 0; i < servers.size(); i++){
			//System.out.println("Server " + i + " Count: " + servers.get(i).getCount());
			if (servers.get(i).getCount() < minServer.getCount()) {
				serverID = i;
				minServer = servers.get(i);
				break;
			}
		}
		servers.get(serverID).addCount();
		return servers.get(serverID).getIP();
	}
	
	public static IPPort getServerInfo(DatagramPacket p) throws Exception{
		ObjectInputStream iStream = new ObjectInputStream(
				new ByteArrayInputStream(p.getData()));
		IPPort message = null;
		try{
			message =  (IPPort) iStream.readObject();
			}
		catch (Exception e){}
		return message;
	}
}