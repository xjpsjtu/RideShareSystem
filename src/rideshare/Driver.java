package rideshare;

public class Driver {
	int num;
	Pos loc;  //current position
	Schedule schedule; //current maintain schedule
	int cap; //maximum capability

	
	public Driver(int cap, int num){
		this.loc = new Pos();
		this.cap = cap;	
		this.num = num;
	}
	
	public void addPassenger(Passenger[] ps){
		schedule.addPassenger(ps);
	}

	public void dropPassenger(Pos pos){
		schedule.dropPassenger(pos);
	}
}
