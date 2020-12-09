/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.business.facade.remote;

import br.com.gma.server.common.entity.Pedido;
import br.com.gma.server.common.entity.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Administrador
 */
public interface RemoteExpInterface extends Remote{
    public void pedidoCancelado(Pedido p)throws RemoteException;
    public void pedidoDeCancelamentoRecusado(Pedido p) throws RemoteException;
    public void updatePedidoStatus(Pedido p) throws RemoteException;
    
    public void receiverMsgFromMoagem(Usuario u, String msg) throws RemoteException;
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException;
}
