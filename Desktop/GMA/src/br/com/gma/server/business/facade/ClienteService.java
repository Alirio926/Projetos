/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.cliente.business.facade.remote.SessionInterface;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemoteCliente;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import static br.com.gma.server.common.entity.CONSTANTES.*;
import br.com.gma.server.common.entity.Cliente;
import br.com.gma.server.persistence.ClienteJpaController;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.presentation.ServerStatusForm;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

/**
 *
 * @author Administrador
 */
public class ClienteService extends UnicastRemoteObject implements RemoteCliente{

    private CallBackServerInterface callBack;
    private ClienteJpaController jpa;
    private final ServerFormInterface sfi;
    
    public ClienteService(Factory em, CallBackServerInterface c,ServerFormInterface sss) throws RemoteException{
        super();
        callBack = c;
        jpa = new ClienteJpaController(em.getfactory());   
        sfi=sss;
        sfi.setNumerosClientes(jpa.getClienteCount());
    }

    @Override
    public void cadastrarCliente(Cliente c, Long id) throws RemoteException {
        try {
            jpa.incluir(c); /* Inclusão no banco de dados */
            sfi.setNumerosClientes(jpa.getClienteCount());
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(INFORMACAO, 
                    "Cliente: "+c.getNome_cliente()+" cadastrado com sucesso!");            
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public void updateCliente(Cliente c, Long id) throws RemoteException {
        try {
            System.out.println("Placa: "+c.getPlaca());
            System.out.println("Usuario: "+c.getUsuario_update().getUsername());
            jpa.alterar(c);
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Update concluido!"); 
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage()); 
        }
    }

    @Override
    public void removerCliente(Cliente c, Long id) throws RemoteException {
        try {
            jpa.deletar(c);
            /*callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Cliente: "+c.getNome_cliente()+" removido com sucesso.");   */          
        } catch (Exception ex) {
            /*callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());*/
            ex.printStackTrace();
        }
    }

    @Override
    public Collection<Cliente> listAllClientes(Long id) throws RemoteException {
        try {
            return jpa.listAllClientes();
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Cliente> pesquisarClientesByNome(String nome, Long id) throws RemoteException {
        try {
            return jpa.pesquisarClienteByNome(nome);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Cliente pesquisarClienteByPlaca(String placa, Long id) throws RemoteException {
        try {
            return jpa.pesquisarClienteByPlaca(placa);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Cliente pesquisarClienteById(Long id, Long idSession) throws RemoteException {
        try {
            return jpa.pesquisarClienteById(id);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(idSession).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Cliente> autoCompleteClienteByNome(String s,Long idSession) throws RemoteException {
        try {
            return jpa.autoCompleteClienteByNome(s);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(idSession).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

}

