package distributedComputing;
import java.io.Serializable;
import java.net.InetAddress;


public class IPPort implements Serializable{
	
	public InetAddress IP;
	public int Port;
	
	public IPPort (InetAddress ip, int port){
		this.IP = ip;
		this.Port = port;
	}
	
	public InetAddress getIP(){
		return this.IP;
	}
	
	public String getIPStr(){
		return this.IP.getHostAddress();
	}
	
	public int getPort(){
		return this.Port;
	}
}
