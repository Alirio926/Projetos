/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag;

import br.com.squallsoft.jtag.adapter.SerialHelper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

/**
 *
 * @author aliri
 */
public class main {
    static SerialHelper serial;
    static int sum;
    public static void main(String args[]) throws FileNotFoundException
    {
        //mainForm form = new mainForm();          
        //form.setVisible(true);
        
        
        SerialHelper serial = new SerialHelper();
		
        RandomAccessFile xsvf = new RandomAccessFile(new File("D:\\Troca de HD\\Codigos\\Genesis\\SquallCart SSF\\Cart_SSF\\impact\\default.xsvf"),"r");
        //RandomAccessFile xsvf = new RandomAccessFile(new File("D:\\Troca de HD\\Codigos\\Genesis\\SquallCart PSolar\\PSolar\\impact\\default.xsvf"),"r");
        //RandomAccessFile xsvf = new RandomAccessFile(new File("D:\\Troca de HD\\Codigos\\Genesis\\SquallCart PSolar\\PSola_Burn\\impact\\default.xsvf"),"r");
        //RandomAccessFile xsvf = new RandomAccessFile(new File("D:\\Troca de HD\\Codigos\\nes\\Alirio Mapper\\sunsoft\\impact\\sunsoft.xsvf"),"r");
        //RandomAccessFile xsvf = new RandomAccessFile(new File("E:\\Troca de HD\\Codigos\\nes\\Alirio Mapper\\UNROM\\impact\\default.xsvf"),"r");
        //RandomAccessFile xsvf = new RandomAccessFile(new File("E:\\Troca de HD\\Codigos\\Genesis\\SquallCart UMKT\\Cart_UMKT\\impact\\cart_umkt.xsvf"),"r");
        
        serial.conectar("COM4", 115200);
        if(!serial.isConnected()) System.err.println("Sem conexão!");
        try{
            String line;
            String command;
            String argument;
            int numBytes;
            long bytesWritten=0;
            long xsvfSize = xsvf.length();
            byte xsvf_data[] = null, code = 0;
            
            while(true){
                line = serial.readLine();                                
                command = line.substring(0, 1);
                argument = line.substring(1).trim();
                
                //System.out.println("Command: "+command+ " arguments: "+argument);
                switch(command){
                    case "S":{
                        numBytes = Integer.decode(argument);
                        xsvf_data = new byte[numBytes]; // init array
                        
                        if((xsvfSize-bytesWritten) >= numBytes){
                            
                            xsvf.read(xsvf_data, 0, numBytes); // read from xsvf
                            
                            for(int x=0;x<numBytes;x++){
                                xsvf_data[x] = (byte) (xsvf_data[x] + code);
                            }
                            
                            serial.writeBlock(xsvf_data, 0, numBytes); // write block to serial
                            bytesWritten += numBytes;
                        }else{
                            System.out.println("Bytes: "+(int)(xsvfSize-bytesWritten));                            
                            xsvf.read(xsvf_data, 0, (int)(xsvfSize-bytesWritten)); // read from xsvf
                            
                            for(int x=0;x<(xsvfSize-bytesWritten);x++){
                                xsvf_data[x] = (byte) (xsvf_data[x] + code); //<<<<<------------------------- encoder
                                //xsvf_data[x] = (byte) xsvf_data[x];
                            }                            
                            
                            serial.writeBlock(xsvf_data, 0, (int)(xsvfSize-bytesWritten)); // write block to serial
                            bytesWritten += (int)(xsvfSize-bytesWritten);
                        }                        
                        
                        // update numero de bytes gravados, utilizado como ponteiro do arquivo lido  
                                               
                        // update hash
                        updateHash(xsvf_data);
                        //
                        //System.err.println("X: " + ((char)0xFF * (numBytes - xsvf_data.length)));
                        System.err.println("Foi enviado: " + bytesWritten + 
                                           ", pendente: " + (xsvfSize-bytesWritten)+
                                           ", Packet Size: "+numBytes);
                        
                        break;
                    }
                    case "R":{
                        initHash();
                        break;
                    }
                    case "Q":{
                        StringTokenizer st = new StringTokenizer(argument, ",");
                        while(st.hasMoreTokens())System.out.println("Received Device Quit: [" + st.nextToken() + "] [" + st.nextToken() + "]");
                        
                        printHash(xsvf_data, xsvfSize);
                        
                        System.exit(0);
                        break;
                    }
                    case "D":{
                        System.out.println("Device: " + argument);
                        break;
                    }
                    case "!":{
                        System.out.println("IMPORTANT: " + argument);
                        break;
                    }
                    default:{
                        System.out.println("Comando desconhecido");
                        break;
                    }
                }
            }
        }catch(IOException io){
            io.printStackTrace();
        }catch(java.lang.StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        
    }
    
    public static void initHash(){
        sum = 0;
    }
    public static void updateHash(byte data[]){
        for(byte d : data){
            sum += (0xFF & d);
        }
    }    
    public static void printHash(byte data[], long xsvf_size){
        int chksum = (-sum)&0xFF;
        System.out.printf("\tExpected checksum: 0x%02X/%d.\n", chksum, xsvf_size);
        System.out.printf("\tExpected sum: 0x%08X/%d.\n", sum, xsvf_size);
    }
    
}

