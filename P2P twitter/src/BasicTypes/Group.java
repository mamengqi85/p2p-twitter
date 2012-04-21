package BasicTypes;

import java.util.ArrayList;

import Server.ServerConfig;

public class Group {
    ArrayList<Integer> memberlist;
	String groupName;
    int groupID;
	
	public Group(String name){
		// uid = new UID();
		ServerConfig sc = ServerConfig.getInstance();
		groupID = sc.getGROUPID();
		groupName = name;
		memberlist = new ArrayList<Integer>();
	}
	
	public boolean AddUser(int UserID){
		if(memberlist.contains(UserID)){
			return false;
		}else{
			memberlist.add(UserID);
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
