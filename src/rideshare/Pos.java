package rideshare;

public class Pos {
	int owner;
	int flag; //flag == 0 起点  flag == 1终点
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
	//设置它所属的乘客
	public void setOwner(int owner){
		this.owner = owner;
	}
	//设置为起点或者终点
	public void setFlag(int flag){
		this.flag = flag;
	}
}
