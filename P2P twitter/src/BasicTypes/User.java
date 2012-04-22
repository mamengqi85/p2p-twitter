package BasicTypes;

import java.util.StringTokenizer;

public class User {
	String UserID;
	String password;
	Node node;
	
	public User(){
		UserID = "INITIAL";
		password = "INITIAL";
	}
	
	public User(String uid, String pwd, Node node){
		this.UserID = uid;
		this.password = pwd;
		this.node = node;
	}
	
	public String getString(){
		return UserID + "#" + password + "#"; 
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public User(String str){
		StringTokenizer st = new StringTokenizer("#");
		UserID = st.nextToken();
		password = st.nextToken();
	}
}

