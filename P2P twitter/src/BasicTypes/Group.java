package BasicTypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Server.ServerConfig;

public class Group implements Serializable{
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
	
	public String MemberListToString(){
		String result = new String();
		for (int i = 0; i < memberlist.size(); i++) {
			result += memberlist.get(i) + "@";
		}
		return result;
	}
	
	public static ArrayList<String> getMemberList(String str){
		StringTokenizer st = new StringTokenizer(str,"@");
		ArrayList<String> result = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			result.add(st.nextToken());
		}
		return result;
	}
	
	
}
