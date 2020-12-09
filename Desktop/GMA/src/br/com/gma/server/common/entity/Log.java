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
@Table(name = "log") 
@Inheritance(strategy= InheritanceType.JOINED) 
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_registro;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date data_update;
    private String hora_registro;
    private String hora_update;
    @OneToOne
    private Usuario usuario_update;
    @OneToOne
    private Usuario usuario_registro;

    public Date getData_registro() {
        return data_registro;
    }

    public void setData_registro(Date data_registro) {
        this.data_registro = data_registro;
    }

    public Date getData_update() {
        return data_update;
    }

    public void setData_update(Date data_update) {
        this.data_update = data_update;
    }

    public String getHora_registro() {
        return hora_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public String getHora_update() {
        return hora_update;
    }

    public void setHora_update(String hora_update) {
        this.hora_update = hora_update;
    }

    public Usuario getUsuario_registro() {
        return usuario_registro;
    }

    public void setUsuario_registro(Usuario usuario_registro) {
        this.usuario_registro = usuario_registro;
    }

    public Usuario getUsuario_update() {
        return usuario_update;
    }

    public void setUsuario_update(Usuario usuario_update) {
        this.usuario_update = usuario_update;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Log other = (Log) obj;
        return true;
    }

    @Override
    public String toString() {
        return "br.com.gma.common.entity.Info[ id=" + id + " ]";
    }
    
}
