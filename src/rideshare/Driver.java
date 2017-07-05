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
		this.schedule = new Schedule(this);
	}
	
	public void addPassengers(Passenger[] ps){
		schedule.addPassengers(ps);
	}
	
	public void addPassenger(Passenger ps) {
		schedule.addPassenger(ps);
	}

	public void dropPassenger(Pos pos, Passenger[] pr){
		schedule.dropPassenger(pos,pr);
	}
}
