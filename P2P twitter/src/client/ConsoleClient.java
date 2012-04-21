package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConsoleClient {
	
	private BufferedReader bReader;
	
	public ConsoleClient(){
		InputStreamReader stdin = new  InputStreamReader(System.in);
		bReader = new BufferedReader(stdin);
	}
	  
	
	public static void main(){
		ConsoleClient client = new ConsoleClient();
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
		
		
	}
	
	private void register(){
		
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
	
}
