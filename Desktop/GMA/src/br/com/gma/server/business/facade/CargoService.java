/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemoteCargo;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import static br.com.gma.server.common.entity.CONSTANTES.*;
import br.com.gma.server.common.entity.Cargo;
import br.com.gma.server.persistence.CargoJpaController;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.presentation.ServerStatusForm;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

/**
 *
 * @author Alirio
 */

public class CargoService extends UnicastRemoteObject implements RemoteCargo{
    private final CallBackServerInterface callBack;
    private final CargoJpaController jpa;
    private final ServerFormInterface sfi;

    public CargoService(Factory em, CallBackServerInterface c, ServerFormInterface sss) throws RemoteException{
        super();
        callBack = c;
        jpa = new CargoJpaController(em.getfactory());  
        sfi=sss;
        sfi.setNumerosCargos(jpa.getCargoCount());
    }

    @Override
    public void cadastrarCargo(Cargo c, Long id) throws RemoteException {
        try {
            jpa.incluir(c);
            sfi.setNumerosCargos(jpa.getCargoCount());
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(INFORMACAO, 
                    "Cadastro realizado com sucesso!");
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public void updateCargo(Cargo c, Long id) throws RemoteException {
       try {
            jpa.alterar(c);
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Update concluido!"); 
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage()); 
        }
    }

    @Override
    public void removerCargo(Cargo c, Long id) throws RemoteException {
        try {
            jpa.deletar(c);
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Cargo: "+c.getCargo()+" removido com sucesso.");             
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public Collection<Cargo> listarCargos() throws RemoteException {
        try {
            return jpa.listarCargos();
        } catch (Exception ex) {
           
        }
        return null;
    }

    @Override
    public Cargo pesquisarCargoById(long id, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarCargoById(id);
        } catch (Exception ex) {
           callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Cargo pesquisarCargoByNome(String nome, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarCargoByNome(nome);
        } catch (Exception ex) {
           callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }
}
