package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import BasicTypes.Node;
import RPC.RPCConstants;

public class ClientSender implements Runnable{
	public static int SUCCESS = 1;
	public static int FAIL = 2;
	Node node;
	byte[] message;
	ClientController cc;
	public ClientSender(Node node,byte[] message, ClientController cc) {
		this.node = node;
		this.message = message;
		this.cc = cc;
		// TODO Auto-generated constructor stub
	}
	
	public boolean SendMessage(){
		try {
			Socket socket = new Socket(node.ip, cc.getSelfNode().serverport);
			socket.setSoTimeout(60000);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			//oos.writeInt(message.length);
			//oos.write(message);
			oos.writeObject(message);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			//int result = ois.readInt();
			String result = (String)ois.readObject();
			if(!result.equals(RPCConstants.SUCCESS)){
				return false;
			}
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!SendMessage()){
			cc.SendFail(message);
		}
	}
	
	
}
