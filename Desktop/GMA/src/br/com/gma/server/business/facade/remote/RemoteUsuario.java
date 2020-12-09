/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

import br.com.gma.server.common.entity.Sessao;
import br.com.gma.server.common.entity.Usuario;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 *
 * @author Administrador
 */
public interface RemoteUsuario extends Remote{
    public void cadastrarUsuario(Usuario u, Long id) throws RemoteException;
    public void updateUsuario(Usuario u, Long id) throws RemoteException;
    public void removerUsuario(Usuario u, Long id) throws RemoteException;
    
    public Collection<Usuario> listAllUsuarios(Long sessionId) throws RemoteException;
    public Collection<Usuario> pesquisarUsuarioByCargo(String cargo, Long sessionId) throws RemoteException;
    public Usuario pesquisarUsuarioById(Long id, Long sessionId) throws RemoteException;
    public Usuario pesquisarUsuarioByGMA(String gma, Long sessionId) throws RemoteException;
    public Usuario pesquisarUsuarioByNome(String nome, Long sessionId) throws RemoteException;
    public Usuario pesquisarUsuarioByGMA(String gma) throws RemoteException;
    
    public Sessao autenticarUsuario(String gma, String senha)throws RemoteException;
}
