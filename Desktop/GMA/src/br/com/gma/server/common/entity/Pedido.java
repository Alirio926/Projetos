/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
public class Pedido implements Serializable {
        //{"Pendente","Processando","Disponivel","Expedindo","Finalizado"}
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length=6,nullable=false,unique=false)
    private String unidade;
    private String duracao_carregamento;
    private Integer tipoProduto;
    @Column(length=5,nullable=false,unique=true)
    private Integer cod_pedido;
    private Integer carga;
    private Integer avaria;
    private Float quant;
    private Float quant_Expedido;
    private Float quant_estoque;
    private String placa_do_pedido;
    private String silo;
    private Integer quant_bags;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Cliente cliente;
    private String status_pedido;
    private boolean expedicaoCanCancel;
    private boolean moagemCanCancel;
    private boolean toCancel;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_pendente;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_finalizado;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_processando;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_efetivado;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_expedido;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_solicitacao_cancelamento;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date pedido_cancelamento;
    @ManyToOne
    private Usuario usuario_pedido;
    @ManyToOne
    private Usuario usuario_finalizacao;
    @ManyToOne
    private Usuario usuario_processamento;
    @ManyToOne
    private Usuario usuario_efetivacao;
    @ManyToOne
    private Usuario usuario_expedicao;
    private boolean pedido_cancelado;
    private long sessao;
    private Integer carga_aberta;
    private Integer carga_fechada;

    public Integer getCarga_aberta() {
        return carga_aberta;
    }

    public void setCarga_aberta(Integer carga_aberta) {
        this.carga_aberta = carga_aberta;
    }

    public Integer getCarga_fechada() {
        return carga_fechada;
    }

    public void setCarga_fechada(Integer carga_fechada) {
        this.carga_fechada = carga_fechada;
    }
    
    public Float getQuant_estoque() {
        return quant_estoque;
    }

    public void setQuant_estoque(Float quant_estoque) {
        this.quant_estoque = quant_estoque;
    }

    public Float getQuant_Expedido() {
        return quant_Expedido;
    }

    public void setQuant_Expedido(Float quant_Expedido) {
        this.quant_Expedido = quant_Expedido;
    }
    
    public Integer getQuant_bags() {
        return quant_bags;
    }

    public void setQuant_bags(Integer quant_bags) {
        this.quant_bags = quant_bags;
    }

    
    public String getDuracao_carregamento() {
        return duracao_carregamento;
    }

    public void setDuracao_carregamento(String duracao_carregamento) {
        this.duracao_carregamento = duracao_carregamento;
    }    
    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Integer getAvaria() {
        return avaria;
    }

    public void setAvaria(Integer avaria) {
        this.avaria = avaria;
    }

    public Integer getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(Integer tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    
    public String getSilo() { return silo;  }
    public void setSilo(String silo) {   this.silo = silo;  } 
    public boolean isToCancel() {
        return toCancel;
    }
    public void setToCancel(boolean toCancel) {
        this.toCancel = toCancel;
    }
    public boolean isExpedicaoCanCancel() {
        return expedicaoCanCancel;
    }
    public void setExpedicaoCanCancel(boolean expedicaoCanCancel) {
        this.expedicaoCanCancel = expedicaoCanCancel;
    }
    public boolean isMoagemCanCancel() {
        return moagemCanCancel;
    }
    public void setMoagemCanCancel(boolean moagemCanCancel) {
        this.moagemCanCancel = moagemCanCancel;
    }    

    public Usuario getUsuario_finalizacao() {
        return usuario_finalizacao;
    }

    public void setUsuario_finalizacao(Usuario usuario_finalizacao) {
        this.usuario_finalizacao = usuario_finalizacao;
    }
    
    public String getPlaca_do_pedido() {
        return placa_do_pedido;
    }

    public void setPlaca_do_pedido(String placa_do_pedido) {
        this.placa_do_pedido = placa_do_pedido;
    }
    
    public long getSessaoId() {
        return sessao;
    }

    public void setSessaoId(long sessao) {
        this.sessao = sessao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(Integer cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public boolean isPedido_cancelado() {
        return pedido_cancelado;
    }

    public void setPedido_cancelado(boolean pedido_cancelado) {
        this.pedido_cancelado = pedido_cancelado;
    }

    public Date getPedido_cancelamento() {
        return pedido_cancelamento;
    }

    public Date getPedido_finalizado() {
        return pedido_finalizado;
    }

    public void setPedido_finalizado(Date pedido_finalizado) {
        this.pedido_finalizado = pedido_finalizado;
    }

    public void setPedido_cancelamento(Date pedido_cancelamento) {
        this.pedido_cancelamento = pedido_cancelamento;
    }

    public Date getPedido_efetivado() {
        return pedido_efetivado;
    }

    public void setPedido_efetivado(Date pedido_efetivado) {
        this.pedido_efetivado = pedido_efetivado;
    }

    public Date getPedido_expedido() {
        return pedido_expedido;
    }

    public void setPedido_expedido(Date pedido_expedido) {
        this.pedido_expedido = pedido_expedido;
    }

    public Date getPedido_pendente() {
        return pedido_pendente;
    }

    public void setPedido_pendente(Date pedido_pendente) {
        this.pedido_pendente = pedido_pendente;
    }

    public Date getPedido_processando() {
        return pedido_processando;
    }

    public void setPedido_processando(Date pedido_processando) {
        this.pedido_processando = pedido_processando;
    }

    public Date getPedido_solicitacao_cancelamento() {
        return pedido_solicitacao_cancelamento;
    }

    public void setPedido_solicitacao_cancelamento(Date pedido_solicitacao_cancelamento) {
        this.pedido_solicitacao_cancelamento = pedido_solicitacao_cancelamento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Float getQuant() {
        return quant;
    }

    public void setQuant(Float quant) {
        this.quant = quant;
    }

    public String getStatus_pedido() {
        return status_pedido;
    }

    public void setStatus_pedido(String status_pedido) {
        this.status_pedido = status_pedido;
    }

    public Usuario getUsuario_efetivacao() {
        return usuario_efetivacao;
    }

    public void setUsuario_efetivacao(Usuario usuario_efetivacao) {
        this.usuario_efetivacao = usuario_efetivacao;
    }

    public Usuario getUsuario_expedicao() {
        return usuario_expedicao;
    }

    public void setUsuario_expedicao(Usuario usuario_expedicao) {
        this.usuario_expedicao = usuario_expedicao;
    }

    public Usuario getUsuario_pedido() {
        return usuario_pedido;
    }

    public void setUsuario_pedido(Usuario usuario_pedido) {
        this.usuario_pedido = usuario_pedido;
    }

    public Usuario getUsuario_processamento() {
        return usuario_processamento;
    }

    public void setUsuario_processamento(Usuario usuario_processamento) {
        this.usuario_processamento = usuario_processamento;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gma.common.entity.Pedido[ id=" + id + " ]";
    }
    
}
