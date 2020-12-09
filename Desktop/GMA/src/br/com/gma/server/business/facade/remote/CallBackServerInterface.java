/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 *
 * @author Administrador
 */
public interface CallBackServerInterface extends Remote{
    public void registerForCallback(ClientInterface obj, Long id)throws RemoteException;
    public void unregisterForCallback(ClientInterface obj, Long id)throws RemoteException;
    public Map<Long, ClientInterface> getInterfaceClientesList() throws RemoteException;
    public void sendMsgToCliente(String msg, String u) throws RemoteException;
    
    public Long obterID() throws RemoteException;
}
