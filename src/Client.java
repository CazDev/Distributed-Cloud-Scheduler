import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


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

		ArrayList<Server> t = new ArrayList<Server>();
		t = rXML();

		for (int i = 0; i < t.size(); i++){
			t.get(i).printData();
		}
		
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

			// send first, THEN read messages
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

	public static ArrayList<Server> rXML(){
        ArrayList<Server> serverList = new ArrayList<Server>();
		
		try {
			File systemXML = new File("ds-system.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(systemXML);

			

			doc.getDocumentElement().normalize();
			NodeList servers = doc.getElementsByTagName("server");
			for (int i = 0; i < servers.getLength(); i++) {
				Element server = (Element) servers.item(i);

				// Parse all XML attributes to new Server object
				String type = server.getAttribute("type");
				int limit = Integer.parseInt(server.getAttribute("limit"));
				int bootupTime = Integer.parseInt(server.getAttribute("bootupTime"));
				float hourlyRate = Float.parseFloat(server.getAttribute("hourlyRate"));
				int coreCount = Integer.parseInt(server.getAttribute("coreCount"));
				int memory = Integer.parseInt(server.getAttribute("memory"));
				int disk = Integer.parseInt(server.getAttribute("disk"));
				
				Server s = new Server(type,limit,bootupTime,hourlyRate,coreCount,memory,disk);
				serverList.add(s);
			}

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return serverList;
    }

	
}