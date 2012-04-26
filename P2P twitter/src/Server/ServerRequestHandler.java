package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

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
	
	boolean login(User user){
		if(tables.checkUser(user.getBasicInfo())){
			if(tables.checkAvailUser(user)){
				return true;
			}
		}
		return false;
	}
	
	boolean CreateGroup(String name){
		return tables.AddGroup(name);
	}
	
	boolean JoinGroup(String Groupname, String userID){
		return tables.JoinGroup(Groupname, userID);
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
				User user = new User(rm.parm);
				resM.result = user.getBasicInfo().UserID + "~";
				if (login(user)) {
					resM.result += RPCConstants.SUCCESS;
					if (tables.availableList.size() == 0) {
						resM.result += "~" + RPCConstants.CREATE + "~";
						tables.addAvailUser(user);
					} else {
						Node bootstrapNode = tables.SelectRandomNode();
						resM.result += "~" + RPCConstants.JOIN + "~" + bootstrapNode.getString() + "~";
						tables.addAvailUser(user);
					}
				} else {
					resM.result += RPCConstants.FAIL + "~";
				}
				
			} else if (rm.opeID.equals(RPCConstants.RETRIEVE)){
				System.out.println("RETRIEVE");
			} else if(rm.opeID.equals(RPCConstants.CREATEGROUP)){
				StringTokenizer st = new StringTokenizer(rm.parm,"~");
				String Groupname = st.nextToken();
				String userID = st.nextToken();
				if(CreateGroup(Groupname)){
					JoinGroup(Groupname, userID);
					resM.result = RPCConstants.SUCCESS;
				}else{
					resM.result = RPCConstants.FAIL;
				}
			} else if(rm.opeID.equals(RPCConstants.JOINGROUP)){
				StringTokenizer st = new StringTokenizer(rm.parm,"~");
				String Groupname = st.nextToken();
				String userID = st.nextToken();
				if(JoinGroup(Groupname, userID)){
					resM.result = RPCConstants.SUCCESS;
				}else{
					resM.result = RPCConstants.FAIL;
				}
			}else {
				
			}
			oos.writeObject(resM);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
