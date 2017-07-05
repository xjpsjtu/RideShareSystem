package rideshare;

import java.util.ArrayList;

public class Process {
	int n;
	int v;
	Passenger[] passengers;
	Driver[] drivers;
	int cap;
	int T;
	
	public void Process(int n, int cap, int T, int v){
		this.n = n;
		this.cap = cap;
		this.T = T;
		this.v = v;
		for(int i = 1; i <= n; i++){
			passengers[i] = new Passenger(i);
			drivers[i] = new Driver(cap,v);
		}
	}
	
	
	/*
	 * requests[u]为新请求
	 * requests[u+1]-requests[u+v]为原来请求
	 * requests[0]-requests[u-1]没有起点
	 */
	public Pos[] neNeigh(Request[] requests, double[] remBudget, int u, Pos driverStart, double startT) {
		
		if(requests.length == 1){
			System.out.println("xxxxxxx");
			System.out.println(driverStart + "," + requests[0].startPoint + "," + requests[0].endPoint);
			double t = (Tool.caldis(driverStart, requests[0].startPoint) + Tool.caldis(requests[0].startPoint, requests[0].endPoint)) / Tool.v;
			System.out.println(t);
			if(startT + t <= requests[0].endTime){
				Pos[] pp = new Pos[2];
				pp[0] = requests[0].startPoint;
				pp[1] = requests[0].endPoint;
				System.out.println(pp[0] + "," + pp[1]);
				return pp;
			}
		}
		
		int v = requests.length - u - 1;
		Pos[] ans = new Pos[u + 2 * v + 3];
		double[] t = new double[u + 2 * v + 3];
		double[] dis = new double[u + v + 1];
		for(int i = 0; i < dis.length; i++) {
			Request request = requests[i];
			Pos start = request.startPoint;
			Pos end = request.endPoint;
			double dist = Tool.caldis(start, end);
			dis[i] = dist;
		}
		//加上汽车初始位置，一共u + 2v + 3个位置
		Pos[] poses = new Pos[u + 2 * v + 3];
		poses[0] = driverStart;
		for(int i = 0; i < u; i++) {
			Pos endp = requests[i].endPoint;
			poses[i + 1] = endp;
		}
		for(int i = u; i < u + v + 1; i++){
			Request request = requests[i];
			Pos start = request.startPoint;
			Pos end = request.endPoint;
			poses[i + 1] = start;
			poses[i + v + 2] = end;
		}
		
		
		//表示某个位置有没有被访问过
		boolean[] visited = new boolean[u + 2 * v + 3];
		//表示位置对应的某请求是否已上车
		boolean[] taken = new boolean[u + 2 * v + 3];
		//表示某请求是否已完成
		boolean[] active = new boolean[u + v + 1];
		for(int i = 0; i < visited.length; i++){
			visited[i] = false;
			if(i <= u + v + 1) taken[i] = true;
			else taken[i] = false;
		}
		visited[0] = true;
		taken[0] = true;
		ans[0] = driverStart;
		t[0] = startT;
		Pos cur = driverStart;
		double curt = startT;
		for(int i = 0; i < active.length; i++)active[i] = false;
		for(int i = 1; i <= u + 2 * v + 2; i++) {
			int minIndex = Tool.findNearest(cur, poses, visited, taken);
			double delta_t = curt + Tool.calt(cur, poses[minIndex]);
			ans[i] = poses[minIndex];
			cur = ans[i];
			curt = curt + delta_t;
			t[minIndex] = curt;
			if(cur.flag == 1){
				for(int j = 0; j < requests.length; j++){
					Request r = requests[j];
					if(cur.owner == r.num)active[j]=false;
				}
			}
		}
		
		for(int i = 1; i <= u; i++){
			Request request = requests[i - 1];
			if(t[i] > request.endTime)return null;
		}
		for(int i = u + 1; i <= u + v + 1; i++){
			Request request = requests[i - 1];
			if(t[i] < request.startTime)return null;
		}
		for(int i = u + v + 2; i <= u + 2 * v + 2; i++){
			Request request = requests[i - v - 2];
			if(t[i] > request.endTime)return null;
		}
		
		return ans;
	}
	
	/*
	 * @param
	 * n:乘客总人数
	 * m:司机总人数
	 * cap:每辆车载客量
	 * ttime:过程总时间 
	 */
	public int liquity_random(int n, int m, int cap) {
		int liquity = 0;
		Passenger[] pg = new Passenger[n];
		for(int i = 0; i < pg.length; i++) {
			pg[i] = new Passenger(i);
			System.out.println(pg[i].request);
		}
		Driver[] dr = new Driver[m];
		for(int i = 0; i < dr.length; i++) {
			dr[i] = new Driver(cap, i);
		}
		
		for(int t = 1; t <= Tool.time; t++) {
			//更新车辆位置信息
			for(int di = 0; di < dr.length; di++) {
				dr[di].loc = Tool.drive(dr[di], t - 1, t, pg);
			}
			for(int pi = 0; pi < pg.length; pi++) {
				ArrayList<Driver> list = new ArrayList<Driver>();
				if(pg[pi].status == 0 && pg[pi].request.declaretime == t) {
					for(int di = 0; di < dr.length; di++) {
						Request[] requests = dr[di].schedule.getRequestsAddNewPassenger(pg[pi], pg);
						double[] rembudget = dr[di].schedule.getRemainBudget(pg[pi], pg);
						int u = dr[di].schedule.getU(pg[pi], pg);
						Pos pos = dr[di].loc;
						double curt = t;
						if(neNeigh(requests, rembudget, u, pos, curt) != null){
							System.out.println("Gotten by " + dr[di]);
							list.add(dr[di]);
						}
					}
				}
				
				int size = list.size();
				//debug
				if(size == 0)continue;
				int ssize = (int)(Math.random() * size);
				System.out.println(ssize);
				Driver driver = list.get(ssize);
				driver.addPassenger(pg[pi]);
				pg[pi].status = 1;
				Schedule schedule = new Schedule(driver);
				Pos[] poss = neNeigh(driver.schedule.getRequestsAddNewPassenger(pg[pi], pg), driver.schedule.getRemainBudget(pg[pi], pg), driver.schedule.getU(pg[pi], pg), driver.loc, t);
				System.out.println("new psssssss" + poss);
				for(int i = 0; i < poss.length; i++){
					schedule.locs.add(poss[i]);
				}
				driver.schedule = schedule;
				liquity++;
			}
		}
		
		return liquity;
	}
	
	public static void main(String[] args) {
		Process p = new Process();
		System.out.println(p.liquity_random(10, 3, 2));
	}
	
}
