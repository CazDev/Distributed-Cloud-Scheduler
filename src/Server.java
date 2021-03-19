public class Server {

		String[] serverData = null;


		String type; 
		int limit; 
		int bootTime;
		float hourlyRate; 
		int coreCount; 
		int memory; 
		int disk;

		public Server(String s, int l, int bt, float hr, int cc, int m, int d){
			this.type = s;
			this.limit = l;
			this.bootTime = bt;
			this.coreCount = cc;
			this.memory = m;
			this.disk = d;
		}


		public Server(String a) {
			serverData = a.split(" ");
		}

		//
		// Identification
		//
		// id: a sequence number based on the submission time
		public String getId() {
			return serverData[0];
		}

		// type: an identifier of job category based on run time
		public String getType() {
			return serverData[1];
		}

		//
		// Timing
		//
		// limit: the number of servers of a particular type
		public int getLimit() {
			return Integer.parseInt(serverData[2]); // the number of servers of a particular type
		}

		// bootupTime: the amount of time taken to boot a server of a particular type
		public int getBootupTime() {
			return Integer.parseInt(serverData[3]);
		}

		// hourlyRate: the monetary cost for renting a server of a particular type per
		// hour
		public Float getHourlyRate() {
			return Float.parseFloat(serverData[3]);
		}

		//
		// Resource requirements
		//
		// core: the number of CPU cores
		public int getCores() {
			return Integer.parseInt(serverData[4]);
		}

		// memory: the amount of RAM (in MB)
		public int getMemory() {
			return Integer.parseInt(serverData[5]);
		}

		// disk: the amount of disk space (in MB)
		public int getDisk() {
			return Integer.parseInt(serverData[6]);
		}


        public void printData(){
             System.out.println(this.type + " " + this.limit + " " + this.bootTime + " " + this.hourlyRate + " " + this.coreCount + " " + this.memory + " " + this.disk);
        }

		// get string containing server data separated by white spaces
		public String getServerData() {
			String data = "";
			for (String str : serverData) {
				data += str + " ";
			}
			return data;
		}    

}
