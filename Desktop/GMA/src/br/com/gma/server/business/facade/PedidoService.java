/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.server.business.facade;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.relatorios.model.CarregamentoEntity;
import br.com.gma.relatorios.model.ProducaoEntity;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemotePedido;
import br.com.gma.server.business.facade.remote.ServerFormInterface;
import static br.com.gma.server.common.entity.CONSTANTES.BIG_BAG;
import static br.com.gma.server.common.entity.CONSTANTES.BIG_BAG_SMALL;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_BUSY;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_EXCE;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_OK;
import static br.com.gma.server.common.entity.CONSTANTES.DOMESTICA;
import static br.com.gma.server.common.entity.CONSTANTES.ERRO;
import static br.com.gma.server.common.entity.CONSTANTES.FARELO;
import static br.com.gma.server.common.entity.CONSTANTES.INDUSTRIAL;
import static br.com.gma.server.common.entity.CONSTANTES.INFORMACAO;
import br.com.gma.server.common.entity.Pedido;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.persistence.PedidoJpaController;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import libsgma.server.CarregamentoInterface;
import libsgma.service.CarregamentoINDSerice;

/**
 *
 * @author Administrador
 */
public class PedidoService extends UnicastRemoteObject implements RemotePedido{

    private final CallBackServerInterface callBack;
    private final PedidoJpaController jpa;
    private final ServerFormInterface sfi;
    private final SessionInterface servidor;
    private final CarregamentoINDSerice indService;
    
    
    public PedidoService(Factory em, CallBackServerInterface c, ServerFormInterface sss, SessionInterface si, CarregamentoINDSerice cc)throws RemoteException{
        super();
        callBack = c;
        jpa = new PedidoJpaController(em.getfactory());
        sfi=sss;
        sfi.setNumerosPedidos(jpa.getPedidoCount());
        servidor=si;
        indService=cc;
    }

    @Override // 1 expedição à Server
    public void solicitarCancelamentoPedido(final Pedido p, Long sessaoId) throws RemoteException {
        /* esse ID é gerado automaticamente pelo programa quando inciado */
        if(p.getStatus_pedido().equalsIgnoreCase("Pendente")){            
            try {
                p.setStatus_pedido("Cancelado");
                p.setPedido_cancelado(true);
                p.setPedido_cancelamento(GregorianCalendar.getInstance().getTime());
                p.setExpedicaoCanCancel(false);
                p.setMoagemCanCancel(false);
                p.setToCancel(true);
                jpa.alterar(p);
                Iterator it =  callBack.getInterfaceClientesList().values().iterator();
                /* avisa todos os clientes que existe update de pedidos*/
                while(it.hasNext())
                    try{
                        ((ClientInterface)it.next()).updatePedidoStatus(p);
                    }catch(RemoteException e){ System.out.println(e.getMessage()); }
                callBack.getInterfaceClientesList().get(sessaoId)
                        .receiverMsgFromServer(INFORMACAO, "Pedido cancelado.");                
            } catch (Exception ex) {
                callBack.getInterfaceClientesList().get(sessaoId)
                        .receiverMsgFromServer(ERRO, "Falha ao tentar acessar o BD: "+ex.getMessage());
            }
        }else{
            //p.setStatus_pedido("P/ cancelar");
            p.setPedido_cancelamento(GregorianCalendar.getInstance().getTime());
            p.setExpedicaoCanCancel(false);
            p.setMoagemCanCancel(true);
            p.setToCancel(true);
            try {
                jpa.alterar(p);
            } catch (Exception ex) {
                callBack.getInterfaceClientesList().get(sessaoId)
                        .receiverMsgFromServer(ERRO, "Falha ao tentar acessar o BD: "+ex.getMessage());
            }
            final Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            /* avisa todos os clientes que existe update de pedidos*/
            while(it.hasNext()){
                ((ClientInterface)it.next()).updatePedidoStatus(p);/* enviado solicitação a moagem */                
            }
        }
        
    }

    @Override // 3.1 moagem à Server
    public void cancelarPedido(Pedido p, Long sessaoId) throws RemoteException {
        try {
                p.setStatus_pedido("Cancelado");
                p.setPedido_cancelado(true);
                p.setPedido_cancelamento(GregorianCalendar.getInstance().getTime());
                p.setExpedicaoCanCancel(false);
                p.setMoagemCanCancel(false);
                p.setToCancel(true);
                jpa.alterar(p);
                Iterator it =  callBack.getInterfaceClientesList().values().iterator();
                /* avisa todos os clientes que existe update de pedidos*/
                while(it.hasNext())
                    try{
                        ((ClientInterface)it.next()).updatePedidoStatus(p);
                    }catch(RemoteException e){ System.out.println(e.getMessage());}
                
                callBack.getInterfaceClientesList().get(sessaoId)
                        .receiverMsgFromServer(INFORMACAO, "Pedido cancelado.");
            } catch (Exception ex) {
                callBack.getInterfaceClientesList().get(sessaoId)
                        .receiverMsgFromServer(ERRO, "Falha ao tentar acessar o BD: "+ex.getMessage());
            }
    }

    @Override // 3.2 moagem à Server
    public void pedidoDeCancelamentoRecusado(Pedido p, Long sessaoId) throws RemoteException {
          
    }

    @Override // expedição
    public void abrirPedido(Pedido p, Long sessaoId) throws RemoteException {
        try {     
            switch(p.getProduto().getClasse_produto()){
                case INDUSTRIAL:   
                    p.setPedido_pendente(GregorianCalendar.getInstance().getTime());
                    break;
                case DOMESTICA:                
                    p.setPedido_pendente(GregorianCalendar.getInstance().getTime());
                    p.setPedido_expedido(GregorianCalendar.getInstance().getTime());
                    p.setPedido_processando(GregorianCalendar.getInstance().getTime());
                    p.setPedido_efetivado(GregorianCalendar.getInstance().getTime());
                    break;
                case BIG_BAG:
                    p.setPedido_pendente(GregorianCalendar.getInstance().getTime());
                    break;
                case FARELO:
                    p.setPedido_expedido(GregorianCalendar.getInstance().getTime());
                    p.setPedido_pendente(GregorianCalendar.getInstance().getTime());
                    p.setPedido_processando(GregorianCalendar.getInstance().getTime());
                    p.setPedido_efetivado(GregorianCalendar.getInstance().getTime());
                    break;    
                case BIG_BAG_SMALL:
                    p.setPedido_pendente(GregorianCalendar.getInstance().getTime());
                    break;                    
            }
            /* faz a persistencia do novo pedido no banco*/
            jpa.incluir(p);
            sfi.setNumerosPedidos(jpa.getPedidoCount());
            /* avisa ao solicitante que foi concluido com exito*/
            callBack.getInterfaceClientesList().get(sessaoId).receiverMsgFromServer(INFORMACAO, 
                    "Pedido: "+p.getCod_pedido()+" cadastrado com sucesso!"); 
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            /* avisa todos os clientes ativos que existe update de pedidos*/            
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).novoPedido(p);
                }catch(RemoteException e){ 
                    System.out.println(e.getMessage());
                    it.remove();
                }
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());         
        }
    }
    
    @Override
    public void atenderMultiplosPedido(Pedido[] p, Long Id) throws RemoteException {
        try{
            for(Pedido pedido : p){
                pedido.setStatus_pedido("Processando");
                pedido.setPedido_processando(GregorianCalendar.getInstance().getTime());
                pedido.setExpedicaoCanCancel(false);
                pedido.setMoagemCanCancel(true);
                jpa.alterar(pedido);
            }
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
             /* avisa todos os clientes que existe update de pedidos*/
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(p[0]);// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){
                    System.out.println(e.getMessage()); 
                    it.remove();
                }
        }catch(Exception ex){
            throw new RemoteException(ex.getMessage());
        }
    }
    @Override // moagem
    public void atenderPedido(Pedido p, Long sessaoId) throws RemoteException {
        try {
            p.setStatus_pedido("Processando");
            p.setPedido_processando(GregorianCalendar.getInstance().getTime());
            p.setExpedicaoCanCancel(false);
            p.setMoagemCanCancel(true);
            jpa.alterar(p);
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            /* avisa todos os clientes que existe update de pedidos*/
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(p);// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){ 
                    System.out.println(e.getMessage());
                    it.remove();
                }                
        } catch (Exception ex) { throw new RemoteException(ex.getMessage()); }
    }
    @Override // moagem
    public void devolverMultiplosPedido(Pedido[] p, Long sessaoId) throws RemoteException {
        try{
            for(Pedido pedido : p){
                pedido.setPedido_efetivado(GregorianCalendar.getInstance().getTime());
                pedido.setStatus_pedido("Disponivel");
                pedido.setExpedicaoCanCancel(false);
                pedido.setMoagemCanCancel(false);
                jpa.alterar(pedido);
            }
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(p[0]);// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){
                    System.out.println(e.getMessage()); 
                    it.remove();
                }    
        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }
    }
    @Override // moagem
    public void devolverPedido(Pedido p, Long sessaoId) throws RemoteException {
        try{
            p.setPedido_efetivado(GregorianCalendar.getInstance().getTime());
            p.setStatus_pedido("Disponivel");
            p.setExpedicaoCanCancel(false);
            p.setMoagemCanCancel(false);
            jpa.alterar(p);
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(p);// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){ 
                    System.out.println(e.getMessage());
                    it.remove();
                }    
        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }
    }

    private void avisarClientes() throws RemoteException{
        Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(new Pedido());// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){
                    System.out.println(e.getMessage()); 
                    it.remove();
                }
    }
    @Override // expedição
    public Integer expedirPedido(Pedido p, Long sessaoId) throws RemoteException {        
        try{            
            switch(p.getProduto().getClasse_produto()){
                case INDUSTRIAL:
                    int ret = servidor.IniciarCarregamentoIndustrial(p.getCod_pedido());
                    if(ret==0)
                        return CARREGAMENTO_BUSY;  
                    
                    libsgma.entity.CarregamentoEntity carregamento;
                    try{
                        Collection<Pedido> pedidosByCarga = jpa.pesquisarPedidoByCarga(p.getCarga());
                        Iterator it = pedidosByCarga.iterator();
                        Pedido pedido;
                        System.out.println("Size JPA: "+pedidosByCarga.size());
                        while(it.hasNext()){
                            pedido = (Pedido)it.next();
                            carregamento = new libsgma.entity.CarregamentoEntity();
                            carregamento.setNum_pedido(pedido.getCod_pedido());
                            carregamento.setProduto(pedido.getProduto().getNome_produto());
                            carregamento.setQtdCarregamento(pedido.getQuant());
                            carregamento.setStatus("Aguardando ensacamento");
                            carregamento.setTipo_carregamento(pedido.getProduto().getClasse_produto());
                
                            pedido.setPedido_expedido(GregorianCalendar.getInstance().getTime());
                            pedido.setStatus_pedido("Expedindo");
                            pedido.setExpedicaoCanCancel(false);
                            pedido.setMoagemCanCancel(false);
                            
                            jpa.alterar(pedido);
                            
                            indService.iniciarCarregamento(carregamento);
                        }
                        Iterator itt =  callBack.getInterfaceClientesList().values().iterator();
                        /* avisa todos os clientes que existe update de pedidos*/
                        while(itt.hasNext())
                            try{
                                ((ClientInterface)itt.next()).updatePedidoStatus(new Pedido());// pedido não esta sendo lido do outro lado.
                            }catch(RemoteException e){
                                System.out.println(e.getMessage()); 
                                itt.remove();
                            }
                        return CARREGAMENTO_OK;
                    }catch(Exception e){e.printStackTrace();}
            } 
            p.setPedido_expedido(GregorianCalendar.getInstance().getTime());
            p.setStatus_pedido("Expedindo");
            p.setExpedicaoCanCancel(false);
            p.setMoagemCanCancel(false);
            jpa.alterar(p);
            avisarClientes();   
            return CARREGAMENTO_OK;
        }catch(Exception e){
            callBack.getInterfaceClientesList().get(sessaoId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+e.getMessage()); 
            return CARREGAMENTO_EXCE;
        }
    }

    @Override
    public Collection<Pedido> listAllPedidos(Long sessaoId) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<Pedido> listLastPedidos(Long sessaoId) throws RemoteException {
       try{
           return jpa.listarPedidos();
       }catch(Exception ex){
            throw new RemoteException(ex.getMessage());
       }
    }

    @Override
    public Integer obterUltimoCodigo(Long sessaoId) throws RemoteException {
        try {
            return jpa.obterUltimoCodigo();
        } catch (Exception ex) {
            callBack.getInterfaceClientesList().get(sessaoId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
            return -1;
        }
    }

    @Override
    public void finalizarPedido(Pedido p, Long sessaoId) throws RemoteException {
        p.setPedido_finalizado(GregorianCalendar.getInstance().getTime());
        StringBuilder sb = new StringBuilder();
        long diferenca = p.getPedido_finalizado().getTime() - p.getPedido_expedido().getTime();
        long diferencaSeg = diferenca / 1000 % 60;    //DIFERENCA EM SEGUNDOS     
        long diferencaMin = diferenca / (60 * 1000) % 60;    //DIFERENCA EM MINUTOS     
        long diferencaHoras = diferenca / (60 * 60 * 1000) % 24;    // DIFERENCA EM HORAS 
        long diffDays = diferenca / (24 * 60 * 60 * 1000);
        //sb.append(diffDays);
        //sb.append("d ");
        if(diferencaHoras < 10)
            sb.append("0");
        
        sb.append(diferencaHoras);
        sb.append(":");
        
        if(diferencaMin < 10)
            sb.append("0");
        
        sb.append(diferencaMin);
        sb.append(":");
        
        if(diferencaSeg < 10)
            sb.append("0");
        
        sb.append(diferencaSeg);
        try{
            switch(p.getProduto().getClasse_produto()){
                case INDUSTRIAL:
                    servidor.finalizarCarregamentoIndustrial(p.getCod_pedido());
            }
            p.setStatus_pedido("Finalizado");
            p.setExpedicaoCanCancel(false);
            p.setMoagemCanCancel(false);
            try{
                System.out.println("Duração "+sb.toString());
                MaskFormatter mask = new MaskFormatter("##:##:##");
                System.out.println("Mask "+mask.stringToValue(sb.toString()));
                p.setDuracao_carregamento(mask.stringToValue(sb.toString()).toString());
            }catch(ParseException e){
                System.out.println(e.getMessage());
                p.setDuracao_carregamento("00:00:00");
            }            
            jpa.alterar(p);
            Iterator it =  callBack.getInterfaceClientesList().values().iterator();
            while(it.hasNext())
                try{
                   ((ClientInterface)it.next()).updatePedidoStatus(p);// pedido não esta sendo lido do outro lado.
                }catch(RemoteException e){
                    System.out.println(e.getMessage()); 
                    it.remove();
                } 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Collection<CarregamentoEntity> pesquisarPedido(Long sessaoId, Date inicio, Date fim, String state, Integer t) throws RemoteException {
        try{
           return jpa.pesquisarPedido(inicio, fim, state, t);
       }catch(Exception ex){
            callBack.getInterfaceClientesList().get(sessaoId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
            return null;
       }
    }
    @Override
    public Collection<Pedido> pesquisarPedido(Long sessaoId, Date inicio, Date fim, Integer t) throws RemoteException {
        try{
           return jpa.pesquisarPedido(inicio, fim, t);
       }catch(Exception ex){
            callBack.getInterfaceClientesList().get(sessaoId).receiverMsgFromServer(ERRO, 
                    "Shiiii, erro: "+ex.getMessage());
            return null;
       }
    }
    @Override
    public Collection<Pedido> pesquisarPedido(Long sessaoId, Date inicio, Date fim, String state, String produto) throws RemoteException {
        try{
           return jpa.pesquisarPedido(inicio, fim, state, produto);
       }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
       }
    }

    @Override
    public Pedido pesquisarPedidoById(Integer Id) throws RemoteException {
        try {
            return jpa.pesquisarPedidoById(Id);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }
    @Override
    public Pedido[] pesquisarPedidoByArrayID(Integer id[], Integer arraySize) throws RemoteException{
        try{
            return jpa.pesquisarPedidoByArrayID(id, arraySize);
        }catch(Exception e){throw new RemoteException(e.getMessage());}
    }

}
