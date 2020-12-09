/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.server.common.entity.Cargo;
import br.com.gma.server.common.entity.Cliente;
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
public class CargoJpaController implements Serializable {

    public CargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void incluir(Cargo cargo) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void alterar(Cargo cargo) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(cargo);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void deletar(Cargo u)  {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo c = em.merge(u);
            em.remove(c);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Collection<Cargo> listarCargos() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Cargo");
            return new HashSet<Cargo>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    } 
    public Cargo pesquisarCargoById(Long id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        } 
    }     
    public Cargo pesquisarCargoByNome(String n) throws Exception{
       EntityManager em = getEntityManager();
        try {
            System.out.println(n);
            Query q = em.createQuery("select o from Cargo o where o.cargo = :cargo");
            q.setParameter("cargo", n);
            return (Cargo)q.getSingleResult();
            
        } catch (NoResultException ex) { throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cargo as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
