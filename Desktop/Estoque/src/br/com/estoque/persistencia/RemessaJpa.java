/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.persistencia;

import br.com.estoque.entity.RemessaEntity;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Alirio
 */
public class RemessaJpa {
    private EntityManagerFactory emf = null;

    public RemessaJpa(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void incluir(RemessaEntity r)throws Exception{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Collection<RemessaEntity> listarTodasRemessas() throws Exception {
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("select o from RemessaEntity o");            
            return new HashSet<>(q.getResultList());
        }catch(Exception e){throw e;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
