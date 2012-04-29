package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class ClientReciver implements Runnable{
	public static final int PORT = 54121;
	boolean stop = false;
	ServerSocket ServerSocket;
	ClientController cc;
	public void stop(){
		stop = true;
	}
	
	public ClientReciver(ClientController cc, int port){
		this.cc = cc;
		if(ServerSocket == null){
			try {
				ServerSocket = new ServerSocket(port);
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
				System.out.println("Reciver Started");
				Socket socket = ServerSocket.accept();
				reciverHandler rh = new reciverHandler(socket,cc);
				Thread t = new Thread(rh);
				t.start();
			} catch(SocketTimeoutException e){
				System.out.println("accept timeout");
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
