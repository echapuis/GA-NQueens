package geneticAlgorithm;

public class Count {
	public int count;
    public State ps;
    
	public  Count(int c, State p) {
		this.count = c;
        this.ps = p;
	}
	
	public int getCount(){
		return this.count;
	}
	
	public void setCount(int c){
		this.count = c;
	}
	
	public void addCount(){
		this.count++;
	}
	
	public State getState(){
		return this.ps;
	}
	
	public void setState(State s){
		this.ps = s;
	}

}
