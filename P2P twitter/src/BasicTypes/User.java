package BasicTypes;

public class User {
	String UserID;
	String password;
	Node node;
	
	public User(String uid, String pwd, Node node){
		this.UserID = uid;
		this.password = pwd;
		this.node = node;
	}
	
	public String getString(){
		return UserID + "#" + password + "#"; 
	}
	
	
}
