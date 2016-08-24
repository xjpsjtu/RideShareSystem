package rideshare;

public class Process {
	int n;
	int v;
	Passenger[] passengers;
	Driver[] drivers;
	int cap;
	int T;
	
	public Process(int n, int cap, int T, int v){
		this.n = n;
		this.cap = cap;
		this.T = T;
		this.v = v;
		for(int i = 1; i <= n; i++){
			passengers[i] = new Passenger(i);
			drivers[i] = new Driver(cap,v);
		}
	}
	
	
	
}
