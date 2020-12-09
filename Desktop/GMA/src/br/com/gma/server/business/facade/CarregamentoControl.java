/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Map;
import libsgma.server.CallbackServerCarregamento;
import libsgma.server.CarregamentoInterface;

/**
 *
 * @author Alirio
 */
public class CarregamentoControl extends UnicastRemoteObject implements CallbackServerCarregamento{

    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
    public CarregamentoInterface carregamento;
    
    public CarregamentoControl()throws RemoteException{
        super();
    }
    @Override
    public void registerForCallback(CarregamentoInterface ci) throws RemoteException {
        carregamento = ci;
    }
    
    @Override
    public CarregamentoInterface getInterface() throws RemoteException {
        return carregamento;
    }

    @Override
    public void sendMsgToCliente(String msg, String u) throws RemoteException {
        
    }    
}
