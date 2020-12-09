/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.expedicao.presentation;

import br.com.gma.cliente.business.facade.remote.ClientInterface;
import br.com.gma.cliente.common.AutenticadorInterface;
import br.com.gma.cliente.common.FileProperties;
import br.com.gma.cliente.common.FileUpdateProperties;
import br.com.gma.cliente.common.MainFormInterface;
import br.com.gma.cliente.common.PedidoTableModel;
import br.com.gma.server.business.facade.remote.*;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_BUSY;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_EXCE;
import static br.com.gma.server.common.entity.CONSTANTES.CARREGAMENTO_OK;
import br.com.gma.server.common.entity.Pedido;
import br.com.gma.server.common.entity.Produto;
import br.com.gma.server.common.entity.Sessao;
import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Administrador
 */
public class ExpedicaoForm extends javax.swing.JFrame implements ClientInterface, 
        AutenticadorInterface, MainFormInterface, Serializable{

    /**
     * Creates new form ExpedicaoForm
     */
    private static final String myVersion = "0.3";
    private UIManager.LookAndFeelInfo looks[] = UIManager.getInstalledLookAndFeels();
    private String str[] = new String[looks.length];
    private CadastroForm cForm;
    private RelatorioForm rForm;
    private PedidoForm pForm;
    private ControleExpForm ceForm;
    private EstoqueForm eForm;
    private Long progID;
    private FileProperties f;
    private FileUpdateProperties fileUpdate;
        
    public ExpedicaoForm() {    
        try {
            f = new FileProperties();
            
        } catch (Exception ex) {ex.printStackTrace();}
        
        try {
            System.out.println(f.ServidorIP());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        /*for(int i = 0; i < looks.length; i++)
            str[i] = looks[i].getName();
        try{
                UIManager.setLookAndFeel(looks[1].getClassName());
                SwingUtilities.updateComponentTreeUI(this);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Não foi possível mudar a aparência!", "Erro!", JOptionPane.ERROR_MESSAGE);
            }*/
        //verde
        UIManager.put( "nimbusBase", new Color( 59, 140, 47 ) );  
        UIManager.put( "nimbusBlueGrey", new Color( 170, 190, 184 ) );  
        UIManager.put( "control", new Color( 214, 223, 220 ) ); /* 
        // laranja
        UIManager.put( "nimbusBase", new Color( 140, 103, 59 ) );
        UIManager.put( "nimbusBlueGrey", new Color( 190, 189, 170 ) );
        UIManager.put( "control", new Color( 221, 223, 212 ) );
        //vermelho
        /*
        UIManager.put( "nimbusBase", new Color( 140, 42, 42 ) );
        UIManager.put( "nimbusBlueGrey", new Color( 190, 167, 167 ) );
        UIManager.put( "control", new Color( 223, 215, 214 ) );
        //cinza
        /*
        UIManager.put( "nimbusBase", new Color( 81, 81, 81 ) );
        UIManager.put( "nimbusBlueGrey", new Color( 190, 190, 190 ) );
        UIManager.put( "control", new Color( 223, 223, 223 ) );*/
        try{
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
                }
                System.out.println(info.getName());
            }
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
        }catch(/*UnsupportedLookAndFeelException*/Exception u){
            u.printStackTrace();
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
                        initForms();
                        setTitle("Grande Moinho Aratu - Expedição v"+myVersion+" - PID "+progID);
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
    private void initForms() throws RemoteException, Exception{
        progID = servidor.obterID();
        System.out.println(progID);
        if(progID != 0)
            servidor.registerForCallback(this, progID);
                
        pilha.push("PID: "+progID);
        //setTitle("Grande Moinho Aratu - Expedição PID "+progID);
        cForm = new CadastroForm(this,this, progID);
        desktopPane.add(cForm);
        try {
            pForm = new PedidoForm(this,this, progID);
            desktopPane.add(pForm);
        } catch (RemoteException ex) { ex.printStackTrace(); }
        ceForm = new ControleExpForm();
        desktopPane.add((ceForm));
        rForm = new RelatorioForm(this,progID, f.carregamentoURL(), f.producaoURL());
        desktopPane.add(rForm);
        eForm = new EstoqueForm(this,this,progID);
        desktopPane.add(eForm);
        
        
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
        jPanel1 = new javax.swing.JPanel();
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
        jdEstoqueBigBag = new javax.swing.JDialog();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jdExpedicao = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblProduto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblQtd = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jftAvarias = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jtfExpedido = new javax.swing.JFormattedTextField();
        btnFinalizarPedido = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        tfEstoque = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        cbStatusBar = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        desktopPane = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        epChat = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        btnCancelarPedido = new javax.swing.JButton();
        brnNovoPedido = new javax.swing.JButton();
        btnExpedirPedido = new javax.swing.JButton();
        cbFiltro = new javax.swing.JComboBox();
        btnFiltro = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblPedidos = new javax.swing.JTable();
        tfUser = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        taSendMsg = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        miEstoque = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        jdAutenticacao.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jdAutenticacao.setResizable(false);
        jdAutenticacao.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                jdAutenticacaoWindowClosing(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/gma_logo.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("Nome");

        tfNomeUser.setEnabled(false);

        jLabel2.setText("Gma");

        jLabel4.setText("Senha");

        cbSalveSession.setText("Salvar sessão");
        cbSalveSession.setEnabled(false);
        cbSalveSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbSalveSessionItemStateChanged(evt);
            }
        });

        btnAutenticar.setText("Autenticar");
        btnAutenticar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutenticarActionPerformed(evt);
            }
        });

        try {
            tfGma.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("GMA#####")));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfNomeUser))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfGma, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(tfPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbSalveSession, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAutenticar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNomeUser, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfGma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSalveSession)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAutenticar)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jdAutenticacaoLayout = new javax.swing.GroupLayout(jdAutenticacao.getContentPane());
        jdAutenticacao.getContentPane().setLayout(jdAutenticacaoLayout);
        jdAutenticacaoLayout.setHorizontalGroup(
            jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAutenticacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jdAutenticacaoLayout.setVerticalGroup(
            jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdAutenticacaoLayout.createSequentialGroup()
                .addGroup(jdAutenticacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jdAutenticacaoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdAutenticacaoLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jdSobre.setTitle("Sobre o programa");
        jdSobre.setAlwaysOnTop(true);
        jdSobre.setResizable(false);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/enoiz.gif"))); // NOI18N

        jLabel6.setText("<html><head> </head><body> <p> Criadores:</p><p><font color=\"blue\">Squall</p></font><p><font color=\"blue\">Garuda</p></font> <p>Versão 0.3 - 2015</p><p>Livre para uso nos moinhos.</p></body></html>");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jdSobreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdSobreLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setText("Estoque atual de Bags");

        jLabel13.setText("Estoque atual de Bags pequenos");

        jLabel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jdEstoqueBigBagLayout = new javax.swing.GroupLayout(jdEstoqueBigBag.getContentPane());
        jdEstoqueBigBag.getContentPane().setLayout(jdEstoqueBigBagLayout);
        jdEstoqueBigBagLayout.setHorizontalGroup(
            jdEstoqueBigBagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdEstoqueBigBagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdEstoqueBigBagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdEstoqueBigBagLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdEstoqueBigBagLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jdEstoqueBigBagLayout.setVerticalGroup(
            jdEstoqueBigBagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdEstoqueBigBagLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdEstoqueBigBagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdEstoqueBigBagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(238, Short.MAX_VALUE))
        );

        jdEstoqueBigBagLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel14, jLabel15});

        jdExpedicao.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jdExpedicao.setTitle("Finalizar pedido");
        jdExpedicao.setAlwaysOnTop(true);
        jdExpedicao.setBackground(java.awt.Color.cyan);
        jdExpedicao.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("Produto:");

        lblProduto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Quant.");

        lblQtd.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Avarias:");

        jftAvarias.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jftAvarias.setText("0");

        jLabel11.setText("Expedido:");

        jtfExpedido.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtfExpedido.setText("0");

        btnFinalizarPedido.setText("Finalizar");
        btnFinalizarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarPedidoActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel16.setText("Saída:");

        tfEstoque.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        tfEstoque.setText("0");
        tfEstoque.setToolTipText("O valor desse campo será descontado do estoque.");

        jLabel17.setText("Entrada:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTextField1, tfEstoque});

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("ESTOQUE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnFinalizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jftAvarias, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfExpedido))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(lblProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancelar, btnFinalizarPedido});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jftAvarias, jtfExpedido});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblQtd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftAvarias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jtfExpedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFinalizarPedido)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, jLabel11, jftAvarias, jtfExpedido});

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/1419991415_Warning.png"))); // NOI18N

        javax.swing.GroupLayout jdExpedicaoLayout = new javax.swing.GroupLayout(jdExpedicao.getContentPane());
        jdExpedicao.getContentPane().setLayout(jdExpedicaoLayout);
        jdExpedicaoLayout.setHorizontalGroup(
            jdExpedicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdExpedicaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdExpedicaoLayout.setVerticalGroup(
            jdExpedicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdExpedicaoLayout.createSequentialGroup()
                .addGroup(jdExpedicaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdExpedicaoLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jdExpedicaoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        cbStatusBar.setMaximumRowCount(20);
        cbStatusBar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sistema Iniciado com sucesso!!!" }));
        cbStatusBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbStatusBar.setFocusable(false);

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setLeftComponent(desktopPane);

        epChat.setEditable(false);
        epChat.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        epChat.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        epChat.setFocusable(false);
        jScrollPane2.setViewportView(epChat);

        jButton1.setText("Finalizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnCancelarPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/Cancel-2-icon.png"))); // NOI18N
        btnCancelarPedido.setText("Cancelar");
        btnCancelarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPedidoActionPerformed(evt);
            }
        });

        brnNovoPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/add-2-icon.png"))); // NOI18N
        brnNovoPedido.setText("Novo     ");
        brnNovoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnNovoPedidoActionPerformed(evt);
            }
        });

        btnExpedirPedido.setText("Expedir");
        btnExpedirPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpedirPedidoActionPerformed(evt);
            }
        });

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Pendentes", "Processando", "Disponivel", "Expedindo" }));
        cbFiltro.setMaximumSize(new java.awt.Dimension(124, 26));
        cbFiltro.setMinimumSize(new java.awt.Dimension(124, 26));
        cbFiltro.setPreferredSize(new java.awt.Dimension(124, 26));

        btnFiltro.setText("Filtrar");
        btnFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroActionPerformed(evt);
            }
        });

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
        tblPedidos.setSelectionForeground(new java.awt.Color(0, 255, 255));
        tblPedidos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblPedidos);

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
        jScrollPane1.setViewportView(taSendMsg);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCancelarPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(brnNovoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExpedirPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFiltro)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(brnNovoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancelarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnExpedirPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnFiltro, cbFiltro});

        jSplitPane1.setRightComponent(jPanel2);

        jMenu1.setText("Menu");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Pedido");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        miEstoque.setText("Estoque");
        miEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEstoqueActionPerformed(evt);
            }
        });
        jMenu1.add(miEstoque);

        jMenuItem9.setText("Estoque Big Bag");
        jMenuItem9.setEnabled(false);
        jMenu1.add(jMenuItem9);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Relatórios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Cadastro");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Expedição");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator1);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/gma/icon/d.PNG"))); // NOI18N
        jMenuItem5.setText("Sair");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Configuração");

        jMenuItem6.setText("Conectar");
        jMenuItem6.setEnabled(false);
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setText("Opções");
        jMenuItem7.setEnabled(false);
        jMenu3.add(jMenuItem7);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Sobre");

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("Sobre");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbStatusBar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(isConnected)
            cForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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

    private void tfGmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfGmaFocusLost
        try {
            // TODO add your handling code here:
            tfNomeUser.setText(usuarioRMI.pesquisarUsuarioByGMA(tfGma.getText().toUpperCase()).getUsername());
        } catch(Exception e){
            System.out.println(e.getLocalizedMessage());
            tfNomeUser.setText("---> Digite um GMA valido<---");
        }
    }//GEN-LAST:event_tfGmaFocusLost

    private void tfGmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfGmaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfGmaActionPerformed

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

    private void jdAutenticacaoWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_jdAutenticacaoWindowClosing
        // TODO add your handling code here:
        session = new Sessao();
        session.setMsg("Cancelado!");
        session.setSession_valida(Boolean.FALSE);
        tfPassword.setText("");
        tfGma.setText("GMA");
    }//GEN-LAST:event_jdAutenticacaoWindowClosing

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        disconectar();
        System.exit(0);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
    
        pForm.setVisible(true);
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void brnNovoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnNovoPedidoActionPerformed
        if(isConnected)
            pForm.setVisible(true);
    }//GEN-LAST:event_brnNovoPedidoActionPerformed

    private void btnCancelarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPedidoActionPerformed
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
        int option = JOptionPane.showConfirmDialog(this, "Confirma o cancelamento do pedido abaixo?\n"+
                "Pedido nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant(), 
                "Cancelar -->", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if(option != JOptionPane.YES_OPTION)
            return; 
        
        autenticarUsuario();
        getSessao();
        
        if(!getSessao().getUsuario().getCargo().isPermissaoCancelarPedido()){
            //não tem permissão
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // se for pendente e um dos cargos abaixo e tiver permissão
        if(!(x.getStatus_pedido().equalsIgnoreCase("Pendente") && getSessao().getUsuario().getCargo().isPermissaoCancelarPedido())){
            
            if(!(x.getStatus_pedido().equalsIgnoreCase("Disponivel") && getSessao().getUsuario().getCargo().isPermissaoEspecialPedido())){
           
                JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                    "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        try {            
            pedidoRMI.solicitarCancelamentoPedido(x, progID);
            pilha.push("Solicitação enviada ao servidor!");              
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
           
           tfGma.setCaretPosition(3);
        }
    }//GEN-LAST:event_btnCancelarPedidoActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ceForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroActionPerformed
        showFiltro();
    }//GEN-LAST:event_btnFiltroActionPerformed

    private void taSendMsgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taSendMsgKeyPressed
        if(evt.isShiftDown() && evt.getKeyCode() == KeyEvent.VK_ENTER && 
                !taSendMsg.getText().equals("") && !tfUser.getText().equals("")){
            try { servidor.sendMsgToCliente(taSendMsg.getText(), tfUser.getText()); 
            taSendMsg.setText("");} 
            catch (RemoteException ex) {ex.printStackTrace();}
        }
    }//GEN-LAST:event_taSendMsgKeyPressed

    private void btnExpedirPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedirPedidoActionPerformed
        int row = tblPedidos.getSelectedRow();
        if(row  == -1)
            return;
        int cod = Integer.decode(tblPedidos.getValueAt(row, 0).toString());
        Pedido x = new Pedido();
        try {
            x = pedidoRMI.pesquisarPedidoById(cod);
        } catch (RemoteException ex) {
            pilha.push("Contate o Admin. Erro mensagem: "+ex.getMessage());
            //Logger.getLogger(ExpedicaoForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!x.getStatus_pedido().equals("Disponivel")){
            JOptionPane.showMessageDialog(this, "Este pedido ainda não foi disponibilizado.",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(this, "Expedir o pedido?\n"+
                "Pedido nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant(), 
                "Expedir -->", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        
        if(option != JOptionPane.YES_OPTION)
            return; 
        
        autenticarUsuario();
        getSessao();
       if(!getSessao().getSession_valida()){
                JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
                return;            
            }
        if(!getSessao().getUsuario().getCargo().isPermissaoExpedirPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        
        x.setUsuario_expedicao(getSessao().getUsuario());
        try {            
            switch(pedidoRMI.expedirPedido(x, progID)){
                case CARREGAMENTO_BUSY:
                    JOptionPane.showMessageDialog(this, "Finalize o pedido com status EXPEDINDO antes de iniciar outro.");
                    break;
                case CARREGAMENTO_EXCE:
                    JOptionPane.showMessageDialog(this, "Contate o Admin. Erro!");
                    break;
                case CARREGAMENTO_OK:
                    pilha.push("Update efetuado!");      
                    break;
            }            
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
           session.setSession_valida(false);
        }
    }//GEN-LAST:event_btnExpedirPedidoActionPerformed

    protected Boolean bOk = false;
    protected Integer a;
    protected Float q;
    protected Float qtdEstoque;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = tblPedidos.getSelectedRow();
        if(row  == -1)
            return;
        int cod = Integer.parseInt(tblPedidos.getValueAt(row, 0).toString());
        Pedido x = null;
        try {
            x = pedidoRMI.pesquisarPedidoById(cod);
        } catch (RemoteException ex) {ex.printStackTrace();}
        
        if(x == null){
            JOptionPane.showMessageDialog(this, "Esse pedido foi excluido do servidor.");
            // chama update tabela de pedidos.
            return;
        }
        if(!x.getStatus_pedido().equals("Expedindo")){
            JOptionPane.showMessageDialog(this, "Este pedido ainda não foi expedido.",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        jdExpedicao.pack();
        jtfExpedido.setText(Float.toString(x.getQuant()));
        lblQtd.setText(x.getQuant().toString());
        lblProduto.setText(x.getProduto().getNome_produto());
        jdExpedicao.setVisible(true);
        if(!bOk)
            return;
        
        /*
        String opt = JOptionPane.showInputDialog(this,"Finalizar o pedido?\n"+
                "Pedido nº: \t"+x.getCod_pedido()+
                "\nProduto: \t"+x.getProduto().getNome_produto()+
                "\nQuatidade: \t"+x.getQuant()+
                "\nHouve avarias?", 
                "Finalizar Pedido!" ,   JOptionPane.WARNING_MESSAGE );
        System.out.println(opt);
        if(opt == null)
            return;
        if(opt.equals("")){
            JOptionPane.showMessageDialog(this, "Informe o número de avarias",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }*/
        x.setAvaria(a);
        x.setQuant_Expedido(q);
        x.setQuant_estoque(qtdEstoque);
        
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
        //checa se login é valido
        if(!getSessao().getSession_valida()){
            JOptionPane.showMessageDialog(this, getSessao().getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
        
        x.setUsuario_finalizacao(getSessao().getUsuario());
        
        try {            
            pedidoRMI.finalizarPedido(x, progID);
            
            pilha.push("Update efetuado!");              
        } catch (RemoteException ex) {
            pilha.push("Erro no servidor: "+ex.getMessage());
        }finally{
            Produto produtoUpdate = x.getProduto();
            // tratamento necessario porque no DB existem valores nulos.
            Float t1,t2;
            if(produtoUpdate.getEstoque() == null)
                t1=0f;
            else
                t1=produtoUpdate.getEstoque();
            
            if(x.getQuant_estoque() == null)
                t2=0f;
            else
                t2=x.getQuant_estoque();
                        
            produtoUpdate.setEstoque(t1-t2);
            try{
                produtoRMI.updateProduto(produtoUpdate, progID);
            } catch (RemoteException ex) {
                pilha.push("Erro ao tentar fazer update no estoque: "+ex.getMessage());
            }
            session.setSession_valida(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(isConnected)
            rForm.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        disconectar();
    }//GEN-LAST:event_formWindowClosing

    private void cbSalveSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbSalveSessionItemStateChanged
        
    }//GEN-LAST:event_cbSalveSessionItemStateChanged

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        jdSobre.pack();
        jdSobre.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void miEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEstoqueActionPerformed
        eForm.setVisible(true);
    }//GEN-LAST:event_miEstoqueActionPerformed

    private void btnFinalizarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarPedidoActionPerformed
        bOk = true;
        if(jftAvarias.getText().equals(""))
            bOk = false;
        else
            a = Integer.parseInt(jftAvarias.getText());
        
        if(jtfExpedido.getText().equals(""))
            bOk = false;
        else
            q = Float.parseFloat(jtfExpedido.getText());
        
        if(tfEstoque.getText().equals(""))
            bOk = false;
        else
            qtdEstoque = Float.parseFloat(tfEstoque.getText());
        
        jdExpedicao.setVisible(false);
        jtfExpedido.setText("0");
        jftAvarias.setText("0");
        tfEstoque.setText("0");
    }//GEN-LAST:event_btnFinalizarPedidoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        bOk = false;
        jdExpedicao.setVisible(false);
    }//GEN-LAST:event_btnCancelarActionPerformed
    private boolean disconectar(){
        
        try{
            servidor.unregisterForCallback(this, progID);/*
            Naming.unbind(f.pedidoURL()); 
            Naming.unbind(f.produtoURL());
            Naming.unbind(f.usuarioURL());
            Naming.unbind(f.clienteURL()); 
            Naming.unbind(f.servidorURL());
            Naming.unbind(f.cargoURL());*/
            UnicastRemoteObject.unexportObject(this, true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brnNovoPedido;
    private javax.swing.JButton btnAutenticar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarPedido;
    private javax.swing.JButton btnExpedirPedido;
    private javax.swing.JButton btnFiltro;
    private javax.swing.JButton btnFinalizarPedido;
    private javax.swing.JComboBox cbFiltro;
    private javax.swing.JCheckBox cbSalveSession;
    private javax.swing.JComboBox cbStatusBar;
    private javax.swing.JDesktopPane desktopPane;
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
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JDialog jdAutenticacao;
    private javax.swing.JDialog jdEstoqueBigBag;
    private javax.swing.JDialog jdExpedicao;
    private javax.swing.JDialog jdSobre;
    private javax.swing.JFormattedTextField jftAvarias;
    private javax.swing.JFormattedTextField jtfExpedido;
    private javax.swing.JLabel lblProduto;
    private javax.swing.JLabel lblQtd;
    private javax.swing.JMenuItem miEstoque;
    private javax.swing.JTextArea taSendMsg;
    private javax.swing.JTable tblPedidos;
    private javax.swing.JFormattedTextField tfEstoque;
    private javax.swing.JFormattedTextField tfGma;
    private javax.swing.JTextField tfNomeUser;
    private javax.swing.JPasswordField tfPassword;
    private javax.swing.JTextField tfUser;
    // End of variables declaration//GEN-END:variables
    
    Sessao session = new Sessao();
    private boolean isConnected = false; 
    private RemotePedido pedidoRMI;
    private RemoteUsuario usuarioRMI;
    private RemoteProduto produtoRMI;
    private RemoteCliente clienteRMI;
    private RemoteCargo cargoRMI;
    private CallBackServerInterface servidor;
    private RemoteUpdate versionControl;
    
    private boolean connect(){
        try{
            versionControl = (RemoteUpdate)             Naming.lookup(f.getVersionURL());
            pedidoRMI  =    (RemotePedido)              Naming.lookup(f.pedidoURL()); 
            produtoRMI =    (RemoteProduto)             Naming.lookup(f.produtoURL());
            usuarioRMI =    (RemoteUsuario)             Naming.lookup(f.usuarioURL());
            clienteRMI =    (RemoteCliente)             Naming.lookup(f.clienteURL()); 
            servidor   =    (CallBackServerInterface)   Naming.lookup(f.servidorURL());
            cargoRMI   =    (RemoteCargo)               Naming.lookup(f.cargoURL());
            
            //Registry r = LocateRegistry.getRegistry(1099);
            
            UnicastRemoteObject.exportObject(this, 0);
            
            //Naming.rebind("rmi://127.0.0.1:" + 1099 + "/" + "SERVIDOR",rr);
            
            //UnicastRemoteObject.unexportObject(rr, true);
            initArrayList();
            
            pilha.push("Sistema: Conectado ao servidor Servidor!!!");
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
            case 3: // Em disponivel
                actionFiltroDisponivel();
                break;
            case 4: // expedindo
                actionFiltroExpedindo();
                break;
        }
    }
    private void actionFiltroAll(){
        pedidoTableModel = new PedidoTableModel();
        pedidoTableModel.adicionarArryPedidos(arrayPedidosPendente);
        pedidoTableModel.adicionarArryPedidos(arrayPedidosAtendimento);
        pedidoTableModel.adicionarArryPedidos(arrayPedidosDisponivel);
        pedidoTableModel.adicionarArryPedidos(arrayPedidosExpedindo);
        //myModel = new MyTableModel(arrayPedidosTable, colunas, edicao);
        tblPedidos.setModel(pedidoTableModel);
    }
    private void actionFiltroDisponivel(){
        pedidoTableModel = new PedidoTableModel(arrayPedidosDisponivel);
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
    private void actionFiltroExpedindo(){
        pedidoTableModel = new PedidoTableModel(arrayPedidosExpedindo);
        //myModel = new MyTableModel(arrayPedidosTable, colunas, edicao);
        tblPedidos.setModel(pedidoTableModel);
    }
    private void initArrayList(){
        try {
            /* Chama servidor lastPedidos */
            ped = pedidoRMI.listLastPedidos(progID);
        } catch (RemoteException ex) {
            pilha.push("Erro ao tentar acessar a lista de pedido: "+ex.getMessage());
            return;
        }        
        Pedido x;
        Iterator it = ped.iterator(); 
        arrayPedidosPendente.clear();
        arrayPedidosAtendimento.clear();
        arrayPedidosDisponivel.clear();
        arrayPedidosExpedindo.clear();
        while(it.hasNext()){
            x = (Pedido)it.next();
            // se o pedido estiver cancelado, return
            if(!x.isPedido_cancelado()){
                if(x.getStatus_pedido().equalsIgnoreCase("Pendente")) arrayPedidosPendente.add(x);
                if(x.getStatus_pedido().equalsIgnoreCase("Processando")) arrayPedidosAtendimento.add(x);  
                if(x.getStatus_pedido().equalsIgnoreCase("Disponivel")) arrayPedidosDisponivel.add(x);
                if(x.getStatus_pedido().equalsIgnoreCase("Expedindo")) arrayPedidosExpedindo.add(x);
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
    
    @Override
    public void novoPedido(Pedido p) throws RemoteException {
        //arrayPedidosPendente.add(p);
        initArrayList(); 
        showFiltro();
    }
    /* Chamada ClientInterface
     * 
     */
    @Override
    public void updatePedidoStatus(Pedido p) throws RemoteException {
        initArrayList();    
    }
    /* Chamada ClientInterface
     * 
     */
    Stack<String> pilha = new Stack<String>();
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
    /* Chamada ClientInterface
     * 
     */
    @Override
    public void receiverMsgFromCliente(String msg, String u) throws RemoteException {
       chat.append(u);
       chat.append(msg);
       epChat.setText(chat.toString());
    }

    @Override
    public void autenticarUsuario() {
        try {
            /* checa se a sessão ja foi removida da lista no servidor
             * caso ja tenha sido removida, chama o autenticador
             */
            // verifica se a sessão é valida para o servidor, se false chama autenticador
            servidor.unregisterForCallback(this, progID);
            jdAutenticacao.pack();
            jdAutenticacao.setVisible(true);
        } catch (RemoteException ex) { ex.printStackTrace(); }        
    }

    @Override
    public Sessao getSessao() {return session;  }
    @Override
    public RemoteUsuario obterUsuarioRemote() {return usuarioRMI;  }
    @Override
    public RemoteCliente obterClienteRemote() { return clienteRMI;}
    @Override
    public RemoteProduto obterProdutoRemote() { return produtoRMI;}
    @Override
    public RemotePedido obterPedidoRemote() {  return pedidoRMI; }
    @Override
    public RemoteCargo obterCargoRemote() {  return cargoRMI;}

    private ArrayList<Pedido> arrayPedidosAtendimento = new ArrayList();
    private ArrayList<Pedido> arrayPedidosPendente = new ArrayList();
    private ArrayList<Pedido> arrayPedidosDisponivel = new ArrayList();
    private ArrayList<Pedido> arrayPedidosExpedindo = new ArrayList();
    private Collection<Pedido> ped = new HashSet<Pedido>();
    private PedidoTableModel pedidoTableModel;
    private StringBuilder chat = new StringBuilder();

    public void persist(Object object) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("GMAPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
