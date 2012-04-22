package BasicTypes;

import java.util.StringTokenizer;


public class User {
	UserInfo basicInfo;
	Node node;
	
	public User(){
		basicInfo = new UserInfo();
		node = new Node();
	}
	
	public User(String uid, String pwd, Node node){
		basicInfo.UserID = uid;
		basicInfo.password = pwd;
		this.node = node;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void SetNode(Node node){
		this.node = node;
	}
	

}

