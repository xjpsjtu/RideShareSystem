package rideshare;

import java.util.ArrayList;

public class Schedule {
	ArrayList<Pos> locs;
	Driver driver;
	
	public Schedule(ArrayList<Pos> locs, Driver driver){
		this.locs = locs;
		this.driver = driver;
	}
	public Schedule(Driver d){
		locs = new ArrayList<Pos>();
		driver = d;
	}
	
	public void addPassengers(Passenger[] ps){
		for(int i = 0; i < ps.length; i++){
			Passenger p = ps[i];
			locs.add(p.request.startPoint);
			locs.add(p.request.endPoint);
		}
	}
	
	public void addPassenger(Passenger ps) {
		locs.add(ps.request.startPoint);
		locs.add(ps.request.endPoint);
	}
	
	public void dropPassenger(Pos pos, Passenger[] pr){
		for(int i = 0; i < locs.size(); i++){
			if(locs.get(i).x == pos.x && locs.get(i).y == pos.y){
				Pos loc = locs.get(i);
				Passenger p = loc.getOwner(pr);
				p.status = 3;
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
	public Request[] getRequestsAddNewPassenger(Passenger p, Passenger[] pr) {
		ArrayList<Request> rlist = new ArrayList<Request>();
		for(Pos pos : locs) {
			if(pos.getOwner(pr).status == 2){
				Passenger pa = pos.getOwner(pr);
				rlist.add(pa.request);
			}
		}
		rlist.add(p.request);
		for(Pos pos : locs) {
			if(pos.getOwner(pr).status == 1 && pos.flag == 0){
				Passenger pa = pos.getOwner(pr);
				rlist.add(pa.request);
			}
		}
		return (Request[])rlist.toArray();
	}
	
	public double[] getRemainBudget(Passenger p, Passenger[] pr) {
		Request[] rs = getRequestsAddNewPassenger(p,pr);
		double[] bud = new double[rs.length];
		for(int i = 0; i < rs.length; i++) {
			Request r = rs[i];
			bud[i] = r.getOwner(pr).budget;
		}
		
		return bud;
	}
	
	public int getU(Passenger p, Passenger[] pr) {
		int u = 0;
		for(Pos pos : locs) {
			if(pos.getOwner(pr).status == 2) {
				u++;
			}
		}
		return u;
	}
	
}
