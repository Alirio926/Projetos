/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

import br.com.squallsoft.jtag.security.Security;
import com.squallsoft.api.dominios.LogEntity;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author alirio.filho
 */
public class LocalLog {
    
    private static Logger logger = Logger.getLogger(LocalLog.class);
    private final String pathFile = System.getProperty("user.dir")+File.separator;
    private StringBuilder sb;   
    
    
    private File openFile() throws IOException{
        Month month = LocalDateTime.now().getMonth();
        sb = new StringBuilder();
        sb.append(pathFile);
        sb.append("pedidos_");
        sb.append(month.toString().toLowerCase());
        sb.append(".log");
        
        //System.out.println("Path FileLog: " + sb);
        File f = new File(sb.toString());
        
        if(!f.isFile()){
            f.createNewFile();
            byte[] compressObject = Security.compressByteArray(SerializationUtils.serialize(new ArrayList<LogEntity>()));
            OutputStream out = new FileOutputStream(f);
            out.write(compressObject);
            out.flush();
            out.close();
        }
        return f;
    }
    
    public ArrayList<LogEntity> loadListLog() throws IOException, ClassNotFoundException{
        ArrayList<LogEntity> lstLogs = null; 
        InputStream read = null;
        byte[] compressedByteArray = null;
        int size = 0, i=0;
        File f = openFile();
        try{
            read = new FileInputStream(f);
            size = (int) f.length();
            compressedByteArray = new byte[size];
            
            read.read(compressedByteArray, 0, size);

            lstLogs = (ArrayList) SerializationUtils.deserialize(Security.decompressByteArray(compressedByteArray));
        }catch(EOFException e){
            logger.error("Erro carregando arquivo de log: " + e.getLocalizedMessage());
        }         
        read.close();
        
        return lstLogs;
    }
    
    public void saveListLog(ArrayList<LogEntity> lstLogs) throws IOException{
        OutputStream out = new FileOutputStream(openFile()); 
        byte[] compressObject = Security.compressByteArray(SerializationUtils.serialize(lstLogs));
        out.write(compressObject);
        out.flush();
        out.close();
    }
    
    public void adiciona(LogEntity log) throws IOException, ClassNotFoundException{
        ArrayList<LogEntity> lstLogs = loadListLog();     
        lstLogs.add(log);        
        OutputStream out = new FileOutputStream(openFile()); 
        
        byte[] compressObject = Security.compressByteArray(SerializationUtils.serialize(lstLogs));
        out.write(compressObject);
        
        out.write(compressObject);
        out.flush();
        out.close();
    }
    
    public void remove(LogEntity log) throws IOException, ClassNotFoundException{
        ArrayList<LogEntity> lstLogs = loadListLog();     
        for(LogEntity e : lstLogs){
            if(e.getPedidoId() == log.getPedidoId()){
                lstLogs.remove(e);
            }
        }
        OutputStream out = new FileOutputStream(openFile()); 
        
        byte[] compressObject = Security.compressByteArray(SerializationUtils.serialize(lstLogs));
        out.write(compressObject);
        
        out.write(compressObject);
        out.flush();
        out.close();
    }
}
