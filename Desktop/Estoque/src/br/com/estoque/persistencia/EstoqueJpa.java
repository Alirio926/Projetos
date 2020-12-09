/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.persistencia;

import br.com.estoque.entity.RoloEntity;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Alirio
 */
public class EstoqueJpa {
    private EntityManagerFactory emf = null;

    public EstoqueJpa(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public void incluir(RoloEntity r)throws Exception{
        EntityManager em = null;
        try {
            em = getEntityManager();
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
    public void alterar(RoloEntity r) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(r);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void apagar(RoloEntity r) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RoloEntity re = em.merge(r);
            em.remove(re);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<RoloEntity> listarTodos() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from RoloEntity");            
            return new HashSet<>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public RoloEntity procurarPorSerial(String serial) throws Exception{
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from RoloEntity o where o.serial = :serial");
            q.setParameter("serial", serial);
            return (RoloEntity) q.getSingleResult();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<RoloEntity> procurarPorSerialDinamico(String Serial) throws Exception{       
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from RoloEntity o where o.serial like :serial");
            q.setParameter("serial", Serial+"%");
            System.out.println("JPA: "+q.getResultList().size());
            return new HashSet<>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }        
    }
    public Collection<RoloEntity> procurarPorPassagem(String passagem) throws Exception{       
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from RoloEntity o where o.passagem = :passagem");
            q.setParameter("passagem", passagem);
            return new HashSet<>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }        
    }
    public Collection<RoloEntity> procurarPorRaiacao(String raiacao) throws Exception{
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from RoloEntity o where o.raiacao = :raiacao");
            q.setParameter("raiacao", raiacao);
            return new HashSet<>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }    
    }   
    public Collection<RoloEntity> procurarPorEstoqueMaiorQ(Integer valor) throws Exception{
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from RoloEntity o where o.qtdEstoque > :qtdEstoque");
            q.setParameter("qtdEstoque", valor);
            return new HashSet<>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }    
    }
    public int getEstoqueCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from RoloEntity as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
