package Utility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
		int port_i = getter.getPort();
		try {
			getter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String port = Integer.toString(port_i);
		return port;
	}
}
