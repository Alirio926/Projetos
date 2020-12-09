/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.server.common.entity.Sessao;
import br.com.gma.server.common.entity.Usuario;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void incluir(Usuario usuario) throws Exception {
        EntityManager em = null;
        try {            
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void alterar(Usuario usuario) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(usuario);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void deletar(Usuario u)  {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.remove(u);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Collection<Usuario> listarUsuario() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Usuario");
            return new HashSet<Usuario>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Usuario> pesquisarUsuarioByCargo(String c) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Usuario o where o.cargo = :cargo");
            q.setParameter("cargo", c);
            return new HashSet<Usuario>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Usuario pesquisarUsuarioByNome(String nome) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Usuario o where o.nome = :username");
            q.setParameter("nome", nome);
            return (Usuario) q.getSingleResult();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }         
    }   
    public Usuario pesquisarUsuarioByGMA(String gma) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Usuario o where o.gma = :gma");
            q.setParameter("gma", gma);
            return (Usuario) q.getSingleResult();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }         
    }     
    public Usuario pesquisarUsuarioById(Long id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        } 
    }     

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Sessao autenticarUsuario(String gma, String senha){
        EntityManager em = getEntityManager();
        Sessao s = new Sessao();
        try{
            Query q = em.createQuery("select o from Usuario o where o.gma = :g and o.senha = :s");
            q.setParameter("g", gma);
            q.setParameter("s", senha);
            /* resultando 0 causa exception e retorna uma sessao nula */
            Usuario usuario = (Usuario)q.getSingleResult();  
            if(usuario.isStatus()){                
                s.setUsuario(usuario);            
                s.setSession_valida(Boolean.TRUE); 
                s.setMsg("Usuario ok!");
            }else{
                s.setUsuario(usuario);            
                s.setSession_valida(Boolean.FALSE);
                s.setMsg("Este usuario esta desativado!");
            }
            return s;
        }catch(NoResultException e){
            s.setSession_valida(Boolean.FALSE);
            s.setMsg(e.getMessage());
            return s;
        }
    }    
}
