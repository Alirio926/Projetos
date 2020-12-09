/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.business.facade.remote;

import br.com.gma.server.common.entity.Pedido;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Administrador
 * Classe utilizada pelo servidor para retornar ou solicitar
 * informções ao cliente expedição e moagem
 */
public interface ClientInterface extends Remote{
    
    /* informa a moagem que foi gerado um novo pedido*/
    public void novoPedido(Pedido p) throws RemoteException;
    //------------------------------------------------------------------------------------------//   
    /* Para expedição e moagem */
    /* chamado do servidor para updates diversos */
    public void updatePedidoStatus(Pedido p) throws RemoteException;
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException;
    public void receiverMsgFromCliente(String msg, String u) throws RemoteException;
}
