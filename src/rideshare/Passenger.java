package rideshare;

public class Passenger {
	int num;
	Request request;
	int status; // status = 0: free, status = 1: matched, status = 2: on the car, status = 3: finished
	int budget;
	
	public Passenger(int num, Request request, int status, int budget){
		this.num = num;
		this.request = request;
		this.status = status;
		this.budget = budget;
	}
	public Passenger(int num){
		this.num = num;
		request = new Request(num);
		status = 0;
	}
	
	public String toString(){
		return "Passenger " + num + " with request" + "\n"
				+ "status: " + status + "\n"
				+ "budget: " + budget + "\n";
	}
}
