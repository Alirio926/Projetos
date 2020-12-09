/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Administrador
 */
@Entity  
@Table(name = "produto") 
@PrimaryKeyJoinColumn(name = "id_log")
public class Produto extends Log implements Serializable, Comparable {
    private static final long serialVersionUID = 1L;
        
    @Column(length=10,nullable=false,unique=true)
    private Integer cod_produto;
    private Integer classe_produto;
    
    @Column(length=30,nullable=false,unique=true)
    private String nome_produto;
    private boolean status;
    private long sessao;
    private Float estoque;
    @Transient
    private Float est_final;
    @Transient
    private Float est_desj;
    
    @Transient
    public Float getEst_final() {
        return est_final;
    }
    @Transient
    public void setEst_final(Float est_final) {
        this.est_final = est_final;
    }
    @Transient
    public Float getEst_desj() {
        return est_desj;
    }
    @Transient
    public void setEst_desj(Float est_desj) {
        this.est_desj = est_desj;
    }
    
    public Float getEstoque() {  return estoque; }

    public void setEstoque(Float estoque) {  this.estoque = estoque;  }
    
    public Integer getClasse_produto() {
        return classe_produto;
    }

    public void setClasse_produto(Integer tipo_produto) {
        this.classe_produto = tipo_produto;
    }
    
    public long getSessaoId() {
        return sessao;
    }

    public void setSessaoId(long sessao) {
        this.sessao = sessao;
    }

    public Integer getCod_produto() {
        return cod_produto;
    }

    public void setCod_produto(Integer cod_produto) {
        this.cod_produto = cod_produto;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public int compareTo(Object o) {
        int classe = ((Produto)o).getClasse_produto();
        return this.classe_produto-classe;
    }
}

