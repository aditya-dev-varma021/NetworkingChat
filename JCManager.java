import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/*
 * Manages a client and creates a frame per client.
 */
public class JCManager implements Runnable{
		
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	private Frame frame;
	
	public JCManager(ObjectOutputStream os, ObjectInputStream is, Frame f){
		this.frame = f;
		this.os = os;
		this.is = is;
	}
	
	public void run(){
		
		while(true){
			try {
				JMessenger mess = (JMessenger)is.readObject();
				if(mess.getStatus() == mess.USER_JOINED){
					//TODO: Add a message to Frame's Messages.
					System.out.println("From the object:\n"+mess+""+"As a client, I have recieved this copy of names:\n"+Arrays.deepToString(mess.getNamesOfUsers().toArray()));
					frame.setUsers(mess.getNamesOfUsers());
					frame.addUser(mess.getName());
				}
				else if(mess.getStatus() == mess.USER_LEAVE){
					System.err.println("The command to register a user leaving has come into ClientListener.");
					frame.removeUser(mess.getName());
				}
				else if(mess.getStatus() == mess.USER_MESSAGE){
					frame.addMessage(mess.getName(), mess.getMessage());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				if(e instanceof EOFException){
					try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		}
	}
	
}
