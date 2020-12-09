/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemoteUsuario;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import static br.com.gma.server.common.entity.CONSTANTES.*;
import br.com.gma.server.common.entity.Sessao;
import br.com.gma.server.common.entity.Usuario;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.persistence.UsuarioJpaController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author Administrador
 */
public class UsuarioService  extends UnicastRemoteObject implements RemoteUsuario{

    private final CallBackServerInterface callBack;
    private final UsuarioJpaController jpa;
    private final Boolean debug = Boolean.FALSE;
    private final ServerFormInterface sfi;
    private final SessionInterface sessionInterface;
    
    public UsuarioService(Factory em, CallBackServerInterface c, ServerFormInterface sss, SessionInterface si) throws RemoteException{
        super();
        jpa = new UsuarioJpaController(em.getfactory());  
        callBack = c;
        /*timer = new Timer();
        /*timer.scheduleAtFixedRate(new UsuarioService.updateInterfaces(), 1000, 1000*5/**60*5);
        /**System.out.println("Sistema automatico de limpeza iniciado.");*/
        sfi=sss;
        sfi.setNumerosUsuarios(jpa.getUsuarioCount());
        sessionInterface = si;
    }
    @Override
    public void cadastrarUsuario(Usuario u, Long id) throws RemoteException {
        try {
            if(debug)
                System.out.println("DEBUG USUARIO.SERVICE ID : "+id);
                        
            jpa.incluir(u); /* Inclusão no banco de dados */  
            sfi.setNumerosUsuarios(jpa.getUsuarioCount());
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(INFORMACAO, 
                    "Cadastro realizado com sucesso!");
        } catch (Exception ex) {
            if(debug)
                System.out.println("DEBUG USUARIO.SERVICE : "+ex.getMessage());            
                callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public void updateUsuario(Usuario u, Long id) throws RemoteException {
        try {
            jpa.alterar(u);
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Update concluido!"); 
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void removerUsuario(Usuario u, Long id) throws RemoteException {
        try {
            jpa.deletar(u);
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(UPDATE, 
                    "Usuario: "+u.getUsername()+" removido com sucesso.");             
        } catch (RemoteException ex) {
            callBack.getInterfaceClientesList().get(id).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
    }

    @Override
    public Collection<Usuario> listAllUsuarios(Long sessionId) throws RemoteException {
        try {
            return jpa.listarUsuario();             
        } catch (Exception ex) {
           callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Usuario> pesquisarUsuarioByCargo(String cargo, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarUsuarioByCargo(cargo);
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Usuario pesquisarUsuarioById(Long id, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarUsuarioById(id);     
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Usuario pesquisarUsuarioByGMA(String gma, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarUsuarioByGMA(gma);        
        } catch (Exception ex) {
            System.out.println("Erro pesquisando by GMA: "+ex.getMessage());
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return new Usuario();
    }

    @Override
    public Usuario pesquisarUsuarioByNome(String nome, Long sessionId) throws RemoteException {
        try {
            return jpa.pesquisarUsuarioByNome(nome);    
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessionId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
        }
        return null;
    }

    @Override
    public Usuario pesquisarUsuarioByGMA(String gma) throws RemoteException {
        try {
            return jpa.pesquisarUsuarioByGMA(gma);
        } catch (Exception ex) {}
        return null;
    }
    
    @Override
    public Sessao autenticarUsuario(String gma, String senha) throws RemoteException {
        Sessao sessao = jpa.autenticarUsuario(gma, senha);
        if(!sessao.getSession_valida()){
            sessao.setMsg("Usuario ou senha errado. Tente novamente!");
            return sessao;
        }
        Long id = sessionInterface.registerID();      
        sessao.setSessionId(id);
        sessao.setData_Login(new Date());
        return sessao;
    }
        
    private class updateInterfaces extends TimerTask{
        @Override
        public void run() { 
            /*System.out.print("Executando limpeza automatica do sistema. "+
                    "Tamanho da Lista antes: "+sessaoList.size());*/            
           for(int i = 0; i < sessaoList.size(); i++){
                
            }
           // System.out.println(" e depois: "+sessaoList.size());
        }
        
    }

    private Date myDate, dNow, dMax;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");        
    private Timer timer;
    private List<Sessao> sessaoList = new ArrayList<Sessao>();
}
