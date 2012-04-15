package BasicTypes;

import java.io.Serializable;
import java.net.InetAddress;
import java.security.PublicKey;

public class Node implements Serializable{
	public InetAddress ip;
	public PublicKey pk;
}
