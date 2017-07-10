package rideshare;

import java.util.ArrayList;

public class Tool {
	public static final double v = 20;
	public static final double dis = 10000;
	public static final int time = 3600;
	public static double caldis(Pos a, Pos b){
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		double dis = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return dis;
	}
	
	public static double calt(Pos a, Pos b) {
		double dis = Tool.caldis(a , b);
		return dis / Tool.v;
	}
	
	public static Pos drive(Driver driver, int t0, int t1, Passenger[] pg){
		Schedule schedule = driver.schedule;
		if(schedule.locs.size() == 0) {
			System.out.println("Noooooo drive");
			return driver.loc;
		}
		ArrayList<Pos> locs = schedule.locs;
		Driver d = schedule.driver;
		double length = Tool.v * (t1 - t0);
		double tmplength = 0;
		Pos cur = d.loc;
		for(int i = 0; i < locs.size(); i++){
			Pos pos = locs.get(i);
			double dis = Tool.caldis(cur, pos);
			if(tmplength + dis <= length){
				tmplength += dis;
				cur = pos;
				d.dropPassenger(pos, pg);
			}else{
				double dif = length - tmplength;
				double ratio = dif / dis;
				double x = cur.x + ratio * (pos.x - cur.x);
				double y = cur.y + ratio * (pos.y - cur.y);
				d.loc.x = x;
				d.loc.y = y;
				return d.loc;
			}
		}
		if(locs.size() == 0) {
			return driver.loc;
		}else return locs.get(locs.size() - 1);
	}
	
	//寻找距离pos最近的没有访问过的，可以访问的位置
	public static int findNearest(Pos ori, Pos[] poses, boolean visited[], boolean taken[]) {
		double minDis = Integer.MAX_VALUE;
		int minindex = -1;
		for(int i = 0; i < poses.length; i++){
			if(visited[i])continue;
			if(!taken[i])continue;
			Pos pos = poses[i];
			double dis = Tool.caldis(ori, pos);
			if(dis <= minDis) {
				minDis = dis;
				minindex = i;
			}
		}
		return minindex;
	}
}
