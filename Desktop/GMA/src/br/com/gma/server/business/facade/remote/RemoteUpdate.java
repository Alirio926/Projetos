/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.business.facade.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Alirio
 */
public interface RemoteUpdate extends Remote{
    public String checkVersion(String version) throws RemoteException;
    public String checkVersion() throws RemoteException;
    public byte[] downloadFile(String filename)throws RemoteException;
    
}
