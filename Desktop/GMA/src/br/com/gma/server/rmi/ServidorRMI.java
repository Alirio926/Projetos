/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.rmi;

import br.com.gma.server.business.facade.*;
import br.com.gma.server.business.facade.remote.ServerInterface;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.presentation.ServerStatusForm;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import libsgma.jpa.IndustrialJpaController;
import libsgma.service.CarregamentoINDSerice;

/**
 *
 * @author Administrador
 */
public class ServidorRMI implements ServerInterface{
    private Factory factory;
    private Registry registry;
    
    public ServidorRMI(int p, Factory f){
        factory = f;
        port = p;
        
        try { log = new FileOutputStream(LOG_FILE, true);}catch(IOException e){}
        startServidorRMI();
    }
    public ServidorRMI(Factory f){
        try { log = new FileOutputStream(LOG_FILE, true);}catch(IOException e){}
        factory = f;
        startServidorRMI();
    }
    private void startServidorRMI(){
        try{
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                System.out.println(info.getName());
            }
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        }catch(/*UnsupportedLookAndFeelException*/ClassNotFoundException u){
            u.printStackTrace();
	} catch (InstantiationException u) {                         
            u.printStackTrace();
        } catch (IllegalAccessException u) {
            u.printStackTrace();
        } catch (UnsupportedLookAndFeelException u) {
            u.printStackTrace();
        }                         
        try{
            registry  = LocateRegistry.createRegistry(port);
            //  = LocateRegistry.getRegistry(port);
            
        }catch(Exception e1){
            System.err.print(e1.getMessage());
            writerLog("Erro registrando porta: "+port+":\n"+e1.getMessage());
        }
        if(System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        ServerStatusForm serverForm = new ServerStatusForm(this);
        try{
            versionControl = new VersionControl("0.3");
            registry.rebind("VERSION", versionControl);
            
            servidor = new SessaoControl(serverForm);
            carregamentoControl = new CarregamentoControl();
            indService = new CarregamentoINDSerice(factory.getfactory(), carregamentoControl);
            
            pedido = new PedidoService(factory, servidor,serverForm,servidor,indService);
            cliente = new ClienteService(factory, servidor,serverForm);
            produto = new ProdutoService(factory, servidor,serverForm);
            usuario = new UsuarioService(factory, servidor,serverForm,servidor);
            cargo = new CargoService(factory, servidor,serverForm);
                        
            registry.rebind("INDUSTRIAL", indService);
            
            registry.bind("SERVIDOR", servidor);
            registry.bind("CARREGAMENTO", carregamentoControl);
            
            
            
            
            
            
            System.out.println(Arrays.toString(LocateRegistry.getRegistry().list()));
                        
            serverForm.setVisible(true);
            System.out.println("Servidor RMI iniciado.");           
            
        }catch(Exception e){
            e.printStackTrace();
            writerLog("Erro RMI:\n"+e.getMessage());
        } 
    }
        
    private void writerLog(String message){
        if(true)
        {
            String output;
            Date time = new Date();
            output = DateFormat.getDateInstance().format(time) +" "+
                     DateFormat.getTimeInstance().format(time) +
                     ": " + message + "\r\n";
            try
            {
                log.write(output.getBytes());
            }catch(IOException e){}
        }
    }
    
    private CarregamentoControl carregamentoControl;
    private CarregamentoINDSerice indService;
    UsuarioService usuario;
    PedidoService pedido;
    ClienteService cliente;
    ProdutoService produto;
    SessaoControl servidor;
    CargoService cargo;
    VersionControl versionControl;
    
    private int port = 1098;
    private String ser = "ServidorRMI";
    private FileOutputStream log;
    public static final String LOG_FILE = "Servidor_Nivel_1.log";

    @Override
    public void registerUsuarioService() {
        try {
            registry.rebind("USUARIO", usuario);
        } catch (RemoteException ex) {}
    }
    @Override
    public void unregisterUsuarioService() {
        try {
            registry.unbind("USUARIO");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    @Override
    public void registerPedidoService() {
        try {
            registry.rebind("PEDIDO", pedido);
        } catch (RemoteException ex) {ex.printStackTrace();}
    }
    @Override
    public void unregisterPedidoService() {
        try {
            registry.unbind("PEDIDO");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    @Override
    public void registerClienteService() {
        try {
            registry.rebind("CLIENTE", cliente);
        } catch (RemoteException ex) {}
    }
    @Override
    public void unregisterClienteService() {
        try {
            registry.unbind("CLIENTE");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    @Override
    public void registerProdutoService() {
        try {
            registry.rebind("PRODUTO", produto);
        } catch (RemoteException ex) {}
    }
    @Override
    public void unregisterProdutoService() {
        try {
            registry.unbind("PRODUTO");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    @Override
    public void registerSessaoService() {
        try {
            registry.rebind("SESSAO", servidor);
        } catch (RemoteException ex) {}
    }
    @Override
    public void unregisterSessaoService() {
        try {
            registry.unbind("SESSAO");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    @Override
    public void registerCargoService() {
        try {
            registry.rebind("CARGO", cargo);
        } catch (RemoteException ex) {}
    }
    @Override
    public void unregisterCargoService() {
        try {
            registry.unbind("CARGO");
        } catch (NotBoundException ex) {} catch (RemoteException ex) {}
    }
    
}
