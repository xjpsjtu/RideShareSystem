package rideshare;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
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
	 * requests[u]涓烘柊璇锋眰
	 * requests[u+1]-requests[u+v]涓哄師鏉ヨ姹�
	 * requests[0]-requests[u-1]娌℃湁璧风偣
	 */
	public Pos[] neNeigh(Request[] requests, double[] remBudget, int u, Pos driverStart, double startT) {
		
		if(requests.length == 1){
			System.out.println("xxxxxxx");
//			System.out.println(driverStart + "," + requests[0].startPoint + "," + requests[0].endPoint);
			double t = (Tool.caldis(driverStart, requests[0].startPoint) + Tool.caldis(requests[0].startPoint, requests[0].endPoint)) / Tool.v;
//			System.out.println(t);
			if(startT + t <= requests[0].endTime){
				Pos[] pp = new Pos[2];
				pp[0] = requests[0].startPoint;
				pp[1] = requests[0].endPoint;
//				System.out.println(pp[0] + "," + pp[1]);
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
		//鍔犱笂姹借溅鍒濆浣嶇疆锛屼竴鍏眜 + 2v + 3涓綅缃�
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
		
		
		//琛ㄧず鏌愪釜浣嶇疆鏈夋病鏈夎璁块棶杩�
		boolean[] visited = new boolean[u + 2 * v + 3];
		//琛ㄧず浣嶇疆瀵瑰簲鐨勬煇璇锋眰鏄惁宸蹭笂杞�
		boolean[] taken = new boolean[u + 2 * v + 3];
		//琛ㄧず鏌愯姹傛槸鍚﹀凡瀹屾垚
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
	 * n:涔樺鎬讳汉鏁�
	 * m:鍙告満鎬讳汉鏁�
	 * cap:姣忚締杞﹁浇瀹㈤噺
	 * ttime:杩囩▼鎬绘椂闂� 
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
			System.out.println("======= time " + t + "=======");
			//鏇存柊杞﹁締浣嶇疆淇℃伅
			System.out.println("======= start update driver locs =======");
			for(int di = 0; di < dr.length; di++) {
				System.out.println("update the information of driver: " + di);
				dr[di].loc = Tool.drive(dr[di], t - 1, t, pg);
			}
			System.out.println("finish of updating");
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
					
					int size = list.size();
					//debug
					if(size == 0)continue;
					int ssize = (int)(Math.random() * size);
					System.out.println(ssize);
					Driver driver = list.get(ssize);
					System.out.println("Choose " + driver);
					Schedule schedule = new Schedule(driver);
					Pos[] poss = neNeigh(driver.schedule.getRequestsAddNewPassenger(pg[pi], pg), driver.schedule.getRemainBudget(pg[pi], pg), driver.schedule.getU(pg[pi], pg), driver.loc, t);
					System.out.println("new psssssss" + poss);
					for(int i = 0; i < poss.length; i++){
						schedule.locs.add(poss[i]);
					}
					driver.schedule = schedule;
					driver.addPassenger(pg[pi]);
					pg[pi].status = 1;
					liquity++;
					
				}
				
				
			}
		}
		
		return liquity;
	}
	
	public int liquity_order(int n, int m, int cap) {
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
			System.out.println("======= time " + t + "=======");
			//鏇存柊杞﹁締浣嶇疆淇℃伅
			System.out.println("======= start update driver locs =======");
			for(int di = 0; di < dr.length; di++) {
				System.out.println("update the information of driver: " + di);
				dr[di].loc = Tool.drive(dr[di], t - 1, t, pg);
			}
			System.out.println("finish of updating");
			for(int pi = 0; pi < pg.length; pi++) {
				if(pg[pi].status == 0 && pg[pi].request.declaretime == t) {
					for(int di = 0; di < dr.length; di++) {
						Request[] requests = dr[di].schedule.getRequestsAddNewPassenger(pg[pi], pg);
						double[] rembudget = dr[di].schedule.getRemainBudget(pg[pi], pg);
						int u = dr[di].schedule.getU(pg[pi], pg);
						Pos pos = dr[di].loc;
						double curt = t;
						if(neNeigh(requests, rembudget, u, pos, curt) != null){
//							System.out.println("Gotten by " + dr[di]);
//							list.add(dr[di]);
							Driver driver = dr[di];
							System.out.println("Choose " + driver);
							Schedule schedule = new Schedule(driver);
							Pos[] poss = neNeigh(driver.schedule.getRequestsAddNewPassenger(pg[pi], pg), driver.schedule.getRemainBudget(pg[pi], pg), driver.schedule.getU(pg[pi], pg), driver.loc, t);
							System.out.println("new psssssss" + poss);
							for(int i = 0; i < poss.length; i++){
								schedule.locs.add(poss[i]);
							}
							driver.schedule = schedule;
							driver.addPassenger(pg[pi]);
							pg[pi].status = 1;
							liquity++;
							break;
						}
					}
					
				}
				
				
			}
		}
		
		return liquity;
	}
	
	
	public static void main(String[] args) {
		Process p = new Process();
		for(int lam = 3; lam <= 10; lam += 2) {
			String filename = lam + "_4.txt";
			File file = new File(filename);
			FileOutputStream fs = null;
			try {
				fs = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			PrintStream ps = new PrintStream(fs);
			for(int i = 1000; i < 10000; i += 1000) {
				int a = p.liquity_random(i, 250, lam);
				ps.println(a + "  " + i/(lam + 1));
				 
			}
			ps.close();
		}
		int a = p.liquity_random(6000, 50, 9);
		int b = p.liquity_order(6000, 50, 9);
		System.out.println(a);
		System.out.println(b);
	}
	
}
