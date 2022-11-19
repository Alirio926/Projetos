package com.squallsoft.api.dominios;

import java.io.Serializable;

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