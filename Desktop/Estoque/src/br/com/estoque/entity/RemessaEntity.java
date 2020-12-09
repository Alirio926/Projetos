/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Alirio
 */
@Entity
public class RemessaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String destino; 
    private Integer qtdRolos;
    private Integer qtdRolosPendentes;
    private Long numRemessa;
    private Integer numNotaFiscal;
    private String motivo;
    @OneToMany
    private Set<RoloEntity> rolo;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataEnvio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataRetorno;
    @OneToOne
    private RoloEntity re;

    public RoloEntity getRe() {
        return re;
    }

    public void setRe(RoloEntity re) {
        this.re = re;
    }   
    
    public Integer getQtdRolosPendentes() {
        return qtdRolosPendentes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQtdRolosPendentes(Integer qtdRolosPendentes) {
        this.qtdRolosPendentes = qtdRolosPendentes;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Set<RoloEntity> getRolo() {
        return rolo;
    }

    public void setRolo(Set<RoloEntity> rolo) {
        this.rolo = rolo;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataRetorno() {
        return dataRetorno;
    }

    public void setDataRetorno(Date dataRetorno) {
        this.dataRetorno = dataRetorno;
    }

    public Integer getQtdRolos() {
        return qtdRolos;
    }

    public void setQtdRolos(Integer qtdRolos) {
        this.qtdRolos = qtdRolos;
    }

    public Long getNumRemessa() {
        return numRemessa;
    }

    public void setNumRemessa(Long numRemessa) {
        this.numRemessa = numRemessa;
    }

    public Integer getNumNotaFiscal() {
        return numNotaFiscal;
    }

    public void setNumNotaFiscal(Integer numNotaFiscal) {
        this.numNotaFiscal = numNotaFiscal;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
