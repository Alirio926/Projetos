/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

/**
 *
 * @author Administrador
 */
public class Generics <T,N>{
    T remote;
    N id;
    public Generics(T remote,N id){
        this.remote = remote; // Interface extends Remote
        this.id = id; // Long
    }
    public T getInterface(){
        return remote;
    }
    public N getId(){
        return id;
    }
    public void setInterface(T remote){
        this.remote = remote;        
    }
    public void setId(N id){
        this.id = id;
    }
    
}
