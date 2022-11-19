/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author aliri
 */
public class SerialHelper {
    
    public SerialPort selectedPort = null;
    private InputStream in = null;
    private OutputStream out = null;
    private BufferedWriter bufferWriter = null;
    private Boolean isComUp = false;
    
    public static SerialPort[] getPorts(){
        SerialPort[] serialPorts = SerialPort.getCommPorts();
        
        for (int i = 0; i < serialPorts.length; ++i){
            System.out.println("   [" + i + "] " + serialPorts[i].getSystemPortName() + ": " + serialPorts[i].getDescriptivePortName() + " - " + serialPorts[i].getPortDescription());
        }
        return serialPorts;
    }

    public SerialHelper() {
        
    }
    
    public void conectar(String portDescriptions, int speed){
        
        selectedPort = SerialPort.getCommPort(portDescriptions);
        
        System.out.println(selectedPort);
        
        selectedPort.openPort(0, 0x100000, 0x100000);
        selectedPort.setBaudRate(speed);
        in = selectedPort.getInputStream();
        out = selectedPort.getOutputStream();
        
        selectedPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 10000, 0);
        
        bufferWriter = new BufferedWriter(new OutputStreamWriter(out));
        
        isComUp = selectedPort.isOpen();
    }
    
    public void desconectar(){
        isComUp = !selectedPort.closePort();
    }
    
    public Boolean isConnected(){ return isComUp; }
    
    public SerialPort getPort(){
        return selectedPort;
    }
    public void writeCommand(String cmd) throws IOException{
        bufferWriter.write(cmd);
        bufferWriter.flush();
    }
    
    public byte[] readBlock(int block_size) throws IOException{
        byte[] b = new byte[block_size];
        in.read(b, 0, block_size);
        return b;
    }
    public byte readByte() throws IOException{
        return (byte)in.read();
    }
    
    public String readLine() throws IOException{
       StringBuilder sb = new StringBuilder();
       char c;
       
       while(true){
           c = (char) in.read();
           if((c != '\r' && c != (char)0x0A)){
               sb.append(c);
           }else{
               if(sb.length() > 0)
                break;
           }
       }
       return sb.toString();
    }
    
    public void writeBlock(byte[] value, int offset, int len) throws IOException{
        out.write(value, offset, len);
    }
    public void writeByte(byte value) throws IOException{
        out.write(value);
    }
}
