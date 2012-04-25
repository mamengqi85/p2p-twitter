package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

import sun.util.logging.resources.logging;

public class ConsoleClient implements Runnable{
	  private BufferedReader bReader;
	  private boolean stop = false;
	  private String ip;
	  private String port;
	  private Chord chord;
	  private ChordDHT dht;
	
	public ConsoleClient(){
		InputStreamReader stdin = new  InputStreamReader(System.in);
		bReader = new BufferedReader(stdin);
		ip = Utility.getIP();
		port = Utility.getPort();
	}
	
	public static void main(String args[]){
		ConsoleClient client = new ConsoleClient();
		Thread t = new Thread(client);
		t.start();
	}
	
	
	private void Init(){
		Put("===================P2P twitter=====================");
		Put("======================start========================");
		Put("===================================================");
	}
	
	private void login(){
		Put("Please input the user name");
		String username = Gets();
		Put("Please unput the password");
		String password = Gets();
		//Put("Login seccuss!");
		User user = new User(username,password,new Node());
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.LOGIN;
		reqM.parm = user.getString();
		sendRequest(reqM);
	}
	
	private void add() {
		if (dht == null) {
			System.out.println("Haven't login.");
			return;
		}
		Put("Please input the key to add");
		String key = Gets();
		StringKey myKey = new StringKey(key);
		Put("Please input the value to add associate with this key");
		String value = Gets();
		System.out.println("chord2"+ chord);
		dht.insertKey(chord, myKey, value);
	}

	private void remove() {
		if (dht == null) {
			System.out.println("Haven't login.");
			return;
		}
		Put("Please input the key to remove");
		String key = Gets();
		StringKey myKey = new StringKey(key);
		Put("Please input the value to remove associate with this key");
		String value = Gets();
		dht.removeKey(chord, myKey, value);
	}
	
	private void retrieve() {
		if (dht == null) {
			System.out.println("Haven't login.");
			return;
		}
		Put("Please input the key to retrieve");
		String key = Gets();
		StringKey myKey = new StringKey(key);
		dht.retrieveKey(chord, myKey);
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
				if (nextAction.equals(RPCConstants.JOIN))
					isExisted = true;
				dht = new ChordDHT();
				if (isExisted) {
					String nodeStr = st.nextToken();
					Node node = new Node(nodeStr);
					String destIp = node.ip.getHostAddress();
					String destPort = Integer.toString(node.port);
					System.out.println("dip"+destIp);
					System.out.println("dport"+destPort);
					System.out.println("ip"+ip);
					System.out.println("port"+port);
					chord = dht.join(destIp, destPort, ip, port);
					System.out.println("chord4"+ chord);
					RequestMessage rqsM = new RequestMessage();
					rqsM.callID = rm.callID;
					rqsM.opeID = RPCConstants.JOIN;
				} else {
					chord = dht.create(ip, port);
					System.out.println("chord1"+ chord);
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
	
	private void sendRequest(RequestMessage rm){
		RequestHandler rh = new RequestHandler(rm, this);
		Thread t = new Thread(rh);
		t.start();
	}
	
	private void register(){
		Put("Please input the user name");
		String username = Gets();
		Put("Please unput the password");
		String password = Gets();
		UserInfo userinfo = new UserInfo(username,password);
		RequestMessage rm = new RequestMessage();
		rm.opeID = RPCConstants.REGISTER;
		rm.parm = userinfo.getString();
		sendRequest(rm);
	}
	
	private void createGroup(){
		
	}
	
	private void joinGroup(){
		
	}
	

	
	private String Gets(){
		try {
			String friend_id = bReader.readLine();
			return friend_id;
		} catch (IOException e) {
			Put("get string error");
			e.printStackTrace();
			return null;
		}
	}
	
	public void Put(String str){
		System.out.println( str);
	}
	
	
	COMMAND ParseCommand(String str){
		if(str.toLowerCase().equals("l")){
			return COMMAND.LOGIN;
		}else if(str.toLowerCase().equals("rg")){
			return COMMAND.REGISTER;
		}else if(str.toLowerCase().equals("rt")){
			return COMMAND.RETRIEVE;
		} else if (str.toLowerCase().equals("a")) {
			return COMMAND.ADD;
		} else if (str.toLowerCase().equals("rm")) {
			return COMMAND.REMOVE;
		}else{
			return COMMAND.INVALID;
		}
	}
	
	
	@Override
	public void run() {
		Init();
		/*
		while(!login()){
			Put("invalid username or password");
		}*/
		while(!stop){
			Put("input the command");
			String commandString = Gets();
			//ClientConstants.
			switch (ParseCommand(commandString)) {
			case RETRIEVE:
				retrieve();
				break;
			case REGISTER:
				register();
				break;
			case LOGIN:
				login();
				break;
			case ADD:
				add();
				break;
			case REMOVE:
				remove();
				break;
			default:
				break;
			}
		}
		// TODO Auto-generated method stub
		
	}
	
}
