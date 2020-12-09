/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Alirio
 */
@Entity
@Table(name = "rolo") 
public class RoloEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length=10,nullable=false,unique=true)
    private String serial;
    @Column(length=10,nullable=true,unique=false)
    private String localizacao;
    @Column(length=10,nullable=true,unique=false)
    private String tag;
    @Column(length=10,nullable=true,unique=false)
    private String passagem;
    @Column(length=10,nullable=true,unique=false)
    private String raiacao;
    

    public String getPassagem() {
        return passagem;
    }

    public void setPassagem(String passagem) {
        this.passagem = passagem;
    }

    public String getRaiacao() {
        return raiacao;
    }

    public void setRaiacao(String raiacao) {
        this.raiacao = raiacao;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String local) {
        this.localizacao = local;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
