/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemoteProduto;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import static br.com.gma.server.common.entity.CONSTANTES.*;
import br.com.gma.server.common.entity.Produto;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.persistence.ProdutoJpaController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class ProdutoService extends UnicastRemoteObject implements RemoteProduto {

    private final CallBackServerInterface callBack;
    private final ProdutoJpaController jpa;
    private final ServerFormInterface sfi;    
    
    public ProdutoService(Factory em, CallBackServerInterface c,ServerFormInterface sss) throws RemoteException{
        super();
        callBack = c;
        jpa = new ProdutoJpaController(em.getfactory());    
        sfi=sss;
        sfi.setNumerosProdutos(jpa.getProdutoCount());
    }
    @Override
    public void cadastrarProduto(Produto p, Long id) throws RemoteException {
        try{
            p.setEstoque(0f);
            jpa.incluir(p);
            sfi.setNumerosProdutos(jpa.getProdutoCount());
            try{
                callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(INFORMACAO, 
                    "Produto: "+p.getNome_produto()+" cadastrado com sucesso!"); 
            }catch(Exception e){ }
        }catch(Exception ex){
            callBack.getInterfaceClientesList().get(p.getSessaoId()).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public void updateProduto(Produto p, Long id) throws RemoteException {
        try {
            jpa.alterar(p);
                /* avisa todos os clientes que existe update de pedidos*/
            try{
                callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE,"Update concluido!");
            }catch(RemoteException e){ }
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    @Override
    public void removerProduto(Produto p, Long id) throws RemoteException {
        try {
            jpa.deletar(p);
            try{
                callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Produto: "+p.getNome_produto()+" removido com sucesso.");   
            }catch(Exception e){ }            
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(p.getSessaoId()).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public Collection<Produto> listAllProdutos(Long sessionId) throws RemoteException {
        try {
            return jpa.listarProduto();
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Produto pesquisarProdutoByNome(String nome, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarProdutoByNome(nome);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Produto pesquisarProdutoById(Long id, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarProdutoById(id);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Produto pesquisarProdutoByCodigoProdudo(String cod, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarProdutoByCodigoProduto(cod);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }    

    @Override
    public Collection<Produto> pesquisarProdutoByTipo(Long id, Integer tipo) throws RemoteException {
        try {
            return jpa.listarProdutoByTipo(tipo);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Produto> listarTodosOsProdutos(Long sessionId) throws RemoteException {
        try {
            return jpa.listarTodosOsProdutos();
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }
}
