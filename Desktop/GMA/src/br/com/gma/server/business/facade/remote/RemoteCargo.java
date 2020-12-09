/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.server.common.entity.Cargo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 *
 * @author Alirio
 */
public interface RemoteCargo extends Remote{
    public void cadastrarCargo(Cargo c, Long id)throws RemoteException;
    public void updateCargo(Cargo c, Long id)throws RemoteException;
    public void removerCargo(Cargo c, Long id)throws RemoteException;
    
    public Collection<Cargo> listarCargos()throws RemoteException;
    public Cargo pesquisarCargoById(long id,Long sessionId) throws RemoteException;
    public Cargo pesquisarCargoByNome(String nome,Long sessionId) throws RemoteException;
}
