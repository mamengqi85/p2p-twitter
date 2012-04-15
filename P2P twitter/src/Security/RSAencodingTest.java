package Security;

import static org.junit.Assert.*;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

public class RSAencodingTest {

	@Test
	public void testEncrypt() throws Exception {
		PrivateKey privateKey = SerilizeKey.ReadPrivateKey("cs5300cornell");
		PublicKey publicKey = SerilizeKey.ReadPublicKey();
		RSAencoding rsa = new RSAencoding();
		String test = "example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!example !!!";
		
		byte[] temp = rsa.encrypt(test, publicKey);
		String result = (String) rsa.decrypt(temp, privateKey);
		
		assertEquals(test, result);
	}

}
