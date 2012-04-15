package Security;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import BasicTypes.Node;

public class P2Psecurity {
	
	
	RSAencoding rsa;
	
	public P2Psecurity() {
		if(rsa == null){
			rsa = new RSAencoding();
		}
		// TODO Auto-generated constructor stub
	}
	
	public byte[] encrypt(Object data, PublicKey pk){
		try {
			return rsa.encrypt(data, pk);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Object decrypt(byte[] data, PrivateKey pk){
		try {
			return rsa.decrypt(data, pk);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public OnionPackage CreateOnionPackage(Node firstDest, Object data){
		if(firstDest == null){
			return null;
		}
		OnionPackage OP = new OnionPackage();  
		if(data == null){
			OP.data = null;
		}else{
			OP.data = encrypt(data,firstDest.pk);
		}
		OP.nextDest = firstDest;
		return OP;
	}
	
	public OnionPackage AddNodeToPackage(Node nextNode, OnionPackage OP){
		if(OP == null){
			return null;
		}
		OnionPackage NewOP = new OnionPackage();
		NewOP.data = encrypt(OP, nextNode.pk);
		NewOP.nextDest = nextNode;
		return NewOP;
	}
	
}
