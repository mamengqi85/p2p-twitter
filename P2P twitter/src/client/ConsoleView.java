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

public class ConsoleView implements Runnable{
	  private BufferedReader bReader;
	  private boolean stop = false;
	  private ClientController cc;
	  
	  
	  
	public ConsoleView(ClientController cc){
		InputStreamReader stdin = new  InputStreamReader(System.in);
		bReader = new BufferedReader(stdin);
		this.cc = cc;
	}
	
	
	public void Show(String str){
		Put(str);
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
		cc.login(username, password);
		//TODO: what if fail
	}
	
	private void add() {
		Put("Please input the key to add");
		String key = Gets();
		Put("Please input the value to add associate with this key");
		String value = Gets();
		cc.PostMessage(key, value);
		//TODO: what if fail
	}

	private void remove() {
		Put("Please input the key to remove");
		String key = Gets();
		Put("Please input the value to remove associate with this key");
		String value = Gets();
		cc.remove(key, value);
		//TODO: what if fail
	}
	
	
	private void register(){
		Put("Please input the user name");
		String username = Gets();
		Put("Please unput the password");
		String password = Gets();
		cc.register(username, password);
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
				//retrieve();
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