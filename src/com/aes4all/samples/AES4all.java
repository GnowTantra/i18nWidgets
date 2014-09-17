package com.aes4all.samples;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.util.Arrays;

final class ByteUtils {
	final static int MAX_FILE_SIZE = 20 * 1024 * 1024;

	public static byte[] fileToByteArray(String filename) throws Exception {
		File f = new File(filename);
		byte[] buffer = null;
		int bytesRead = 0;
		if(f.exists()) 
		{
			InputStream in = new FileInputStream(f);
			buffer = new byte[MAX_FILE_SIZE];

			bytesRead = in.read(buffer);
			if (bytesRead == -1 || bytesRead > MAX_FILE_SIZE) {
				throw new Exception("File too large, currently limited to 5 megs");
			}
		}
	
		return Arrays.copyOf(buffer, bytesRead);
	}

	public static void byteArrayToFile(byte[] content, String filename)
			throws IOException {
		
		System.out.println("Name:" + filename + " and size: " + content.length);
		File file = new File(filename);
		OutputStream out = new FileOutputStream(file);
		out.write(content);
		out.close();
	}
}
public class AES4all {
	
    public static Cipher getAESCBCEncryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return cipher;
    }
    
    public static Cipher getAESCBCDecryptor(byte[] keyBytes, byte[] IVBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IVBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher;
    } 

    public static Cipher getAESECBEncryptor(byte[] keyBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher;
    }
    
    public static Cipher getAESECBDecryptor(byte[] keyBytes, String padding) throws Exception{
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/"+padding);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher;
    }
    
    public static byte[] encrypt(Cipher cipher, byte[] dataBytes) throws Exception{
        ByteArrayInputStream bIn = new ByteArrayInputStream(dataBytes);
        CipherInputStream cIn = new CipherInputStream(bIn, cipher);
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int ch;
        while ((ch = cIn.read()) >= 0) {
          bOut.write(ch);
        }
        return bOut.toByteArray();
    } 

    public static byte[] decrypt(Cipher cipher, byte[] dataBytes) throws Exception{
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        CipherOutputStream cOut = new CipherOutputStream(bOut, cipher);
        cOut.write(dataBytes);
        cOut.close();
        return bOut.toByteArray();    
    } 
    /**
     * @param args
     */
    
    public static byte[] demo1encrypt(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] messageBytes) throws Exception {
        Cipher cipher = getAESCBCEncryptor(keyBytes, ivBytes, sPadding); 
        return encrypt(cipher, messageBytes);
    }

    public static byte[] demo1decrypt(byte[] keyBytes, byte[] ivBytes, String sPadding, byte[] encryptedMessageBytes) throws Exception {
        Cipher decipher = getAESCBCDecryptor(keyBytes, ivBytes, sPadding);
        return decrypt(decipher, encryptedMessageBytes);
    }
    
    public boolean encryptFile (String PlainFile, String TargetFile)
    {
    	byte[] demoMesageBytes;
    	try{
    	demoMesageBytes = ByteUtils.fileToByteArray(PlainFile);}
    	catch (Exception ex){
    		return false;
    	}
    	
    	String source = "5f983cdedc76443ccc5a9d2f38b3377c";

        byte[] demoKeyBytes = null;
		try {
			demoKeyBytes = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        //shared secret
//        byte[] demoKeyBytes = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
//                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        
        // Initialization Vector - usually a random data, stored along with the shared secret,
        // or transmitted along with a message.
        // Not all the ciphers require IV - we use IV in this particular sample
        byte[] demoIVBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                                        0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        
        String sPadding = "PKCS5Padding"; //"ISO10126Padding", "PKCS5Padding"
try
{
        byte[] demo1EncryptedBytes = demo1encrypt(demoKeyBytes, demoIVBytes, sPadding, demoMesageBytes);
        ByteUtils.byteArrayToFile(demo1EncryptedBytes, TargetFile);
    	return true;
}
catch (Exception ex)
{
	return false;
	}
       
    
    }
    
    public boolean decryptFile (String EncryptedFile, String TargetFile)
    {
    	
    	byte[] demoMesageBytes;
    	try{
    	demoMesageBytes = ByteUtils.fileToByteArray(EncryptedFile);}
    	catch (Exception ex){
    		return false;
    	}
//        //shared secret
//        byte[] demoKeyBytes = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
//                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    	String source = "5f983cdedc76443ccc5a9d2f38b3377c";

        byte[] demoKeyBytes = null;
		try {
			demoKeyBytes = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        // Initialization Vector - usually a random data, stored along with the shared secret,
        // or transmitted along with a message.
        // Not all the ciphers require IV - we use IV in this particular sample
        byte[] demoIVBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                                        0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        
        String sPadding = "PKCS5Padding"; //"ISO10126Padding", "PKCS5Padding"
try
{
	 byte[] demo1DecryptedBytes = demo1decrypt(demoKeyBytes, demoIVBytes, sPadding, demoMesageBytes);
     ByteUtils.byteArrayToFile(demo1DecryptedBytes, TargetFile);
    	return true;
}
catch (Exception ex)
{
	return false;
	}
    }
    
    public void main(String[] args) throws Exception {
    	
//    	args= new String[4];
//    	args[0]= "popthesound.swf";
//    	args[1]= "popthesoundEncrypt.swf";
//    	args[2]= "popthesoundDecrypt.swf";
    	
    	
        byte[] demoMesageBytes= ByteUtils.fileToByteArray(args[0]);
        //shared secret
//        byte[] demoKeyBytes = new byte[] {  0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
//                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
//        
        
        String source = "5f983cdedc76443ccc5a9d2f38b3377c";

        byte[] demoKeyBytes = null;
		try {
			demoKeyBytes = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Initialization Vector - usually a random data, stored along with the shared secret,
        // or transmitted along with a message.
        // Not all the ciphers require IV - we use IV in this particular sample
        byte[] demoIVBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                                        0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
        
        String sPadding = "PKCS5Padding"; //"ISO10126Padding", "PKCS5Padding"

//        byte[] demo1EncryptedBytes = demo1encrypt(demoKeyBytes, demoIVBytes, sPadding, demoMesageBytes);
//        ByteUtils.byteArrayToFile(demo1EncryptedBytes, args[1]);
        
        byte[] demo1DecryptedBytes = demo1decrypt(demoKeyBytes, demoIVBytes, sPadding, demoMesageBytes);
        ByteUtils.byteArrayToFile(demo1DecryptedBytes, args[2]);
    }

}
