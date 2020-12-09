/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Alirio
 */
public class RMIOutputStreamImpl implements RMIOutputStreamInterf {

    private OutputStream out;
    public RMIOutputStreamImpl(FileOutputStream fileOutputStream) throws IOException{
        this.out=fileOutputStream;
        System.out.println("RMIOutputStreamImpl");
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void write(int b) throws IOException, RemoteException {
        out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException, RemoteException {
        out.write(b,off,len);
    }

    @Override
    public void close() throws IOException, RemoteException {
        out.close();
    }
    
}
