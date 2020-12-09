/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.relatorios.model;

/**
 *
 * @author Alirio
 */
public class FarinhasEntity implements Comparable{

    private String Produto;
    private Integer Produtoclass;
    private Float Expedido;
    private Float Saidaestoque;
    private Float Estoqueatual;

    public FarinhasEntity(String Produto, Float Expedido, Float SaidaEstoque, Float EstoqueAtual, Integer prodClass) {
        this.Produto = Produto;
        this.Expedido = Expedido;
        this.Saidaestoque = SaidaEstoque;
        this.Estoqueatual = EstoqueAtual;
        this.Produtoclass = prodClass;
    }
    public FarinhasEntity(){}
    public String getProduto() {
        return Produto;
    }

    public Integer getProdutoclass() {
        return Produtoclass;
    }

    public void setProdutoclass(Integer Produtoclass) {
        this.Produtoclass = Produtoclass;
    }
    
    public void setProduto(String Produto) {
        this.Produto = Produto;
    }

    public Float getExpedido() {
        return Expedido;
    }

    public void setExpedido(Float Expedido) {
        this.Expedido = Expedido;
    }

    public Float getSaidaestoque() {
        return Saidaestoque;
    }

    public void setSaidaestoque(Float SaidaEstoque) {
        this.Saidaestoque = SaidaEstoque;
    }

    public Float getEstoqueatual() {
        return Estoqueatual;
    }

    public void setEstoqueatual(Float EstoqueAtual) {
        this.Estoqueatual = EstoqueAtual;
    }
    
    
    @Override
    public int compareTo(Object o) {
        int classe = ((FarinhasEntity)o).getProdutoclass();
        return this.Produtoclass-classe;
    }
    
}
