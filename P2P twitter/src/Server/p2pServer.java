package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import BasicTypes.User;
import RPC.RequestMessage;

public class p2pServer implements Runnable{

	boolean stop = false;
	ServerSocket ServerSocket;
	ArrayList<User> userlist;
	
	public void stop(){
		stop = true;
	}
	
	public p2pServer(){
		userlist = new ArrayList<User>();
		if(ServerSocket == null){
			try {
				ServerSocket = new ServerSocket(5412);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while(!stop){
			try {
				Socket socket = ServerSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				RequestMessage rm =  (RequestMessage)ois.readObject();
				
				
				oos.writeObject("hi");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
