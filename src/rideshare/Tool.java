package rideshare;

import java.util.ArrayList;

public class Tool {
	public static double caldis(Pos a, Pos b){
		double x1 = a.x;
		double y1 = a.y;
		double x2 = b.x;
		double y2 = b.y;
		double dis = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		return dis;
	}
	
	public static Pos drive(Schedule schedule, int t0, int t1){
		ArrayList<Pos> locs = schedule.locs;
		Driver d = schedule.driver;
		double length = d.v * (t1 - t0);
		double tmplength = 0;
		Pos cur = d.loc;
		for(int i = 0; i < locs.size(); i++){
			Pos pos = locs.get(i);
			double dis = Tool.caldis(cur, pos);
			if(tmplength + dis <= length){
				tmplength += dis;
				cur = pos;
				d.dropPassenger(pos);
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
		return locs.get(locs.size() - 1);
	}
}
