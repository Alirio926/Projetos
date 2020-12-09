/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.relatorios.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Alirio
 */
public class ProducaoEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carregamentoTipo;
    private Integer cargaOpen;
    private Integer cargaClose;

    public String getCarregamentotipo() {
        return carregamentoTipo;
    }

    public void setCarregamentotipo(String carregamentoTipo) {
        this.carregamentoTipo = carregamentoTipo;
    }

    public Integer getCargaopen() {
        return cargaOpen;
    }

    public void setCargaopen(Integer cargaOpen) {
        this.cargaOpen = cargaOpen;
    }

    public Integer getCargaclose() {
        return cargaClose;
    }

    public void setCargaclose(Integer cargaClose) {
        this.cargaClose = cargaClose;
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
        if (!(object instanceof ProducaoEntity)) {
            return false;
        }
        ProducaoEntity other = (ProducaoEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gma.relatorios.model.ProducaoEntity[ id=" + id + " ]";
    }
    
}
