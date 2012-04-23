package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import BasicTypes.User;
import BasicTypes.UserInfo;
import DHT.ChordDHT;
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
	
	public ConsoleClient(){
		InputStreamReader stdin = new  InputStreamReader(System.in);
		bReader = new BufferedReader(stdin);
		try {
		    InetAddress addr = InetAddress.getLocalHost();
		    byte[] ipAddr = addr.getAddress();
		    ip = ipAddr[0] + "." + ipAddr[1] + "." + ipAddr[2] + "." + ipAddr[3];
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.port = "12345";
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
	
	private boolean login(){
		Put("Please input the user name");
		String username = Gets();
		Put("Please unput the password");
		String password = Gets();
		Put("Login seccuss!");
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.LOGIN;
		return true;
	}
	
	public ResponseMessage callBack(ResponseMessage rm){
		System.out.println("recieved response: " + rm.opeID);
		System.out.println(rm.result);
		if(rm.opeID.equals(RPCConstants.REGISTER)){
			
		} else if (rm.opeID.equals(RPCConstants.LOGIN)){
			
		} else if (rm.opeID.equals(RPCConstants.JOIN)){
			//parse result
			StringTokenizer st = new StringTokenizer(rm.result,"#");
			String isExisted = st.nextToken();
			ChordDHT dht = new ChordDHT();
			if (Utility.StrToBool(isExisted)) {
				chord = dht.create(ip, port);
			} else {
				String destIp = st.nextToken();
				String destPort = st.nextToken();
				chord = dht.join(destIp, destPort, ip, port);
			}
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
				break;
			case REGISTER:
				register();
				break;
			default:
				break;
			}
		}
		// TODO Auto-generated method stub
		
	}
	
}
