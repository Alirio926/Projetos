/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.io;

import br.com.gma.server.io.RMIInputStreamInterf;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Alirio
 */
public class RMIInputStreamImpl implements RMIInputStreamInterf {
    private InputStream in;
    private byte[] b;
    public RMIInputStreamImpl(FileInputStream fileInputStream) throws IOException{
        this.in=fileInputStream;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public byte[] readBytes(int len) throws IOException, RemoteException {
        if(b==null || b.length !=len)
            b=new byte[len];
        int len2=in.read(b);
        if(len2==0)
            return null;
        if(len2 != len){
            byte[] b2=new byte[len2];
            System.arraycopy(b, 0, b2, 0, len2);
            return b2;
        }else{
            return b;
        }
    }

    @Override
    public int read() throws IOException, RemoteException {
        return in.read();
    }

    @Override
    public void close() throws IOException, RemoteException {
        in.close();
    }
    
}
