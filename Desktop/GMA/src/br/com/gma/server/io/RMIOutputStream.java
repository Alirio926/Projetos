/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.io;

import br.com.gma.server.io.RMIOutputStreamInterf;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 *
 * @author Alirio
 */
public class RMIOutputStream extends OutputStream implements Serializable{
    private RMIOutputStreamInterf out; 
    public RMIOutputStream(RMIOutputStreamInterf out)throws IOException{
        System.out.println("RMIOutputStream");
        this.out=out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    @Override
    public void write(byte[] b, int off, int len) throws IOException{
        out.write(b, off, len);
    }
    @Override
    public void close()throws IOException{
        out.close();
    }
}
