package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import BasicTypes.User;
import RPC.RPCConstants;
import RPC.RequestMessage;

public class ServerRequestHandler implements Runnable{
	
	
	Socket socket;
	public ServerRequestHandler(Socket socket){
		this.socket = socket;
	}
	
	boolean register(User user){
		
		return false;
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			RequestMessage rm =  (RequestMessage)ois.readObject();
			if(rm.opeID.equals(RPCConstants.REGISTER)){
				
			}else if(rm.opeID.equals(RPCConstants.LOGIN)){
				
			}else if(rm.opeID.equals(RPCConstants.RETRIEVE)){
				
			}else{
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	
	
	
	
	
}
