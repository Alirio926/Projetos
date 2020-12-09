/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.business.facade;

import br.com.gma.server.business.facade.remote.RemoteUpdate;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Alirio
 */
public class VersionControl extends UnicastRemoteObject implements RemoteUpdate{
    private final String version;
    public VersionControl(String version)throws RemoteException{
        super();
        this.version=version;
    }

    @Override
    public String checkVersion(String version) throws RemoteException {
        return this.version;
    }

    @Override
    public byte[] downloadFile(String filename) throws RemoteException {
        try{
            File file = new File(filename);
            System.out.println("Lendo arquivo: "+file.getAbsolutePath());
            byte buffer[] = new byte[(int)file.length()];
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
            
            input.read(buffer,0,buffer.length);
            input.close();
            return(buffer);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String checkVersion() throws RemoteException {
        return this.version;
    }

    
}
