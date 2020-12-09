/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.server.common.entity.Produto;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface RemoteProduto extends Remote{
    public void cadastrarProduto(Produto p, Long id)throws RemoteException;
    public void updateProduto(Produto p, Long id)throws RemoteException;
    public void removerProduto(Produto p, Long id)throws RemoteException;
    
    public Collection<Produto> listAllProdutos(Long sessionId) throws RemoteException;
    public List<Produto> listarTodosOsProdutos(Long sessionId) throws RemoteException;
    public Produto pesquisarProdutoByNome(String nome, Long sessionId) throws RemoteException;
    public Produto pesquisarProdutoById(Long id,Long sessionId) throws RemoteException;
    public Collection<Produto> pesquisarProdutoByTipo(Long id,Integer tipo) throws RemoteException;
    public Produto pesquisarProdutoByCodigoProdudo(String cod, Long sessionId) throws RemoteException;    
}
