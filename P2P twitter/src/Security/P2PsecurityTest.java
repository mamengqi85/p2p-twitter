package Security;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

import BasicTypes.Node;

public class P2PsecurityTest {

	@Test
	public void testEncrypt() {
		PrivateKey privateKey = SerilizeKey.ReadPrivateKey("cs5300cornell");
		PublicKey publicKey = SerilizeKey.ReadPublicKey();
		
		String test = "This is a example This is a example This is a example This is a example This is a example This is a example This is a example This is a example This is a example This is a example v v This is a exampleThis is a exampleThis is a examplevThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a exampleThis is a example";
		P2Psecurity p2p = new P2Psecurity();
		byte[] data = p2p.encrypt(test,publicKey);
		String temp;
		try {
			temp = (String)p2p.decrypt(data, privateKey);
			assertEquals(temp, test);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		Node node = new Node();
		try {
			node.ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		node.pk = publicKey;
		
		OnionPackage op = p2p.CreateOnionPackage(node, test);
		op = p2p.AddNodeToPackage(node, op);
		byte[] sendDate = p2p.encrypt(op, node.pk);
		
		OnionPackage dop;
		try {
			dop = (OnionPackage)p2p.decrypt(sendDate,privateKey);
			dop = (OnionPackage)p2p.decrypt(dop.data,privateKey);
			temp = (String)p2p.decrypt(dop.data, privateKey);
			assertEquals(temp, test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
