package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import BasicTypes.User;
import RPC.RequestMessage;
import Server.ServerRequestHandler;

public class p2pServer implements Runnable{
	
	private final int PORT = 5412;

	boolean stop = false;
	ServerSocket ServerSocket;
	//TODO: remove?
	ArrayList<User> userlist;
	ServerTable tables = new ServerTable();
	
    public static void main(String args[]) {
		p2pServer server = new p2pServer();
		server.run();
	}
	
	public void stop(){
		stop = true;
	}
	
	public p2pServer(){
		userlist = new ArrayList<User>();
		if(ServerSocket == null){
			try {
				ServerSocket = new ServerSocket(PORT);
			} catch (IOException e) {
				System.err.println("Failed to start p2p Server!");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while(!stop){
			try {
				Socket socket = ServerSocket.accept();
				
				ServerRequestHandler sHandler = new ServerRequestHandler(socket, tables);
				sHandler.run();
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				RequestMessage rm =  (RequestMessage)ois.readObject();
				
				oos.writeObject("hi");
				System.out.println(rm.getString());
				
				
				
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
