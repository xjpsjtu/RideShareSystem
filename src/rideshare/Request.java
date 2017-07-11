package rideshare;
/*
 * @author: xjp
 */

public class Request {
	public int num;
	int declaretime;
	public int startTime;
	public int endTime;
	public Pos startPoint;
	public Pos endPoint;
	
	public Request(int num, int startTime, int endTime, Pos startPoint, Pos endPoint) {
		this.num = num;	
		this.startTime = startTime;
		this.endTime = endTime;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	public Request(int num) {
		this.num = num;
		this.startTime = (int)(Math.random() * Tool.time);
		this.endTime = this.startTime + (int)(Math.random() * (Tool.time - this.startTime)) / 3;
		double dis = (endTime - startTime) * Tool.v / 10;
		this.startPoint = new Pos(num);
		startPoint.setOwner(this.num);
		startPoint.setFlag(0);
		this.endPoint = new Pos(startPoint.x + Math.random() * dis, startPoint.y + Math.random() * dis, num);
		endPoint.setOwner(this.num);
		endPoint.setFlag(1);
		declaretime = (int)(Math.random() * startTime);
	}
	public Passenger getOwner(Passenger[] pr) {
		return pr[num];
	}
	
	public String toString(){
		return "Request of: " + num +"\n"
				+ "declare time: " + declaretime + "\n"
				+ "startTime: " + startTime + "\n"
				+ "endTime: " + endTime + "\n"
				+"startPoint: " + startPoint + "\n"
				+"endPoint: " + endPoint + "\n"
				+"true distance: " + Tool.caldis(startPoint, endPoint) + "\n"
				+"bare distance: " + Tool.v + "*" + (endTime - startTime) + "\n";
	}
}
