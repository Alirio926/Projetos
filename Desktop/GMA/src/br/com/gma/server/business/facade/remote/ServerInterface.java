/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade.remote;

/**
 *
 * @author Administrador
 */
public interface ServerInterface {
    public void registerUsuarioService();
    public void unregisterUsuarioService();
    
    public void registerPedidoService();
    public void unregisterPedidoService();
    
    public void registerClienteService();
    public void unregisterClienteService();
    
    public void registerProdutoService();
    public void unregisterProdutoService();
    
    public void registerSessaoService();
    public void unregisterSessaoService();
    
    public void registerCargoService();
    public void unregisterCargoService();
}
