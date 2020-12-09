/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity  
@Table(name = "usuario") 
@PrimaryKeyJoinColumn(name = "id_log")
public class Usuario extends Log implements Serializable {
    private static final long serialVersionUID = 1L;
      
    @Column(length=9,nullable=false,unique=true)
    private String gma;
    @Column(length=50,nullable=false,unique=true)
    private String username;
    private String senha;
    private boolean status;
    private long sessao;    
    @ManyToOne
    private Cargo cargo;
    

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    
    public long getSessaoId() {
        return sessao;
    }

    public void setSessaoId(long sessao) {
        this.sessao = sessao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getGma() {
        return gma;
    }

    public void setGma(String gma) {
        this.gma = gma;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


   
}
