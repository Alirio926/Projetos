/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.common;

import br.com.gma.server.business.facade.remote.*;

/**
 *
 * @author Alirio
 */
public interface MainFormInterface {
    public RemoteUsuario obterUsuarioRemote();
    public RemoteCliente obterClienteRemote();
    public RemoteProduto obterProdutoRemote();
    public RemotePedido obterPedidoRemote();
    public RemoteCargo obterCargoRemote();
}
