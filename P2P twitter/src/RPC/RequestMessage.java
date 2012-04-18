package RPC;

import java.io.Serializable;
import java.rmi.server.UID;
import java.util.StringTokenizer;

import RPC.RPCConstants.REQUEST;


public class RequestMessage implements Serializable{
	
	public String callID;
	public String opeID;
	public String parm;
	//public CookieType cookie;
	//public long discardTime;
	
	
	public RequestMessage(){
		UID uid= new UID();
		callID = uid.toString();
		parm = "";
		opeID = "";
	}
	
	public RequestMessage(String opeID, String str){
		this.opeID = opeID;
		this.parm = str;
		UID uid = new UID();
		callID = uid.toString();
	}
	
	
	public RequestMessage(String str){
		StringTokenizer st = new StringTokenizer(str,"$");
		callID = st.nextToken();
		opeID = st.nextToken();
		//ss.message = st.nextToken();
		this.parm = st.nextToken();
	}
	
	public String getString(){
		return callID + "$" + opeID + "$" + parm + "$";
	}
	
}
