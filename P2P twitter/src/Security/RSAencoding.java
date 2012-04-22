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

public class RSAencoding {

	static Cipher cipher;
	
	 public RSAencoding() {
		Security.addProvider(new BouncyCastleProvider());
		try {
			if(cipher == null){
				cipher = Cipher.getInstance("RSA");
			}
				
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public byte[] encrypt(Object data, PublicKey pk) throws Exception{
		this.cipher.init(Cipher.ENCRYPT_MODE, pk);
		//byte[] bytes = plaintext.getBytes("UTF-8");

		// Create stream
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream sessionOos = new ObjectOutputStream(baos);
	    sessionOos.writeObject(data);
	    sessionOos.close();
		
		byte[] encrypted = blockCipher(baos.toByteArray(),Cipher.ENCRYPT_MODE);

		//char[] encryptedTranspherable = Hex.encodeHex(encrypted);
		return encrypted;
	}
	
	public Object decrypt(byte[] encrypted, PrivateKey pk) throws Exception{
		this.cipher.init(Cipher.DECRYPT_MODE, pk);
		//byte[] bts = Hex.decodeHex(encrypted.toCharArray());

		byte[] decrypted = blockCipher(encrypted,Cipher.DECRYPT_MODE);
		ByteArrayInputStream bais = new ByteArrayInputStream(decrypted);
	    ObjectInputStream sessionObjectInput = new ObjectInputStream(bais);
	    Object obj = sessionObjectInput.readObject();
		return obj;
	}
	
	
	private byte[] blockCipher(byte[] bytes, int mode) throws IllegalBlockSizeException, BadPaddingException{
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
		byte[] toReturn = new byte[0];
		// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE)? 100 : 128;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0)){
				//execute the operation
				scrambled = cipher.doFinal(buffer);
				// add the result to our total result.
				toReturn = append(toReturn,scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the bytes array we shorten it.
				if (i + length > bytes.length) {
					 newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i%length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		scrambled = cipher.doFinal(buffer);

		// final step before we can return the modified data.
		toReturn = append(toReturn,scrambled);

		return toReturn;
	}
	
	
	private byte[] append(byte[] prefix, byte[] suffix){
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i=0; i< prefix.length; i++){
			toReturn[i] = prefix[i];
		}
		for (int i=0; i< suffix.length; i++){
			toReturn[i+prefix.length] = suffix[i];
		}
		return toReturn;
	}
}
