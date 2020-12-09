/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import java.util.Vector;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Administrador
 */
@Entity  
@Table(name = "cliente") 
@PrimaryKeyJoinColumn(name = "id_log")
public class Cliente extends Log implements Serializable,Comparable<Cliente>  {
    private static final long serialVersionUID = 1L;
    
    @Column(length=30,nullable=false,unique=false)
    private String nome_cliente;
    private Vector<String> placa = new Vector<String>();
    private boolean status;
    private long sessao;

    public long getSessaoId() {
        return sessao;
    }

    public void setSessaoId(long sessao) {
        this.sessao = sessao;
    }

    public Vector<String> getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa.addElement(placa);
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }


    public boolean Status() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return nome_cliente;
    }

    @Override
    public int compareTo(Cliente o) {
        return nome_cliente.compareTo(o.getNome_cliente());  
    }
    
}
