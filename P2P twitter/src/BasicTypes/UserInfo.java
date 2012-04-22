package BasicTypes;

import java.util.StringTokenizer;

public class UserInfo {
	public String UserID;
	public String password;
	
	public UserInfo(){
		UserID = "INITIAL";
		password = "INITIAL";
	}
	
	public UserInfo(String userid, String password){
		UserID = userid;
		this.password = password;
	}
	
	public String getString(){
		return UserID + "#" + password + "#"; 
	}
	
	public UserInfo(String str){
		StringTokenizer st = new StringTokenizer("#");
		UserID = st.nextToken();
		password = st.nextToken();
	}
}
