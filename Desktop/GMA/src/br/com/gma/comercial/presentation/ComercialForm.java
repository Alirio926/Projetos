/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.comercial.presentation;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.cliente.common.ComercialTableModel;
import br.com.gma.cliente.common.FileProperties;
import br.com.gma.cliente.common.FileUpdateProperties;
import br.com.gma.cliente.common.JCalendar;
import br.com.gma.cliente.common.PedidoTableModel;
import br.com.gma.cliente.common.ProgressBarInterface;
import br.com.gma.expedicao.presentation.ExpedicaoForm;
import br.com.gma.expedicao.presentation.RelatorioForm;
import br.com.gma.relatorios.Carregamento;
import br.com.gma.relatorios.model.CarregamentoEntity;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemotePedido;
import br.com.gma.server.business.facade.remote.RemoteUpdate;
import static br.com.gma.server.common.entity.CONSTANTES.BIG_BAG;
import static br.com.gma.server.common.entity.CONSTANTES.DOMESTICA;
import static br.com.gma.server.common.entity.CONSTANTES.FARELO;
import static br.com.gma.server.common.entity.CONSTANTES.INDUSTRIAL;
import br.com.gma.server.common.entity.Pedido;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Alirio
 */
public class ComercialForm extends javax.swing.JFrame implements ClientInterface, Serializable, Runnable, ProgressBarInterface{

    private static final String myVersion = "0.3";
    private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
    private String str[] = new String[looks.length];
    private Long progID;
    private FileProperties f;
    private FileUpdateProperties fileUpdate;
    private boolean isConnected = false; 
    private RemotePedido pedidoRMI;
    private RemoteUpdate versionControl;
    private CallBackServerInterface servidor;
    private ArrayList<Pedido> arrayPedidosDisponivel = new ArrayList();
    private ArrayList<Pedido> arrayPedidosExpedindo = new ArrayList();
    private Collection<Pedido> ped = new HashSet<Pedido>();
    private PedidoTableModel pedidoTableModel;
    private ComercialTableModel comercialModel;
    private Collection<CarregamentoEntity> pedidos;
    private List<CarregamentoEntity> rel;
    private Carregamento carregamentoREL;
    private RelatorioForm rForm;
    private volatile int tt;
    
    /**
     * Creates new form ComercialForm
     */
    public ComercialForm(String xyz) {
        if(xyz.equalsIgnoreCase("f")){
            try {
                UIManager.put( "nimbusBase", new Color( 140, 42, 42 ) );
                UIManager.put( "nimbusBlueGrey", new Color( 190, 150, 150 ) );
                UIManager.put( "control", new Color( 223, 215, 214 ) );
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }else if(xyz.equalsIgnoreCase("m")){
            try {
                
                UIManager.put( "nimbusBase", new Color( 59, 140, 47 ) );  
                UIManager.put( "nimbusBlueGrey", new Color( 170, 190, 184 ) );  
                UIManager.put( "control", new Color( 214, 223, 220 ) ); 
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ComercialForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        try {
            f = new FileProperties();
            System.out.println(f.ServidorIP());
        } catch (Exception ex) {System.out.println(ex.getMessage());}
        
        initComponents();
        if(xyz.equalsIgnoreCase("m"))
            tblPedidos.setSelectionBackground(new Color(57,105,138));
        setVisible(true);
        
        checkVersion();
        
        try {
            carregamentoREL = new Carregamento(this,f.carregamentoURL(),"");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        pedidos = new HashSet<CarregamentoEntity>();
        rel = new ArrayList<CarregamentoEntity>();
    }
    private void checkVersion(){
        try {
            if(f.isAutoConnect()){
                if(connect()){
                    isConnected = true;
                    String serverVersion = versionControl.checkVersion();
                    if(serverVersion.equals(myVersion)){
                        System.out.println("Servidor Versão: "+versionControl.checkVersion(""));
                        progID = servidor.obterID();
                        System.out.println(progID);
                        if(progID != 0)
                            servidor.registerForCallback(this, progID);
                        setTitle("M. Dias Branco - Comercial v"+myVersion+" - PID "+progID);
                    }else{
                        /*JOptionPane.showMessageDialog(this, "O sistema esta desatualizado, será reiniciado\ne assim que atualizado reiniciará automaticamente!\n"+
                                "Versão atual "+myVersion+" versão disponivel no servidor "+serverVersion);
                        doUpdate(serverVersion);*/
                        JOptionPane.showMessageDialog(this, "Existe uma nova versão do programa, porem essa versão não suporta update automativo.\n"+ 
                                "Algumas funcionalidades podem funcionar com resultados inesperados.");
                        JOptionPane.showMessageDialog(this, "Caso algum problema ocorra, gentileza tirar print da tela e enviar para:\n"+
                                "alirio.oliveira@mdiasbranco.com.br , Obrigado!");
                        ///---- by pass versão update ----///
                        progID = servidor.obterID();
                        System.out.println(progID);
                        if(progID != 0)
                            servidor.registerForCallback(this, progID);
                        setTitle("M. Dias Branco - Comercial v"+myVersion+" - PID "+progID);
                        ///---- by pass versão update ----///                        
                    }                    
                }else
                    JOptionPane.showMessageDialog(this, "Erro ao tentar connectar com servidor.");
            }            
        } catch (RemoteException ex) {
            System.exit(0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } catch (Exception ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
    private void doUpdate(String serverVersion){
         // update found it
        fileUpdate = new FileUpdateProperties(versionControl);
        StringTokenizer org,file;
        String dst;
        String fileVersion;
        if(fileUpdate.downloadFileInfo()){
            try {
                // arquivo com lista de files para download foi carregado
                file = new StringTokenizer(fileUpdate.readPropertiesInterno("filename"), ";");
                org = new StringTokenizer(fileUpdate.readPropertiesInterno("remotefiles"), ";");
                dst = fileUpdate.readPropertiesInterno("localfiles");
                fileVersion = fileUpdate.readPropertiesInterno("version");
                // compara se o update esta na mesma versão do servidor
                if(!fileVersion.equals(serverVersion)){
                    JOptionPane.showMessageDialog(null, "Informe ao administrador que o update esta desatualizado.");
                    System.exit(0);
                }
                System.out.println("MyVersion: "+myVersion);
                System.out.println("serverVersion: "+serverVersion);
                System.out.println("updateVersion: "+fileVersion);
                // faz o download dos arquivos
                String f;
                while(file.hasMoreTokens()){
                    f=file.nextToken();
                    if(!fileUpdate.downloadFiles(org.nextToken()+"\\"+f,dst+"\\"+f)){
                        JOptionPane.showMessageDialog(null, "Erro duranto o download dos arquivos, por favor, tente novamente em 10 minutos");
                        System.exit(0);
                    }
                }
                // apos fazer o download dos arquivos
                System.out.println("ExecApp");
                ExecApp("d:\\jdk\\bin\\javaw.exe -jar d:\\gmacliente\\GMAUpdate.jar 0");
                System.exit(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }  
        }    
    }
    public void ExecApp(String app) throws IOException{
        Process process = Runtime.getRuntime().exec(app);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
    private boolean connect(){
        try{
            versionControl = (RemoteUpdate)             Naming.lookup(f.getVersionURL());
            pedidoRMI  =    (RemotePedido)              Naming.lookup(f.pedidoURL()); 
            servidor   =    (CallBackServerInterface)   Naming.lookup(f.servidorURL());
            
            UnicastRemoteObject.exportObject(this, 0);
            
            initArrayList();
            
        }catch (NotBoundException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            isConnected = false;
            return false;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            isConnected = false;
            return false;
        } catch (FileNotFoundException ex) { 
            ex.printStackTrace();
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    private void initArrayList(){
        try {
            /* Chama servidor lastPedidos */
            ped = pedidoRMI.listLastPedidos(progID);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this,"Erro ao tentar acessar a lista de pedido: "+ex.getMessage());
            return;
        }        
        Pedido x;
        Iterator it = ped.iterator(); 
        arrayPedidosDisponivel.clear();
        arrayPedidosExpedindo.clear();
        while(it.hasNext()){
            x = (Pedido)it.next();
            // se o pedido estiver cancelado, return
            if(!x.isPedido_cancelado()){
                if(x.getStatus_pedido().equalsIgnoreCase("Disponivel")) arrayPedidosDisponivel.add(x);
                if(x.getStatus_pedido().equalsIgnoreCase("Expedindo")) arrayPedidosExpedindo.add(x);
            }
            
        }
        // no primeiro momento mostra todos os pedidos na tabela
        actionFiltroAll();     
        TableColumnModel cm = tblPedidos.getColumnModel();
        cm.getColumn(0).setResizable(true);
        cm.getColumn(0).setPreferredWidth(10);
        cm.getColumn(1).setResizable(true);
        cm.getColumn(1).setPreferredWidth(80);
        cm.getColumn(2).setResizable(true);
        cm.getColumn(2).setPreferredWidth(38);
        cm.getColumn(3).setResizable(true);
        cm.getColumn(3).setPreferredWidth(80);
        cm.getColumn(4).setResizable(true);
        cm.getColumn(4).setPreferredWidth(40);
        cm.getColumn(5).setResizable(true);
        cm.getColumn(5).setPreferredWidth(45);
        cm.getColumn(6).setResizable(true);
        cm.getColumn(6).setPreferredWidth(40);
        cm.getColumn(7).setResizable(true);
        cm.getColumn(7).setPreferredWidth(55);
    }
    private void actionFiltroAll(){
        pedidoTableModel = new PedidoTableModel();
        comercialModel = new ComercialTableModel();
        comercialModel.adicionarArryPedidos(arrayPedidosDisponivel);
        comercialModel.adicionarArryPedidos(arrayPedidosExpedindo);
        tblPedidos.setModel(comercialModel);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        panelIndustrial1 = new javax.swing.JPanel();
        cbDataInicialIndustrial = new JCalendar(true);
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbDataFinalIndustrial = new JCalendar(true);
        btnIndustrial = new javax.swing.JButton();
        pbarIndustrial = new javax.swing.JProgressBar();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        hiIndustrial = new javax.swing.JFormattedTextField();
        hfIndustrial = new javax.swing.JFormattedTextField();
        lbStatus0 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panelDomestica1 = new javax.swing.JPanel();
        cbDataInicialDomestica = new JCalendar(true);
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        cbDataFinalDomestica = new JCalendar(true);
        btnDomestica = new javax.swing.JButton();
        pbarDomestica = new javax.swing.JProgressBar();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        hiDomestica = new javax.swing.JFormattedTextField();
        hfDomestica = new javax.swing.JFormattedTextField();
        lbStatus1 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        panelFarelo1 = new javax.swing.JPanel();
        cbDataInicialFarelo = new JCalendar(true);
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        cbDataFinalFarelo = new JCalendar(true);
        btnFarelo = new javax.swing.JButton();
        pbarFarelo = new javax.swing.JProgressBar();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        hiFarelo = new javax.swing.JFormattedTextField();
        hfFarelo = new javax.swing.JFormattedTextField();
        lbStatus2 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        panelBags1 = new javax.swing.JPanel();
        cbDataInicialBags = new JCalendar(true);
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cbDataFinalBags = new JCalendar(true);
        btnBags = new javax.swing.JButton();
        pbarBags = new javax.swing.JProgressBar();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        hiBags = new javax.swing.JFormattedTextField();
        hfBags = new javax.swing.JFormattedTextField();
        lbStatus3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("M. Dias Branco - Comercial");
        setResizable(false);

        jSplitPane2.setDividerLocation(135);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        panelIndustrial1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("Data Inicial");

        jLabel13.setText("Data Final");

        btnIndustrial.setText("Gerar Relatorio Industrial");
        btnIndustrial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndustrialActionPerformed(evt);
            }
        });

        pbarIndustrial.setStringPainted(true);

        jLabel14.setText("Hora Inicio");

        jLabel15.setText("Hora Fim");

        try {
            hiIndustrial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hiIndustrial.setText("05:00");

        try {
            hfIndustrial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hfIndustrial.setText("05:00");

        lbStatus0.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lbStatus0.setText("...");

        javax.swing.GroupLayout panelIndustrial1Layout = new javax.swing.GroupLayout(panelIndustrial1);
        panelIndustrial1.setLayout(panelIndustrial1Layout);
        panelIndustrial1Layout.setHorizontalGroup(
            panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndustrial1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelIndustrial1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataInicialIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelIndustrial1Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataFinalIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hiIndustrial, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(hfIndustrial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIndustrial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbarIndustrial, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelIndustrial1Layout.setVerticalGroup(
            panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIndustrial1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbDataInicialIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel14)
                        .addComponent(hiIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbStatus0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hfIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pbarIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnIndustrial))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIndustrial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbDataFinalIndustrial, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelIndustrial1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnIndustrial, cbDataFinalIndustrial, cbDataInicialIndustrial, hfIndustrial, hiIndustrial});

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIndustrial1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelIndustrial1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Industrial", jPanel5);

        panelDomestica1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setText("Data Inicial");

        jLabel29.setText("Data Final");

        btnDomestica.setText("Gerar Relatorio Domestica");
        btnDomestica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDomesticaActionPerformed(evt);
            }
        });

        pbarDomestica.setStringPainted(true);

        jLabel30.setText("Hora Inicio");

        jLabel31.setText("Hora Fim");

        try {
            hiDomestica.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hiDomestica.setText("05:00");

        try {
            hfDomestica.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hfDomestica.setText("05:00");

        lbStatus1.setText("...");

        javax.swing.GroupLayout panelDomestica1Layout = new javax.swing.GroupLayout(panelDomestica1);
        panelDomestica1.setLayout(panelDomestica1Layout);
        panelDomestica1Layout.setHorizontalGroup(
            panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDomestica1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelDomestica1Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataInicialDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDomestica1Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataFinalDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hiDomestica, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(hfDomestica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDomestica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbarDomestica, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelDomestica1Layout.setVerticalGroup(
            panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDomestica1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbDataInicialDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel28)
                        .addComponent(jLabel30)
                        .addComponent(hiDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbStatus1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addGroup(panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hfDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pbarDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDomestica))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomestica1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbDataFinalDomestica, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29)))
                .addContainerGap())
        );

        panelDomestica1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDomestica, cbDataFinalDomestica, cbDataInicialDomestica, hfDomestica, hiDomestica});

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDomestica1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(panelDomestica1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Domestica", jPanel10);

        panelFarelo1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel32.setText("Data Inicial");

        jLabel33.setText("Data Final");

        btnFarelo.setText("Gerar Relatorio Farelo");
        btnFarelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFareloActionPerformed(evt);
            }
        });

        pbarFarelo.setStringPainted(true);

        jLabel34.setText("Hora Inicio");

        jLabel35.setText("Hora Fim");

        try {
            hiFarelo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hiFarelo.setText("05:00");

        try {
            hfFarelo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hfFarelo.setText("05:00");

        lbStatus2.setText("...");

        javax.swing.GroupLayout panelFarelo1Layout = new javax.swing.GroupLayout(panelFarelo1);
        panelFarelo1.setLayout(panelFarelo1Layout);
        panelFarelo1Layout.setHorizontalGroup(
            panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFarelo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFarelo1Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataInicialFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFarelo1Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataFinalFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hiFarelo, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(hfFarelo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbarFarelo, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelFarelo1Layout.setVerticalGroup(
            panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFarelo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelFarelo1Layout.createSequentialGroup()
                        .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel32)
                                .addComponent(jLabel34)
                                .addComponent(hiFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbDataInicialFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hfFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pbarFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFarelo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFarelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbDataFinalFarelo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)))
                .addContainerGap())
        );

        panelFarelo1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnFarelo, cbDataFinalFarelo, cbDataInicialFarelo, hfFarelo, hiFarelo});

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFarelo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelFarelo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Farelo", jPanel11);

        panelBags1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel36.setText("Data Inicial");

        jLabel37.setText("Data Final");

        btnBags.setText("Gerar Relatorio Bag");
        btnBags.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBagsActionPerformed(evt);
            }
        });

        pbarBags.setStringPainted(true);

        jLabel38.setText("Hora Inicio");

        jLabel39.setText("Hora Fim");

        try {
            hiBags.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hiBags.setText("05:00");

        try {
            hfBags.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        hfBags.setText("05:00");

        lbStatus3.setText("...");

        javax.swing.GroupLayout panelBags1Layout = new javax.swing.GroupLayout(panelBags1);
        panelBags1.setLayout(panelBags1Layout);
        panelBags1Layout.setHorizontalGroup(
            panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBags1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBags1Layout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataInicialBags, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBags1Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbDataFinalBags, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39)
                    .addComponent(jLabel38))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(hiBags, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(hfBags))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBags, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbarBags, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelBags1Layout.setVerticalGroup(
            panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBags1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbDataInicialBags, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
                    .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel36)
                        .addComponent(jLabel38)
                        .addComponent(hiBags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbStatus3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addGroup(panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(hfBags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pbarBags, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBags))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBags1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbDataFinalBags, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelBags1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnBags, cbDataFinalBags, cbDataInicialBags, hfBags, hiBags});

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBags1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBags1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Big Bag", jPanel12);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel1);

        tblPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPedidos.setSelectionBackground(java.awt.Color.pink);
        jScrollPane3.setViewportView(tblPedidos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIndustrialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndustrialActionPerformed
       doRel(INDUSTRIAL);
    }//GEN-LAST:event_btnIndustrialActionPerformed

    private void btnDomesticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDomesticaActionPerformed
        doRel(DOMESTICA);
    }//GEN-LAST:event_btnDomesticaActionPerformed

    private void btnFareloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFareloActionPerformed
        doRel(FARELO);
    }//GEN-LAST:event_btnFareloActionPerformed

    private void btnBagsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBagsActionPerformed
        doRel(BIG_BAG);
    }//GEN-LAST:event_btnBagsActionPerformed
    private void doRel(int op){
        setPercent(op,0, "Instanciando objetos");
        tt = op;
        setStateButton(op,false);
        setPercent(op,2, "Instanciando objetos");
        Date i = null;
        Date f = null;
        setPercent(op,4, "Formatando datas");
        switch(op){
            case 0:
                i = ((JCalendar)cbDataInicialIndustrial).date();
                f = ((JCalendar)cbDataFinalIndustrial).date();
                i.setHours(Integer.parseInt(hiIndustrial.getText().substring(0, 2)));// 00:00
                i.setMinutes(Integer.parseInt(hiIndustrial.getText().substring(3, 5)));
                f.setHours(Integer.parseInt(hfIndustrial.getText().substring(0, 2)));
                f.setMinutes(Integer.parseInt(hfIndustrial.getText().substring(3, 5)));
                break;
            case 1:
                i = ((JCalendar)cbDataInicialDomestica).date();
                f = ((JCalendar)cbDataFinalDomestica).date();
                i.setHours(Integer.parseInt(hiDomestica.getText().substring(0, 2)));// 00:00
                i.setMinutes(Integer.parseInt(hiDomestica.getText().substring(3, 5)));
                f.setHours(Integer.parseInt(hfDomestica.getText().substring(0, 2)));
                f.setMinutes(Integer.parseInt(hfDomestica.getText().substring(3, 5)));
                break;
            case 2:
                i = ((JCalendar)cbDataInicialFarelo).date();
                f = ((JCalendar)cbDataFinalFarelo).date();
                i.setHours(Integer.parseInt(hiFarelo.getText().substring(0, 2)));// 00:00
                i.setMinutes(Integer.parseInt(hiFarelo.getText().substring(3, 5)));
                f.setHours(Integer.parseInt(hfFarelo.getText().substring(0, 2)));
                f.setMinutes(Integer.parseInt(hfFarelo.getText().substring(3, 5)));
                break;
            case 3:
                i = ((JCalendar)cbDataInicialBags).date();
                f = ((JCalendar)cbDataFinalBags).date();
                i.setHours(Integer.parseInt(hiBags.getText().substring(0, 2)));// 00:00
                i.setMinutes(Integer.parseInt(hiBags.getText().substring(3, 5)));
                f.setHours(Integer.parseInt(hfBags.getText().substring(0, 2)));
                f.setMinutes(Integer.parseInt(hfBags.getText().substring(3, 5)));
                break;
            
        }        
        
        setPercent(op,6, "Consultando banco de dados");
        try {
            pedidos = pedidoRMI.pesquisarPedido(progID,i,f,"Finalizado",op);
            setPercent(op,10, "Lendo tablelas");
        } catch (RemoteException ex) { ex.printStackTrace();setStateButton(op,true); } 
          catch (Exception ex) { ex.printStackTrace();setStateButton(op,true);}
        if(pedidos == null){
            JOptionPane.showMessageDialog(this, "Sem resultados", "Pesquisa vazia",JOptionPane.INFORMATION_MESSAGE);
            setPercent(op,0, "");
            setStateButton(op,true);
            return;
        }
        
        Iterator it = pedidos.iterator();
        Pedido x;
        rel.clear();
        setPercent(op,15, "Organizando informações em arraylist");
        int h,m;
        Float t1,t2;
        Float intt;
        String time;
        while(it.hasNext()){            
            x = (Pedido) it.next();
            time = x.getDuracao_carregamento();
            if(time.contains("d"))
                time=time.substring(3, time.length());
            
            h = Integer.parseInt(time.substring(0, 2));
            m = Integer.parseInt(time.substring(3, 5));
            if(x.getQuant_estoque() == null)
                t2=0f;
            else
                t2=x.getQuant_estoque();
            
            if(x.getQuant_Expedido() == null)
                t1=0f;
            else
                t1=x.getQuant_Expedido();
            //String str = Float.toString(t);
            //str = str.substring(0, str.indexOf("."));
            intt = (((t1+t2)/((h*60)+m))*60);
            rel.add(new CarregamentoEntity(
                    x.getProduto().getNome_produto(),
                    x.getQuant(),
                    x.getCliente().getNome_cliente(),
                    x.getPlaca_do_pedido(),
                    x.getCarga(),
                    x.getPedido_expedido(),
                    x.getPedido_finalizado(),
                    x.getAvaria(),
                    time,
                    intt,//media
                    x.getQuant_Expedido(),
                    x.getQuant_estoque()));
        }
        Collections.sort(rel);
        setPercent(op,30, "Passando parametros ao Jasper Report");
        new Thread(this).start();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ComercialForm("f").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBags;
    private javax.swing.JButton btnDomestica;
    private javax.swing.JButton btnFarelo;
    private javax.swing.JButton btnIndustrial;
    private javax.swing.JComboBox cbDataFinalBags;
    private javax.swing.JComboBox cbDataFinalDomestica;
    private javax.swing.JComboBox cbDataFinalFarelo;
    private javax.swing.JComboBox cbDataFinalIndustrial;
    private javax.swing.JComboBox cbDataInicialBags;
    private javax.swing.JComboBox cbDataInicialDomestica;
    private javax.swing.JComboBox cbDataInicialFarelo;
    private javax.swing.JComboBox cbDataInicialIndustrial;
    private javax.swing.JFormattedTextField hfBags;
    private javax.swing.JFormattedTextField hfDomestica;
    private javax.swing.JFormattedTextField hfFarelo;
    private javax.swing.JFormattedTextField hfIndustrial;
    private javax.swing.JFormattedTextField hiBags;
    private javax.swing.JFormattedTextField hiDomestica;
    private javax.swing.JFormattedTextField hiFarelo;
    private javax.swing.JFormattedTextField hiIndustrial;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lbStatus0;
    private javax.swing.JLabel lbStatus1;
    private javax.swing.JLabel lbStatus2;
    private javax.swing.JLabel lbStatus3;
    private javax.swing.JPanel panelBags1;
    private javax.swing.JPanel panelDomestica1;
    private javax.swing.JPanel panelFarelo1;
    private javax.swing.JPanel panelIndustrial1;
    private javax.swing.JProgressBar pbarBags;
    private javax.swing.JProgressBar pbarDomestica;
    private javax.swing.JProgressBar pbarFarelo;
    private javax.swing.JProgressBar pbarIndustrial;
    private javax.swing.JTable tblPedidos;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void novoPedido(Pedido p) throws RemoteException {
        initArrayList();
    }

    @Override
    public void updatePedidoStatus(Pedido p) throws RemoteException {
        initArrayList();
    }

    @Override
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException {
       
    }

    @Override
    public void receiverMsgFromCliente(String msg, String u) throws RemoteException {
        
    }

    @Override
    public void run() {
        try {
            carregamentoREL.imprimir(rel,tt);
        } catch (Exception ex) {
            setStateButton(tt,true);
            setPercent(tt,0,""); 
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Não foi possivel gerar o relatório, erro: "+ex.getMessage());
        }
    }

    @Override
    public void setPercent(int p, int f, String process) {
        switch(p){
            case 0:
                pbarIndustrial.setValue(f);
                lbStatus0.setText(process);
                break;
            case 1:
                pbarDomestica.setValue(f);
                lbStatus1.setText(process);
                break;
            case 2:
                pbarFarelo.setValue(f);
                lbStatus2.setText(process);
                break;
            case 3:
                pbarBags.setValue(f);
                lbStatus3.setText(process);
                break;
        }
    }

    @Override
    public void setStateButton(int b, boolean s) {
        switch(b){
            case 0:
                btnIndustrial.setEnabled(s);
                break;
            case 1:
                btnDomestica.setEnabled(s);
                break;
            case 2:
                btnFarelo.setEnabled(s);
                break;
            case 3:
                btnBags.setEnabled(s);
                break;
        }
    }

    @Override
    public void setPercent(int p, int f) {
        
    }
}
