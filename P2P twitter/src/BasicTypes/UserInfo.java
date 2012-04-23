package BasicTypes;

import java.io.Serializable;
import java.util.StringTokenizer;

public class UserInfo implements Serializable{
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
		StringTokenizer st = new StringTokenizer(str,"#");
		UserID = st.nextToken();
		password = st.nextToken();
	}
}
