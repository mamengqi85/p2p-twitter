package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import BasicTypes.User;
import RPC.RPCConstants;
import RPC.RequestMessage;
import RPC.ResponseMessage;

import client.ClientConstants.COMMAND;

import sun.util.logging.resources.logging;

public class ConsoleClient implements Runnable{
	  private BufferedReader bReader;
	  private boolean stop = false;
	  
	
	public ConsoleClient(){
		  InputStreamReader stdin = new  InputStreamReader(System.in);
		   bReader = new BufferedReader(stdin);
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
		
		return true;
	}
	
	public ResponseMessage callBack(ResponseMessage rm){
		System.out.println("recieved response: " + rm.opeID);
		return null;
	}
	
	
	private void sendRequest(RequestMessage rm){
		RequestHandler rh = new RequestHandler(rm, this);
		Thread t = new Thread(rh);
		t.run();
	}
	
	
	private void register(){
		Put("Please input the user name");
		String username = Gets();
		Put("Please unput the password");
		String password = Gets();
		User user = new User(username,password,null);
		RequestMessage rm = new RequestMessage();
		rm.opeID = RPCConstants.REGISTER;
		rm.parm = user.getString();
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
