/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.server.common.entity.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Administrador
 */
public interface RemoteMensagem extends Remote{
    public void sendMsgToMoagem(Usuario u, String msg) throws RemoteException;
    public void sendMsgToExpedicao(Usuario u, String msg) throws RemoteException;
    public void serverMsgToMoagem(Integer cod, String msg) throws RemoteException;
    public void serverMsgToExpecicao(Integer cod, String msg) throws RemoteException;
}
