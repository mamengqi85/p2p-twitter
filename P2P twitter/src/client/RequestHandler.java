package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import de.uniba.wiai.lspi.chord.service.Chord;

import BasicTypes.Node;
import DHT.ChordDHT;
import RPC.RPCConstants;
import RPC.RequestMessage;
import RPC.ResponseMessage;

public class RequestHandler implements Runnable{

	Socket socket;
	static String SERVERIP = "localhost";
	int SERVERPORT = 5412;
	public String message = "";
	RequestMessage rm;
	ClientController client;
	
	//public RequestHandler(RequestMessage rm, ClientController client) {
	public RequestHandler(RequestMessage rm, ClientController client) {
		this.rm = rm;
		this.client = client;
		if(socket == null){
			try {
				socket = new Socket(SERVERIP,SERVERPORT);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oss = new ObjectOutputStream(socket.getOutputStream());
			oss.writeObject(rm);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			client.callBack((ResponseMessage)ois.readObject(),message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
