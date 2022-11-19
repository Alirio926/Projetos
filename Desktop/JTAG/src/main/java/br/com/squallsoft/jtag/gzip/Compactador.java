/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.gzip;

import com.squallsoft.api.dominios.FirmwareEntity;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.springframework.util.SerializationUtils;
/**
 *
 * @author alirio.filho
 */
public class Compactador {    
    
    public static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            byte data[] = null;
            String gzipF = "c:/teste/file.pdf.gz";
            FileInputStream fis = new FileInputStream(gzipF);
            GZIPInputStream gis = new GZIPInputStream(fis);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                bos.write(buffer, 0, len);
            }             
            FirmwareEntity entity = (FirmwareEntity) SerializationUtils.deserialize(bos.toByteArray());
            bos.close();
            gis.close();
            
            System.out.println("Nome: "+entity.getName());
            
            
            //////////////////////////////////
            /*
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void compressGzipFile(String file, String gzipFile) {
        try {
            FirmwareEntity entity = new FirmwareEntity();
            entity.setName("TxROM");
            entity.setVersion("1.0");
            entity.setDescription("Simple Mapper para jogos como Blah");
            entity.setDataactivate("01/01/2021");
            entity.setXsvf_file(new byte[8196]);
            
            String gzipFileS = "c:/teste/file.pdf.gz";
            FileOutputStream fos = new FileOutputStream(gzipFileS);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            ByteArrayInputStream bis = new ByteArrayInputStream(SerializationUtils.serialize(entity));
            
            byte[] buffer = new byte[1024];
            int len;
            while((len=bis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            gzipOS.close();
            fos.close();
            bis.close();
            /////////////////////////////
            /*
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
