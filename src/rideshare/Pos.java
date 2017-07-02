package rideshare;

public class Pos {
	int owner;
	int flag; //flag == 0 origin  flag == 1 end
	double x;
	double y;
	
	public Pos(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Pos(){
		this.x = Math.random() * 100;
		this.y = Math.random() * 100;
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
}
