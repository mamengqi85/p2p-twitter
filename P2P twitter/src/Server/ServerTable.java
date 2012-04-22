package Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import BasicTypes.Group;
import BasicTypes.User;
import BasicTypes.Node;
import BasicTypes.UserInfo;

public class ServerTable {
	HashMap<String, UserInfo> userlist;
	HashMap<String, User> availableList;
	HashMap<Integer, Group> GroupIDList;
	
	public boolean addUser(UserInfo user){
		if(!userlist.containsKey(user.UserID)){
			userlist.put(user.UserID, user);
			return true;
		}
		return false;
	}
	
	
	
	
	//availableList should not be empty
	public Node SelectRandomNode() {
		Collection<String> keySet = availableList.keySet();
		ArrayList<String> keyArray = new ArrayList<String>(keySet);
		Random ran = new Random();
		int r = ran.nextInt(keyArray.size());
		String key = keyArray.get(r);
		User user = availableList.get(key);
		
		return user.getNode();
	}
	
	//availableList should not be empty	
	public Node selectFirstNode() {
		Iterator<Entry<String, User>> iter = availableList.entrySet().iterator();
		Entry<String, User> entry = iter.next(); 
		String key = entry.getKey(); 
		User user = availableList.get(key);
		return user.getNode(); 
	}
	
	
	
}
