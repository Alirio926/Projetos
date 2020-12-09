/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Alirio
 */
public class SessaoControl extends UnicastRemoteObject implements CallBackServerInterface, SessionInterface {
    
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
    private final ServerFormInterface sfi;
    private final Boolean debug = Boolean.FALSE;
    private Boolean unique;
    private Long id;
    public Map<Long, ClientInterface> interfaces;
    private List<Long> idList = new ArrayList<Long>(); 
    private int[] carregamentoFarelo = new int[2];
    private int[] carregamentoIndustrial = new int[2];
    private int[] carregamentoDomestica = new int[4];
    private int[] carregamentoBigBag = new int[2];
    
    public SessaoControl(ServerFormInterface sss)throws RemoteException{
        super();
        interfaces = new HashMap<Long, ClientInterface>();
        sfi=sss;
    }

    @Override
    public void registerForCallback(ClientInterface obj, Long id) throws RemoteException {
        if(interfaces.containsKey(id)){
            interfaces.remove(id);
            interfaces.put(id, obj);
        }else{
            interfaces.put(id, obj);
        }
        sfi.setNumerosConexao(interfaces.size());
    }

    @Override
    public void unregisterForCallback(ClientInterface obj, Long id) throws RemoteException {
        interfaces.remove(id);
        unregisterID(id);
        System.out.println("Interfaces registrados: "+interfaces.size());
        System.out.println("PIDs registrados:       "+idList.size());
        sfi.setNumerosConexao(interfaces.size());
    }

    @Override
    public Map<Long, ClientInterface> getInterfaceClientesList() throws RemoteException{
        return interfaces;
    }
       
    @Override
    public void sendMsgToCliente(String msg, String u) throws RemoteException {
        Iterator it = interfaces.values().iterator();
        fmt.format(GregorianCalendar.getInstance().getTime());
        while(it.hasNext())
            ((ClientInterface)it.next()).receiverMsgFromCliente(msg+"\n", 
                    fmt.format(GregorianCalendar.getInstance().getTime())+
                    " "+u+" diz: ");
    }
    /* metodo usando por usuarioservice */
    @Override
    public Long registerID() {
        
        int maxCount = 0;
        
        unique = true;
        //id = sessionID.nextLong();
        id = ThreadLocalRandom.current().nextLong(101, 999);
        
        if(idList.isEmpty()){
            if(debug)
                System.out.println("DEBUG SESSION.registerID() List is empty, end job.");
            idList.add(id);
            return id;
        }
        while(true){  
            for (int i=0;i<idList.size();i++) 
                if(idList.get(i).compareTo(id)==0) unique=false;
            
            // certifica que o id é unico
            if(unique){                    
                if(debug)
                    System.out.println("DEBUG SESSION.registerID() find unique ID, end job."); 
                idList.add(id);
                return id;
            }
            if(maxCount == 100){
                return 0L;
            }
            id = ThreadLocalRandom.current().nextLong(101, 999);
            maxCount++;            
            unique = true;
        }  
    }
    @Override
    public void unregisterID(Long id) {
        for(int i=0;i<idList.size();i++)
            if(idList.get(i).compareTo(id)==0)
                idList.remove(i);
        if(debug)
            System.out.println("DEBUG SESSION.unregisterID(Machine ID) end job.");
    }
    /* Metodo chamado pelos clientes */
    @Override
    public Long obterID() throws RemoteException {
        if(debug)
            System.out.println("DEBUG SESSION.obterID(Machine ID) Init var.");
        
        int maxCount = 0;
        id = ThreadLocalRandom.current().nextLong(101, 999);
        unique = true;
        
        if(debug)
            System.out.println("DEBUG SESSION.obterID()Id : "+id); 
        
        // lista esta vazia
        if(idList.isEmpty()){            
            if(debug)
                System.out.println("DEBUG SESSION.obterID() List is empty, end job."); 
            idList.add(id);
            return id;
        }
        // lista não esta vazia
        while(true){
            // checa se lista contem id.random
            for (int i=0;i<idList.size();i++) 
                if (idList.get(i).compareTo(id)==0) unique = false;
            // é unico?    
            if(unique){   
                if(debug)
                    System.out.println("DEBUG SESSION.obterID() List is not empty, set id: "+id+", end job."); 
                idList.add(id);
                return id;
            }
            if(maxCount == 100){
                if(debug)
                    System.out.println("DEBUG SESSION.obterID() max count, end job."); 
                return 0L;
            }
            // apos 100 interações do loop não foi possivel obter um ID unico.
            id = ThreadLocalRandom.current().nextLong(101, 999); 
            unique = true;
            maxCount++;
        }
    }
    
    @Override
    public Integer IniciarCarregamentoIndustrial(int cod){
        if(carregamentoIndustrial[0] == 0){
            carregamentoIndustrial[0] = cod;
            System.out.println("Carregamento de farinha industrial iniciado boca 01");
            return cod;
        }else if(carregamentoIndustrial[1] == 0){
            carregamentoIndustrial[1] = cod;
            System.out.println("Carregamento de farinha industrial iniciado boca 02");
            return cod;
        }else{
            return 0;
        }
    }

    @Override
    public void finalizarCarregamentoIndustrial(int cod) {
        if(carregamentoIndustrial[0] == cod){
            carregamentoIndustrial[0] = 0;
            System.out.println("Carregamento de farinha industrial finalizado boca 01");
        }else if(carregamentoIndustrial[1] == cod){
            carregamentoIndustrial[1] = 0;
            System.out.println("Carregamento de farinha industrial finalizado boca 02");
        }
    }
}
