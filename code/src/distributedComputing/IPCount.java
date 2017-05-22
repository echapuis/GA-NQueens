package distributedComputing;

public class IPCount {
	
	public IPPort ip;
	public int count;
	
	public IPCount(IPPort ip){
		
		this.ip = ip;
		this.count = 0;
	}
	
	
	public IPPort getIP(){
		return this.ip;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public void addCount(){
		this.count++;
	}
	
	public void reduceCount(){
		this.count--;
	}
}
