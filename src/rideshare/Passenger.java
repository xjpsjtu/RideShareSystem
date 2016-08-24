package rideshare;

public class Passenger {
	int num;
	int startTime;
	int endTime;
	Pos startPoint;
	Pos endPoint;
	
	public Passenger(int num, int startTime, int endTime, Pos startPoint, Pos endPoint){
		this.num = num;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	public Passenger(int num){
		this.num = num;
		this.startTime = (int)(Math.random() * 100);
		this.endTime = this.startTime + (int)(Math.random() * (100 - this.startTime)) / 2;
		this.startPoint = new Pos();
		startPoint.setOwner(this.num);
		startPoint.setFlag(0);
		this.endPoint = new Pos();
		endPoint.setOwner(this.num);
		endPoint.setFlag(1);
	}
}
