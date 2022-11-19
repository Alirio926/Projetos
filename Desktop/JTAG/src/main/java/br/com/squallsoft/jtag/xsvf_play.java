/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag;

import br.com.squallsoft.jtag.adapter.FormUpdate;
import br.com.squallsoft.jtag.adapter.SerialHelper;
import br.com.squallsoft.jtag.adapter.Status;
import br.com.squallsoft.jtag.adapter.Timer;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alirio.filho
 */
public class xsvf_play {
    
    static SerialHelper serial = new SerialHelper();
    static Timer tempo = new Timer();
    static int sum;
    
    
    public static Status burn_xsvf(FormUpdate updateForm, byte xsvf_file[], int size, String port){
        if(!serial.isConnected()) serial.conectar(port, 115200);
        if(!serial.isConnected()) return Status.SERIAL_ERROR;
        
        try{
            String line;
            String command;
            String argument;
            int numBytes;
            int numWrite;
            int bytesWritten=0;
            int xsvfSize = size;
            byte xsvf_data[] = null;
            
            while(true){
                line        = serial.readLine();
                command     = line.substring(0,1);
                argument    = line.substring(1).trim();
                
                switch(command){
                    case "S":{
                        numBytes = Integer.decode(argument);
                        xsvf_data= new byte[numBytes];
                        
                        if((xsvfSize - bytesWritten) >= numBytes){
                            System.arraycopy(xsvf_file, bytesWritten, xsvf_data, 0, numBytes);
                            numWrite = numBytes;
                        }else{
                            System.arraycopy(xsvf_file, bytesWritten, xsvf_data, 0, (xsvfSize - bytesWritten));
                            numWrite = (xsvfSize - bytesWritten);
                        }
                        serial.writeBlock(xsvf_data, 0, numWrite);
                        bytesWritten += numWrite;
                        updateHash(xsvf_data);
                        //updateForm.updateBurn(bytesWritten, (xsvfSize - bytesWritten), numBytes);
                    }break;
                    case "R":{
                        initHash();
                        break;
                    }
                    case "Q":{
                        StringTokenizer st = new StringTokenizer(argument, ",");
                        //while(st.hasMoreTokens())System.out.println("Received Device Quit: [" + st.nextToken() + "] [" + st.nextToken() + "]");
                        updateForm.deviceQuit(st);                        
                        updateForm.printInfo(Status.HASH, Integer.toString(xsvfSize));
                        break;
                    }
                    case "D":{
                        updateForm.printInfo(Status.DEVICE, "Device: " + argument);
                        break;
                    }
                    case "!":{
                        updateForm.printInfo(Status.IMPORTANT, "IMPORTANT: " + argument);
                        break;
                    }
                    default:{
                        updateForm.printInfo(Status.DESCONHECIDO, "Comando desconhecido");
                        break;
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(xsvf_play.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Status.DESCONHECIDO;
    }

    private static void updateHash(byte[] xsvf_data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void initHash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
