package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import RPC.RequestMessage;
import client.RequestHandler;

public class p2pClient implements Runnable{
	
	private String serverAddress = "127.0.0.1"; //ip to link
	private int serverPort = 5412; //port to link
	private Socket clientSocket;
	
    public p2pClient() {
        clientSocket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.serverAddress, this.serverPort);
        try {
            clientSocket.connect(isa, 10000);
        } catch (IOException e) {
            System.err.println("p2pClient socket failed!");
            //System.err.println("IOException :" + e.toString());
            e.printStackTrace();
        }
    }
 
    public static void main(String args[]) {
        p2pClient client = new p2pClient();
        client.run();
    }
	
    @Override
    public void run() {
		RequestMessage rm = new RequestMessage("ID1", "retrieve");
		RequestHandler cHandler = new RequestHandler(rm);
		cHandler.run();
/*		try {
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject("greeting");
			oos.close();
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ResponseMessage rm =  (ResponseMessage)ois.readObject();
			System.out.println(rm.getString());

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
