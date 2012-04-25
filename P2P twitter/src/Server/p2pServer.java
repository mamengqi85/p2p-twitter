package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	ServerTable tables;
	
	
	
    public static void main(String args[]) {
		p2pServer server = new p2pServer();
		Thread t = new Thread(server);
		t.start();
		InputStreamReader stdin = new  InputStreamReader(System.in);
		BufferedReader bReader = new BufferedReader(stdin);
		try {
			while(!bReader.readLine().equals("stop")){
				System.out.println("Input command again");
			}
			server.stop();
			System.out.println("Server Stoped");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			server.stop();
			e.printStackTrace();
		}

	}
	
	public void stop(){
		ServerConfig sc = ServerConfig.getInstance();
		sc.close();
		stop = true;
	}
	
	public p2pServer(){
		ServerConfig sc = ServerConfig.getInstance();
		tables = sc.getTables();
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
				System.out.println("Server Started");
				ServerSocket.setSoTimeout(60000);
				Socket socket = ServerSocket.accept();
				ServerRequestHandler sHandler = new ServerRequestHandler(socket, tables);
				Thread t = new Thread(sHandler);
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
		}
	}
}
