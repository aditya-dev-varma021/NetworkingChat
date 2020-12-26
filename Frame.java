import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
public class Frame extends JFrame 
{
	
	public static final int LEFTCLOSE = 1;
	public static final int RUNNING = 2;
	public int status;
	private JButton btn_exit 		= new JButton("Exit");
	private JButton btn_send 		= new JButton("Send");
	
	private JLabel lbl_users		= new JLabel("Users:");
	private static JList list_users		= new JList();
	
	private JLabel lbl_chatBox		= new JLabel("Messages:");
	private JScrollPane	scr_chatBox	= null;
	private JTextArea txt_chatBox	= new JTextArea();
	
	private JLabel lbl_message		= new JLabel("Enter Message:");
	private JTextArea txt_message	= new JTextArea();
	
	private ArrayList<String> messages = new ArrayList<String>();
	
	private ObjectOutputStream stream;
	
	private String userName			= "";
	private  ArrayList<String> users = new ArrayList<String>();
	
	
	public void addUser(String user){
		txt_chatBox.append(user + " has joined the chat!"+"\n");
	}
	
	public void removeUser(String user){
		System.err.println("Method also called!");
		users.remove(user);
		txt_chatBox.append(user + " has left the chat!"+"\n");
		list_users.setListData(users.toArray());
		
	}
	public void addMessage(String user, String message){
		txt_chatBox.append( message + "\n");
	}
	
	public void setUsers(ArrayList<String> a){
		users = a;
		list_users.setListData(users.toArray());
		
	}
	public Frame(String userName, ObjectOutputStream o)
	{
		
		super("Chat Client");
		status = RUNNING;
		this.userName = userName;
		stream = o;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		
		users.add(userName);
		list_users.setEnabled(false);
		lbl_users.setBounds(640,30,130,20);
		list_users.setBounds(640,50,130,550);
		
		scr_chatBox = new JScrollPane(txt_chatBox,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scr_chatBox.setBounds(20,50,600,550);
		lbl_chatBox.setBounds(20,30,100,20);
		txt_chatBox.setEditable(false);		
		
		txt_message.setBounds(20,650,600,80);
		lbl_message.setBounds(20,630,100,20);
		
		btn_send.setBounds(640,650,130,30);
		btn_exit.setBounds(640,700,130,30);
		
		setLayout(null);
		add(txt_message);
		add(lbl_message);
		add(lbl_users);
		add(lbl_chatBox);
		add(scr_chatBox);
		add(list_users);
		add(btn_send);
		add(btn_exit);
		
	this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	users.remove(users.indexOf(getUserName()));
				try{
					stream.writeObject(new JMessenger("",JMessenger.USER_LEAVE,getUserName()));
					stream.reset();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				System.exit(0);
		    }
		});
		
		btn_exit.addActionListener(
			new java.awt.event.ActionListener()	
			{
				public void actionPerformed(ActionEvent e)
				{
					exit();
				}
			}
		);
		
		btn_send.addActionListener(
			new java.awt.event.ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					sendtxt_message();
				}
			}
		);
		
		
		setVisible(true);
	}
	public String getUserName(){
		return this.userName;
	}
	
	
	
	
	
	
	
	
	
	public void exit()
	{
		users.remove(users.indexOf(this.getUserName()));
		try{
			stream.writeObject(new JMessenger("",JMessenger.USER_LEAVE,this.getUserName()));
			stream.reset();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	public void sendtxt_message()
	{
		System.out.println("FRAME: Status = "+getStatus());
		String m = userName + ": " + txt_message.getText();
		txt_message.setText("");
		try {
			stream.writeObject(new JMessenger(m,JMessenger.USER_MESSAGE,userName));
			stream.reset();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public int getStatus(){
		return this.status;
	}

}