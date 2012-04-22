package RPC;

import java.io.Serializable;
import java.util.StringTokenizer;

import RPC.RPCConstants.REQUEST;

public class ResponseMessage implements Serializable{
	public String callID;
	public String opeID;
	public String result;
	
	public ResponseMessage(){}
	
	public ResponseMessage(String str){
		StringTokenizer st = new StringTokenizer(str,"@");
		callID = st.nextToken();
		opeID = (st.nextToken());
		result = st.nextToken();
	}
	
	public ResponseMessage(String callID,String opeID,String result){
		this.callID = callID;
		this.opeID = opeID;
		this.result = result;
	}
	
	public String getString(){
		return callID + "@" + opeID + "@" + result + "@";
	}
	
}
