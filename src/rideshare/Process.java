package rideshare;

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
		Pos[] ans = new Pos[u + 2 * v + 3];
		double[] t = new double[u + 2 * v + 3];
		int v = requests.length - u - 1;
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
		}
		Driver[] dr = new Driver[m];
		for(int i = 0; i < dr.length; i++) {
			dr[i] = new Driver(cap, i);
		}
		
		for(int i = 1; i <= Tool.time; i++) {
			
		}
		
		return liquity;
	}
	
}
