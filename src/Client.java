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
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = input.readLine(); // TODO: This is where we can send messages to the server
                out.writeUTF(line); 
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
} 