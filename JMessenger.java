import java.io.Serializable;
import java.util.ArrayList;

/*
 * Class meant to contain a single message transmitted from the user to 
 * the server, from where it shall be sent to all remaining clients.
 * 
 * Also entails situational messages such as client leaving or joining, etc.
 */
public class JMessenger implements Serializable {
	
	/**
	 * Implements Serializable such that it may be stream-safe.
	 */
	private static final long serialVersionUID = 1L;

	//Simple command integers for the described functions below
	public static final int USER_MESSAGE = 1, USER_LEAVE = 2, USER_JOINED = 3;
	
	//Message object
	public String message = "";
	
	//Status manager
	public int status = 0;
	
	//Name storage of user sending
	public String name = "";
	
	//Names storage of the list, just in case.
	public ArrayList<String> names;
	
	//initial constructor, receiving a message, an initial status, and a name
	public JMessenger(String mail, int initialStatus, String userName){
		this.message = mail;
		this.status = initialStatus;
		this.name = userName;
	}
	
	//Secret constructor in case you are sending names
	public JMessenger(String mail, int initialStatus, String userName, ArrayList<String> names){
		this.message = mail;
		this.status = initialStatus;
		this.name = userName;
		this.names = names;
	}
	
	
	//Below method to be used solely in case of an actual message
	public void setMessage(String m){
		this.message = m;
	}
	
	//Use for leaving and joining user
	public void setMessage(int stat){
		switch(stat){
		case 1:
			return;
		case 2: 
			message =  name + " has left the chat.";
			return;
		case 3:
			message =  name + " has joined the chat.";
			return;
				
		}
	}
	
	//Sets name
	public void setName(String n){
		this.name = n;
	}
	
	//Sets status 
	public void setStatus(int newStatus){
		this.status = newStatus;
	}
	
	//General 'getter' methods entailed below for the status, message, and name
	
	public String getName(){
		return this.name;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public int getStatus(){
		return this.status;
	}

	public ArrayList<String> getNamesOfUsers(){
		return this.names;
	}
	
	public String toString(){
		return new String("\n***TO STRING J MESSENGER***\nMessage:"+this.getMessage()+"\nType:"+this.getStatus()+"\nUser sending:"+this.getName());
	}
}


