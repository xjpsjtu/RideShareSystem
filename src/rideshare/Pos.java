package rideshare;

public class Pos {
	int owner;
	int flag; //flag == 0 ���  flag == 1�յ�
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
	//�����������ĳ˿�
	public void setOwner(int owner){
		this.owner = owner;
	}
	//����Ϊ�������յ�
	public void setFlag(int flag){
		this.flag = flag;
	}
}
