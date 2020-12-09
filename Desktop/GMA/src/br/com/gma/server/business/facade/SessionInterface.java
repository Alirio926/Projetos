/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.business.facade;

/**
 *
 * @author Alirio
 */
public interface SessionInterface {
    public Long registerID();
    public void unregisterID(Long Id);
    public Integer IniciarCarregamentoIndustrial(int cod);
    public void finalizarCarregamentoIndustrial(int cod);
    
}
