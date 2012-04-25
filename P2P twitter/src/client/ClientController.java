package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

import BasicTypes.Node;
import BasicTypes.User;
import BasicTypes.UserInfo;
import DHT.ChordDHT;
import DHT.StringKey;
import RPC.RPCConstants;
import RPC.RequestMessage;
import RPC.ResponseMessage;
import Utility.Utility;

import client.ClientConstants.COMMAND;
import de.uniba.wiai.lspi.chord.service.Chord;

public class ClientController {
	boolean CommmandClient = true;
	private BufferedReader bReader;
	boolean stop = false;
	ConsoleView cV;
	private String ip;
	private String port;
	private Chord chord;
	private ChordDHT dht;
	
	public boolean isDHTempty(){
		return dht == null;
	}
	
	public static void main(String args[]){
		ClientController cc = new ClientController();
	}
	
	
	public ClientController(){
		if(CommmandClient){
			cV = new ConsoleView(this);
			Thread t = new Thread(cV);
			t.start();
		}
		ip = Utility.getIP();
		port = Utility.getPort();
	}
	
	private void sendRequest(RequestMessage rm){
		RequestHandler rh = new RequestHandler(rm, this);
		Thread t = new Thread(rh);
		t.start();
	}
	
	public void login(String username, String password){
		//Put("Login seccuss!");
		User user = new User(username,password,new Node());
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.LOGIN;
		reqM.parm = user.getString();
		sendRequest(reqM);
	}
	
	public boolean PostMessage(String key, String value) {
		StringKey myKey = new StringKey(key);
		if (isDHTempty()) {
			System.out.println("Haven't login.");
			return false;
		}
		dht.insertKey(chord, myKey, value);
		return true;
	}
	
	public void register(String username, String password){
		UserInfo userinfo = new UserInfo(username,password);
		RequestMessage rm = new RequestMessage();
		rm.opeID = RPCConstants.REGISTER;
		rm.parm = userinfo.getString();
		sendRequest(rm);
	}
	
	public boolean remove(String key, String value) {
		
		if (isDHTempty()) {
			System.out.println("Haven't login.");
			return false;
		}
		StringKey myKey = new StringKey(key);
		dht.removeKey(chord, myKey, value);
		return true;
	}
	
	
	public void Put(String str){
		System.out.println( str);
	}
	
	private void retrieve() {
		/*
		if (dht == null) {
			System.out.println("Haven't login.");
			return;
		}
		Put("Please input the key to retrieve");
		String key = Gets();
		StringKey myKey = new StringKey(key);
		dht.retrieveKey(chord, myKey);*/
	}
	
	
	public ResponseMessage callBack(ResponseMessage rm){
		System.out.println("recieved response: " + rm.opeID);
		System.out.println(rm.result);
		if(rm.opeID.equals(RPCConstants.REGISTER)){
			
		} else if (rm.opeID.equals(RPCConstants.LOGIN)){
			//parse result
			StringTokenizer st = new StringTokenizer(rm.result,"~");
			if (Utility.StrToBool(st.nextToken())) {
				String nextAction = st.nextToken();
				boolean isExisted = false;
				if (nextAction == RPCConstants.LOGIN)
					isExisted = true;
				dht = new ChordDHT();
				if (isExisted) {
					String nodeStr = st.nextToken();
					Node node = new Node(nodeStr);
					String destIp = node.ip.getHostAddress();
					String destPort = Integer.toString(node.port);
					chord = dht.join(destIp, destPort, ip, port);
					RequestMessage rqsM = new RequestMessage();
					rqsM.callID = rm.callID;
					rqsM.opeID = RPCConstants.JOIN;
				} else {
					chord = dht.create(ip, port);
					RequestMessage rqsM = new RequestMessage();
					rqsM.callID = rm.callID;
					rqsM.opeID = RPCConstants.CREATE;
				}
			}
		} else if (rm.opeID.equals(RPCConstants.CREATE)){
			
		} else if (rm.opeID.equals(RPCConstants.JOIN)){
			
		} else if (rm.opeID.equals(RPCConstants.RETRIEVE)){
			
		} else {
		
		}
		return null;
	}
	
}
