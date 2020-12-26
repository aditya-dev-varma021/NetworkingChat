import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Object that represents the user connection to the JServer class.
 * (JServer.class included in this default package).
 * Also manages a Graphical User Interface through a Frame.
 */



public class JClient {

	
	static ArrayList<String> names;
	//default values for the port and IP address
	static String IP = "127.0.0.1";
	static int port = 5457;
	
	//For input 
	static java.util.Scanner userInput = new java.util.Scanner(System.in);
	
	
	//Main function 
	public static void main(String[] args){
		//Asks if the connection configuration is OK or if a different connection is desired
		System.out.println("The client will currently connect to: \nIP Address 127.0.0.1\nPort 5457.");
		System.out.println("If this is the correct configuration, type in OK.\nElse, type NO.");
		System.out.println("Enter:");
		
		//Takes in the choice
		String input = userInput.next();
		
	
		//if one desires an alternate configuration 
		if(!input.equalsIgnoreCase("OK")){
			System.out.println("Enter in a new port:");
			port = userInput.nextInt();
			System.out.println("Enter in a new IP:");
			IP = userInput.next();
		}
		
		
		//asks for a name 
		System.out.println("Enter in a username as well:");
		
		
		String userName = userInput.next();
		System.out.println("Logging in...");
		
		//attempts connection and sends a JMessenger with the 'joined' message
		try{
			Socket connection = new Socket(IP,port);
			
			ObjectInputStream is = new ObjectInputStream(connection.getInputStream());
			ObjectOutputStream os = new ObjectOutputStream(connection.getOutputStream());
			
			JMessenger temp = new JMessenger("  has joined the chat!\n",JMessenger.USER_JOINED,userName);
			System.out.println("Command to say someone has joined:"+temp);
			os.writeObject(temp);
			
			os.reset();
			
			
			JCManager man = new JCManager(os,is,new Frame(userName,os));
			Thread t = new Thread(man);
			t.start();
			
			
			
			os.writeObject(new JMessenger("  has joined the chat!\n",JMessenger.USER_JOINED,userName));
			os.reset();
			
		}catch(Exception e){
			e.printStackTrace();
		}

		}

		
	}


