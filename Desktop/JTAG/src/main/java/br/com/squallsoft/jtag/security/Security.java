/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.security;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author aliri
 */
public class Security {

    private final String appName;
    FileLock lock;
    FileChannel channel;
    static String IV = "AAAAAAAAAAAAAAAA";

    public Security(String appName) {
        this.appName = appName;
    }
 //https://nullbeans.com/how-to-encrypt-decrypt-files-byte-arrays-in-java-using-aes-gcm/
    public static byte[] encrypt(byte[] array, String chave) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(array);
    }

    public static byte[] decrypt(byte[] array, String chave) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");        
        SecretKeySpec key = new SecretKeySpec(chave.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        
        return decripta.doFinal(array);
    }

    public static byte[] compressByteArray(byte[] bytes) throws UnsupportedEncodingException, IOException {

        ByteArrayOutputStream dst = new ByteArrayOutputStream();
        dst.reset();
        GZIPOutputStream gzisOS = new GZIPOutputStream(dst);
        gzisOS.write(bytes);

        gzisOS.close();
        return dst.toByteArray();
    }

    public static byte[] decompressByteArray(byte[] bytes) throws UnsupportedEncodingException, IOException, ClassNotFoundException {
        
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream dst = new ByteArrayOutputStream();
        dst.reset();
        
        byte[] buf = new byte[1024 * 100];
        int len;
        while((len = gis.read(buf)) != -1){
            dst.write(buf, 0, len);
        }
        return dst.toByteArray();
    }

    public boolean isAppActive() throws Exception {
        File file = new File(System.getProperty("user.home"), appName + ".tmp");
        channel = new RandomAccessFile(file, "rw").getChannel();

        lock = channel.tryLock();
        if (lock == null) {
            lock.release();
            channel.close();
            return true;
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    lock.release();
                    channel.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return false;
    }

    public static String getMotherboardSN() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    public static String getProcessadorID() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs
                    = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from win32_processor\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.processorID \n"
                    + "    exit for  ' do the first cpu only! \n"
                    + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    public static String getHardDiskSN(String drive) {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);

            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
                    + "Set colDrives = objFSO.Drives\n"
                    + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
                    + "Wscript.Echo objDrive.SerialNumber";  // see note
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    public static String getMacAddress() throws SocketException{
        final byte[] address = NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress();
        return Arrays.toString(address);
    }
}
