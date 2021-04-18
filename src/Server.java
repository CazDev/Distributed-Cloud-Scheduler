public class Server {

		/* 
			These fields are based on the data contained in ds-system.xml 
		*/

		String type; 
		int limit; 
		int bootTime;
		float hourlyRate; 
		int coreCount; 
		int memory; 
		int disk;

		// Server constructor
		public Server(String type, int limit, int bootTime, float hourlyRate, int coreCount, int memory, int disk){
			this.type = type;
			this.limit = limit;
			this.bootTime = bootTime;
            this.hourlyRate = hourlyRate;
			this.coreCount = coreCount;
			this.memory = memory;
			this.disk = disk;
		}
		
		//
		// type: an identifier of job category based on run time
		//
		public String getType() {
			return this.type;
		}

		//
		// Timing
		//
		// limit: the number of servers of a particular type
		public int getLimit() {
			return this.limit; // the number of servers of a particular type
		}

		//
		// bootupTime: the amount of time taken to boot a server of a particular type
		//
		public int getBootupTime() {
			return this.bootTime;
		}

		//
		// hourlyRate: the monetary cost for renting a server of a particular type per
		// 
		public Float getHourlyRate() {
			return this.hourlyRate;
		}

		//
		// Resource requirements
		//
		// core: the number of CPU cores
		public int getCores() {
			return this.coreCount;
		}

		//
		// memory: the amount of RAM (in MB)
		//
		public int getMemory() {
			return this.memory;
		}

		//
		// disk: the amount of disk space (in MB)
		//
		public int getDisk() {
			return this.disk;
		}

		//
		// This is a debug method to print all the fields of a given server object;
		// 	this is essentially a 'toString()'-like method.
		//
        public void printData(){
             System.out.println(this.type + " " + this.limit + " " + this.bootTime + " " + this.hourlyRate + " " + this.coreCount + " " + this.memory + " " + this.disk);
        }


}
