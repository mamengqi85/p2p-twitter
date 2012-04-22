package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import BasicTypes.Node;
import BasicTypes.User;
import BasicTypes.UserInfo;
import RPC.RPCConstants;
import RPC.RequestMessage;
import RPC.ResponseMessage;

public class ServerRequestHandler implements Runnable{
	
	Socket socket;
	ServerTable tables;
	public ServerRequestHandler(Socket socket, ServerTable tables){
		this.socket = socket;
		this.tables = tables;
	}
	
	boolean register(UserInfo user){
		if (tables.addUser(user)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("ready to serve");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			RequestMessage rm =  (RequestMessage)ois.readObject();
			ResponseMessage resM = new ResponseMessage();
			resM.callID = rm.callID;
			resM.opeID = rm.opeID;
			if(rm.opeID.equals(RPCConstants.REGISTER)){
				System.out.println("REGISTER");
				UserInfo userinfo = new UserInfo(rm.parm);
				if(register(userinfo)){
					resM.result = RPCConstants.SUCCESS;
				}else{
					resM.result = RPCConstants.FAIL;
				}
			} else if (rm.opeID.equals(RPCConstants.LOGIN)){
				System.out.println("LOGIN");
			} else if (rm.opeID.equals(RPCConstants.JOIN)){
				System.out.println("JOIN");
				if (tables.availableList.size() == 0) {
					resM.result = "true";
				} else {
					Node bootstrapNode = tables.SelectRandomNode();
					resM.result = "false#" + bootstrapNode.ip + "#" + bootstrapNode.port;
				}
			} else if (rm.opeID.equals(RPCConstants.RETRIEVE)){
				System.out.println("RETRIEVE");
			} else {
				
			}
			oos.writeObject(resM);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
