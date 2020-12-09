/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.relatorios.model;

import java.util.Date;

/**
 *
 * @author Alirio
 */
public class CarregamentoEntity implements Comparable{
    
    private String Produto;
    private Float Quant;
    private String Cliente;
    private String Placa;
    private Integer Carga;
    private Date Inicio;
    private Date Fim;
    private Integer Avaria;
    private String Duracao;
    private Float Media;
    private Float Expedido;
    private Float Estoque;

    public CarregamentoEntity(String Produto, Float Quant, String Cliente, String Placa, Integer Carga, Date Inicio, Date Fim, Integer Avaria, String Duracao, Float Media, Float qtd_exp, Float est) {
        this.Produto = Produto;
        this.Quant = Quant;
        this.Cliente = Cliente;
        this.Placa = Placa;
        this.Carga = Carga;
        this.Inicio = Inicio;
        this.Fim = Fim;
        this.Avaria = Avaria;
        this.Duracao = Duracao;
        this.Media = Media;
        this.Expedido = qtd_exp;
        this.Estoque = est;
    }

    public Float getEstoque() {
        return Estoque;
    }

    public void setEstoque(Float Estoque) {
        this.Estoque = Estoque;
    }

    public Float getExpedido() {
        return Expedido;
    }
    public void setExpedido(Float Expedido) {
        this.Expedido = Expedido;
    }    
    public String getProduto() {
        return Produto;
    }

    public void setProduto(String Produto) {
        this.Produto = Produto;
    }

    public Float getQuant() {
        return Quant;
    }

    public void setQuant(Float Quant) {
        this.Quant = Quant;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public Integer getCarga() {
        return Carga;
    }

    public void setCarga(Integer Carga) {
        this.Carga = Carga;
    }

    public Date getInicio() {
        return Inicio;
    }

    public void setInicio(Date Inicio) {
        this.Inicio = Inicio;
    }

    public Date getFim() {
        return Fim;
    }

    public void setFim(Date Fim) {
        this.Fim = Fim;
    }

    public Integer getAvaria() {
        return Avaria;
    }

    public void setAvaria(Integer Avaria) {
        this.Avaria = Avaria;
    }

    public String getDuracao() {
        return Duracao;
    }

    public void setDuracao(String Duracao) {
        this.Duracao = Duracao;
    }

    public Float getMedia() {
        return Media;
    }

    public void setMedia(Float Media) {
        this.Media = Media;
    }
    @Override
    public int compareTo(Object o) {
        int comp = ((CarregamentoEntity)o).getCarga();
        
        return this.Carga-comp;
    }
}
