package rideshare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
	
	//瀵绘壘璺濈pos鏈�杩戠殑娌℃湁璁块棶杩囩殑锛屽彲浠ヨ闂殑浣嶇疆
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
	
	@SuppressWarnings("unchecked")
	public int phi(int n) {
		Request[] requests = new Request[n];
		for(int i = 0; i < n; i++) {
			requests[i] = new Request(i);
		}
		java.util.Arrays.sort(requests, new RequestComparator());

		boolean[] bools = new boolean[n];
		for(int i = 0; i < n; i++) {
			bools[i] = false;
		}
		Pos pos = requests[0].endPoint;
		Pos curpos = pos;
		bools[0] = true;
		double disone = 0;
		disone = disone + Tool.caldis(requests[0].startPoint, requests[0].endPoint);
		for(int i = 0; i < n - 1; i++) {
			int minindex = -1;
			double minDis = Integer.MAX_VALUE;
			for(int j = 0; j < n; j++) {
				if(bools[j] == true)continue;
				Pos nowpos = requests[j].startPoint;
				double dis = Tool.caldis(curpos, nowpos);
				if(dis < minDis) {
					minDis = dis;
					minindex = j;
				}
			}
			bools[minindex] = true;
			disone += Tool.caldis(curpos, requests[minindex].startPoint);
			disone += Tool.caldis(requests[minindex].startPoint, requests[minindex].endPoint);
			curpos = requests[minindex].endPoint;
		}
		
		Pos[] poss = new Pos[2*n];
		int m = 0;
		for(int i = 0; i < n; i++) {
			poss[m] = requests[i].startPoint;
			poss[m+1] = requests[i].endPoint;
			m += 2;
		}
		boolean[] bs = new boolean[2*n];
		for(int i = 0; i < 2 * n; i++) {
			bs[i] = false;
		}
		curpos = poss[0];
		bs[0] = true;
		double distwo = 0;
		for(int i = 0; i < 2 * n - 1; i++) {
			int minindex = -1;
			double mindis = Integer.MAX_VALUE;
			for(int j = 0; j < 2 * n; j++) {
				if(bs[j] == true)continue;
				if(poss[j].flag == 0) {
					double nowdis = Tool.caldis(curpos, poss[j]);
					if(nowdis <= mindis) {
						minindex = j;
						mindis = nowdis;
					}
				} else {
					if(bs[j - 1] == false)continue;
					else {
						double nowdis = Tool.caldis(curpos, poss[j]);
						if(nowdis <= mindis) {
							minindex = j;
							mindis = nowdis;
						}
					}
				}
			}
			bs[minindex] = true;
			distwo += Tool.caldis(curpos, poss[minindex]);
			curpos = poss[minindex];
		}
		
		
		
		
		return 0;
	}
	
	class RequestComparator implements Comparator{
		public int compare(Request r1, Request r2) {
			if(r1.declaretime < r2.declaretime) {
				return -1;
			} else return 1;
		}

		@Override
		public int compare(Object arg0, Object arg1) {
			Request r1 = (Request)arg0;
			Request r2 = (Request)arg1;
			if(r1.declaretime < r2.declaretime) {
				return -1;
			} else return 1;
		}
	}
	
	public static void main(String[] args) {
		Tool t = new Tool();
		for(int i = 10; i < 100; i+=10) {
			System.out.println(t.phi(i));
		}
	}
	
}
