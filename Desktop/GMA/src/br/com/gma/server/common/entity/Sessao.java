/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Alirio Oliveira
 */
@Entity 
public class Sessao implements Serializable{
    
    private static final long serialVersionUID = 1L;
     @Id
    private long id;
    private Boolean session_valida;
    private Long sessionId=0l;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date Data_Login;
    private String Hora_Ultimo_Acesso;
    @OneToOne
    private Usuario usuario;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getData_Login() {
        return Data_Login;
    }

    public void setData_Login(Date Data_Login) {
        this.Data_Login = Data_Login;
    }

    public String getHora_Ultimo_Acesso() {
        return Hora_Ultimo_Acesso;
    }

    public void setHora_Ultimo_Acesso(String Hora_Ultimo_Acesso) {
        this.Hora_Ultimo_Acesso = Hora_Ultimo_Acesso;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getSession_valida() {
        return session_valida;
    }

    public void setSession_valida(Boolean session_valida) {
        this.session_valida = session_valida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
