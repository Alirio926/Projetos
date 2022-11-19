package com.squallsoft.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Security {

	static String IV = "AAAAAAAAAAAAAAAA";
	
	public static byte[] encrypt(byte[] array, String chave) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
                
        return encripta.doFinal(array);
    }
	
	public static byte[] compressByteArray(byte[] bytes) throws IOException {

        ByteArrayOutputStream dst = new ByteArrayOutputStream();
		dst.reset();
		GZIPOutputStream gzisOS = new GZIPOutputStream(dst);
		gzisOS.write(bytes);
		
		gzisOS.close();
		return dst.toByteArray();
	}
}
