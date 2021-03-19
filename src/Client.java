import java.net.*;
import java.util.concurrent.TimeUnit;


import java.io.*;

public class Client {
	// initialize socket and input output streams
	private static Socket socket = null;
	private BufferedReader input = null;
	private DataOutputStream out = null;
	private BufferedReader in = null;

	// constructor to put ip address and port
	public Client(String address, int port) throws IOException {

		// connect to server
		connect(address, port);

		// takes input from keyboard
		input = new BufferedReader(new InputStreamReader(System.in));

		// sends output to the server
		out = new DataOutputStream(socket.getOutputStream());

		// gets input from server
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// keep track of if we are connected to server or not
		boolean connected = false;

		// client input
		String outStr = "";

		// Initial handshake
		sendMessage("HELO");
		readMessage();
		sendMessage("AUTH user");
		readMessage();

		//
		// read xml file here
		//

		// Hand-shake completed - client now connected
		connected = true;

		sendMessage("REDY");

		// if client receive's NONE; send quit and set connected to false
		if (readMessage().contains("NONE")) {
			sendMessage("QUIT");
			connected = false;
		}

	
		// allow user to input messages until 'QUIT' is sent
		while (connected){
			
			outStr = input.readLine();

			if (outStr.equals("QUIT")){
				connected = false;
				sendMessage("QUIT");
				break;
			}

			// first send message before we check for incoming messages
			sendMessage(outStr);

			readMessage();

		}

		// close the connection
		try {
			
			// QUIT hand-shake, must receive confirmation from server for quit
			if (readMessage().contains("QUIT")){
				input.close();
				out.close();
				socket.close();
			}
				
			
		} catch (IOException i) {
			System.out.println(i);
		}

		// Exit the program
		System.exit(1);
	}

	private void sendMessage (String outStr) {
		// send message to server
		byte[] byteMsg = outStr.getBytes();
		try {
			out.write(byteMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Display outgoing message from client
		System.out.println("OUT: " + outStr);
	}

	private String readMessage () {
		// read string sent from server
		String inStr = "";
		char[] cbuf = new char[65535];
		try {
			in.read(cbuf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		inStr = new String(cbuf, 0, cbuf.length);

		// Display incoming message from server
		System.out.println("INC: " + inStr);

		return inStr;
	}

	private static void connect(String address, int port) {
		// try establish a connection
		double secondsToWait = 1;
		int tryNum = 1;
		while (true) {
			try {
				System.out.println("Connecting to server at: " + address + ":" + port);
				socket = new Socket(address, port);
				System.out.println("Connected");
				break; // we connected, so exit the loop
			} catch (IOException e) {
				// reconnect failed, wait.
				secondsToWait = Math.min(30, Math.pow(2, tryNum));
				tryNum++;
				System.out.println("Connection timed out, retrying in  " + (int) secondsToWait + " seconds ...");
				try {
					TimeUnit.SECONDS.sleep((long) secondsToWait);
				} catch (InterruptedException ie) {
					// interrupted
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {
		Client client = new Client("127.0.0.1", 50000);
	}

	public class Server {
		String[] serverData = null;

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
		public int getHourlyRate() {
			return Integer.parseInt(serverData[3]);
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

		// get string containing server data separated by white spaces
		public String getServerData() {
			String data = "";
			for (String str : serverData) {
				data += str + " ";
			}
			return data;
		}
	}
}