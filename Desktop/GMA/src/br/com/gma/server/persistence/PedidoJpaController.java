
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.persistence;

import br.com.gma.relatorios.model.CarregamentoEntity;
import static br.com.gma.server.common.entity.CONSTANTES.INDUSTRIAL;
import br.com.gma.server.common.entity.Pedido;
import java.io.Serializable;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Administrador
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void incluir(Pedido pedido) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void alterar(Pedido pedido) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(pedido);
            em.getTransaction().commit();
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public void deletar(Pedido u)  {
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

    public Collection<Pedido> listarPedidos() throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select p from Pedido p where "+
                    "p.status_pedido = :Pendente "+
                    "OR p.status_pedido = :Expedindo "+
                    "OR p.status_pedido = :Processando "+
                    "OR p.status_pedido = :Disponivel");
            q.setParameter("Pendente", "Pendente");
            q.setParameter("Expedindo", "Expedindo");
            q.setParameter("Processando", "Processando");
            q.setParameter("Disponivel", "Disponivel");
            return new HashSet<Pedido>(q.getResultList());
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Pedido> pesquisarPedidoByProduto(Integer cod_produto) throws Exception {
        EntityManager em = getEntityManager();
        Collection<Pedido> sh = new HashSet<Pedido>();
        Pedido pedido;
        try {
            /*Query q = em.createQuery("select o from Pedido o where o.p = :produto");
            q.setParameter("p", p);*/
            Collection<Pedido> hashSet = listarPedidos();
            Iterator iterator = hashSet.iterator();
            while(iterator.hasNext()){
                pedido = (Pedido) iterator.next();
                if(pedido.getProduto().getCod_produto() == cod_produto){
                    sh.add(pedido);
                }
            }
            return sh;
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Pedido> pesquisarPedidoByCliente(String nome) throws Exception {
        EntityManager em = getEntityManager();
        Collection<Pedido> sh = new HashSet<Pedido>();
        Pedido pedido;
        try {
            /*Query q = em.createQuery("select o from Pedido o where o.p = :cliente");
            q.setParameter("p", p);*/
            Collection<Pedido> hashSet = listarPedidos();
            Iterator iterator = hashSet.iterator();
            while(iterator.hasNext()){
                pedido = (Pedido)iterator.next();
                if(pedido.getCliente().getNome_cliente().equalsIgnoreCase(nome)){
                    sh.add(pedido);
                }
            }
            return sh;
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Collection<Pedido> pesquisarPedidoByUsuario(String gma) throws Exception {
        EntityManager em = getEntityManager();
        Collection<Pedido> sh = new HashSet<Pedido>();
        Pedido pedido;
        try {
            /*Query q = em.createQuery("select o from Pedido o where o.u = :"+user_tipo);
            q.setParameter("u", p);*/
            Collection<Pedido> hashSet = listarPedidos();
            Iterator iterator = hashSet.iterator();
            while(iterator.hasNext()){
                pedido = (Pedido) iterator.next();
                if(pedido.getUsuario_pedido().getGma().equalsIgnoreCase(gma)){
                    sh.add(pedido);
                }
            }
            return sh;
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }   
    public Pedido pesquisarPedidoByProdutoAndStatus(String produto_nome, String status){
        Pedido pedido = null;
        
        
        
        return pedido;
    }
    public Collection<Pedido> pesquisarPedidoByStatus(String status) throws Exception{
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("select o from Pedido o where o.status_pedido =:Finalizado");
            q.setParameter("Finalizado", status);
            Collection<Pedido> hashSet = new HashSet<Pedido>(q.getResultList());
            if(!hashSet.isEmpty())
                return hashSet;
            
            return null;
        }catch(Exception e){throw e;}
        finally{
            if(em != null)
                em.close();
        }
    }
    public Pedido pesquisarUsuarioById(Long id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } catch (Exception ex) {throw ex;} 
        finally {
            if (em != null) {
                em.close();
            }
        } 
    }     
    public Collection<CarregamentoEntity> pesquisarPedido(Date inicio, Date fim, String state, Integer t) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Pedido o where "+
                    "o.tipoProduto = :tipoProduto "+
                    "AND o.status_pedido like :keyword "+
                    "AND o.pedido_expedido > :pedido_expedido "+
                    "AND o.pedido_finalizado < :pedido_finalizado");
            q.setParameter("tipoProduto", t);
            q.setParameter("keyword", "%"+state+"%");
            q.setParameter("pedido_expedido",inicio,TemporalType.TIMESTAMP);
            q.setParameter("pedido_finalizado", fim,TemporalType.TIMESTAMP);
            
            Collection<CarregamentoEntity> hashSet = new HashSet<CarregamentoEntity>(q.getResultList());
            
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
    public Collection<Pedido> pesquisarPedido(Date inicio, Date fim, Integer tipoproduto) throws Exception {
        EntityManager em = getEntityManager();
        try{
            Query q = em.createQuery("select o from Pedido o where "+
                    "o.tipoProduto =:tipoProduto "+
                     "AND o.status_pedido like :keyword "+
                    "AND o.pedido_expedido > :pedido_expedido "+
                    "AND o.pedido_finalizado < :pedido_finalizado");
            q.setParameter("tipoProduto",tipoproduto);
            q.setParameter("keyword", "%Finalizado%");
            q.setParameter("pedido_expedido",inicio,TemporalType.TIMESTAMP);
            q.setParameter("pedido_finalizado", fim,TemporalType.TIMESTAMP);
            
            Collection<Pedido> hashSet = new HashSet<Pedido>(q.getResultList());
            
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
    public Collection<Pedido> pesquisarPedido(Date inicio, Date fim, String state, String produto) throws Exception {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Pedido o where "+
                    "o.produto.nome_produto =:nome_produto "+
                    "AND o.status_pedido like :keyword "+
                    "AND o.pedido_expedido > :pedido_expedido "+
                    "AND o.pedido_finalizado < :pedido_finalizado");
            q.setParameter("nome_produto",produto);
            q.setParameter("keyword", "%"+state+"%");
            q.setParameter("pedido_expedido",inicio,TemporalType.TIMESTAMP);
            q.setParameter("pedido_finalizado", fim,TemporalType.TIMESTAMP);
            
            Collection<Pedido> hashSet = new HashSet<Pedido>(q.getResultList());
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
    public Integer obterUltimoCodigo()throws Exception{
        Session sessao = (Session)getEntityManager().getDelegate();
        try {
            Criteria crit = sessao.createCriteria(Pedido.class);
            ProjectionList projection = Projections.projectionList();
            projection.add(Projections.max("cod_pedido"));
            crit.setProjection(projection);
            return (Integer)crit.uniqueResult();
        } catch (Exception ex) {throw ex;}  
        finally {
            sessao.close();
        }
    }
    public Collection<Pedido>  pesquisarPedidoByCarga(Integer carga) throws Exception{
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Pedido o where o.carga = :carga"+
                    " AND o.status_pedido = :status_pedido"+
                    " AND o.produto.classe_produto = :classe_produto");
            q.setParameter("carga",carga);
            q.setParameter("status_pedido","Disponivel");
            q.setParameter("classe_produto",INDUSTRIAL);
            
            Collection<Pedido> hashSet = new HashSet<Pedido>(q.getResultList());
            
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
    public Pedido pesquisarPedidoById(Integer id) throws Exception{
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select o from Pedido o where o.cod_pedido = :cod_pedido");
            q.setParameter("cod_pedido",id);
            
            return (Pedido) q.getSingleResult();
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public Pedido[] pesquisarPedidoByArrayID(Integer id[], Integer arraySize) throws Exception{
        EntityManager em = getEntityManager();
        Pedido[] pedido = new Pedido[arraySize];
        try {
            for(int i=0;i<arraySize;i++){
                Query q = em.createQuery("select o from Pedido o where o.cod_pedido = :cod_pedido");
                q.setParameter("cod_pedido",id[i]);
                pedido[i] = (Pedido) q.getSingleResult();
            }       
            return pedido;
        } catch (Exception ex) {throw ex;}  
        finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Pedido as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
}
