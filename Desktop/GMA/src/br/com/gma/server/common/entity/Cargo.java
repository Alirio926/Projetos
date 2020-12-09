/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.common.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Alirio
 */
@Entity  
@Table(name = "cargo") 
@Inheritance(strategy= InheritanceType.JOINED) 
public class Cargo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cargo;    
    private boolean permissaoCadastrarCliente;
    private boolean permissaoEditarCliente;
    private boolean permissaoDesativarCliente;
    private boolean permissaoApagarCliente;
    private boolean permissaoCadastrarUsuario;
    private boolean permissaoEditarUsuario;
    private boolean permissaoDesativarUsuario;
    private boolean permissaoApagarUsuario;
    private boolean permissaoCadastrarProduto;
    private boolean permissaoEditarProduto;
    private boolean permissaoDesativarProduto;
    private boolean permissaoApagarProduto;
    private boolean permissaoCadastrarPedido;
    private boolean permissaoEditarPedido;
    private boolean permissaoExpedirPedido;
    private boolean permissaoAtenderPedido;
    private boolean permissaoFinalizarPedido;
    private boolean permissaoCancelarPedido;
    
    
    
    private boolean permissaoEspecial;

    public boolean isPermissaoEspecialPedido() {
        return permissaoEspecial;
    }

    public void setPermissaoEspecialPedido(boolean permissaoEspecialPedido) {
        this.permissaoEspecial = permissaoEspecialPedido;
    }
    
    public boolean isPermissaoCancelarPedido() {
        return permissaoCancelarPedido;
    }

    public void setPermissaoCancelarPedido(boolean permissaoCancelarPedido) {
        this.permissaoCancelarPedido = permissaoCancelarPedido;
    }

    public boolean isPermissaoExpedirPedido() {
        return permissaoExpedirPedido;
    }

    public void setPermissaoExpedirPedido(boolean permissaoExpedirPedido) {
        this.permissaoExpedirPedido = permissaoExpedirPedido;
    }

    public boolean isPermissaoAtenderPedido() {
        return permissaoAtenderPedido;
    }

    public void setPermissaoAtenderPedido(boolean permissaoAtenderPedido) {
        this.permissaoAtenderPedido = permissaoAtenderPedido;
    }

    public boolean isPermissaoFinalizarPedido() {
        return permissaoFinalizarPedido;
    }

    public void setPermissaoFinalizarPedido(boolean permissaoFinalizarPedido) {
        this.permissaoFinalizarPedido = permissaoFinalizarPedido;
    }
    public Long getCargoId() {
        return id;
    }

    public void setCargoId(long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public boolean isPermissaoApagarCliente() {
        return permissaoApagarCliente;
    }

    public void setPermissaoApagarCliente(boolean permissaoApagarCliente) {
        this.permissaoApagarCliente = permissaoApagarCliente;
    }

    public boolean isPermissaoApagarProduto() {
        return permissaoApagarProduto;
    }

    public void setPermissaoApagarProduto(boolean permissaoApagarProduto) {
        this.permissaoApagarProduto = permissaoApagarProduto;
    }

    public boolean isPermissaoApagarUsuario() {
        return permissaoApagarUsuario;
    }

    public void setPermissaoApagarUsuario(boolean permissaoApagarUsuario) {
        this.permissaoApagarUsuario = permissaoApagarUsuario;
    }

    public boolean isPermissaoCadastrarCliente() {
        return permissaoCadastrarCliente;
    }

    public void setPermissaoCadastrarCliente(boolean permissaoCadastrarCliente) {
        this.permissaoCadastrarCliente = permissaoCadastrarCliente;
    }

    public boolean isPermissaoCadastrarProduto() {
        return permissaoCadastrarProduto;
    }

    public void setPermissaoCadastrarProduto(boolean permissaoCadastrarProduto) {
        this.permissaoCadastrarProduto = permissaoCadastrarProduto;
    }

    public boolean isPermissaoCadastrarUsuario() {
        return permissaoCadastrarUsuario;
    }

    public void setPermissaoCadastrarUsuario(boolean permissaoCadastrarUsuario) {
        this.permissaoCadastrarUsuario = permissaoCadastrarUsuario;
    }

    public boolean isPermissaoDesativarCliente() {
        return permissaoDesativarCliente;
    }

    public void setPermissaoDesativarCliente(boolean permissaoDesativarCliente) {
        this.permissaoDesativarCliente = permissaoDesativarCliente;
    }

    public boolean isPermissaoDesativarProduto() {
        return permissaoDesativarProduto;
    }

    public void setPermissaoDesativarProduto(boolean permissaoDesativarProduto) {
        this.permissaoDesativarProduto = permissaoDesativarProduto;
    }

    public boolean isPermissaoDesativarUsuario() {
        return permissaoDesativarUsuario;
    }

    public void setPermissaoDesativarUsuario(boolean permissaoDesativarUsuario) {
        this.permissaoDesativarUsuario = permissaoDesativarUsuario;
    }

    public boolean isPermissaoEditarCliente() {
        return permissaoEditarCliente;
    }

    public void setPermissaoEditarCliente(boolean permissaoEditarCliente) {
        this.permissaoEditarCliente = permissaoEditarCliente;
    }

    public boolean isPermissaoEditarProduto() {
        return permissaoEditarProduto;
    }

    public void setPermissaoEditarProduto(boolean permissaoEditarProduto) {
        this.permissaoEditarProduto = permissaoEditarProduto;
    }

    public boolean isPermissaoEditarUsuario() {
        return permissaoEditarUsuario;
    }

    public void setPermissaoEditarUsuario(boolean permissaoEditarUsuario) {
        this.permissaoEditarUsuario = permissaoEditarUsuario;
    }

    public boolean isPermissaoCadastrarPedido() {
        return permissaoCadastrarPedido;
    }

    public void setPermissaoCadastrarPedido(boolean permissaoCadastrarPedido) {
        this.permissaoCadastrarPedido = permissaoCadastrarPedido;
    }

    public boolean isPermissaoEditarPedido() {
        return permissaoEditarPedido;
    }

    public void setPermissaoEditarPedido(boolean permissaoEditarPedido) {
        this.permissaoEditarPedido = permissaoEditarPedido;
    }

}
