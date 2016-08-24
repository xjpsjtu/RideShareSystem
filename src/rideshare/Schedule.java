package rideshare;

import java.util.ArrayList;

public class Schedule {
	ArrayList<Pos> locs;
	Driver driver;
	
	public Schedule(ArrayList<Pos> locs, Driver driver){
		this.locs = locs;
		this.driver = driver;
	}
	
	public void addPassenger(Passenger[] ps){
		for(int i = 0; i < ps.length; i++){
			Passenger p = ps[i];
			locs.add(p.startPoint);
			locs.add(p.endPoint);
		}
	}
	public void dropPassenger(Pos pos){
		for(int i = 0; i < locs.size(); i++){
			if(locs.get(i).x == pos.x && locs.get(i).y == pos.y){
				locs.remove(i);
			}
		}
	}
	public boolean coverPart(Pos pos){
		for(int i = 0; i < locs.size(); i++){
			if(locs.get(i).owner == pos.owner && (locs.get(i).flag + pos.flag) == 1){
				return true;
			}
		}
		return false;
	}
}
