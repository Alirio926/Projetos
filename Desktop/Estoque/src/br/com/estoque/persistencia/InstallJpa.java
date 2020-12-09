/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.persistencia;

import br.com.estoque.entity.InstallEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Alirio
 */
public class InstallJpa {
    private EntityManagerFactory emf = null;
    
    public InstallJpa(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void incluir(InstallEntity e) throws Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
