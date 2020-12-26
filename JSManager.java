import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/*
 * Equivalent to a ServerListener. Meant to manage input from it's clients and send out messages
 */
public class JSManager implements Runnable {

	public ObjectOutputStream os;
	public ObjectInputStream is;
	static java.util.ArrayList<ObjectOutputStream> osList = new java.util.ArrayList<ObjectOutputStream>();
	static java.util.ArrayList<String> names = new java.util.ArrayList<String>();
	
	public JSManager(ObjectOutputStream o, ObjectInputStream i,String name) {
		this.os = o;
		this.is = i;
		osList.add(o);
	
	}

	public static int getNamesSize(){
		return names.size();
	}
	public void run() {
		while (true) {
			try {

				JMessenger message = (JMessenger) is.readObject();

				if (message.getStatus() == message.USER_JOINED) {
					names.add(message.getName());
					System.out.println(names.size() + ","+ getNamesSize());
					JMessenger totalMessage = new JMessenger(" has joined the chat!", JMessenger.USER_JOINED,
							message.getName(), names);
					
					for (ObjectOutputStream a : osList) {

						a.writeObject(totalMessage);
						a.reset();
					}

					System.out.println("Have sent this copy of names: \n" + Arrays.deepToString(names.toArray()));
				} else if (message.getStatus() == message.USER_LEAVE) {
					System.out.println("Remove indication recieved in Server.");
					osList.remove(names.indexOf(message.getName()));
					names.remove(message.getName());

					for (ObjectOutputStream a : osList) {
						a.writeObject(
								new JMessenger(" has left the chat!", JMessenger.USER_LEAVE, message.getName(), names));
						a.reset();

					}
				} else if (message.getStatus() == message.USER_MESSAGE) {
					for (ObjectOutputStream a : osList) {
						a.writeObject(new JMessenger(message.getMessage(), JMessenger.USER_MESSAGE, message.getName()));
						a.reset();
					}
				}

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				osList.remove(os);

			}
		}
	}

}
