import java.net.*; 
import java.io.*;

public class Client 
{ 
    // initialize socket and input output streams 
    private Socket socket            = null; 
    private BufferedReader  input   = null; 
    private DataOutputStream out     = null; 
    private BufferedReader in	 = null; 
  
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
			// takes input from keyboard
			input = new BufferedReader( new InputStreamReader(System.in)); 

			// sends output to the server 
			out = new DataOutputStream(socket.getOutputStream()); 
 			
			// gets input from server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
		// Outgoing message
		String outStr = ""; 
		// Incoming message
		String inStr = "";
		
		// Protocol step
		int step = 0;
		boolean error = false;
		
		int jobID = 0;
		String[] job = null;
		
        // keep reading until "QUIT" is input 
        while (!outStr.equals("QUIT")) 
        { 
            try
            { 
            	switch (step){
				case 0:{
					outStr = "HELO";
					step++;
					break;
				}
				case 1:{
					outStr = "AUTH user";
					step++;
					break;
				}
				
				case 2:{
					// read ds-system.xml here
					// then
					outStr = "REDY";
					step++;
					break;
				}
				default:{
					outStr = input.readLine();
				}
			}
            
            // Display outgoing message from client
			System.out.println("OUT: " + outStr);
			
			byte[] byteMsg = outStr.getBytes();
			out.write(byteMsg);
			
			// read string sent from server
			char[] cbuf = new char[10];
			in.read(cbuf);
			inStr = new String(cbuf, 0, cbuf.length);
			
			if (inStr=="OK") {
				
			}
			
            // Display incoming message from server
			System.out.println("INC: " + inStr);
			
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
        
        //Exit the program
        System.exit(1);
    } 
  
    public static void main(String args[]) 
    { 
        Client client = new Client("127.0.0.1", 50000); 
    } 
    
    public class Server{
    	String[] serverData = null;
    	public Server(String a){
    		serverData = a.split(" ");
    	}
    	//
    	// Identification
    	//
    	// id: a sequence number based on the submission time
    	public String getId(){
    		return serverData[0];
    	}
    	// type: an identifier of job category based on run time
    	public String getType(){
    		return serverData[1];
    	}
    	//
    	// Timing
    	//
    	// limit: the number of servers of a particular type
    	public int getLimit(){
    		return Integer.parseInt(serverData[2]); // the number of servers of a particular type
    	}
    	// bootupTime: the amount of time taken to boot a server of a particular type
    	public int getBootupTime(){
    		return Integer.parseInt (serverData[3]);
    	}
    	// hourlyRate: the monetary cost for renting a server of a particular type per hour
    	public int getHourlyRate(){
    		return Integer.parseInt (serverData[3]);
    	}
    	//
    	// Resource requirements
    	//
    	// core: the number of CPU cores
    	public int getCores(){
    		return Integer.parseInt(serverData[4]);
    	}
    	// memory: the amount of RAM (in MB)
    	public int getMemory(){
    		return Integer.parseInt(serverData[5]);
    	}
    	// disk: the amount of disk space (in MB)
    	public int getDisk(){
    		return Integer.parseInt(serverData[6]);
    	}
    	// get string containing server data separated by white spaces
    	public String getServerData(){
    		String data = "";
    		for (String str : serverData){
    			data += str + " ";
    		}	
    		return data;
    	}
    }
} 