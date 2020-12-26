
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/*
 * Server class that attempts to accept all client connections through a 
 * perennially running loop. Contains a 32-bit open port and a specific IP address.
 */
public class JServer {

	public static final int port = 80;
	
	//Stores all connections to the Server
	public static ArrayList<Object> clientConnections;

	//Main function of Server 
	
	public static void main(String[] args){
		
		/* attempts to forever accept connections while in instance
		 * try-catch used to handle any possible exceptions in the creation of sockets
		 * etc.
		 */
		try{
			ServerSocket centralSocket = new ServerSocket(port);
			
			while(true){
				//Accepts a connection 
				Socket socket = centralSocket.accept();
				
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
				
				JMessenger j = (JMessenger)is.readObject();
				String name  = j.getName();
				JSManager manager = new JSManager(os,is,name);
				Thread t = new Thread(manager);
				
				t.start();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
