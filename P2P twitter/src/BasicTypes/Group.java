package BasicTypes;

import java.util.ArrayList;

import Server.ServerConfig;

public class Group {
    ArrayList<String> memberlist;
	String groupName;
	
	public Group(String name){
		groupName = name;
		memberlist = new ArrayList<String>();
	}
	
	public boolean AddUser(String UserID){
		if(memberlist.contains(UserID)){
			return false;
		}else{
			memberlist.add(UserID);
			System.out.println(memberlist);
			return true;
		}
	}
	
	public boolean DeleteUser(int UserID){
		if(memberlist.contains(UserID)){
			return false;
		}else{
			memberlist.remove(UserID);
			return true;
		}
	}
	
}
