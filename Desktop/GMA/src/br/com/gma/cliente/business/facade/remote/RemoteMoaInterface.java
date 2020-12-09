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
public interface RemoteMoaInterface extends Remote{
    public void solicitacaoCancelamento(Pedido p)throws RemoteException;
    public void novoPedido(Pedido p) throws RemoteException;
    public void updatePedidoStatus(Pedido p) throws RemoteException;
    
    public void receiverMsgFromExpedicao(Usuario u, String msg) throws RemoteException;
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException;
}
