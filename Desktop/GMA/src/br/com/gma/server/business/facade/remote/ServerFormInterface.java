/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.server.business.facade.remote;

/**
 *
 * @author Alirio
 */
public interface ServerFormInterface {
   public void setNumerosUsuarios(Integer i);
   public void setNumerosCargos(Integer i);
   public void setNumerosPedidos(Integer i);
   public void setNumerosProdutos(Integer i);
   public void setNumerosClientes(Integer i);
   public void setNumerosConexao(Integer i);
}
