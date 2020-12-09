/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.server.common.entity.Log;
import br.com.gma.persistence.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Administrador
 */
public class InfoJpaController implements Serializable {

    public InfoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Log info) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(info);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Log info) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            info = em.merge(info);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = info.getId();
                if (findInfo(id) == null) {
                    throw new NonexistentEntityException("The info with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Log info;
            try {
                info = em.getReference(Log.class, id);
                info.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The info with id " + id + " no longer exists.", enfe);
            }
            em.remove(info);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Log> findInfoEntities() {
        return findInfoEntities(true, -1, -1);
    }

    public List<Log> findInfoEntities(int maxResults, int firstResult) {
        return findInfoEntities(false, maxResults, firstResult);
    }

    private List<Log> findInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Info as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Log findInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Log.class, id);
        } finally {
            em.close();
        }
    }

    public int getInfoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Info as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
