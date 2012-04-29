package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.plaf.SliderUI;

import BasicTypes.Group;
import BasicTypes.Node;
import BasicTypes.User;
import BasicTypes.UserInfo;
import DHT.ChordDHT;
import DHT.StringKey;
import RPC.RPCConstants;
import RPC.RequestMessage;
import RPC.ResponseMessage;
import Security.OnionPackage;
import Security.P2Psecurity;
import Security.SerilizeKey;
import Utility.Utility;

import client.ClientConstants.COMMAND;
import de.uniba.wiai.lspi.chord.service.Chord;

public class ClientController {
	boolean CommmandClient = false;
	private BufferedReader bReader;
	boolean stop = false;
	ConsoleView cV;
	GUIView gv;
	private Node selfNode;
	public Node getSelfNode() {
		return selfNode;
	}

	public void setSelfNode(Node selfNode) {
		this.selfNode = selfNode;
	}
	private String ip;
	//private String port;
	private Chord chord;
	private ChordDHT dht;
	private String userID;
	private ArrayList<Node> nodes;
	private static int NODESIZE = 2;
	private ClientReciver cr;
	
	
	
	//private method
	private void sendRequest(RequestMessage rm){
		RequestHandler rh = new RequestHandler(rm, this);
		Thread t = new Thread(rh);
		t.start();
	}
	
	private void sendToOther(Node node, byte[] sendData){
		ClientSender cs = new ClientSender(node, sendData, this);
		Thread t = new Thread(cs);
		t.start();
	}

	
	private void AddinNodeList(Node node){
		if(nodes == null){
			nodes = new ArrayList<Node>();
		}
		if(node != null){
			nodes.add(node);
		}
	}
	
	private void RequestNode(){
		for (int i = 0; i < NODESIZE; i++) {
			RequestMessage reqM = new RequestMessage();
			reqM.opeID = RPCConstants.GETNODE;
			reqM.parm = "";
			sendRequest(reqM);
		}
	}
	
	
	private void sendRequestWithMessage(RequestMessage rm, String mes){
		RequestHandler rh = new RequestHandler(rm, this);
		rh.message = mes;
		Thread t = new Thread(rh);
		t.start();
	}
	
	
	
	
	private void postMessageOnDHT(String members, String message){
		ArrayList<String> memberArray = Group.getMemberList(members);
		for (int i = 0; i < memberArray.size(); i++) {
			PostMessage(memberArray.get(i), message);
		}
	}
	
	//public method
	
	public void recieveFromOtherUser(OnionPackage opackage){
		if(opackage.nextDest.dest){
			P2Psecurity p2p = new P2Psecurity();
			PrivateKey privateKey = SerilizeKey.ReadPrivateKey("cs5300cornell");
			String temp;
			try {
				temp = (String)p2p.decrypt(opackage.data, privateKey);
				StringTokenizer st = new StringTokenizer(temp,"*");
				String members = st.nextToken();
				String message = st.nextToken();
				postMessageOnDHT(members, message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			sendToOther(opackage.nextDest, opackage.data);
		}
	}
	
	public void SendFail(byte[] message){
		System.out.println("send fail");
	};
	
	
	
	public void preparePostMessage(String groupname,String message){
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.GETGROUPLIST;
		reqM.parm = groupname;
		sendRequestWithMessage(reqM, message);
	} 
	
	
	public boolean isDHTempty(){
		return dht == null;
	}
	
	public static void main(String args[]){
		ClientController cc = new ClientController();
	}
	
	public ClientController(){
		if(CommmandClient){
			cV = new ConsoleView(this);
			Thread t = new Thread(cV);
			t.start();
		} else {
			gv = new GUIView(this);
			Thread t = new Thread(gv);
			t.start();
		}
		selfNode = new Node();
		cr = new ClientReciver(this,selfNode.serverport);
		Thread t = new Thread(cr);
		t.start();
		ip = Utility.getIP();
		//port = Utility.getPort();
	}
	
	
	
	
	public void login(String username, String password){
		//Node node = new Node();
		User user = new User(username,password,selfNode);
		System.out.println(selfNode.port+ "~!~!~!~!~!~!!");
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.LOGIN;
		reqM.parm = user.getString();
		sendRequest(reqM);
	}
	
	public boolean PostMessage(String key, String value) {
		StringKey myKey = new StringKey(key);
		if (isDHTempty()) {
			System.out.println("Haven't login.");
			return false;
		}
		dht.insertKey(chord, myKey, value);
		return true;
	}
	
	public void register(String username, String password){
		UserInfo userinfo = new UserInfo(username,password);
		RequestMessage rm = new RequestMessage();
		rm.opeID = RPCConstants.REGISTER;
		rm.parm = userinfo.getString();
		sendRequest(rm);
	}
	
	public boolean remove(String key, String value) {
		
		if (isDHTempty()) {
			System.out.println("Haven't login.");
			return false;
		}
		StringKey myKey = new StringKey(key);
		dht.removeKey(chord, myKey, value);
		return true;
	}
	
	
	public void Put(String str){
		System.out.println( str);
	}
	
	public boolean retrieve() {
		if (isDHTempty()) {
			System.out.println("Haven't login.");
			return false;
		}
		StringKey myKey = new StringKey(userID);
		//dht.retrieveKey(chord, myKey);
		System.out.println(dht.retrieveKey(chord, myKey));
		
		return true;
	}
	
	public boolean createGroup(String groupName){
		if(isDHTempty()){
			return false;
		}
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.CREATEGROUP;
		reqM.parm = groupName + "~" + userID + "~";
		sendRequest(reqM);
		return true;
	}
	
	public boolean joinGroup(String groupName){
		if(isDHTempty()){
			return false;
		}
		RequestMessage reqM = new RequestMessage();
		reqM.opeID = RPCConstants.JOINGROUP;
		reqM.parm = groupName + "~" + userID + "~";
		sendRequest(reqM);
		return true;
	}
	
	
	public ResponseMessage callBack(ResponseMessage rm, String message){
		System.out.println("recieved response: " + rm.opeID);
		System.out.println(rm.result);
		if(rm.opeID.equals(RPCConstants.REGISTER)){
			if (!CommmandClient) {
				gv.showMessage(rm.result);
			}
		} else if (rm.opeID.equals(RPCConstants.LOGIN)){
			//parse result
			StringTokenizer st = new StringTokenizer(rm.result,"~");
			userID = st.nextToken();
			String status = st.nextToken();
			if (!CommmandClient) {
				gv.showMessage(status);
			}
			if (Utility.StrToBool(status)) {
				//send the request Node 
				RequestNode();
				String nextAction = st.nextToken();
				boolean isExisted = false;
				if (nextAction.equals(RPCConstants.JOIN))
					isExisted = true;
				dht = new ChordDHT();
				if (isExisted) {
					String nodeStr = st.nextToken();
					Node node = new Node(nodeStr);
					String destIp = node.ip.getHostAddress();
					String destPort = Integer.toString(node.port);
					if (CommmandClient) {
						System.out.println("dip"+destIp);
						System.out.println("dport"+destPort);
						System.out.println("ip"+ip);
						System.out.println("port"+selfNode.port);
					}
					chord = dht.join(destIp, destPort, ip,Integer.toString(selfNode.port));
					//TODO
					//System.out.println("chord4"+ chord);
					RequestMessage rqsM = new RequestMessage();
					rqsM.callID = rm.callID;
					rqsM.opeID = RPCConstants.JOIN;
				} else {
					chord = dht.create(ip, Integer.toString(selfNode.port));
					//TODO
					//System.out.println("chord1"+ chord);
					RequestMessage rqsM = new RequestMessage();
					rqsM.callID = rm.callID;
					rqsM.opeID = RPCConstants.CREATE;
				}

			}
		} else if (rm.opeID.equals(RPCConstants.GETGROUPLIST)){
			String members = rm.result;
			if(nodes.isEmpty()){
				RequestNode();
				return null;
			}
			if(nodes.size() > 0){
				Random random = new Random();
				P2Psecurity p2p = new P2Psecurity();
				Node node = new Node();
				node = nodes.get(random.nextInt(nodes.size()));
				node.dest = true;
				OnionPackage op = p2p.CreateOnionPackage(node, members + "*" + message + "*" );
				int ran = random.nextInt(2)+1;
				for (int i = 0; i < ran; i++) {
					op = p2p.AddNodeToPackage(nodes.get(random.nextInt(nodes.size())), op);
				}
				byte[] sendDate = p2p.encrypt(op, op.nextDest.pk);
				sendToOther(op.nextDest,sendDate);
			}
		} else if (rm.opeID.equals(RPCConstants.GETNODE)){
			if(rm.result != RPCConstants.FAIL){
				Node node = new Node(rm.result);
				AddinNodeList(node);
			}

		} else if (rm.opeID.equals(RPCConstants.RETRIEVE)){
			
		} else {

		}
		return null;
	}
	
}
