package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;

import RPC.RPCConstants;
import Security.OnionPackage;
import Security.P2Psecurity;
import Security.SerilizeKey;

public class reciverHandler implements Runnable{
	Socket socket;
	ClientController cc;
	public reciverHandler(Socket socket,ClientController cc){
		this.socket = socket;
		this.cc = cc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("ready to serve");
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			//int length = ois.readInt();
			byte[] message;
			message =  (byte[])ois.readObject();
			//oos.writeInt(ClientSender.SUCCESS);
			PrivateKey privateKey = SerilizeKey.ReadPrivateKey("cs5300cornell");
			P2Psecurity p2p = new P2Psecurity();
			OnionPackage dop;
			try {
				dop = (OnionPackage)p2p.decrypt(message,privateKey);
			} catch (Exception e) {
				e.printStackTrace();
				oos.writeObject(RPCConstants.FAIL);;
				return;
			}
			//oos.writeInt(ClientSender.SUCCESS);
			oos.writeObject(RPCConstants.SUCCESS);
			cc.recieveFromOtherUser(dop);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
