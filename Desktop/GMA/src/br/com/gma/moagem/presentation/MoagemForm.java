/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.moagem.presentation;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.cliente.common.AutenticadorInterface;
import br.com.gma.cliente.common.FileProperties;
import br.com.gma.cliente.common.FileUpdateProperties;
import br.com.gma.cliente.common.JCalendar;
import br.com.gma.cliente.common.MainFormInterface;
import br.com.gma.cliente.common.PedidoTableModel;
import br.com.gma.cliente.common.ProgressBarInterface;
import br.com.gma.expedicao.presentation.ExpedicaoForm;
import br.com.gma.relatorios.Carregamento;
import br.com.gma.relatorios.model.FarinhasEntity;
import br.com.gma.server.business.facade.remote.CallBackServerInterface;
import br.com.gma.server.business.facade.remote.RemoteCargo;
import br.com.gma.server.business.facade.remote.RemoteCliente;
import br.com.gma.server.business.facade.remote.RemotePedido;
import br.com.gma.server.business.facade.remote.RemoteProduto;
import br.com.gma.server.business.facade.remote.RemoteUpdate;
import br.com.gma.server.business.facade.remote.RemoteUsuario;
import br.com.gma.server.common.entity.Pedido;
import br.com.gma.server.common.entity.Produto;
import br.com.gma.server.common.entity.Sessao;
import java.awt.event.KeyEvent;
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
import java.util.Stack;
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
public class MoagemForm extends javax.swing.JFrame implements ClientInterface, 
        AutenticadorInterface, MainFormInterface, Serializable,Runnable,ProgressBarInterface{

    /**
     * Creates new form MoagemForm
     */
    private static final String myVersion = "0.3";
    private List<FarinhasEntity> rel;
    private Carregamento carregamentoREL;
    private FileProperties f;
    private FileUpdateProperties fileUpdate;
    private RemoteUpdate versionControl;
    private int count = 7;
    private EstoqueForm estoqueForm;
    
    public MoagemForm() {
        try {
            f = new FileProperties();
            
        } catch (Exception ex) {ex.printStackTrace();}
        
        try {
            System.out.println(f.ServidorIP());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        try{
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                System.out.println(info.getName());
            }
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        }catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MoagemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MoagemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MoagemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MoagemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        initComponents();
        setVisible(true);
        try {
            if(f.isAutoConnect()){
                if(connect()){
                    isConnected = true;
                    String serverVersion = versionControl.checkVersion();
                    if(serverVersion.equals(myVersion)){
                        System.out.println("Servidor Versão: "+versionControl.checkVersion(""));
                        progID = servidor.obterID();
                        
                        servidor.registerForCallback(this, progID);
                
                        pilha.push("PID: "+progID);
                        
                        setTitle("Grande Moinho Aratu - Moagem v"+myVersion+" - PID "+progID);
                        
                        estoqueForm = new EstoqueForm(this,this,progID);
                        desktoPane.add(estoqueForm);
                    }else{
                        JOptionPane.showMessageDialog(this, "O sistema esta desatualizado, será reiniciado\ne assim que atualizado reiniciará automaticamente!\n"+
                                "Versão atual "+myVersion+" versão disponivel no servidor "+serverVersion);
                        doUpdate(serverVersion);
                    }
                }else
                    JOptionPane.showMessageDialog(this, "Erro ao tentar connectar com servidor.");
            }           
        } catch (RemoteException ex) {
            pilha.push("Erro tentando obter progID: "+ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }   
        rel = new ArrayList<FarinhasEntity>();
        try {
            carregamentoREL = new Carregamento(this,f.moagemURL(),"");
        } catch (Exception ex) {
            Logger.getLogger(MoagemForm.class.getName()).log(Level.SEVERE, null, ex);
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
                ExecApp("d:\\jdk\\bin\\javaw.exe -jar d:\\gmacliente\\GMAUpdate.jar 1");
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
            JOptionPane.showMessageDialog(null, "Erro: "+ex.getMessage());
        }
        System.exit(0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdAutenticacao = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfNomeUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfPassword = new javax.swing.JPasswordField();
        cbSalveSession = new javax.swing.JCheckBox();
        btnAutenticar = new javax.swing.JButton();
        tfGma = new javax.swing.JFormattedTextField();
        jdSobre = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jdMultiAtendimento = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jtfProdutoM = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtfQtdProdutoM = new javax.swing.JTextField();
        btnAtenderSelecao = new javax.swing.JButton();
        btnAtenderFinalizarSelecao = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jdMultiFinalizacao = new javax.swing.JDialog();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbSiloF = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        tfQtdProdutoF = new javax.swing.JTextField();
        tfProdutoF = new javax.swing.JTextField();
        btnDisponibilizar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jdRelatorio = new javax.swing.JDialog();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbInitDate = new JCalendar(true);
        cbEndDate = new JCalendar(true);
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tfInitHora = new javax.swing.JFormattedTextField();
        tfEndHora = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        cbFiltro = new javax.swing.JComboBox();
        btnFiltro = new javax.swing.JButton();
        btnAtender = new javax.swing.JButton();
        btnFInalizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        tfUser = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        taSendMsg = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        epChat = new javax.swing.JEditorPane();
        cbStatusBar = new javax.swing.JComboBox();
        desktoPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jdAutenticacao.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jdAutenticacao.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jdAutenticacaoWindowClosing(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon("E:\\gma_logo.png")); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Nome");

        tfNomeUser.setEnabled(false);

        jLabel2.setText("Gma");

        jLabel4.setText("Senha");

        cbSalveSession.setText("Salvar sessão");
        cbSalveSession.setEnabled(false);
        cbSalveSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSalveSessionActionPerformed(evt);
            }
        });

        btnAutenticar.setText("Autenticar");
        btnAutenticar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutenticarActionPerformed(evt);
            }
        });

        try {
            tfGma.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("Gma#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfGma.setText("GMA");
        tfGma.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfGmaCaretUpdate(evt);
            }
        });
        tfGma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfGmaFocusLost(evt);
            }
        });
        tfGma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfGmaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAutenticar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tfGma, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbSalveSession))
                    .addComponent(tfNomeUser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tfNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfGma, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSalveSession))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAutenticar)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdAutenticacaoLayout = new javax.swing.GroupLayout(jdAutenticacao.getContentPane());
        jdAutenticacao.getContentPane().setLayout(jdAutenticacaoLayout);
        jdAutenticacaoLayout.setHorizontalGroup(
            jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAutenticacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdAutenticacaoLayout.setVerticalGroup(
            jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAutenticacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdSobre.setAlwaysOnTop(true);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/enoiz.gif"))); // NOI18N

        jLabel6.setText("<html><head> </head><body> <p> Criadores:</p><p><font color=\"blue\">Squall</p></font><p><font color=\"blue\">Garuda</p></font> <p>Versão 0.1 - 2015</p><p>Livre para uso nos moinhos.</p></body></html>");

        jLabel7.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Controle de Expedição");

        javax.swing.GroupLayout jdSobreLayout = new javax.swing.GroupLayout(jdSobre.getContentPane());
        jdSobre.getContentPane().setLayout(jdSobreLayout);
        jdSobreLayout.setHorizontalGroup(
            jdSobreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdSobreLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jdSobreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jdSobreLayout.setVerticalGroup(
            jdSobreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdSobreLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdSobreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdSobreLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdMultiAtendimento.setTitle("Multi Atendimentos");
        jdMultiAtendimento.setAlwaysOnTop(true);
        jdMultiAtendimento.setModal(true);
        jdMultiAtendimento.setResizable(false);
        jdMultiAtendimento.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jdMultiAtendimentoWindowClosing(evt);
            }
        });

        jLabel8.setText("Produto:");

        jtfProdutoM.setEnabled(false);

        jLabel10.setText("Quant. Produto:");

        jtfQtdProdutoM.setEnabled(false);
        jtfQtdProdutoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfQtdProdutoMActionPerformed(evt);
            }
        });

        btnAtenderSelecao.setText("Atender selecionado");
        btnAtenderSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderSelecaoActionPerformed(evt);
            }
        });

        btnAtenderFinalizarSelecao.setText("Atender e finalizar");
        btnAtenderFinalizarSelecao.setEnabled(false);

        javax.swing.GroupLayout jdMultiAtendimentoLayout = new javax.swing.GroupLayout(jdMultiAtendimento.getContentPane());
        jdMultiAtendimento.getContentPane().setLayout(jdMultiAtendimentoLayout);
        jdMultiAtendimentoLayout.setHorizontalGroup(
            jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdMultiAtendimentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdMultiAtendimentoLayout.createSequentialGroup()
                        .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdMultiAtendimentoLayout.createSequentialGroup()
                                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(jdMultiAtendimentoLayout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfQtdProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdMultiAtendimentoLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnAtenderSelecao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAtenderFinalizarSelecao)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jdMultiAtendimentoLayout.setVerticalGroup(
            jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMultiAtendimentoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jtfProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jtfQtdProdutoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMultiAtendimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtenderSelecao)
                    .addComponent(btnAtenderFinalizarSelecao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdMultiFinalizacao.setTitle("Multi disponibilização");
        jdMultiFinalizacao.setAlwaysOnTop(true);
        jdMultiFinalizacao.setModal(true);
        jdMultiFinalizacao.setResizable(false);
        jdMultiFinalizacao.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jdMultiFinalizacaoWindowClosing(evt);
            }
        });

        jLabel9.setText("Produto:");

        jLabel11.setText("Quant. Produto:");

        cbSiloF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SILO 099", "SILO 100", "SILO 101", "SILO 102" }));
        cbSiloF.setMinimumSize(new java.awt.Dimension(69, 21));
        cbSiloF.setPreferredSize(new java.awt.Dimension(69, 21));

        jLabel12.setText("Silo:");

        tfQtdProdutoF.setEnabled(false);

        tfProdutoF.setEnabled(false);

        btnDisponibilizar.setText("Disponibilizar");
        btnDisponibilizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponibilizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jdMultiFinalizacaoLayout = new javax.swing.GroupLayout(jdMultiFinalizacao.getContentPane());
        jdMultiFinalizacao.getContentPane().setLayout(jdMultiFinalizacaoLayout);
        jdMultiFinalizacaoLayout.setHorizontalGroup(
            jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMultiFinalizacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdMultiFinalizacaoLayout.createSequentialGroup()
                        .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jdMultiFinalizacaoLayout.createSequentialGroup()
                                .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfQtdProdutoF)
                                    .addComponent(cbSiloF, 0, 95, Short.MAX_VALUE))
                                .addGap(0, 103, Short.MAX_VALUE))
                            .addComponent(tfProdutoF)))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdMultiFinalizacaoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDisponibilizar)))
                .addContainerGap())
        );
        jdMultiFinalizacaoLayout.setVerticalGroup(
            jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdMultiFinalizacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tfProdutoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tfQtdProdutoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdMultiFinalizacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbSiloF, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDisponibilizar)
                .addContainerGap())
        );

        jdRelatorio.setTitle("Posição do Estoque");
        jdRelatorio.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jdRelatorio.setResizable(false);

        jLabel13.setText("Data Inicial:");

        jLabel14.setText("Data Final:");

        jLabel15.setText("Hora Inicial:");

        jLabel16.setText("Hora FInal:");

        try {
            tfInitHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfInitHora.setText("05:00");

        try {
            tfEndHora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfEndHora.setText("05:00");

        jButton1.setText("Gerar relatório");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout jdRelatorioLayout = new javax.swing.GroupLayout(jdRelatorio.getContentPane());
        jdRelatorio.getContentPane().setLayout(jdRelatorioLayout);
        jdRelatorioLayout.setHorizontalGroup(
            jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jdRelatorioLayout.createSequentialGroup()
                        .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jdRelatorioLayout.createSequentialGroup()
                                .addComponent(cbInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfInitHora, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jdRelatorioLayout.createSequentialGroup()
                                .addComponent(cbEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfEndHora))))
                    .addGroup(jdRelatorioLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdRelatorioLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel13, jLabel14, jLabel15, jLabel16});

        jdRelatorioLayout.setVerticalGroup(
            jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdRelatorioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cbInitDate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(tfInitHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel16))
                    .addComponent(tfEndHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jdRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jdRelatorioLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbEndDate, cbInitDate, jLabel13, jLabel14, jLabel15, jLabel16, tfEndHora, tfInitHora});

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(1081, 251));

        tblPedidos.setAutoCreateRowSorter(true);
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
        tblPedidos.setIntercellSpacing(new java.awt.Dimension(4, 4));
        tblPedidos.setRowHeight(23);
        tblPedidos.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(tblPedidos);

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Pendentes", "Processando" }));
        cbFiltro.setEnabled(false);

        btnFiltro.setText("Filtrar");
        btnFiltro.setEnabled(false);
        btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });

        btnAtender.setText("Atender");
        btnAtender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtenderActionPerformed(evt);
            }
        });

        btnFInalizar.setText("Finalizar");
        btnFInalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFInalizarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        tfUser.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuario", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        taSendMsg.setColumns(20);
        taSendMsg.setLineWrap(true);
        taSendMsg.setRows(2);
        taSendMsg.setToolTipText("Tecle Shift + Enter para enviar");
        taSendMsg.setWrapStyleWord(true);
        taSendMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                taSendMsgKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(taSendMsg);

        epChat.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        epChat.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(epChat);

        cbStatusBar.setMaximumRowCount(20);
        cbStatusBar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sistema Iniciado com sucesso!!!" }));
        cbStatusBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbStatusBar.setFocusable(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiltro)))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAtender)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFInalizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(cbStatusBar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAtender, btnCancelar, btnFInalizar});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAtender)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFInalizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(tfUser)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAtender, btnCancelar, btnFInalizar});

        jSplitPane1.setBottomComponent(jPanel3);

        javax.swing.GroupLayout desktoPaneLayout = new javax.swing.GroupLayout(desktoPane);
        desktoPane.setLayout(desktoPaneLayout);
        desktoPaneLayout.setHorizontalGroup(
            desktoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1079, Short.MAX_VALUE)
        );
        desktoPaneLayout.setVerticalGroup(
            desktoPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(desktoPane);

        jMenu1.setText("Menu");

        jMenuItem2.setText("Relatório");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Estoque");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");

        jMenuItem1.setText("Sobre");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAutenticarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutenticarActionPerformed
        // TODO add your handling code here:
        String senha = "";
        String gmaToAuthen = tfGma.getText().toUpperCase();
        for(char c : tfPassword.getPassword()){
            senha += c;
        }
        try{
            if(senha.length() == 0){
                JOptionPane.showMessageDialog(this,"Digite uma senha valida!", "Atenção!",
                    JOptionPane.INFORMATION_MESSAGE);
            }else{
                session = usuarioRMI.autenticarUsuario(gmaToAuthen, senha);
                System.out.println(session.getMsg());
                if(session.getSession_valida()){
                    System.out.println("Sessao ID:" +session.getSessionId()+" Sessão lastacess: "+session.getHora_Ultimo_Acesso());
                    servidor.registerForCallback(this, progID);
                    jdAutenticacao.setVisible(false);  
                    tfPassword.setText("");
                    tfGma.setText("GMA");
                }else{
                    tfPassword.setText("");
                    tfGma.setCaretPosition(3);
                }
            }
        }catch(RemoteException e){ e.printStackTrace(); }
    }//GEN-LAST:event_btnAutenticarActionPerformed

    private void tfGmaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfGmaCaretUpdate
        try {
            // TODO add your handling code here:
            if(tfGma.getCaretPosition() == 8){
                tfNomeUser.setText(usuarioRMI.pesquisarUsuarioByGMA(tfGma.getText().toUpperCase()).getUsername());
                tfPassword.requestFocus();
            }
        } catch(Exception e){
            tfGma.setCaretPosition(3);
            tfNomeUser.setText("---> Digite um GMA valido<---");
        }
    }//GEN-LAST:event_tfGmaCaretUpdate

    private void tfGmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfGmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfGmaActionPerformed

    private void tfGmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfGmaFocusLost
        try {
            // TODO add your handling code here:
            tfNomeUser.setText(usuarioRMI.pesquisarUsuarioByGMA(tfGma.getText().toUpperCase()).getUsername());
        } catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            tfNomeUser.setText("---> Digite um GMA valido<---");
        }
    }//GEN-LAST:event_tfGmaFocusLost

    private void jdAutenticacaoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jdAutenticacaoWindowClosing
        // TODO add your handling code here:
        if(jdMultiAtendimento.isVisible())
            jdMultiAtendimento.setVisible(false);
        session = new Sessao();
        session.setMsg("Cancelado!");
        session.setSession_valida(Boolean.FALSE);
        tfPassword.setText("");
        tfGma.setText("GMA");
        
    }//GEN-LAST:event_jdAutenticacaoWindowClosing
    private Pedido[] pedido;
    private void btnAtenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderActionPerformed
        int index[] = tblPedidos.getSelectedRows();
        if(index.length==0)// nenhuma row selecionada
            return;
        // cria o array de inteiros com o tamanho da seleção na tabela
        Integer codPedido[] = new Integer[index.length];   
        // pega o tipo de produto da primeira linha selecionada.
        String tp = tblPedidos.getValueAt(index[0], 1).toString();
        // percorre a seleção obtendo os codigos de cada pedido e comparando os produtos.
        for(int i=0;i<index.length;i++){
            if(!(tp.equals(tblPedidos.getValueAt(index[i], 1).toString())) || (tblPedidos.getValueAt(index[i], 7).toString().equals("Processando"))){
                System.out.println("Primeira seleção: "+tp+" outra seleção: "+tblPedidos.getValueAt(index[i], 1).toString());
                JOptionPane.showMessageDialog(this, "Para multiplos atendimentos, é necessario que sejam"+
                        "\nselecionados produtos iguais e com status PENDENTE.");
                return;
            }
            // adiciona o cod pedido ao array
            codPedido[i] = Integer.parseInt(tblPedidos.getValueAt(index[i], 0).toString());
        }
        pedido = new Pedido[codPedido.length];
        try {
            // pesquisa no banco pedido por pedido atraves do cod do pedido no array
            pedido = pedidoRMI.pesquisarPedidoByArrayID(codPedido, codPedido.length);
        } catch (RemoteException ex) {
            Logger.getLogger(MoagemForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        // verifica quantos pedidos foram selecionados
        if(Integer.valueOf(pedido.length).compareTo(1) == 0){
            atenderUmPedido(pedido[0]);
        }else{
            jtfProdutoM.setText(tp);
            Float s=0f;
            for(Pedido size : pedido)
                s+=size.getQuant();
            jtfQtdProdutoM.setText(Float.toString(s));
            jdMultiAtendimento.pack();
            jdMultiAtendimento.setVisible(true);
        }
        /*
        int option = JOptionPane.showConfirmDialog(this, "Iniciar fabricação do produto?\n"+
                "Nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant(), 
                "Atendimento -->", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(option != JOptionPane.YES_OPTION)
            return;                    
           */  
    }//GEN-LAST:event_btnAtenderActionPerformed
    private void atenderVariosPedidos(Pedido[] pedido){
        autenticarUsuario();
        getSessao();
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        if(!getSessao().getUsuario().getCargo().isPermissaoAtenderPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for(Pedido pedit : pedido)
            pedit.setUsuario_processamento(getSessao().getUsuario());
         
        try{
            pedidoRMI.atenderMultiplosPedido(pedido, progID);
        }catch(RemoteException ex){ pilha.push("Erro no servidor: "+ex.getMessage()); }
        finally{
            if(jdMultiAtendimento.isVisible())
                jdMultiAtendimento.setVisible(false);
        }
        
    }
    private void atenderUmPedido(Pedido ped){
        autenticarUsuario();
        getSessao();
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        if(!getSessao().getUsuario().getCargo().isPermissaoAtenderPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            ped.setUsuario_processamento(getSessao().getUsuario());            
            pedidoRMI.atenderPedido(ped, progID);
            pilha.push("Solicitação enviada ao servidor!");              
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
           
        }
    }
    private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroActionPerformed
        showFiltro();
    }//GEN-LAST:event_btnFiltroActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int row = tblPedidos.getSelectedRow();
        if(row  == -1)
            return;
        try {
            ped = pedidoRMI.listLastPedidos(progID);
        } catch (RemoteException ex) {ex.printStackTrace();}
        Iterator it = ped.iterator();
        Pedido x = null;
        while(it.hasNext()){
            x = (Pedido)it.next();
            if(0 == Integer.decode(tblPedidos.getValueAt(row, 0).toString()).compareTo(x.getCod_pedido()))
                break;
        }
        if(x == null){
            JOptionPane.showMessageDialog(this, "Esse pedido ja foi removido da lista.");
            // chama update tabela de pedidos.
            return;
        }
        System.out.println("Usuario: "+x.isMoagemCanCancel()+"  cancel op: "+x.isToCancel());
        if(!x.isMoagemCanCancel() || x.isToCancel()){
            JOptionPane.showMessageDialog(this, "Este pedido não pode ser cancelado.",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Confirma cancelamento?\n"+
                "Nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant(), 
                "Atendimento -->", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if(option != JOptionPane.YES_OPTION)
            return; 
        
        autenticarUsuario();
        getSessao();
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        if(!getSessao().getUsuario().getCargo().isPermissaoCancelarPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {            
            pedidoRMI.cancelarPedido(x, progID);
            pilha.push("Solicitação enviada ao servidor!");              
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
           
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbSalveSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSalveSessionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSalveSessionActionPerformed

    private void btnFInalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFInalizarActionPerformed
        int index[] = tblPedidos.getSelectedRows();
        if(index.length==0)// nenhuma row selecionada
            return;
        if(index.length == 1)
            if(tblPedidos.getValueAt(index[0], 7).toString().equals("Pendente")){
                JOptionPane.showMessageDialog(this, "Para finalizar é preciso ter feito o atendimento.");
                return;
            }
        // cria o array de inteiros com o tamanho da seleção na tabela
        Integer codPedido[] = new Integer[index.length];   
        // pega o tipo de produto da primeira linha selecionada.
        String tp = tblPedidos.getValueAt(index[0], 1).toString();
        // percorre a seleção obtendo os codigos de cada pedido e comparando os produtos.
        for(int i=0;i<index.length;i++){
            if(!(tp.equals(tblPedidos.getValueAt(index[i], 1).toString())) || (tblPedidos.getValueAt(index[i], 7).toString().equals("Pendente"))){
                System.out.println("Primeira seleção: "+tp+" outra seleção: "+tblPedidos.getValueAt(index[i], 1).toString());
                JOptionPane.showMessageDialog(this, "Para multiplos atendimentos, é necessario que sejam"+
                        "\nselecionados produtos iguais e com status PROCESSANDO.");
                return;
            }
            // adiciona o cod pedido ao array
            codPedido[i] = Integer.parseInt(tblPedidos.getValueAt(index[i], 0).toString());
        }
        pedido = new Pedido[codPedido.length];
        try {
            // pesquisa no banco pedido por pedido atraves do cod do pedido no array
            pedido = pedidoRMI.pesquisarPedidoByArrayID(codPedido, codPedido.length);
        } catch (RemoteException ex) {
            Logger.getLogger(MoagemForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        // verifica quantos pedidos foram selecionados
        if(Integer.valueOf(pedido.length).compareTo(1) == 0){
            finalizarUmPedido(pedido[0]);
        }else{
            tfProdutoF.setText(tp);
            Float s=0f;
            for(Pedido size : pedido)
                s+=size.getQuant();
            tfQtdProdutoF.setText(Float.toString(s));
            jdMultiFinalizacao.pack();
            jdMultiFinalizacao.setVisible(true);
        }
    }//GEN-LAST:event_btnFInalizarActionPerformed
    private void finalizarVariosPedidos(Pedido[] pedido){
        /* preciso incluir o silo */
        
        
        autenticarUsuario();
        getSessao();               
        
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        if(!getSessao().getUsuario().getCargo().isPermissaoFinalizarPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for(Pedido pedit : pedido){
            pedit.setUsuario_efetivacao(getSessao().getUsuario());
            /* necessario incluir o silo no pedido */
            if(pedit.getSilo().equals("")){
                pedit.setSilo(cbSiloF.getSelectedItem().toString());
            }
        }
        try{
            pedidoRMI.devolverMultiplosPedido(pedido, progID);
        }catch(RemoteException ex){ pilha.push("Erro no servidor: "+ex.getMessage()); }
        finally{
            if(jdMultiAtendimento.isVisible())
                jdMultiAtendimento.setVisible(false);
        }
    }
    private void finalizarUmPedido(Pedido x){
        /* necessario incluir o silo no pedido unico */
        if(x.getSilo().equals("")){
            Object[] opcoes = {"SILO 099","SILO 100","SILO 101","SILO 102"};  
            String res = (String)JOptionPane.showInputDialog(this, "Informar a Expedição que o produto foi disponibilizado?\n"+
                "Nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant() ,
                "Liberar Produto!" ,   JOptionPane.WARNING_MESSAGE , null ,opcoes,"");  
            if(res == null)
                return;
            
            x.setSilo(res);
        }
        
        autenticarUsuario();
        getSessao();               
        
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        if(!getSessao().getUsuario().getCargo().isPermissaoFinalizarPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
                
        x.setUsuario_efetivacao(getSessao().getUsuario()); 
        
        try {            
            pedidoRMI.devolverPedido(x, progID);
            pilha.push("Atendimento confirmado!");              
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
           
        }
    }
    private void taSendMsgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taSendMsgKeyPressed
        if(evt.isShiftDown() && evt.getKeyCode() == KeyEvent.VK_ENTER &&
            !taSendMsg.getText().equals("") && !tfUser.getText().equals("")){
            try { servidor.sendMsgToCliente(taSendMsg.getText(), tfUser.getText());
                taSendMsg.setText("");}
            catch (RemoteException ex) {ex.printStackTrace();}
        }
    }//GEN-LAST:event_taSendMsgKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jdSobre.pack();
        jdSobre.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        disconectar();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        jdRelatorio.pack();
        jdRelatorio.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnAtenderSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtenderSelecaoActionPerformed
        if(jdMultiAtendimento.isVisible())
            jdMultiAtendimento.setVisible(false);
        atenderVariosPedidos(pedido);
    }//GEN-LAST:event_btnAtenderSelecaoActionPerformed

    private void jdMultiAtendimentoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jdMultiAtendimentoWindowClosing
        if(jdMultiAtendimento.isVisible())
            jdMultiAtendimento.setVisible(false);
    }//GEN-LAST:event_jdMultiAtendimentoWindowClosing

    private void jtfQtdProdutoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfQtdProdutoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfQtdProdutoMActionPerformed

    private void btnDisponibilizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponibilizarActionPerformed
        if(jdMultiFinalizacao.isVisible())
            jdMultiFinalizacao.setVisible(false);
        finalizarVariosPedidos(pedido);
    }//GEN-LAST:event_btnDisponibilizarActionPerformed

    private void jdMultiFinalizacaoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jdMultiFinalizacaoWindowClosing
        if(jdMultiFinalizacao.isVisible())
            jdMultiFinalizacao.setVisible(false);
    }//GEN-LAST:event_jdMultiFinalizacaoWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setPercent(0,0);
        Date i = null;
        Date f = null;
        setStateButton(0,false);
        setPercent(0,2);
        i = ((JCalendar)cbInitDate).date();
        f = ((JCalendar)cbEndDate).date();
        i.setHours(Integer.parseInt(tfInitHora.getText().substring(0, 2)));// 00:00
        i.setMinutes(Integer.parseInt(tfInitHora.getText().substring(3, 5)));
        f.setHours(Integer.parseInt(tfEndHora.getText().substring(0, 2)));
        f.setMinutes(Integer.parseInt(tfEndHora.getText().substring(3, 5)));
        setPercent(0,4);
        Collection<Pedido> pedidos;
        Collection<Produto> produtos;
        Iterator it;
        Iterator it2;
        FarinhasEntity farinhas;
        rel.clear();
        setPercent(0,6);
        
        try{
            produtos = produtoRMI.listAllProdutos(progID);
            it = produtos.iterator();
            System.out.println("\nProdutos size: "+produtos.size());
            Produto prod;
            Float t1,t2,t0=0f,t3 = 0f,t4 = 0f;
            while(it.hasNext()){// loop por produto
                setPercent(0,count++);
                prod = (Produto)(it.next());
                System.out.println("Pesquisando por: "+prod.getNome_produto());
                pedidos = pedidoRMI.pesquisarPedido(progID,i,f,"Finalizado",prod.getNome_produto());
                if(prod.getEstoque()==null)
                    t0=0f;
                else
                    t0=prod.getEstoque();      
                //System.out.println("Encontrados: "+pedidos.size());
                if(pedidos!=null){
                    it2 = pedidos.iterator();
                    farinhas = new FarinhasEntity();
                    farinhas.setProduto(prod.getNome_produto());                    
                                                      
                    while(it2.hasNext()){
                        Pedido mped = ((Pedido)it2.next());
                        if(mped.getQuant_Expedido() == null)// espedido
                            t3=t3+0f;
                        else
                            t3=t3+mped.getQuant_Expedido();
                        
                        if(mped.getQuant_estoque() == null)// saida do estoque
                           t4=t4+0f;
                        else
                            t4=t4+mped.getQuant_estoque();
                        
                    }
                    farinhas.setExpedido(t3);
                    farinhas.setSaidaestoque(t4);
                    /* t0 = estoque atual do produto
                       t4 = saida todal do produto na ranger de data selecionada.*/
                    farinhas.setEstoqueatual(t0);
                    farinhas.setProdutoclass(prod.getClasse_produto());
                    rel.add(farinhas);
                    t0=0f;
                    t1=0f;
                    t2=0f;
                    t3=0f;
                    t4=0f;
                }else{
                    // pedido vazio nullo
                    farinhas = new FarinhasEntity();
                    farinhas.setProduto(prod.getNome_produto()); 
                    farinhas.setExpedido(0f);
                    farinhas.setSaidaestoque(0f);
                    farinhas.setProdutoclass(prod.getClasse_produto());
                    /* t0 = estoque atual do produto
                       t4 = saida todal do produto na ranger de data selecionada.*/
                    farinhas.setEstoqueatual(t0);
                    rel.add(farinhas);
                }                    
            }
            Collections.sort(rel);
            setPercent(0,count++);
            new Thread(this).start();
        }catch(RemoteException re){}
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        estoqueForm.pack();
        estoqueForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    private void actionFiltroAll(){
        pedidoTableModel = new PedidoTableModel();
        pedidoTableModel.adicionarArryPedidos(arrayPedidosPendente);
        pedidoTableModel.adicionarArryPedidos(arrayPedidosAtendimento);
        //myModel = new MyTableModel(arrayPedidosTable, colunas, edicao);
        tblPedidos.setModel(pedidoTableModel);
    }
    private void actionFiltroPendente(){
        pedidoTableModel = new PedidoTableModel(arrayPedidosPendente);
        //myModel = new MyTableModel(arrayPedidosTable, colunas, edicao);
        tblPedidos.setModel(pedidoTableModel);
    }
    private void actionFiltroAtendimento(){
        pedidoTableModel = new PedidoTableModel(arrayPedidosAtendimento);
        //myModel = new MyTableModel(arrayPedidosTable, colunas, edicao);
        tblPedidos.setModel(pedidoTableModel);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtender;
    private javax.swing.JButton btnAtenderFinalizarSelecao;
    private javax.swing.JButton btnAtenderSelecao;
    private javax.swing.JButton btnAutenticar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDisponibilizar;
    private javax.swing.JButton btnFInalizar;
    private javax.swing.JButton btnFiltro;
    private javax.swing.JComboBox cbEndDate;
    private javax.swing.JComboBox cbFiltro;
    private javax.swing.JComboBox cbInitDate;
    private javax.swing.JCheckBox cbSalveSession;
    private javax.swing.JComboBox cbSiloF;
    private javax.swing.JComboBox cbStatusBar;
    private javax.swing.JDesktopPane desktoPane;
    private javax.swing.JEditorPane epChat;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JDialog jdAutenticacao;
    private javax.swing.JDialog jdMultiAtendimento;
    private javax.swing.JDialog jdMultiFinalizacao;
    private javax.swing.JDialog jdRelatorio;
    private javax.swing.JDialog jdSobre;
    private javax.swing.JTextField jtfProdutoM;
    private javax.swing.JTextField jtfQtdProdutoM;
    private javax.swing.JTextArea taSendMsg;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JFormattedTextField tfEndHora;
    private javax.swing.JFormattedTextField tfGma;
    private javax.swing.JFormattedTextField tfInitHora;
    private javax.swing.JTextField tfNomeUser;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfProdutoF;
    private javax.swing.JTextField tfQtdProdutoF;
    private javax.swing.JTextField tfUser;
    // End of variables declaration//GEN-END:variables

    private void showFiltro(){
        switch(cbFiltro.getSelectedIndex()){
            case 0: // Todos
                actionFiltroAll();
                break;
            case 1: // Pendentes
                actionFiltroPendente();
                break;
            case 2: // Em atendimento
                actionFiltroAtendimento();
                break;
        }
    }
    
    @Override
    public void novoPedido(Pedido p) throws RemoteException {
        //arrayPedidosPendente.add(p);
        initArrayList(); 
        showFiltro();
    }
    @Override
    public void updatePedidoStatus(Pedido p) throws RemoteException {
        initArrayList();
    }
    @Override
    public void receiverMsgFromServer(Integer cod, String msg) throws RemoteException {
        if(pilha.size() == 50){
            pilha.removeElement(pilha.lastElement());
        }
        cbStatusBar.removeAllItems();
        pilha.push(msg);
        Iterator it = pilha.iterator();
        while(it.hasNext()){ cbStatusBar.addItem(it.next()); }
        cbStatusBar.setSelectedIndex(cbStatusBar.getItemCount()-1);
    }
    private StringBuilder chat = new StringBuilder();
    @Override
    public void receiverMsgFromCliente(String msg, String u) throws RemoteException {
        chat.append(u);
        chat.append(msg);
        epChat.setText(chat.toString());
    }
    @Override
    public void autenticarUsuario() {
        try {
            servidor.unregisterForCallback(this, progID);
            jdAutenticacao.pack();
            jdAutenticacao.setVisible(true);
        } catch (RemoteException ex) { ex.printStackTrace(); }
    }
    @Override
    public Sessao getSessao() {
        return session;
    }
    @Override
    public RemoteUsuario obterUsuarioRemote() {return usuarioRMI;}
    @Override
    public RemoteCliente obterClienteRemote() { return clienteRMI;}
    @Override
    public RemoteProduto obterProdutoRemote() { return produtoRMI; }
    @Override
    public RemotePedido obterPedidoRemote() { return pedidoRMI; }
    @Override
    public RemoteCargo obterCargoRemote() { return cargoRMI; }  
    
    private boolean connect(){
        try{
            versionControl = (RemoteUpdate)             Naming.lookup(f.getVersionURL());
            pedidoRMI  =    (RemotePedido)              Naming.lookup(f.pedidoURL()); 
            produtoRMI =    (RemoteProduto)             Naming.lookup(f.produtoURL());
            usuarioRMI =    (RemoteUsuario)             Naming.lookup(f.usuarioURL());
            clienteRMI =    (RemoteCliente)             Naming.lookup(f.clienteURL()); 
            servidor   =    (CallBackServerInterface)   Naming.lookup(f.servidorURL());
            cargoRMI   =    (RemoteCargo)               Naming.lookup(f.cargoURL());
            UnicastRemoteObject.exportObject(this, 0);
                        
            // preenche o array da tabela e mostra todos os registros *+*
            initArrayList();
            
            pilha.push("Sistema: Conectado ao servidor Servidor!!!");
            isConnected = true;
        }catch (NotBoundException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            isConnected = false;
            return isConnected;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            isConnected = false;
            return isConnected;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            isConnected = false;
            return isConnected;
        } catch (Exception ex) {
            Logger.getLogger(MoagemForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isConnected;
    }  
    private boolean disconectar(){
        
        try{
            servidor.unregisterForCallback(this, progID);/*
            Naming.unbind(f.pedidoURL()); 
            Naming.unbind(f.produtoURL());
            Naming.unbind(f.usuarioURL());
            Naming.unbind(f.clienteURL()); 
            Naming.unbind(f.servidorURL());
            Naming.unbind(f.cargoURL());
            UnicastRemoteObject.unexportObject(this, true);*/
            isConnected = false;/*
        }catch (NotBoundException ex) {
            ex.printStackTrace();
            isConnected = true;
            return isConnected;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            isConnected = true;
            return isConnected;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            isConnected = true;
            return isConnected;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);*/
        }catch(Exception e){
            isConnected = false;
        }
        return isConnected;
    }    
    private void initArrayList(){
        try {
            /* Chama servidor lastPedidos */
            ped = pedidoRMI.listLastPedidos(progID);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        Pedido x;
        Iterator it = ped.iterator(); 
        arrayPedidosPendente.clear();
        arrayPedidosAtendimento.clear();
        while(it.hasNext()){
            x = (Pedido)it.next();
            // se o pedido estiver cancelado, return
            if(!x.isPedido_cancelado()){
                if(x.getStatus_pedido().equalsIgnoreCase("Pendente")) arrayPedidosPendente.add(x);
                if(x.getStatus_pedido().equalsIgnoreCase("Processando")) arrayPedidosAtendimento.add(x);  
            }
            
        }
        // no primeiro momento mostra todos os pedidos na tabela
        showFiltro();   
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
        cm.getColumn(6).setPreferredWidth(75);
        cm.getColumn(7).setResizable(true);
        cm.getColumn(7).setPreferredWidth(55);
        cm.getColumn(8).setResizable(true);
        cm.getColumn(8).setPreferredWidth(40);
    }
    public void atenderPedido(){
        int row = tblPedidos.getSelectedRow();
        if(row == -1)
            return;
        
        autenticarUsuario();
        getSessao();
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        Iterator it = ped.iterator();
        Pedido x = null;
        while(it.hasNext()){
            x = (Pedido)it.next();
            if(0 == Integer.decode(tblPedidos.getValueAt(row, 0).toString()).compareTo(x.getCod_pedido()))
                break;
        }
        /* informar qual ususrio esta atendendo */
        
        x.setPedido_processando(new Date());
        x.setUsuario_processamento(getSessao().getUsuario());
        x.setStatus_pedido("Em Atendimento");
        
        int option = JOptionPane.showConfirmDialog(this, "Confirma colocar o pedido selecionado em atendimento?",
                "Confirmação",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if(option == 1 || option == -1)
            return;
        
        try {
                pedidoRMI.atenderPedido(x,getSessao().getSessionId());
        } catch (RemoteException ex) { ex.printStackTrace(); }
        finally{            
            
        }
        
    }
    
    private Collection<Pedido> ped = new HashSet<Pedido>();
    private PedidoTableModel pedidoTableModel;
    Sessao session = new Sessao();
    private boolean isConnected = false; 
    private RemotePedido pedidoRMI;
    private RemoteUsuario usuarioRMI;
    private RemoteProduto produtoRMI;
    private RemoteCliente clienteRMI;
    private RemoteCargo cargoRMI;
    private CallBackServerInterface servidor;
    private Stack<String> pilha = new Stack<String>();
    private Long progID;
    private ArrayList<Pedido> arrayPedidosAtendimento = new ArrayList();
    private ArrayList<Pedido> arrayPedidosPendente = new ArrayList();

    @Override
    public void run() {
        try {
            setPercent(0,count++);
            System.out.println(count);
            carregamentoREL.imprimirMoagem(rel, count);
        } catch (Exception ex) {
            setStateButton(0,true);
            setPercent(0,0);
            Logger.getLogger(MoagemForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setPercent(int p, int f) {
        jProgressBar1.setValue(f);
    }

    @Override
    public void setStateButton(int b, boolean s) {
        jButton1.setEnabled(s);
    }

    @Override
    public void setPercent(int p, int f, String process) {
        jProgressBar1.setValue(f);
    }
    
    
}
