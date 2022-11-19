/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squallsoft.api.dominios;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author aliri
 */
public class LogEntity implements Serializable{
    
    private LocalDateTime dataTime;
    private String mapper;
    private String log;
    private long pedidoId;
    private Long burnID;
    //private long processId;
    private Boolean success;

    public Long getBurnID() {
        return burnID;
    }

    public void setBurnID(Long burnID) {
        this.burnID = burnID;
    }
    
    
    public String getMes(){
        return dataTime.getMonth().name();
    }
    public int getDia(){
        return dataTime.getDayOfMonth();
    }

    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }    
    public String getLog() {
        return log;
    }
    public void setLog(String log) {
        this.log = log;
    }    
    public LocalDateTime getDataTime() {
        return dataTime;
    }
    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }
    public String getMapper() {
        return mapper;
    }
    public void setMapper(String mapper) {
        this.mapper = mapper;
    }
    public long getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(long pedidoId) {
        this.pedidoId = pedidoId;
    }   
}
