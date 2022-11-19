/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.squallsoft.api.dominios;

import java.time.LocalDate;

public class Mapper{

    private Long id;    
    private String chip_name;
    private String supported_boards;
    private LocalDate data_ativacao;
    private Boolean ativo;	
    private byte[] xsvf_file;

    public Mapper() {}

    public Mapper(String chip_name, String supported_boards, LocalDate data_ativacao, Boolean ativo, byte[] xsvf_file) {
            super();
            this.chip_name = chip_name;
            this.supported_boards = supported_boards;
            this.data_ativacao = data_ativacao;
            this.ativo = ativo;
            this.xsvf_file = xsvf_file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getChip_name() {
            return chip_name;
    }

    public void setChip_name(String chip_name) {
            this.chip_name = chip_name;
    }

    public String getSupported_boards() {
            return supported_boards;
    }

    public void setSupported_boards(String supported_boards) {
            this.supported_boards = supported_boards;
    }

    public LocalDate getData_ativacao() {
            return data_ativacao;
    }

    public void setData_ativacao(LocalDate data_ativacao) {
            this.data_ativacao = data_ativacao;
    }

    public Boolean getAtivo() {
            return ativo;
    }

    public void setAtivo(Boolean ativo) {
            this.ativo = ativo;
    }

    public byte[] getXsvf_file() {
            return xsvf_file;
    }

    public void setXsvf_file(byte[] xsvf_file) {
            this.xsvf_file = xsvf_file;
    }	
}
