package rideshare;

public class Pos {
	int owner; //owner的num
	int flag; //flag == 0 origin  flag == 1 end
	double x;
	double y;
	
	public Pos(double x, double y, int owner){
		this.x = x;
		this.y = y;
		this.owner = owner;
	}
	public Pos(int owner){
		this.x = Math.random() * Tool.dis;
		this.y = Math.random() * Tool.dis;
		this.owner = owner;
	}
	public Pos(int owner, int flag, double x, double y){
		this.owner = owner;
		this.flag = flag;
		this.x = x;
		this.y = y;
	}

	public void setOwner(int owner){
		this.owner = owner;
	}

	public void setFlag(int flag){
		this.flag = flag;
	}
	public Passenger getOwner(Passenger[] pr) {
		return pr[owner];
	}
	
	public String toString(){
		return "(" + x + "," + y + ") of owner: " + owner;
	}
}
