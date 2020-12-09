/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.main;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.expedicao.presentation.ExpedicaoForm;
import br.com.gma.moagem.presentation.MoagemForm;
import static br.com.gma.server.common.entity.CONSTANTES.INDUSTRIAL;
import br.com.gma.server.common.entity.Cargo;
import br.com.gma.server.common.entity.Pedido;
import br.com.gma.server.common.entity.Usuario;
import br.com.gma.server.persistence.CargoJpaController;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.persistence.PedidoJpaController;
import br.com.gma.server.persistence.UsuarioJpaController;
import br.com.gma.server.rmi.ServidorRMI;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Alirio
 */
public class DOSMenu {
    
    private boolean isRunning = false;
    private Scanner in;
    private ServidorRMI server;
    private PedidoJpaController pedidoJPA;
    private CargoJpaController cargoJPA;
    private UsuarioJpaController usuarioJPA;
    private Factory factory;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
    
    public DOSMenu(){
        in = new Scanner(System.in);        
        showMenu();
    }
    private void showMenu(){
        clearScreen();
        if(!isRunning)
            System.out.println(1+" - Iniciar servidor.");
        
        System.out.println(2+" - Listar serviços em execução.");
        System.out.println(3+" - Cadastrar usuario.");
        System.out.println(4+" - Cadastrar cargo.");
        System.out.println(5+" - Iniciar BD.");
        System.out.println(8+" - Corrigir duração.");
        System.out.println();
        System.out.print("Escolha uma opção : ");
        
        parseCommand(in.nextInt());
        
    }
    public void parseCommand(int command){
        switch(command){
            case 1:
                startServidor();
                showMenu();
                break;
            case 2:
                break;
            
            case 3:
                cadastrarUsuario();
                showMenu();
                break;
            case 5:
                makeAdmin();
                showMenu();
                break;
            case 6:
                startExpedicao();
                showMenu();
                break;
            case 7:
                startMoagem();
                showMenu();
                break;
            case 8:
                checkPedido();
                showMenu();
                break;
        }
    }
    public void clearScreen(){
        for(int i = 0 ; i < 20; i++){
            System.out.println();
        }
    }
    public void startServidor(){
        if(!isRunning){
            if(in.hasNextInt()){
                factory = new Factory(); 
                server = new ServidorRMI(in.nextInt(), factory);
                isRunning = true;
            }else{
                factory = new Factory(); 
                server = new ServidorRMI(factory);
                isRunning = true;
            }
            pedidoJPA = new PedidoJpaController(factory.getfactory());
            cargoJPA = new CargoJpaController(factory.getfactory());
            usuarioJPA = new UsuarioJpaController(factory.getfactory());
        }else{
            System.out.println("O servidor já foi iniciado.");
        }        
    }
    private void checkPedido(){
        Collection<Pedido> pedido;
        try {
            pedido = pedidoJPA.pesquisarPedidoByStatus("Finalizado");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return;
        }
        Iterator it = pedido.iterator();
        Pedido p;
        while(it.hasNext()){
            p = (Pedido) it.next();
            if(p.getDuracao_carregamento().equalsIgnoreCase("00:00:00")){
                if(!processDuration(p)){
                    System.err.println("Pedido ID: "+p.getCod_pedido());
                }else{
                    System.out.println("Pedido duration: "+p.getDuracao_carregamento());
                }
            }
            
        }
    }
    private boolean processDuration(Pedido p){
        Boolean done = false;
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
            //System.out.println("Duração "+sb.toString());
            MaskFormatter mask = new MaskFormatter("##:##:##");
            //System.out.println("Mask "+mask.stringToValue(sb.toString()));
            p.setDuracao_carregamento(mask.stringToValue(sb.toString()).toString());
        }catch(ParseException e){
            System.err.println(e.getMessage());
            System.err.println("Exp: "+p.getPedido_expedido());
            System.err.println("Fin: "+p.getPedido_finalizado());
            System.err.println("Diferença: "+diferenca);
            System.err.println("Minutos: "+diferencaMin);
            System.err.println("Segundos: "+diferencaSeg);
            p.setDuracao_carregamento("00:00:00");
        }    
        try{
            pedidoJPA.alterar(p);
            done = true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            done = false;
        }
        return done;        
    }
    private void makeAdmin(){
        Cargo cargo = new Cargo();
        cargo.setCargo("Administrador");
        cargo.setPermissaoApagarCliente(Boolean.TRUE);
        cargo.setPermissaoApagarProduto(Boolean.TRUE);
        cargo.setPermissaoApagarUsuario(Boolean.TRUE);
        
        cargo.setPermissaoCadastrarCliente(Boolean.TRUE);
        cargo.setPermissaoCadastrarProduto(Boolean.TRUE);
        cargo.setPermissaoCadastrarUsuario(Boolean.TRUE);
        
        cargo.setPermissaoDesativarCliente(Boolean.TRUE);
        cargo.setPermissaoDesativarProduto(Boolean.TRUE);
        cargo.setPermissaoDesativarUsuario(Boolean.TRUE);
        
        cargo.setPermissaoEditarCliente(Boolean.TRUE);
        cargo.setPermissaoEditarProduto(Boolean.TRUE);
        cargo.setPermissaoEditarUsuario(Boolean.TRUE);
        
        cargo.setPermissaoCancelarPedido(Boolean.TRUE);
        cargo.setPermissaoAtenderPedido(Boolean.TRUE);
        cargo.setPermissaoExpedirPedido(Boolean.TRUE);
        cargo.setPermissaoFinalizarPedido(Boolean.TRUE);
        cargo.setPermissaoCadastrarPedido(Boolean.TRUE);
        
        Cargo cargo2 = new Cargo();
        cargo2.setCargo("Supervisor Expedição");
        cargo2.setPermissaoApagarCliente(Boolean.TRUE);
        cargo2.setPermissaoApagarProduto(Boolean.TRUE);
        cargo2.setPermissaoApagarUsuario(Boolean.TRUE);
        cargo2.setPermissaoCadastrarCliente(Boolean.TRUE);
        cargo2.setPermissaoCadastrarProduto(Boolean.TRUE);
        cargo2.setPermissaoCadastrarUsuario(Boolean.TRUE);
        cargo2.setPermissaoDesativarCliente(Boolean.TRUE);
        cargo2.setPermissaoDesativarProduto(Boolean.TRUE);
        cargo2.setPermissaoDesativarUsuario(Boolean.TRUE);
        cargo2.setPermissaoEditarCliente(Boolean.TRUE);
        cargo2.setPermissaoEditarProduto(Boolean.TRUE);
        cargo2.setPermissaoEditarUsuario(Boolean.TRUE);
        cargo2.setPermissaoCancelarPedido(Boolean.TRUE);
        cargo2.setPermissaoAtenderPedido(Boolean.TRUE);
        cargo2.setPermissaoExpedirPedido(Boolean.TRUE);
        cargo2.setPermissaoFinalizarPedido(Boolean.TRUE);
        cargo2.setPermissaoCadastrarPedido(Boolean.TRUE);
        
        Cargo cargo1 = new Cargo();
        cargo1.setCargo("Encarregado Expedição");
        cargo1.setPermissaoApagarCliente(Boolean.TRUE);
        cargo1.setPermissaoApagarProduto(Boolean.TRUE);
        cargo1.setPermissaoApagarUsuario(Boolean.TRUE);
        cargo1.setPermissaoCadastrarCliente(Boolean.TRUE);
        cargo1.setPermissaoCadastrarProduto(Boolean.TRUE);
        cargo1.setPermissaoCadastrarUsuario(Boolean.TRUE);
        cargo1.setPermissaoDesativarCliente(Boolean.TRUE);
        cargo1.setPermissaoDesativarProduto(Boolean.TRUE);
        cargo1.setPermissaoDesativarUsuario(Boolean.TRUE);
        cargo1.setPermissaoEditarCliente(Boolean.TRUE);
        cargo1.setPermissaoEditarProduto(Boolean.TRUE);
        cargo1.setPermissaoEditarUsuario(Boolean.TRUE);
        cargo1.setPermissaoCancelarPedido(Boolean.TRUE);
        cargo1.setPermissaoAtenderPedido(Boolean.TRUE);
        cargo1.setPermissaoExpedirPedido(Boolean.TRUE);
        cargo1.setPermissaoFinalizarPedido(Boolean.TRUE);
        cargo1.setPermissaoCadastrarPedido(Boolean.TRUE);
        
        Cargo cargo3 = new Cargo();
        cargo3.setCargo("Moleiro");
        cargo3.setPermissaoApagarCliente(Boolean.TRUE);
        cargo3.setPermissaoApagarProduto(Boolean.TRUE);
        cargo3.setPermissaoApagarUsuario(Boolean.TRUE);
        cargo3.setPermissaoCadastrarCliente(Boolean.TRUE);
        cargo3.setPermissaoCadastrarProduto(Boolean.TRUE);
        cargo3.setPermissaoCadastrarUsuario(Boolean.TRUE);
        cargo3.setPermissaoDesativarCliente(Boolean.TRUE);
        cargo3.setPermissaoDesativarProduto(Boolean.TRUE);
        cargo3.setPermissaoDesativarUsuario(Boolean.TRUE);
        cargo3.setPermissaoEditarCliente(Boolean.TRUE);
        cargo3.setPermissaoEditarProduto(Boolean.TRUE);
        cargo3.setPermissaoEditarUsuario(Boolean.TRUE);
        cargo3.setPermissaoCancelarPedido(Boolean.TRUE);
        cargo3.setPermissaoAtenderPedido(Boolean.TRUE);
        cargo3.setPermissaoExpedirPedido(Boolean.TRUE);
        cargo3.setPermissaoFinalizarPedido(Boolean.TRUE);
        cargo3.setPermissaoCadastrarPedido(Boolean.TRUE);
        
        Usuario usuario = new Usuario();
        usuario.setUsername("Admin");
        usuario.setGma("GMA00000");
        usuario.setSenha("luyt");
        usuario.setStatus(Boolean.TRUE);
        Date data = new Date();        
        usuario.setHora_registro(fmt.format(data));
        usuario.setData_registro(data);
        usuario.setHora_update(fmt.format(data));
        usuario.setData_update(data);
        
        try {
            //cargoJPA = new CargoJpaController(factory.getfactory());
            if(cargoJPA.getCargoCount() > 0)
                return;
            
            cargoJPA.incluir(cargo);
            cargoJPA.incluir(cargo1);
            cargoJPA.incluir(cargo2);
            cargoJPA.incluir(cargo3);
            System.out.println("Cargo inserido");
            cargo = cargoJPA.pesquisarCargoByNome(cargo.getCargo());
            System.out.println("Encontrado: "+cargo.getCargo());
            usuario.setCargo(cargo);
            //usuarioJPA = new UsuarioJpaController(factory.getfactory());
            usuarioJPA.incluir(usuario);
            System.out.println("Usuario admin cadastrado com sucesso!");
        } catch (Exception ex) { System.err.println("Erro : "+ex.getMessage()); 
        ex.printStackTrace();}
    }
    public void cadastrarUsuario(){
        Usuario usuario = new Usuario();
        
        System.out.print("Entre com o nome do usuario : ");
        String nome = in.nextLine();
        usuario.setUsername(nome);
        
        System.out.print("Entre com o GMA do usuario, Ex.: GMA03897 : ");
        String gma = in.nextLine();
        usuario.setGma(gma);
        
        System.out.print("Digite a senha : ");
        String senha = in.next();
        usuario.setSenha(senha);
        
        System.out.print("Digite um nome de cargo valido : ");
        boolean con = true;
        String cargo;
        Cargo c;
        while(con){
            try {
                cargo = in.next();
                c = cargoJPA.pesquisarCargoByNome(cargo);
                usuario.setCargo(c);
                con = false;                    
            } catch (Exception ex) {
                System.err.print("O cargo digitado não foi encontrado, deseja tentar novamente? S/N : ");
                String d = in.next();
                if(d.equals("s") || d.equals("1")){
                    System.out.print("Nome do Cargo : ");
                    con = true;
                }else{                    
                    return;
                }                        
            }
        }
        usuario.setSessaoId(Long.MIN_VALUE);
        usuario.setStatus(Boolean.TRUE);
        Date data = new Date();        
        usuario.setHora_registro(fmt.format(data));
        usuario.setData_registro(data);
        usuario.setHora_update(fmt.format(data));
        usuario.setData_update(data);
        try {
            usuarioJPA.incluir(usuario);
            System.out.println("Usuario cadastrado.");
        } catch (Exception ex) { System.err.println("Erro : "+ex.getMessage()); }
        
    }
    
    public void startExpedicao(){
        new ExpedicaoForm();
    }
    public void startMoagem(){
        new MoagemForm();
    }

    private void addCargo() {
        Cargo cargo = new Cargo();
        System.out.print("Qual o nome do cargo: ");
        String nome = in.next();
        cargo.setCargo(nome);
        System.out.println("Tecle '1' para sim, e '0' para não");
        
        System.out.print("Tem permissão para apagar Clientes: ");
        boolean b = in.nextBoolean();
        cargo.setPermissaoApagarCliente(b);
        System.out.print("Tem permissão para apagar Produtos: ");
        b = in.nextBoolean();
        cargo.setPermissaoApagarProduto(b);
        System.out.print("Tem permissão para apagar Usuarios: ");
        b = in.nextBoolean();
        cargo.setPermissaoApagarUsuario(b);
                
        System.out.print("Tem permissão para cadastrar Clientes: ");
        b = in.nextBoolean();
        cargo.setPermissaoCadastrarCliente(b);
        System.out.print("Tem permissão para cadastrar Produtos: ");
        b = in.nextBoolean();
        cargo.setPermissaoCadastrarProduto(b);
        System.out.print("Tem permissão para cadastrar Usuarios: ");
        b = in.nextBoolean();
        cargo.setPermissaoCadastrarUsuario(b);
        System.out.print("Tem permissão para cadastrar Pedidos: ");
        b = in.nextBoolean();
        cargo.setPermissaoCadastrarPedido(b);
        
        System.out.print("Tem permissão para desativar Clientes: ");
        b = in.nextBoolean();
        cargo.setPermissaoDesativarCliente(b);
        System.out.print("Tem permissão para desativar Produtos: ");
        b = in.nextBoolean();
        cargo.setPermissaoDesativarProduto(b);
        System.out.print("Tem permissão para desativar Usuarios: ");
        b = in.nextBoolean();
        cargo.setPermissaoDesativarUsuario(b);
        
        System.out.print("Tem permissão para editar Clientes: ");
        b = in.nextBoolean();
        cargo.setPermissaoEditarCliente(b);
        System.out.print("Tem permissão para editarProdutos: ");
        b = in.nextBoolean();
        cargo.setPermissaoEditarProduto(b);
        System.out.print("Tem permissão para editar Usuarios: ");
        b = in.nextBoolean();
        cargo.setPermissaoEditarUsuario(b);
        System.out.print("Tem permissão para editar Pedidos: ");
        b = in.nextBoolean();
        cargo.setPermissaoEditarPedido(b);
        
        try {
            //cargoJPA = new CargoJpaController(factory.getfactory());
           
            cargoJPA.incluir(cargo);
            System.out.println("Cargo inserido");
            cargo = cargoJPA.pesquisarCargoByNome(cargo.getCargo());
            System.out.println("Encontrado: "+cargo.getCargo());
            System.out.println("Cargo inserido com sucesso!");
        } catch (Exception ex) { System.err.println("Erro : "+ex.getMessage()); 
            ex.printStackTrace();}
    }
}
