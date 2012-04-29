package BasicTypes;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.StringTokenizer;

import Security.SerilizeKey;
import Utility.Utility;

public class Node implements Serializable{
	public InetAddress ip;
	public int port;
	public int serverport;
	public PublicKey pk;
	public boolean dest = false;
	
	public Node(){
		try {
			ip = InetAddress.getLocalHost();
			port = Integer.parseInt(Utility.getPort());
			serverport = Integer.parseInt(Utility.getPort());
			pk = SerilizeKey.ReadPublicKey();
			dest = false;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getString(){
		return ip.getHostAddress() + "%" + Integer.toString(port) + "%" + Integer.toString(serverport) + "%";
	}
	
	public Node(String str){
		StringTokenizer st = new StringTokenizer(str,"%");
		try {
			ip = InetAddress.getByName(st.nextToken());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			port = Integer.parseInt(st.nextToken());
			serverport = Integer.parseInt(st.nextToken());
			pk = SerilizeKey.ReadPublicKey();
	}
	
	
}
