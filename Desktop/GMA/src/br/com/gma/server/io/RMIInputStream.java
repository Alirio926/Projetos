/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 *
 * @author Alirio
 */
public class RMIInputStream extends InputStream implements Serializable{
    private RMIInputStreamInterf in;
    
    public RMIInputStream(RMIInputStreamInterf in){
        this.in=in;
    }
    @Override
    public int read() throws IOException {
        return in.read();
    }
    @Override
    public int read(byte[] b, int off, int len)throws IOException{
        byte[] b2=in.readBytes(len);
        if(b2==null)
            return-1;
        int i = b2.length;
        System.arraycopy(b2, 0, b, off, i);
        return i;
    }
    @Override
    public void close()throws IOException{
        super.close();
    }
}
