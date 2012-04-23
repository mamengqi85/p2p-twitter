package BasicTypes;

import java.util.StringTokenizer;


public class User {
	UserInfo basicInfo;
	Node node;
	
	public UserInfo getBasicInfo() {
		return basicInfo;
	}


	public void setBasicInfo(UserInfo basicInfo) {
		this.basicInfo = basicInfo;
	}


	public Node getNode() {
		return node;
	}


	public void setNode(Node node) {
		this.node = node;
	}


	public User(){
		basicInfo = new UserInfo();
		node = new Node();
	}
	
	
	public User(String uid, String pwd, Node node){
		basicInfo.UserID = uid;
		basicInfo.password = pwd;
		this.node = node;
	}
	
	public String getString(){
		return basicInfo.getString() + "^" + node.getString() + "^";
	}
	
	public User(String str){
		StringTokenizer st = new StringTokenizer(str,"^");
		basicInfo = new UserInfo(st.nextToken());
		node = new Node(st.nextToken());
	}
	
	

}

