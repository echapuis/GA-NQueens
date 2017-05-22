package distributedComputing;
import geneticAlgorithm.TestInfo;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class Server{
	
	protected static DatagramSocket socket = null;
	protected static ServerSocket Server;
	protected static Socket newSock = null;
	protected BufferedReader in = null;
	protected static IPPort client;
	protected static String clientIP = "136.167.101.75";
	protected static int clientPort = 5000;
	protected static LinkedList<Socket> sockets;
	
	public static void main(String[] args) throws Exception{
		client = new IPPort(InetAddress.getByName(clientIP),clientPort);
		socket = new DatagramSocket();
		Server = new ServerSocket(0,999, InetAddress.getLocalHost());
		sockets = new LinkedList<Socket>();
		System.out.println("Server Socket = " +
				Server.getInetAddress().getHostAddress() + ":" + 
				Server.getLocalPort());
		connectServer(clientIP);
		Thread sT = null;
		int i = 0;
		while (true) {
			//newSock = Server.accept();
			sockets.add(Server.accept());
		    PrintWriter out =new PrintWriter(sockets.get(i).getOutputStream(), true);  
		    ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(sockets.get(i).getInputStream()));
		    TestInfo t = (TestInfo) in.readObject();
		    //System.out.println("received " + t.toString());
		    //out.println("test");
		    IPPort clientIP = new IPPort(sockets.get(i).getInetAddress(), sockets.get(i).getPort());
		    PrintWriter p = new PrintWriter(sockets.get(i).getOutputStream(), true);
		    //p.println("testing");
			ServerTest s = new ServerTest(t.getGroupID(), t.getTestID(), t.getCutoff(), t.getAssumptions(), p, sockets.get(i));
			sT = new Thread(s);
			sT.start();
			i++;
		}
		
	}
	
	public static void connectServer(String ip) throws IOException{
		IPPort serv = new IPPort(Server.getInetAddress(), Server.getLocalPort());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(outputStream);
		os.writeObject(serv);
		
		byte[] ping = outputStream.toByteArray();
		DatagramPacket send = new DatagramPacket(ping, ping.length,
				client.getIP(), client.getPort());
		socket.send(send);
		os.flush();
	}
	
	
}
