package Utility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import RPC.RPCConstants;

public class Utility {
	public static final boolean StrToBool(String str) {
		boolean ans = false;
		if (str == "1")
			ans = true;
		else if (str == "0")
			ans = false;
		else if (str == "true")
			ans = true;
		else if (str == "false")
			ans = false;
		else if (str.equals(RPCConstants.SUCCESS))
			ans = true;
		else if (str.equals(RPCConstants.FAIL));
		return ans;
	}
	
	public static final String getIP() {
		String ip = null;
		try {
		    ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
	
	public static final String getPort() {
		Socket getter = new Socket();
		try {
			getter.bind(null);
		} catch (IOException e1) {
			System.out.println("Failed to bind a socket.");
			e1.printStackTrace();
		}
		int port_i = getter.getLocalPort();
		try {
			getter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String port = Integer.toString(port_i);
		return port;
	}
}
