package Security;

import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class SharedKey{
			//TODO: just illustrate the idea
			static SharedKey sKey;
			MyKey mk = new MyKey();
			static final String xform = "PBEWithMD5AndDES";
			private SharedKey(){}
			public static SharedKey getInstance(){
				if (sKey == null) {
					sKey = new SharedKey();
				}
				return sKey;
			}
			
			public SecretKey GenerateSysKey(){
				try {
					// Generate a DES key
				    KeyGenerator keyGen = KeyGenerator.getInstance("DES");
				    SecretKey key = keyGen.generateKey();

				    return key;
				} catch (Exception e) {
					// TODO: handle exception
					return null;
				}

			}
			
			
			
			public  String decrypt(byte[] data, MyKey mk){
				if (data == null) {
					return null;
				}
				byte[] resultArray;
				String result = "";
				
				// Create PBE Cipher
	            Cipher pbeCipher=null;
				try {
					pbeCipher = Cipher.getInstance(xform);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}

	            // Initialize PBE Cipher with key and parameters
	            try {
					pbeCipher.init(Cipher.DECRYPT_MODE, mk.skey, mk.pps);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
				}
				
	            try {
                        //int remainder = data.length % 8;
                        
                        
					resultArray= pbeCipher.doFinal(data);
					/*
					for (int i = 0; i < resultArray.length; i++) {
						result += Integer.toHexString((0x000000ff & resultArray[i]) | 0xffffff00).substring(6);
					}
					*/
					result= new String(resultArray);
					
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
	            return result;
			}
			

			
			public  byte[] encrypt(String data, MyKey mk){
				if (data == null) {
					return null;
				}
				// Create PBE Cipher
	            Cipher pbeCipher=null;
	            byte[] ciphertext = null;
	            
				try {
						pbeCipher = Cipher.getInstance(xform);	
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				}

	            // Initialize PBE Cipher with key and parameters
	            try {
					pbeCipher.init(Cipher.ENCRYPT_MODE, mk.skey, mk.pps);
				} catch (InvalidKeyException e1) {
					e1.printStackTrace();
				} catch (InvalidAlgorithmParameterException e1) {
					e1.printStackTrace();
				}

	            byte[] cleartext;
	            if (data == null) {
	            	  cleartext = null;
				}else {
					 cleartext = data.getBytes();
				}
	            

	            // Encrypt the cleartext
	            try {
					//byte[] ciphertext = null;
					ciphertext=pbeCipher.doFinal(cleartext);
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
				
				return ciphertext;
			}
	
			
		
			
			public  MyKey generateKeyWithPwd(String pwd){
				 
		         SecretKeyFactory keyFac=null;
		         SecretKey pbeKey=null;
		         
		        // byte[] passwordByte=pwd.getBytes();
		         char[] passwordChar =pwd.toCharArray();

		         
	            //convert the password into a SecretKey object, using a PBE key factory.
		        PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordChar);
	            try {
					
						keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
	            try {
					pbeKey = keyFac.generateSecret(pbeKeySpec);
				} catch (InvalidKeySpecException e) {
					e.printStackTrace();
				}
	            
	            
	            mk.skey = pbeKey;

	            byte[] salt = pwd.getBytes();
	            byte[] s = new byte[8];
	            for (int i = 0; i < s.length; i++) {
					s[i] = (byte) i;
				}
	            //int count = salt.length;
	            mk.pps = new PBEParameterSpec(s, 8);
	            

				return mk;
			}
			
			
			
			public SecretKey generateWithInt(int gene){
				try {
					KeyGenerator generator;
					generator = KeyGenerator.getInstance("DES");
					generator.init(gene);
					SecretKey key = generator.generateKey();
					return key;
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}
			
			
}
