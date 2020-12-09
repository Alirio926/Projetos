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
import javax.persistence.Temporal;

/**
 *
 * @author Alirio
 */
@Entity
public class InstallEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
    @OneToMany
    private Set<RoloEntity> rolo;
    private String tag;
    private String diagrama;
    private Long horimetro;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataInstalacao;

    public Set<RoloEntity> getRolo() {
        return rolo;
    }

    public void setRolo(Set<RoloEntity> rolo) {
        this.rolo = rolo;
    }
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDiagrama() {
        return diagrama;
    }

    public void setDiagrama(String diagrama) {
        this.diagrama = diagrama;
    }

    public Long getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(Long horimetro) {
        this.horimetro = horimetro;
    }

    public Date getDataInstalacao() {
        return dataInstalacao;
    }

    public void setDataInstalacao(Date dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
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
        if (!(object instanceof InstallEntity)) {
            return false;
        }
        InstallEntity other = (InstallEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.estoque.common.InstallEntity[ id=" + id + " ]";
    }
    
}
