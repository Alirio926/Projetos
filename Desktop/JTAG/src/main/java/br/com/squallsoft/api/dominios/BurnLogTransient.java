/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.api.dominios;

import java.io.Serializable;

/**
 *
 * @author aliri
 */
public class BurnLogTransient  implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long pedidoID;
    private Long burnID;
    private StringBuilder log;

    public Long getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Long pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Long getBurnID() {
        return burnID;
    }

    public void setBurnID(Long burnID) {
        this.burnID = burnID;
    }

    public StringBuilder getLog() {
        return log;
    }

    public void setLog(StringBuilder log) {
        this.log = log;
    }
    
    
}