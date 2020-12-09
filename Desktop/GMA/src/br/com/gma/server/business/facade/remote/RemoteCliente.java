/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.server.common.entity.Cliente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 *
 * @author Administrador
 */
public interface RemoteCliente  extends Remote{
    public void cadastrarCliente(Cliente c, Long id)throws RemoteException;
    public void updateCliente(Cliente c, Long id)throws RemoteException;
    public void removerCliente(Cliente c, Long id)throws RemoteException;
    
    public Collection<Cliente> listAllClientes(Long id)throws RemoteException;
    public Collection<Cliente> autoCompleteClienteByNome(String s,Long id) throws RemoteException; 
    public Collection<Cliente> pesquisarClientesByNome(String nome, Long id)throws RemoteException;
    public Cliente pesquisarClienteByPlaca(String placa, Long id)throws RemoteException;
    public Cliente pesquisarClienteById(Long id, Long idSession)throws RemoteException;    
}
