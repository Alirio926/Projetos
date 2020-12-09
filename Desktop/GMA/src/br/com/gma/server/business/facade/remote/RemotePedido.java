/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.relatorios.model.CarregamentoEntity;
import br.com.gma.relatorios.model.ProducaoEntity;
import br.com.gma.server.common.entity.Pedido;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public interface RemotePedido extends Remote{
    public void solicitarCancelamentoPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void cancelarPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void pedidoDeCancelamentoRecusado(Pedido p, Long sessaoId)throws RemoteException;
    
    public void abrirPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void atenderPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void atenderMultiplosPedido(Pedido p[], Long Id) throws RemoteException;
    public void devolverPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void devolverMultiplosPedido(Pedido[] p, Long sessaoId) throws RemoteException;
    public Integer expedirPedido(Pedido p, Long sessaoId)throws RemoteException;
    public void finalizarPedido(Pedido p, Long sessaoId)throws RemoteException;
    public Integer obterUltimoCodigo(Long sessaoId)throws RemoteException;
    
    public Collection<Pedido> listAllPedidos(Long sessaoId)throws RemoteException;
    public Collection<Pedido> listLastPedidos(Long sessaoId)throws RemoteException;
    public Pedido pesquisarPedidoById(Integer Id)throws RemoteException;
    public Pedido[] pesquisarPedidoByArrayID(Integer id[], Integer arraySize) throws RemoteException;
    public Collection<CarregamentoEntity> pesquisarPedido(Long sessaoId, Date inicio, Date fim, String state, Integer t) throws RemoteException;
    public Collection<Pedido> pesquisarPedido(Long sessaoId, Date inicio, Date fim, Integer t) throws RemoteException;
    public Collection<Pedido> pesquisarPedido(Long sessaoId, Date inicio, Date fim, String state, String produto) throws RemoteException;
}
