package Security;

import java.io.Serializable;

import BasicTypes.Node;

public class OnionPackage implements Serializable{
	public byte[] data;
	public Node nextDest;
}
