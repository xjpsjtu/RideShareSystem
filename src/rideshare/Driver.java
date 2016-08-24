package rideshare;

public class Driver {
	Pos loc;  //current position
	Schedule schedule; //current maintain schedule
	int cap; //maximum capability
	int v;
	
	public Driver(int cap, int v){
		this.loc = new Pos();
		this.cap = cap;
		this.v = v;
		
	}
	
	//添加乘客
	public void addPassenger(Passenger[] ps){
		schedule.addPassenger(ps);
	}
	//乘客下车
	public void dropPassenger(Pos pos){
		schedule.dropPassenger(pos);
	}
}
