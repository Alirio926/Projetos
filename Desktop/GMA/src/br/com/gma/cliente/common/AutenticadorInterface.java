/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.common;

import br.com.gma.server.common.entity.Sessao;

/**
 *
 * @author Alirio
 */
public interface AutenticadorInterface {
    public void autenticarUsuario();
    public Sessao getSessao();
}
