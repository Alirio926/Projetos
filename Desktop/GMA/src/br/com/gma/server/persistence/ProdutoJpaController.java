/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.server.common.entity.Produto;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void incluir(Produto produto) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void alterar(Produto produto) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(produto);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void deletar(Produto u)  {
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

    public Collection<Produto> listarProduto() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto");
            return new HashSet<Produto>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Produto> listarTodosOsProdutos() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("from Produto");
            return q.getResultList();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Produto> listarProdutoByTipo(Integer tipo) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Produto o where o.status =:status AND o.classe_produto = :classe_produto");
            q.setParameter("classe_produto", tipo);
            q.setParameter("status", Boolean.TRUE);
            return new HashSet<Produto>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Produto pesquisarProdutoByNome(String nome) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Produto o where o.status =:status AND o.nome_produto = :nome_produto");
            q.setParameter("nome_produto", nome);
            q.setParameter("status", Boolean.TRUE);
            return (Produto) q.getSingleResult();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }          
    } 
    public Produto pesquisarProdutoByCodigoProduto(String codigo) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Produto o where o.cod_produto = :cod_produto");
            q.setParameter("cod_produto", codigo);
            return (Produto) q.getSingleResult();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }          
    }    
    public Produto pesquisarProdutoById(Long id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        } 
    }     

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Produto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
