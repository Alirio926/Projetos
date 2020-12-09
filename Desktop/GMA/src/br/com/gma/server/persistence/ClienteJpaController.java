/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.server.common.entity.Cliente;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void incluir(Cliente cliente) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void alterar(Cliente cliente) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void deletar(Cliente u)  {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente c = em.merge(u);
            em.remove(c);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Collection<Cliente> listAllClientes() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cliente");            
            return new HashSet<Cliente>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Cliente> pesquisarClienteByNome(String nome) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Cliente o where o.nome_cliente = :nome_cliente");
            q.setParameter("nome_cliente", nome);
            Collection<Cliente> hashSet = new HashSet<Cliente>(q.getResultList());
            
            if(!hashSet.isEmpty())
                return hashSet;
            
            return null;
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Cliente pesquisarClienteByPlaca(String placa) throws Exception {
        EntityManager em = getEntityManager();
        Cliente cliente;
        try {
            Collection<Cliente> c = listAllClientes();
            Iterator iterator = c.iterator();
            while(iterator.hasNext()){
                cliente = (Cliente) iterator.next();
                for(int i = 0; i <= cliente.getPlaca().size(); i++){
                    if(cliente.getPlaca().get(i).equalsIgnoreCase(placa))
                        return cliente;
                }
            }
            return null;
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }         
    }    
    public Cliente pesquisarClienteById(Long id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        } 
    }     
    public Collection<Cliente> autoCompleteClienteByNome(String s) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Cliente o where o.nome_cliente like :keyword");
            q.setParameter("keyword", "%"+s+"%");
            return new HashSet<Cliente>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cliente as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
