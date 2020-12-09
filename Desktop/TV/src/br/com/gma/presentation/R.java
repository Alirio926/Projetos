/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.presentation;

import br.com.gma.common.MyTableModel;
import br.com.gma.common.OptionFile;
import br.com.gma.security.WindowsSecurity;
import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import libsgma.entity.CarregamentoEntity;
import libsgma.file.FileProperties;
import libsgma.model.CarregamentoTableModel;
import libsgma.remote.RemoteCarregamentoIND;
import libsgma.server.CallbackServerCarregamento;
import libsgma.server.CarregamentoInterface;

/**
 *
 * @author Alirio
 */
public class R extends javax.swing.JFrame implements Runnable, SerialPortEventListener, CarregamentoInterface{

    // variaves diversas
    private int 
    count = 300, taxa, pulse,lastDay, div,qtdPacote=0, avaria=0,pctAcum = 0,
    pctBom = 0,pctReject = 0, horaCount; 
    private String s, inicioRelatorio, fimRelatorio, inicioRelatorio1, fimRelatorio1; 
    private static String myPort;
    // Form relatorios
    private boolean boolOcorrencia = false;
    private boolean boolOcorrencia1 = false;
    // tabelas
    private String[] col,col1,col2;
    private boolean [] edit,edit1,edit2;
    private ArrayList arrayRelatorio = new ArrayList();
    private ArrayList arrayRelatorioProducao = new ArrayList();
    private ArrayList arrayAjust = new ArrayList();    
    private final int[] arrayHoras = new int[26];
       
    // arquivo
    private boolean isConnected = false; //  false to enable
    private boolean timer2_5m = false;
    private boolean timer1_5m = false;
    private boolean startHourTime = false;
    private boolean startQuinzeTime = false;
    // Comunicação Serial
    private SerialPort serial;   
    private OptionFile f;
    // timers
    private final Timer timerUmaHora, timerQuinzeMinutos, timerCincoMinutes1, timerCincoMinutes2;
            
    // Comunicação com servidor
    private RemoteCarregamentoIND RMIcarregamento;
    private FileProperties file;
    private CallbackServerCarregamento rmiServer;
    
    public void connect(){
        try{
            RMIcarregamento = (RemoteCarregamentoIND)Naming.lookup(file.industrialURL());
            rmiServer = (CallbackServerCarregamento)Naming.lookup(file.servidorURL());
            UnicastRemoteObject.exportObject(this, 0); 
            rmiServer.registerForCallback(this);
            
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    public R() {
        try {
            file = new FileProperties();            
        } catch (Exception ex) {ex.printStackTrace();}
        
        connect();
        
        initComponents();
        
        f = new OptionFile();
        
        CardLayout card = (CardLayout)(paneMain.getLayout());     
        card.show(panePrincipal.getParent(), "principal");
        timerUmaHora = new Timer();
        timerQuinzeMinutos = new Timer();
        timerCincoMinutes1 = new Timer();
        timerCincoMinutes2 = new Timer();
        
        jdComment.setLocation(10, 10);
        // set o Model inicial da tabela
        col = new String[] { "Hora de Inicio","Hora de Final","Produto","Descrição da ocorrência"};
        edit = new boolean[]{false, false, false, false};
        tblRelatoriosParada.setModel(new MyTableModel(arrayRelatorio,col,edit));
        
        col1 = new String[] { "Hora de Inicio","Hora de Final","Produto","Avaria","Quant.","Estoque" };
        edit1 = new boolean[]{false, false, false,false,false,false};
        tblRelatoriosProducao.setModel(new MyTableModel(arrayRelatorioProducao,col1,edit1)); 
        
        col2 = new String[] { "00hr","01hr","02hr",
        "03hr","04hr","05hr","06hr","07hr","08hr","09hr","10hr","11hr","12hr","13hr","14hr",
        "15hr","16hr","17hr","18hr","19hr","20hr","21hr","22hr","23hr"};
        
        edit2 = new boolean[]{true, true, true,true,true, true, true,true,
            true, true, true,true,true, true, true,true,true, true, true,true,
            true, true,true,true};
        
        arrayAjust.add(new String[]{"0","0","0","0","0","0","0","0","0","0","0","0","0","0",
            "0","0","0","0","0","0","0","0","0","0",});
        
        tblAjust.setModel(new MyTableModel(arrayAjust,col2,edit2));
        
        new Thread(this).start();// Thread de 24hrs
        new Thread(new calcVelocidade()).start(); // Thread calc de pulsos
        
        initArryList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdComment = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jeComment = new javax.swing.JEditorPane();
        bStoreComment = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox();
        jLabel20 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox();
        jTextField9 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jComboBox9 = new javax.swing.JComboBox();
        jTextField10 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jComboBox10 = new javax.swing.JComboBox();
        jComboBox11 = new javax.swing.JComboBox();
        jComboBox12 = new javax.swing.JComboBox();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox();
        jTextField14 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        jTextField15 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jLabel41 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox();
        jTextField18 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox();
        jLabel46 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox();
        jLabel48 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        tfBal01 = new javax.swing.JTextField();
        tfBal02 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        lbHora2 = new javax.swing.JLabel();
        jdAjust = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblAjust = new javax.swing.JTable();
        btnAjustHora = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jftSenha = new javax.swing.JPasswordField();
        jPanel2 = new javax.swing.JPanel();
        tfMeta = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        tfAcum = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        btnComment = new javax.swing.JButton();
        tfMsg = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        paneMain = new javax.swing.JPanel();
        paneOcorrenciaManutencao = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taRelatorio = new javax.swing.JTextArea();
        lbHora = new javax.swing.JLabel();
        btnOpenOcorrencia = new javax.swing.JButton();
        btnFecharOcorrencia = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRelatoriosParada = new javax.swing.JTable();
        cbParadaFarinha = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        paneOcorrenciaProducao = new javax.swing.JPanel();
        btnOpenOcorrencia1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblRelatoriosProducao = new javax.swing.JTable();
        btnFecharOcorrencia1 = new javax.swing.JButton();
        lbHora1 = new javax.swing.JLabel();
        cbProducaoFarinha = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        taAvaria = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        tfQuant = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfEstoque = new javax.swing.JTextField();
        panePrincipal = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfPBom = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfpAva = new javax.swing.JTextField();
        tfTimeStopped = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfTaxa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfTimeRunning = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfSM = new javax.swing.JTextField();
        lbH = new javax.swing.JLabel();
        paneAvaria = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jftAvaria = new javax.swing.JFormattedTextField();
        jButton5 = new javax.swing.JButton();
        lbAvariaTime = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tfSpeed = new javax.swing.JTextField();

        jdComment.setAlwaysOnTop(true);
        jdComment.setResizable(false);

        jeComment.setBorder(javax.swing.BorderFactory.createTitledBorder("Observação geral"));
        jScrollPane1.setViewportView(jeComment);

        bStoreComment.setText("Gravar");
        bStoreComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStoreCommentActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Carrossel MWPL"));

        jLabel14.setText("Existe vazamento de ar comprimido no carrossel?");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jTextField1.setEnabled(false);

        jLabel15.setText("Ordem");

        jLabel16.setText("Existem alguma interferencia no giro do carrossel?");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel18.setText("Ordem");

        jTextField2.setEnabled(false);

        jLabel19.setText("As garras estao funcionando corretamente?");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel20.setText("Ordem");

        jTextField3.setEnabled(false);

        jLabel21.setText("As balanças estao funcionando corretamente?");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });

        jLabel22.setText("Ordem");

        jTextField4.setEnabled(false);

        jLabel23.setText("Deseja fazer alguma observacao geral?");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });

        jTextField9.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Máquina de costura"));

        jLabel24.setText("A tesoura esta funcionando corretamente?");

        jLabel25.setText("Houve alteracao nos parametros da maquina?");

        jLabel26.setText("Existe algum ruido na esteira de dobra do saco?");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox7ItemStateChanged(evt);
            }
        });

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox8ItemStateChanged(evt);
            }
        });

        jLabel27.setText("Ordem");

        jLabel28.setText("Ordem");

        jLabel29.setText("Ordem");

        jTextField6.setEnabled(false);

        jTextField7.setEnabled(false);

        jTextField8.setEnabled(false);

        jLabel30.setText("Deseja fazer alguma observacao geral?");

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox9ItemStateChanged(evt);
            }
        });

        jTextField10.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Esteira"));

        jLabel31.setText("Esteira esta alinhada?");

        jLabel32.setText("Existe ruido?");

        jLabel33.setText("Velocidade esta compativel com maquia de costura");

        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox10ItemStateChanged(evt);
            }
        });

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox11.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox11ItemStateChanged(evt);
            }
        });

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox12.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox12ItemStateChanged(evt);
            }
        });

        jLabel34.setText("Ordem");

        jLabel35.setText("Ordem");

        jLabel36.setText("Ordem");

        jTextField11.setEnabled(false);

        jTextField12.setEnabled(false);

        jTextField13.setEnabled(false);

        jLabel37.setText("Deseja fazer alguma observacao geral?");

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox13.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox13ItemStateChanged(evt);
            }
        });

        jTextField14.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextField14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Video Jet"));

        jLabel38.setText("As duas video jet estao abastecidas?");

        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox14.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox14ItemStateChanged(evt);
            }
        });

        jTextField15.setEnabled(false);

        jLabel39.setText("Ordem");

        jLabel40.setText("As duas video jets estao operacional?");

        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox15.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox15ItemStateChanged(evt);
            }
        });

        jLabel41.setText("Ordem");

        jTextField16.setEnabled(false);

        jTextField17.setEnabled(false);

        jLabel42.setText("Ordem");

        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox16.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox16ItemStateChanged(evt);
            }
        });

        jLabel43.setText("O equipamento reserva esta disponivel no setor?");

        jLabel44.setText("Deseja fazer alguma observacao geral?");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox17.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox17ItemStateChanged(evt);
            }
        });

        jTextField18.setEnabled(false);

        jLabel45.setText("Foi feito limpeza hoje na video jet?");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NAO", "SIM" }));
        jComboBox18.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox18ItemStateChanged(evt);
            }
        });

        jLabel46.setText("Ordem");

        jTextField19.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel40)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel41)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel38)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel39)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel43)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel42)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel45)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel46)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel46))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Balança"));

        jLabel47.setText("O peso foi verificado e esta dentro dos padroes?");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIM", "NAO" }));
        jComboBox19.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox19ItemStateChanged(evt);
            }
        });

        jLabel48.setText("Ordem");

        jTextField20.setEnabled(false);

        jLabel49.setText("Balança 01");

        jLabel52.setText("Balança 02");

        tfBal01.setText("0");

        tfBal02.setText("0");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49)
                            .addComponent(tfBal01, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addComponent(tfBal02, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfBal01, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBal02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jEditorPane1.setContentType("text/html"); // NOI18N
        jEditorPane1.setText("<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \r<DIV ALIGN=\"CENTER\">AVISO LEGAL</DIV>\n\t<br>Declaro que as informações neste form são verídicas e dou fé,\n    </p>\r\n  </body>\r\n</html>\r\n");
        jEditorPane1.setEnabled(false);
        jScrollPane6.setViewportView(jEditorPane1);

        lbHora2.setFont(new java.awt.Font("Calibri", 1, 60)); // NOI18N
        lbHora2.setForeground(new java.awt.Color(153, 0, 0));
        lbHora2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHora2.setText("0:00");
        lbHora2.setAlignmentY(0.0F);
        lbHora2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbHora2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jdCommentLayout = new javax.swing.GroupLayout(jdComment.getContentPane());
        jdComment.getContentPane().setLayout(jdCommentLayout);
        jdCommentLayout.setHorizontalGroup(
            jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdCommentLayout.createSequentialGroup()
                .addGroup(jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdCommentLayout.createSequentialGroup()
                        .addComponent(lbHora2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bStoreComment, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );
        jdCommentLayout.setVerticalGroup(
            jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdCommentLayout.createSequentialGroup()
                .addGroup(jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdCommentLayout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbHora2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bStoreComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jdCommentLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        jScrollPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Informe os valores produzidos até o momento e preencha os demais com zeros"));

        tblAjust.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "07:00", "08:00", "Title 3", "Título 4", "Título 5", "Título 6", "Título 7", "Título 8", "Título 9", "Título 10", "Título 11", "Título 12", "Título 13", "Título 14", "Título 15", "Título 16", "Título 17", "Título 18", "Título 19", "Título 20", "Título 21", "Título 22", "Título 23", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblAjust);

        btnAjustHora.setText("Salvar");
        btnAjustHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjustHoraActionPerformed(evt);
            }
        });

        jLabel11.setText("Informe a senha para edição");

        javax.swing.GroupLayout jdAjustLayout = new javax.swing.GroupLayout(jdAjust.getContentPane());
        jdAjust.getContentPane().setLayout(jdAjustLayout);
        jdAjustLayout.setHorizontalGroup(
            jdAjustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAjustLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdAjustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 957, Short.MAX_VALUE)
                    .addGroup(jdAjustLayout.createSequentialGroup()
                        .addGroup(jdAjustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jftSenha)
                            .addComponent(btnAjustHora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jdAjustLayout.setVerticalGroup(
            jdAjustLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAjustLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jftSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAjustHora)
                .addContainerGap(98, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Carrossel app");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1280, 780));
        setUndecorated(true);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setMaximumSize(new java.awt.Dimension(456, 224));
        jPanel2.setMinimumSize(new java.awt.Dimension(456, 224));
        jPanel2.setPreferredSize(new java.awt.Dimension(456, 224));

        tfMeta.setEditable(false);
        tfMeta.setFont(new java.awt.Font("Calibri", 1, 56)); // NOI18N
        tfMeta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfMeta.setText("0");
        tfMeta.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfMeta)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfMeta)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setMaximumSize(new java.awt.Dimension(456, 224));
        jPanel3.setMinimumSize(new java.awt.Dimension(456, 224));
        jPanel3.setPreferredSize(new java.awt.Dimension(456, 224));

        tfAcum.setEditable(false);
        tfAcum.setFont(new java.awt.Font("Calibri", 1, 56)); // NOI18N
        tfAcum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfAcum.setText("0");
        tfAcum.setFocusable(false);
        tfAcum.setMaximumSize(new java.awt.Dimension(28, 92));
        tfAcum.setMinimumSize(new java.awt.Dimension(28, 92));
        tfAcum.setPreferredSize(new java.awt.Dimension(28, 92));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfAcum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfAcum, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("ACUMULADO");
        jLabel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel50.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("ATINGIMENTO DA META");
        jLabel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnComment.setText("Inserir relatório (a cada 15 minutos)");
        btnComment.setEnabled(false);
        btnComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCommentActionPerformed(evt);
            }
        });

        tfMsg.setText("Sistema:");
        tfMsg.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfMsg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tfMsgMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Calibri", 2, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("M. DIAS BRANCO - GRANDE MOINHO ARATU");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel51.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("VELOCIDADE");
        jLabel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        paneMain.setLayout(new java.awt.CardLayout());

        paneOcorrenciaManutencao.setBorder(javax.swing.BorderFactory.createTitledBorder("Paradas"));

        taRelatorio.setColumns(20);
        taRelatorio.setRows(5);
        taRelatorio.setEnabled(false);
        jScrollPane2.setViewportView(taRelatorio);

        lbHora.setFont(new java.awt.Font("Calibri", 1, 60)); // NOI18N
        lbHora.setForeground(new java.awt.Color(153, 0, 0));
        lbHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHora.setText("0:00");
        lbHora.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbHora.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnOpenOcorrencia.setText("Abrir ocorrência");
        btnOpenOcorrencia.setMaximumSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia.setMinimumSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia.setPreferredSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenOcorrenciaActionPerformed(evt);
            }
        });

        btnFecharOcorrencia.setText("Fechar ocorrência");
        btnFecharOcorrencia.setEnabled(false);
        btnFecharOcorrencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharOcorrenciaActionPerformed(evt);
            }
        });

        tblRelatoriosParada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Dta Inicio", "Dta Fim", "Ocorrencia"
            }
        ));
        jScrollPane3.setViewportView(tblRelatoriosParada);

        cbParadaFarinha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Adorita", "Adorita 25Kg", "Aratu", "Aratu ID", "Do Padeiro", "Do Padeiro 25Kg", "Finna Aditivada", "Finna Mix", "Imperial", "Medalha de Prata", "Medalha de Ouro", "Medalha de Ouro ID", "Medalha de Ouro 25Kg", "Medalha de Ouro Premium 25Kg", "Monarca Class", "Monarca Comum", "Monarca Normal" }));

        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneOcorrenciaManutencaoLayout = new javax.swing.GroupLayout(paneOcorrenciaManutencao);
        paneOcorrenciaManutencao.setLayout(paneOcorrenciaManutencaoLayout);
        paneOcorrenciaManutencaoLayout.setHorizontalGroup(
            paneOcorrenciaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneOcorrenciaManutencaoLayout.createSequentialGroup()
                .addGroup(paneOcorrenciaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneOcorrenciaManutencaoLayout.createSequentialGroup()
                        .addComponent(btnOpenOcorrencia, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFecharOcorrencia, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbParadaFarinha, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(paneOcorrenciaManutencaoLayout.createSequentialGroup()
                        .addComponent(lbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 772, Short.MAX_VALUE))
        );
        paneOcorrenciaManutencaoLayout.setVerticalGroup(
            paneOcorrenciaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneOcorrenciaManutencaoLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(paneOcorrenciaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFecharOcorrencia, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenOcorrencia, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbParadaFarinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paneOcorrenciaManutencaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbHora, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneOcorrenciaManutencaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        /*

        paneMain.add(paneOcorrenciaManutencao, "card2");
        */
        paneMain.add(paneOcorrenciaManutencao, "parada");

        paneOcorrenciaProducao.setBorder(javax.swing.BorderFactory.createTitledBorder("Produção"));

        btnOpenOcorrencia1.setText("Inicio produção");
        btnOpenOcorrencia1.setMaximumSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia1.setMinimumSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia1.setPreferredSize(new java.awt.Dimension(127, 28));
        btnOpenOcorrencia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenOcorrencia1ActionPerformed(evt);
            }
        });

        tblRelatoriosProducao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Dta Inicio", "Dta Fim", "Ocorrencia"
            }
        ));
        jScrollPane4.setViewportView(tblRelatoriosProducao);

        btnFecharOcorrencia1.setText("Fim produção");
        btnFecharOcorrencia1.setEnabled(false);
        btnFecharOcorrencia1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharOcorrencia1ActionPerformed(evt);
            }
        });

        lbHora1.setFont(new java.awt.Font("Calibri", 1, 60)); // NOI18N
        lbHora1.setForeground(new java.awt.Color(153, 0, 0));
        lbHora1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHora1.setText("0:00");
        lbHora1.setAlignmentY(0.0F);
        lbHora1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lbHora1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cbProducaoFarinha.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Adorita", "Adorita 25Kg", "Aratu", "Aratu ID", "Do Padeiro", "Do Padeiro 25Kg", "Finna Aditivada", "Finna Mix", "Imperial", "Medalha de Prata", "Medalha de Ouro", "Medalha de Ouro ID", "Medalha de Ouro 25Kg", "Medalha de Ouro Premium 25Kg", "Monarca Class", "Monarca Comum", "Monarca Normal" }));
        cbProducaoFarinha.setMaximumSize(new java.awt.Dimension(159, 26));
        cbProducaoFarinha.setMinimumSize(new java.awt.Dimension(159, 26));
        cbProducaoFarinha.setPreferredSize(new java.awt.Dimension(159, 26));

        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Avaria");

        taAvaria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        taAvaria.setText("0");
        taAvaria.setEnabled(false);

        jLabel12.setText("Quantidade");

        tfQuant.setText("0");

        jLabel13.setText("Estoque");

        tfEstoque.setText("0");
        tfEstoque.setEnabled(false);

        javax.swing.GroupLayout paneOcorrenciaProducaoLayout = new javax.swing.GroupLayout(paneOcorrenciaProducao);
        paneOcorrenciaProducao.setLayout(paneOcorrenciaProducaoLayout);
        paneOcorrenciaProducaoLayout.setHorizontalGroup(
            paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                        .addComponent(lbHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                        .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                    .addComponent(btnOpenOcorrencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                    .addComponent(btnFecharOcorrencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbProducaoFarinha, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel7))
                                    .addComponent(taAvaria, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(tfQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        paneOcorrenciaProducaoLayout.setVerticalGroup(
            paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                        .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(btnFecharOcorrencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnOpenOcorrencia1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbProducaoFarinha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(taAvaria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(paneOcorrenciaProducaoLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(paneOcorrenciaProducaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        /*

        paneMain.add(paneOcorrenciaProducao, "card3");
        */
        paneMain.add(paneOcorrenciaProducao, "producao");

        jButton3.setText("Abrir relatório de Produção");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Abrir relatório de Paradas");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Eficiencia geral do Carrossel"));

        jLabel1.setText("Pacotes bons");

        tfPBom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfPBom.setText("12000");
        tfPBom.setToolTipText("Pacotes bons menos avarias.");
        tfPBom.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfPBom.setEnabled(false);
        tfPBom.setFocusable(false);

        jLabel2.setText("Pacotes Rejeitados");

        tfpAva.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfpAva.setText("0");
        tfpAva.setToolTipText("Avaria");
        tfpAva.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfpAva.setEnabled(false);
        tfpAva.setFocusable(false);

        tfTimeStopped.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTimeStopped.setText("0");
        tfTimeStopped.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfTimeStopped.setEnabled(false);
        tfTimeStopped.setFocusable(false);

        jLabel3.setText("Tempo parado");

        jLabel4.setText("Taxa de produtividade");

        tfTaxa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTaxa.setText("0");
        tfTaxa.setToolTipText("Pacotes bons divididos  por 100 e multiplicado por avaria");
        tfTaxa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfTaxa.setEnabled(false);
        tfTaxa.setFocusable(false);

        jLabel6.setText("Tempo em operação");

        tfTimeRunning.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTimeRunning.setText("0");
        tfTimeRunning.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfTimeRunning.setEnabled(false);
        tfTimeRunning.setFocusable(false);

        jLabel9.setText("Sacos por minuto");

        tfSM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSM.setText("0");
        tfSM.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tfSM.setEnabled(false);
        tfSM.setFocusable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfpAva)
                    .addComponent(tfPBom))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfTimeStopped)
                    .addComponent(tfTimeRunning, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                    .addComponent(tfSM))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {tfPBom, tfTaxa, tfpAva});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPBom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfpAva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfTimeStopped, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfTimeRunning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tfTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tfSM)))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel2, jLabel3, jLabel4, jLabel6, jLabel9, tfPBom, tfSM, tfTaxa, tfTimeRunning, tfTimeStopped, tfpAva});

        lbH.setFont(new java.awt.Font("Calibri", 1, 140)); // NOI18N
        lbH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbH.setText("00:00:00");

        javax.swing.GroupLayout panePrincipalLayout = new javax.swing.GroupLayout(panePrincipal);
        panePrincipal.setLayout(panePrincipalLayout);
        panePrincipalLayout.setHorizontalGroup(
            panePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panePrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbH, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        panePrincipalLayout.setVerticalGroup(
            panePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panePrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panePrincipalLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        /*

        paneMain.add(panePrincipal, "card4");
        */
        paneMain.add(panePrincipal, "principal");

        jLabel8.setText("Informe o número de embalagens avariadas na última hora");

        jftAvaria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jftAvaria.setText("000");

        jButton5.setText("Salvar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        lbAvariaTime.setFont(new java.awt.Font("sansserif", 0, 40)); // NOI18N
        lbAvariaTime.setText("300");

        jLabel10.setText(" sec para fechar a janela.");

        javax.swing.GroupLayout paneAvariaLayout = new javax.swing.GroupLayout(paneAvaria);
        paneAvaria.setLayout(paneAvariaLayout);
        paneAvariaLayout.setHorizontalGroup(
            paneAvariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneAvariaLayout.createSequentialGroup()
                .addGroup(paneAvariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paneAvariaLayout.createSequentialGroup()
                        .addComponent(lbAvariaTime)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jftAvaria, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(0, 678, Short.MAX_VALUE))
        );
        paneAvariaLayout.setVerticalGroup(
            paneAvariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneAvariaLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(paneAvariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftAvaria, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneAvariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbAvariaTime, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        paneAvariaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel8, jftAvaria});

        /*

        paneMain.add(paneAvaria, "card5");
        */
        paneMain.add(paneAvaria, "avaria");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(456, 224));
        jPanel4.setMinimumSize(new java.awt.Dimension(456, 224));
        jPanel4.setPreferredSize(new java.awt.Dimension(456, 224));

        tfSpeed.setEditable(false);
        tfSpeed.setFont(new java.awt.Font("Calibri", 1, 56)); // NOI18N
        tfSpeed.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfSpeed.setText("0");
        tfSpeed.setFocusable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSpeed)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tfSpeed)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(paneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 1197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(36, 36, 36)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 1191, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnComment, javax.swing.GroupLayout.PREFERRED_SIZE, 1191, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel50, jPanel2});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel51, jPanel4});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel17, jPanel3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paneMain, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnComment, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfMsg)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean countdown = false;   
    private void btnCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCommentActionPerformed
        jdComment.pack();
        jdComment.setVisible(true);     
        countdown = true;        
    }//GEN-LAST:event_btnCommentActionPerformed

    private void bStoreCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStoreCommentActionPerformed
        salvaComment();
    }//GEN-LAST:event_bStoreCommentActionPerformed
    
    private void btnOpenOcorrenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenOcorrenciaActionPerformed
        if(boolOcorrencia) { // se true abilita o botão de abrir ocorrencia
            btnOpenOcorrencia.setEnabled(true);
            btnFecharOcorrencia.setEnabled(false);
            taRelatorio.setEnabled(false);
        }
        else{
            btnOpenOcorrencia.setEnabled(false);
            btnFecharOcorrencia.setEnabled(true);
            taRelatorio.setEnabled(true);
        }
        boolOcorrencia = true;
        // Apenas obtem a hora de inicio do relatório
        inicioRelatorio = DateFormat.getDateInstance().format(new Date()) +" "+
                          DateFormat.getTimeInstance().format(new Date());
    }//GEN-LAST:event_btnOpenOcorrenciaActionPerformed

    private void btnFecharOcorrenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharOcorrenciaActionPerformed
        if(boolOcorrencia) { // se true abilita o botão de abrir ocorrencia
            btnOpenOcorrencia.setEnabled(true);
            btnFecharOcorrencia.setEnabled(false);
            taRelatorio.setEnabled(false);
        }
        else{
            btnOpenOcorrencia.setEnabled(false);
            btnFecharOcorrencia.setEnabled(true);
            taRelatorio.setEnabled(true);
        }
        boolOcorrencia = false;
        // obtem a hora final do relatorio
        fimRelatorio = DateFormat.getDateInstance().format(new Date()) +" "+
                          DateFormat.getTimeInstance().format(new Date());       
        arrayRelatorio.add(new String[]{inicioRelatorio,fimRelatorio,
            cbParadaFarinha.getSelectedItem().toString(),taRelatorio.getText()});
        tblRelatoriosParada.setModel(new MyTableModel(arrayRelatorio,col,edit));  
        
        StringBuilder sb = new StringBuilder();
        sb.append(inicioRelatorio);sb.append(";");
        sb.append(fimRelatorio);sb.append(";");
        sb.append(cbParadaFarinha.getSelectedItem());sb.append(";");
        sb.append(taRelatorio.getText());sb.append(";\n");
                 
        try{
            f.addLinhaParada(sb);
        }catch(IOException e){
            exception("Erro na linha 1747 "+e.getMessage());
        }
        taRelatorio.setText("");
    }//GEN-LAST:event_btnFecharOcorrenciaActionPerformed

    private void btnOpenOcorrencia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenOcorrencia1ActionPerformed
        
    }//GEN-LAST:event_btnOpenOcorrencia1ActionPerformed

    private void btnFecharOcorrencia1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharOcorrencia1ActionPerformed
        
    }//GEN-LAST:event_btnFecharOcorrencia1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        CardLayout card = (CardLayout)(paneMain.getLayout());
        card.show(paneOcorrenciaProducao.getParent(), "producao");

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        CardLayout card = (CardLayout)paneMain.getLayout();
        card.show(paneOcorrenciaManutencao.getParent(), "parada");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CardLayout card = (CardLayout)paneMain.getLayout();
        card.show(panePrincipal.getParent(), "principal");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CardLayout card = (CardLayout)paneMain.getLayout();
        card.show(panePrincipal.getParent(), "principal");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        cincoMinEvent();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tfMsgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tfMsgMouseClicked
        if(evt.getClickCount() == 2){
            jdAjust.pack();
            jdAjust.setVisible(true);
        }
    }//GEN-LAST:event_tfMsgMouseClicked

    private void btnAjustHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjustHoraActionPerformed
        
        if(!jftSenha.getText().equals("27128")){
            JOptionPane.showMessageDialog(this, "A senha digitada esta errada.",
                    "Erro!",JOptionPane.ERROR_MESSAGE);  
            jdAjust.setVisible(false);
            return;
        }
        int count=0;
        int total = 0;
        for(int i=0;i<tblAjust.getColumnCount();i++){
            arrayHoras[i] = Integer.parseInt(tblAjust.getValueAt(0, i).toString());            
            if(!(tblAjust.getValueAt(0,i).toString().equals("0"))){
                count++;
                total += arrayHoras[i];
            }
        }
        pctAcum = total;
        if(total != 0)
            total = (int) ((total/count)*20.5);
        tfMeta.setText(convert(total));
        cor(total);
        jdAjust.setVisible(false);
    }//GEN-LAST:event_btnAjustHoraActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if(jComboBox1.getSelectedItem().toString().equals("SIM")) jTextField1.setEnabled(true);            
        else jTextField1.setEnabled(false);            
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged
        if(jComboBox2.getSelectedItem().toString().equals("SIM")) jTextField2.setEnabled(true);            
        else jTextField2.setEnabled(false);  
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        if(jComboBox3.getSelectedItem().toString().equals("NAO")) jTextField3.setEnabled(true);            
        else jTextField3.setEnabled(false);  
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        if(jComboBox4.getSelectedItem().toString().equals("NAO")) jTextField4.setEnabled(true);            
        else jTextField4.setEnabled(false);  
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        if(jComboBox5.getSelectedItem().toString().equals("SIM")) jTextField9.setEnabled(true);            
        else jTextField9.setEnabled(false);  
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox8ItemStateChanged
        if(jComboBox8.getSelectedItem().toString().equals("NAO")) jTextField8.setEnabled(true);            
        else jTextField8.setEnabled(false);  
    }//GEN-LAST:event_jComboBox8ItemStateChanged

    private void jComboBox7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox7ItemStateChanged
        if(jComboBox7.getSelectedItem().toString().equals("SIM")) jTextField7.setEnabled(true);            
        else jTextField7.setEnabled(false);  
    }//GEN-LAST:event_jComboBox7ItemStateChanged

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        if(jComboBox6.getSelectedItem().toString().equals("SIM")) jTextField6.setEnabled(true);            
        else jTextField6.setEnabled(false);  
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox9ItemStateChanged
        if(jComboBox9.getSelectedItem().toString().equals("SIM")) jTextField10.setEnabled(true);            
        else jTextField10.setEnabled(false);  
    }//GEN-LAST:event_jComboBox9ItemStateChanged

    private void jComboBox12ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox12ItemStateChanged
        if(jComboBox12.getSelectedItem().toString().equals("SIM")) jTextField13.setEnabled(true);            
        else jTextField13.setEnabled(false);  
    }//GEN-LAST:event_jComboBox12ItemStateChanged

    private void jComboBox11ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox11ItemStateChanged
        if(jComboBox11.getSelectedItem().toString().equals("SIM")) jTextField12.setEnabled(true);            
        else jTextField12.setEnabled(false);  
    }//GEN-LAST:event_jComboBox11ItemStateChanged

    private void jComboBox10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox10ItemStateChanged
        if(jComboBox10.getSelectedItem().toString().equals("NAO")) jTextField11.setEnabled(true);            
        else jTextField11.setEnabled(false);  
    }//GEN-LAST:event_jComboBox10ItemStateChanged

    private void jComboBox13ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox13ItemStateChanged
        if(jComboBox13.getSelectedItem().toString().equals("SIM")) jTextField14.setEnabled(true);            
        else jTextField14.setEnabled(false);  
    }//GEN-LAST:event_jComboBox13ItemStateChanged

    private void jComboBox14ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox14ItemStateChanged
        if(jComboBox14.getSelectedItem().toString().equals("NAO")) jTextField15.setEnabled(true);            
        else jTextField15.setEnabled(false);  
    }//GEN-LAST:event_jComboBox14ItemStateChanged

    private void jComboBox15ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox15ItemStateChanged
        if(jComboBox15.getSelectedItem().toString().equals("NAO")) jTextField16.setEnabled(true);            
        else jTextField16.setEnabled(false);  
    }//GEN-LAST:event_jComboBox15ItemStateChanged

    private void jComboBox16ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox16ItemStateChanged
        if(jComboBox16.getSelectedItem().toString().equals("NAO")) jTextField17.setEnabled(true);            
        else jTextField17.setEnabled(false);  
    }//GEN-LAST:event_jComboBox16ItemStateChanged

    private void jComboBox18ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox18ItemStateChanged
        if(jComboBox18.getSelectedItem().toString().equals("SIM")) jTextField19.setEnabled(true);            
        else jTextField19.setEnabled(false);  
    }//GEN-LAST:event_jComboBox18ItemStateChanged

    private void jComboBox17ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox17ItemStateChanged
        if(jComboBox17.getSelectedItem().toString().equals("SIM")) jTextField18.setEnabled(true);            
        else jTextField18.setEnabled(false);  
    }//GEN-LAST:event_jComboBox17ItemStateChanged

    private void jComboBox19ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox19ItemStateChanged
        if(jComboBox19.getSelectedItem().toString().equals("NAO")) jTextField20.setEnabled(true);            
        else jTextField20.setEnabled(false);  
    }//GEN-LAST:event_jComboBox19ItemStateChanged

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        myPort = args[0];
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(R.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(R.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(R.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(R.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                R frame = new R();
                frame.setVisible(true);
                WindowsSecurity ws = new WindowsSecurity(frame);
                ws.run();             
                
            }
        });
    }

    private int[][] meiaHora = new int[24][4];
    private boolean control = false;
    private int ocount = 0;
    private int oavaria = 0;
    @Override
    public void run() {        
        while(true){
            Date d=new Date();
            lbHora.setText(DateFormat.getTimeInstance().format(d));
            lbHora1.setText(DateFormat.getTimeInstance().format(d));
            lbHora2.setText(DateFormat.getTimeInstance().format(d));
            lbH.setText(DateFormat.getTimeInstance().format(d));
            if(!isConnected)
            {
                try{
                    System.out.println(Arrays.toString(SerialPortList.getPortNames()));
                    serial = new SerialPort(myPort);
                    serial.openPort();
                    serial.setParams(9600, 8, 1, 0);
                    serial.setEventsMask(SerialPort.MASK_RXCHAR);
                    serial.addEventListener(this);
                    tfMsg.setText("Conectado com dispositivo externo.");
                    isConnected = true;
                }catch(SerialPortException spe){
                    exception("Exception na comunicação serial: "+spe.getExceptionType());
                    tfMsg.setText("Falha de comunicação com dispositivo gerador de sinal.");
                }
            }
            startOneHoraTimer(d.getMinutes());// apenas de estiver no minuto 0 e var startOneHoraTimer for FALSE
            startQuinzeMinTimer(d.getMinutes());  
            switch(d.getHours()){
                case 0:// 0 horas    
                    MotherFucking(0,d.getMinutes());                    
                    break;
                case 1:// 1 horas
                    MotherFucking(1,d.getMinutes());
                    break;
                case 2:// 2 horas
                    MotherFucking(2,d.getMinutes());
                    break;
                case 3:// 3 horas
                    MotherFucking(3,d.getMinutes());
                    break;
                case 4:// 4 horas
                    MotherFucking(4,d.getMinutes());
                    break;
                case 5:// 5 horas
                    MotherFucking(5,d.getMinutes());
                    break;
                case 7:// 7 horas  
                    MotherFucking(7,d.getMinutes());
                    break;
                case 6:// 6 horas
                    MotherFucking(6,d.getMinutes());
                    break;
                case 8:// 0 horas
                    MotherFucking(8,d.getMinutes());
                    break;
                case 9:// 1 horas
                    MotherFucking(9,d.getMinutes());
                    break;
                case 10:// 2 horas
                    MotherFucking(10,d.getMinutes());
                    break;
                case 11:// 3 horas
                    MotherFucking(11,d.getMinutes());
                    break;
                case 12:// 4 horas
                    MotherFucking(12,d.getMinutes());
                    break;
                case 13:// 5 horas
                    MotherFucking(13,d.getMinutes());
                    break;
                case 14:// 6 horas
                    MotherFucking(14,d.getMinutes());
                    break;
                case 15:// 7 horas
                    MotherFucking(15,d.getMinutes());
                    break;
                case 16:// 0 horas
                    MotherFucking(16,d.getMinutes());
                    break;
                case 17:// 1 horas
                    MotherFucking(17,d.getMinutes());
                    break;
                case 18:// 2 horas
                    MotherFucking(18,d.getMinutes());
                    break;
                case 19:// 3 horas
                    MotherFucking(19,d.getMinutes());
                    break;
                case 20:// 4 horas
                    MotherFucking(20,d.getMinutes());
                    break;
                case 21:// 5 horas
                    MotherFucking(21,d.getMinutes());
                    break;
                case 22:// 6 horas
                    MotherFucking(21,d.getMinutes());
                    break;
                case 23:// 7 horas
                    MotherFucking(23,d.getMinutes());
                    break;
                default:
                    System.out.println("Que hora é essa?");
            }
            tfAcum.setText(convert(pctAcum));
            pctBom = pctAcum-avaria;
            taxa = 100-((pctBom/100)*pctReject);            
            tfPBom.setText(convert(pctBom));
            tfpAva.setText(convert(avaria));
            
            tfTaxa.setText(Float.toString(taxa));
            
            // Contagem de tempo parado
            if(pctAcum == in){
                if(z < 20){
                    z++;
                    Count = true;
                }else{
                    time++;
                    Count = false;
                    hf = time / 3600;
                    rem = time%3600;
                    mf = time/60;
                    sf = time%60;
                    tfTimeStopped.setText((hf<10 ? "0" : "")+hf+":"+(mf<10 ? "0" : "")+mf+":"+(sf<10 ? "0" : "")+sf);
                }
            }else{
                z = 0;
                Count = true;
            }
            in = pctAcum;
            // Contagem de tempo parado FIM
            
            // Contagem de tempo rodando           
            if(Count){
                run++;
                hi = run / 3600;
                rem = run%3600;
                mi = run/60;
                si = run%60;
                tfTimeRunning.setText((hi<10 ? "0" : "")+hi+":"+(mi<10 ? "0" : "")+mi+":"+(si<10 ? "0" : "")+si);                
            }
             // Contagem de tempo rodando FIM
            
            if(count > 0 && timer2_5m)
                lbAvariaTime.setText(convert(count--));
            else
                count = 300;
                  
                
            try{Thread.sleep(1000);}
            catch(InterruptedException e){continue;}
            //isStopped(pctAcum);
            
            
        }
    }   
    private void MotherFucking(int i, int m){
        if(m == 0 && !control){
            control = true;
            meiaHora[i][0]=ocount;
            meiaHora[i][1]=avaria-oavaria;
            ocount = 0;
            oavaria= 0;
        }else if(m == 30 && !control){
            control = true;
            meiaHora[i][2]=ocount;
            meiaHora[i][3]=avaria-oavaria;
            ocount = 0;
        }else if(m != 0 && m != 30)
            control = false;
        
        oavaria = avaria;
    }
    private String convert(int i){return Integer.toString(i);}
    public void cor(int v){        
        if( v > 9000 && v < 10000)
            tfMeta.setForeground(Color.ORANGE);
        else if(v < 9000)
            tfMeta.setForeground(Color.RED);
        else
            tfMeta.setForeground(Color.GREEN);
    }
    private int in=0,to=0,z=0, zz=0;
    private boolean Count = true;
    private Date dateIn,date;
    private int hi,mi,si,hf,mf,sf,rem, time, run;
    
    private void isStopped(int p){     
        //
        if(in == p && !Count){    
            z++;//20 sec
            if(z==20){
                date=new Date();      
                dateIn = date;           
                hi = date.getHours();
                mi = date.getMinutes();
                si = date.getSeconds();
                Count = true;
                System.out.println("Iniciando contagem.");
                in=p;
                z=0;
            }
            // se pctAcum foi incrementado e estava contando...,
        }else if(in != p && Count){
                // termina a contagem
            date=new Date();
            Date ddd = new Date(hi,mi,si);
            long diferenca = date.getTime() - dateIn.getTime();
            long diferencaSeg = diferenca / 1000 % 60;    //DIFERENCA EM SEGUNDOS     
            long diferencaMin = diferenca / (60 * 1000) % 60;    //DIFERENCA EM MINUTOS     
            long diferencaHoras = diferenca / (60 * 60 * 1000) % 24;    // DIFERENCA EM HORAS 
            long diffDays = diferenca / (24 * 60 * 60 * 1000);
            
            hf = date.getHours();
            mf = date.getMinutes();
            sf = date.getSeconds();
            System.out.print("Hora de inicio:"+hi+":"+mi+":"+si+"\t fim: ");
            System.out.println(hf+":"+mf+":"+sf);
            time += difHora(hi,mi,si,hf,mf,sf);
            
            tfTimeStopped.setText(diferencaHoras+":"+diferencaMin+":"+diferencaSeg);
            /*if(time < 60 )
                tfTimeStopped.setText(convert(time)+"sec");
            else if(time > 60 && time < 3600)
                tfTimeStopped.setText(convert(time/60)+"min");
            else
                tfTimeStopped.setText(convert(time/3600)+"hr");*/
            
            Count = false;
            System.out.println("Fim contagem, total: "+time);
            //tfTimeStopped.setText(convert(time));
            
            
        }else{
            System.out.println("in: "+in+"\t p:"+p+"\t z:"+z);     
        }
        in = p;
    }
    private int difHora(int h, int m, int s, int hf, int mf, int sf){
            int mm,hh,ss;
            if(mf < m)
                mm = (60 - m) + mf;
            else
                mm = mf - m;
            
            if(hf < h)
                hh = (60 - h) + hf;
            else
                hh = hf - h;
            
            if(sf < s)
                ss = (60 - s) + sf;
            else
                ss = sf - s;
            
            return ((hh*360)+(mm*60)+ss);
        }
        
    @Override    
    public void serialEvent(SerialPortEvent spe) {
        
        try{
            s = serial.readString();
            //System.out.println("Received: "+s);
            while(s != null){
                if(spe.isRXCHAR() && s.equals("a")){
                    pulse++;
                    pctAcum++;
                    qtdPacote++;
                    ocount++;
                    //System.out.println("49received: "+s); 
                    s=null;
                    /*try{
                        serial.writeString("b");
                    }catch(SerialPortException s){s.printStackTrace();} */        
                }/*else if(spe.isRXCHAR() && s.equals("b")){
                    //System.out.println("2received: "+s); 
                    s=null;
                }*/
            }
        }catch (SerialPortException ex) {
                ex.printStackTrace();
        }
    }
    /* calcula a media de 20,5h */
    private String Calc(int loop){  
        arrayHoras[25]=0;
        if((loop > 7) && (loop < 24)){// de 8 horas até 23
            div = 0;
            for(int i=8;i<=loop;i++){
                arrayHoras[25] += arrayHoras[i];
                div++;
            }
            arrayHoras[25] = (int) ((arrayHoras[25] / div)*20.5);
        }else if((loop >= 0) && (loop <= 7)){// de 0 hores até 7
            div = 17;
            for(int i=0;i<=loop;i++){
                arrayHoras[25] += arrayHoras[i];
                div++;
            }
            arrayHoras[25] = (int) ((arrayHoras[25] / div)*20.5);
        }
        return convert(arrayHoras[25]);
    }   
    /* executa funções expecificas de cada hora*/
    private void do7Stuff(){
        // salva arquivo
                arrayHoras[7] = qtdPacote;
                 
                //salvaNumerosProducao();
                try{
                    for(int i=0;i<meiaHora.length;i++){
                        f.saveMeiaHora(i, 0,meiaHora[i][0],meiaHora[i][1]); 
                        f.saveMeiaHora(i, 30,meiaHora[i][2],meiaHora[i][3]); 
                    }
                }catch(IOException e){
                    exception("Erro (IOException) na linha 2400"+
                        "Função salvaNumerosProducao com a seguinte mensagem: "+
                    e.getMessage());
                }finally{try { f.horahoraFileClose(); } catch (IOException ex) {ex.printStackTrace();}}

                // zera array
                for(int i=0;i<arrayHoras.length;i++){  arrayHoras[i] = 0; }
                for(int i=0;i<meiaHora.length;i++)  
                    for(int z=0;z<4;z++)
                        meiaHora[i][z] = 0;
                
                qtdPacote = 0;
                pctReject = 0;
                pctAcum = 0;
                pctBom = 0;
                taxa = 0;
                avaria = 0;
                time = 0;
                run = 0;
                
                arrayAjust.clear();
                arrayRelatorio.clear();
                arrayRelatorioProducao.clear();
                
                tblAjust.setModel(new MyTableModel(arrayAjust,col2,edit2));
                tblRelatoriosParada.setModel(new MyTableModel(arrayRelatorio,col,edit));
                tblRelatoriosProducao.setModel(new MyTableModel(arrayRelatorioProducao,col1,edit1)); 
    }
    /* Inicia timer de 1hora */
    private void startOneHoraTimer(int mm){
        if(mm == 0 && !startHourTime){
            timerUmaHora.scheduleAtFixedRate(new eventUmaHora(), 1000, 3600000);
            startHourTime = true;
        }
    }
    /* Inicia timer de quinze minutos */
    private void startQuinzeMinTimer(int mm){
        /*if(((mm >= 0 && mm <= 5) || (mm >= 15 && mm <= 20)|| 
            (mm >= 30 && mm <= 35)|| (mm >= 45 && mm <= 50)) && !startQuinzeTime)
            {*/
        /* Inicia apenas se for o primeiro minuto, caso tenha passado
         * algum tempo ele espera promixo timer para executar. */
        if((mm==0|| mm==15 || mm==30 || mm==45) && !startQuinzeTime){
            timerQuinzeMinutos.scheduleAtFixedRate(new eventQuinzeMin(), 1000, 900000);
            startQuinzeTime = true;
        } 
    }
    
    private void salvaComment(){
        // cancela o tesk de 5 min pois essa função pode ser chamada antes do task.
        if(!timer1_5m)
            return;
        
        StringBuilder sb = new StringBuilder();
        String output;        
        Date t = new Date();
        
        output = DateFormat.getDateInstance().format(t)+" "+
                 DateFormat.getTimeInstance().format(t);
        sb.append("Relatorio Enc gerado em: ");
        sb.append(";");
        sb.append(output);
        sb.append("\n");
        
        // MWPL             Linha 01
        sb.append(jLabel14.getText());sb.append(";");sb.append(jComboBox1.getSelectedItem());sb.append(";");sb.append(jTextField1.getText());
        sb.append(";;");
        // Union Special    Linha 01
        sb.append(jLabel24.getText());sb.append(";");sb.append(jComboBox8.getSelectedItem());sb.append(";");sb.append(jTextField8.getText());
        sb.append(";;");
        // Esteira          Linha 01
        sb.append(jLabel31.getText());sb.append(";");sb.append(jComboBox12.getSelectedItem());sb.append(";");sb.append(jTextField13.getText());        
        sb.append(";;");
        // Videojet         Linha 01
        sb.append(jLabel38.getText());sb.append(";");sb.append(jComboBox14.getSelectedItem());sb.append(";");sb.append(jTextField15.getText());
        sb.append("\n");
        // MWPL             Linha 02
        sb.append(jLabel16.getText());sb.append(";");sb.append(jComboBox2.getSelectedItem());sb.append(";");sb.append(jTextField2.getText());
        sb.append(";;");
        // Union Special    Linha 02
        sb.append(jLabel25.getText());sb.append(";");sb.append(jComboBox7.getSelectedItem());sb.append(";");sb.append(jTextField7.getText());
        sb.append(";;");
        // Esteira          Linha 02
        sb.append(jLabel32.getText());sb.append(";");sb.append(jComboBox11.getSelectedItem());sb.append(";");sb.append(jTextField12.getText());        
        sb.append(";;");
        // Videojet         Linha 02
        sb.append(jLabel40.getText());sb.append(";");sb.append(jComboBox15.getSelectedItem());sb.append(";");sb.append(jTextField16.getText());
        sb.append("\n");
        // MWPL             Linha 03
        sb.append(jLabel19.getText());sb.append(";");sb.append(jComboBox3.getSelectedItem());sb.append(";");sb.append(jTextField3.getText());
        sb.append(";;");
        // Union Special    Linha 03
        sb.append(jLabel26.getText());sb.append(";");sb.append(jComboBox6.getSelectedItem());sb.append(";");sb.append(jTextField6.getText());
        sb.append(";;");
        // Esteira          Linha 03
        sb.append(jLabel33.getText());sb.append(";");sb.append(jComboBox10.getSelectedItem());sb.append(";");sb.append(jTextField11.getText());        
        sb.append(";;");
        // Videojet         Linha 03
        sb.append(jLabel43.getText());sb.append(";");sb.append(jComboBox16.getSelectedItem());sb.append(";");sb.append(jTextField17.getText());
        sb.append("\n");
        // MWPL             Linha 04
        sb.append(jLabel21.getText());sb.append(";");sb.append(jComboBox4.getSelectedItem());sb.append(";");sb.append(jTextField4.getText());
        sb.append(";;");
        // Union Special    Linha 04
        sb.append(jLabel30.getText());sb.append(";");sb.append(jComboBox9.getSelectedItem());sb.append(";");sb.append(jTextField10.getText());
        sb.append(";;");
        // Esteira          Linha 04
        sb.append(jLabel37.getText());sb.append(";");sb.append(jComboBox13.getSelectedItem());sb.append(";");sb.append(jTextField14.getText());        
        sb.append(";;");
        // Videojet         Linha 04
        sb.append(jLabel45.getText());sb.append(";");sb.append(jComboBox18.getSelectedItem());sb.append(";");sb.append(jTextField19.getText());
        sb.append("\n");
        // MWPL             Linha 05
        sb.append(jLabel23.getText());sb.append(";");sb.append(jComboBox5.getSelectedItem());sb.append(";");sb.append(jTextField9.getText());
        sb.append(";;");
        // Videojet         Linha 05
        sb.append(jLabel44.getText());sb.append(";");sb.append(jComboBox17.getSelectedItem());sb.append(";");sb.append(jTextField18.getText());
        sb.append(";;");
        sb.append(jLabel47.getText());sb.append(";");sb.append(jComboBox19.getSelectedItem());sb.append(";");sb.append(jTextField20.getText());
        sb.append(";");sb.append(tfBal01.getText());sb.append(";");sb.append(tfBal02.getText());
        sb.append(";;");
        sb.append(jeComment.getText());
        sb.append("\n");
        sb.append("Timer Stopped:");
        sb.append(";");
        sb.append(tfTimeStopped.getText());
        sb.append(";");
        sb.append("\n");
        sb.append("Timer Running:");
        sb.append(";");
        sb.append(tfTimeRunning.getText());
        sb.append("\n\n");
            
        try {
            f.addComment(sb);
        } catch (IOException ex) {ex.printStackTrace();
        }finally{
            // apos salvar o arquivo
            jeComment.setText("");
            jdComment.setVisible(false); // esconde o dialogo
            btnComment.setEnabled(false);  // por segurança, desabilita o botão 
        }            
        timer1_5m = false; 
        tfBal01.setEditable(false);
        tfBal02.setEditable(false);
        countdown = false;
    }
    
    private void salvaNumerosProducao(){
        try{
            for(int i=0;i<arrayHoras.length-2;i++)
                f.saveHoraHora(i, arrayHoras[i]);    
        }catch(IOException e){
            exception("Erro (IOException) na linha 2400"+
                      "Função salvaNumerosProducao com a seguinte mensagem: "+
                      e.getMessage());
        }finally{try { f.horahoraFileClose(); } catch (IOException ex) {ex.printStackTrace();}
} 
    }
    /* Responsavel por retornar ao pane principal
     * somar a avaria e mostrar a meta */ 
    private void cincoMinEvent(){
        if(!timer2_5m)
                return;
            CardLayout card = (CardLayout)paneMain.getLayout();
            card.show(panePrincipal.getParent(), "principal");
            Date d = new Date();
            avaria += Integer.parseInt(jftAvaria.getText());
            tfpAva.setText(convert(avaria));
            if(d.getHours() == 7)//{
                do7Stuff();
            /*}else if(d.getHours() == 0){
                arrayHoras[23] = qtdPacote;
                qtdPacote=0;
            }else{*///
                arrayHoras[d.getHours()] = qtdPacote;// subtrai 1 para pegar a hora anterior
                qtdPacote=0;
            //}
            tfMeta.setText(Calc(d.getHours()));
            timer2_5m = false;
    }

   

    /* Call cinco Min Event */ 
    private class eventCincoMin2 extends TimerTask{
        @Override
        public void run() {
            cincoMinEvent();
        }        
    }
    private class eventCincoMin1 extends TimerTask{
        @Override
        public void run() {
            salvaComment();
        }
        
    }
    private class eventQuinzeMin extends TimerTask{
        @Override
        public void run() {
            //abilita o botão
            btnComment.setEnabled(true);  
            
            Date d = new Date();
            int min = d.getMinutes();
            long t=0;
            if(min >= 0 && min <= 5)
                t = (5-min)*60000;
            if(min >= 15 && min <= 20)
                t = (20-min)*60000;
            if(min >= 30 && min <= 35)
                t = (35-min)*60000;
            if(min >= 45 && min <= 50)
                t = (50-min)*60000;
            
            timerCincoMinutes1.schedule(new eventCincoMin1(), t);
            timer1_5m = true;
        }
        
    }
    /* Start time de 5 minutos para retornar ao Pane principal
     * chama JPAnel de avarias */
    private class eventUmaHora extends TimerTask{
        @Override
        public void run() {
            // A cada 01 hora essa rotina muda o pane para o de 
            // avaria e start um time de 5 minutes responsavel para voltar o pane
            timerCincoMinutes2.schedule(new eventCincoMin2(), 300000);
            /* variavel de controle */
            timer2_5m = true;
            /* retorna o pane */
            CardLayout card = (CardLayout)paneMain.getLayout();
            card.show(paneAvaria.getParent(), "avaria");            
            tfBal01.setEditable(true);
            tfBal02.setEditable(true);
        }        
    }
    
    private class calcVelocidade implements Runnable{
        private int start,end,total;
        
        @Override
        public void run() {
            while(true){
            start = pulse;
            // dorme 30 sec
            try { Thread.sleep(30000);} catch (InterruptedException ex) {
                exception("Erro (IOException) na linha 1554"+
                            "Função calcVelocidade com a seguinte mensagem: "+
                            ex.getMessage());
            }
            end = pulse;            
            total = end - start;
            pulse = 0;
            
            tfSpeed.setText(Integer.toString((total*2)*60));
            tfSM.setText(convert(total*2));
            }
        }
        private int difHora(int h, int m, int s, int hf, int mf, int sf){
            int mm,hh,ss;
            if(mf < m)
                mm = (60 - m) + mf;
            else
                mm = mf - m;
            
            if(hf < h)
                hh = (60 - h) + hf;
            else
                hh = hf - h;
            
            if(sf < s)
                ss = (60 - s) + sf;
            else
                ss = sf - s;
            
            return ((hh*360)+(mm*60)+ss);
        }
        
    }
    FileOutputStream expeption;
    private void exception(String message){
        if(true)
        {
            String output;
            Date time = new Date();
            output = DateFormat.getDateInstance().format(time) +" "+
                     DateFormat.getTimeInstance().format(time) +
                     ": " + message + "\r\n";
            try
            {
                expeption = new FileOutputStream("Relatorio de falhas.csv", true);
                expeption.write(output.getBytes());
            }catch(IOException e){}
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bStoreComment;
    private javax.swing.JButton btnAjustHora;
    private javax.swing.JButton btnComment;
    private javax.swing.JButton btnFecharOcorrencia;
    private javax.swing.JButton btnFecharOcorrencia1;
    private javax.swing.JButton btnOpenOcorrencia;
    private javax.swing.JButton btnOpenOcorrencia1;
    private javax.swing.JComboBox cbParadaFarinha;
    private javax.swing.JComboBox cbProducaoFarinha;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox16;
    private javax.swing.JComboBox jComboBox17;
    private javax.swing.JComboBox jComboBox18;
    private javax.swing.JComboBox jComboBox19;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JDialog jdAjust;
    private javax.swing.JDialog jdComment;
    private javax.swing.JEditorPane jeComment;
    private javax.swing.JFormattedTextField jftAvaria;
    private javax.swing.JPasswordField jftSenha;
    private javax.swing.JLabel lbAvariaTime;
    private javax.swing.JLabel lbH;
    private javax.swing.JLabel lbHora;
    private javax.swing.JLabel lbHora1;
    private javax.swing.JLabel lbHora2;
    private javax.swing.JPanel paneAvaria;
    private javax.swing.JPanel paneMain;
    private javax.swing.JPanel paneOcorrenciaManutencao;
    private javax.swing.JPanel paneOcorrenciaProducao;
    private javax.swing.JPanel panePrincipal;
    private javax.swing.JFormattedTextField taAvaria;
    private javax.swing.JTextArea taRelatorio;
    private javax.swing.JTable tblAjust;
    private javax.swing.JTable tblRelatoriosParada;
    private javax.swing.JTable tblRelatoriosProducao;
    private javax.swing.JTextField tfAcum;
    private javax.swing.JTextField tfBal01;
    private javax.swing.JTextField tfBal02;
    private javax.swing.JTextField tfEstoque;
    private javax.swing.JTextField tfMeta;
    private javax.swing.JLabel tfMsg;
    private javax.swing.JTextField tfPBom;
    private javax.swing.JTextField tfQuant;
    private javax.swing.JTextField tfSM;
    private javax.swing.JTextField tfSpeed;
    private javax.swing.JTextField tfTaxa;
    private javax.swing.JTextField tfTimeRunning;
    private javax.swing.JTextField tfTimeStopped;
    private javax.swing.JTextField tfpAva;
    // End of variables declaration//GEN-END:variables
    
    private CarregamentoTableModel tableModelProducao;
    private Collection<CarregamentoEntity> arrayCarregamento = new HashSet<CarregamentoEntity>();
    private List<CarregamentoEntity> arrayCarregamentoPendete = new ArrayList();
    private void initArryList(){        
        try {
            arrayCarregamentoPendete = RMIcarregamento.obterListaDeCarregamento();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }                
        tableModelProducao = new CarregamentoTableModel(arrayCarregamentoPendete);
        tblRelatoriosProducao.setModel(tableModelProducao);
    }
    /* expedição expedindo pedido */
    @Override
    public void adicionarCarregamento(CarregamentoEntity ce) throws RemoteException {
        initArryList();
    }
    /* a lista foi atualizada */
    @Override
    public void atualizarListaDeCarregamento() throws RemoteException {
        initArryList();
        // é necessario corrigir ordem de carregamento toda vez que essa função for carregada.
    }

    @Override
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException {
        System.out.println("msg server");
    }

    @Override
    public void receiverMsgFromCliente(String msg, String u) throws RemoteException {
        System.out.println("msg cliente");
    }    
}// Elaborado por Alirio Freire em 31/10/2014
