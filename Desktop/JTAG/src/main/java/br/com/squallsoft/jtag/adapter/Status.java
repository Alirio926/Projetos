/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

/**
 *
 * @author alirio.filho
 */
public enum Status {
    BURN_OK("SUCESSO!"),
    BURN_ERROR("ERROR!"),
    SERIAL_ERROR("NÃO CONECTADO!"),
    DEVICE(""),
    HASH(""),
    IMPORTANT(""),
    DESCONHECIDO("");
    
    private String description ;
    
    Status(String desc){
        this.description = desc;
    }
    public String getDescription(){ return description; }
}
