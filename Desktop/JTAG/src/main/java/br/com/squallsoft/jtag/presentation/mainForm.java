/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.presentation;

import br.com.squallsoft.api.dominios.BurnLogTransient;
import com.squallsoft.api.dominios.Mapper;
import com.squallsoft.api.dominios.FirmwareToBurn;
import br.com.squallsoft.api.resteasy.RESTEasyClient;
import br.com.squallsoft.jtag.adapter.FileProperties;
import static br.com.squallsoft.jtag.adapter.FileProperties.ServidorIP;
import br.com.squallsoft.jtag.adapter.FormUpdate;
import br.com.squallsoft.jtag.adapter.LocalLog;
import br.com.squallsoft.jtag.adapter.LogTree;
import br.com.squallsoft.jtag.adapter.NodeService;
import static br.com.squallsoft.jtag.adapter.NodeService.getImage;
import br.com.squallsoft.jtag.adapter.SerialHelper;
import br.com.squallsoft.jtag.adapter.Status;
import br.com.squallsoft.jtag.facade.APITask;
import br.com.squallsoft.jtag.facade.AudioPlayer;
import br.com.squallsoft.jtag.facade.XsvfPlay;
import com.fazecast.jSerialComm.SerialPort;
import com.squallsoft.api.dominios.Board;
import com.squallsoft.api.dominios.LogEntity;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author aliri
 */
public class mainForm extends javax.swing.JFrame implements FormUpdate{

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(mainForm.class);
    private SerialHelper serial;
    private String selectedPort;
    private final int serialSpeed = 115200;
    private RESTEasyClient restAPI;
    public static SimpleAttributeSet INFO, ERROR, AVISO, SUCCESS;
    private StyledDocument log;
    private XsvfPlay xsvfplay;
    private APITask api;
    private LocalLog localLog;
    private DefaultMutableTreeNode root;
    private List<LogEntity> listLog;
    private DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy 'as' H':'mm':'ss");
    private List<ImageIcon> lstImagens;
    private List<Board> boards;
    
    private AudioPlayer audioOk, audioFail;

    
    static class LogTreeCellRenderer implements TreeCellRenderer {
       private JLabel label;

       LogTreeCellRenderer() {
           label = new JLabel();
       }

       @Override
       public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                     boolean leaf, int row, boolean hasFocus) {
           Object o = ((DefaultMutableTreeNode) value).getUserObject();
           if (o instanceof LogTree) {
               LogTree country = (LogTree) o;
               label.setIcon(new ImageIcon(getImage(country.getFlagIcon())));
               label.setText(country.getName());
           } else {
               if(row == 0){ // root
                   label.setIcon(new ImageIcon(getImage("nes.png")));
                   label.setText("" + value);
               }else if(leaf){ // folha
                   if (o instanceof LogTree) {
                        LogTree country = (LogTree) o;
                        label.setIcon(new ImageIcon(getImage(country.getFlagIcon())));
                        label.setText(country.getName());
                    }
               }else if(expanded){ // open
                   label.setIcon(new ImageIcon(getImage("hidden.png")));
                   label.setText("" + value);
               }else{ // close
                   label.setIcon(new ImageIcon(getImage("show.png")));
                   label.setText("" + value);
               }
           }
           
        
           return label;
       }
   }
    
    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public static <E, K> Map<K, List<E>> groupBy(List<E> list, Function<E, K> keyFunction) {
    return Optional.ofNullable(list)
            .orElseGet(ArrayList::new)
            .stream()
            .collect(Collectors.groupingBy(keyFunction));
    }
    /**
     * Creates new form mainForm
     */
    public mainForm() {
        
        //https://icons8.com.br/icons/set/nintendo--red
        //ImageIcon icon = new ImageIcon(getImage("chip.png"));
        
        //UIManager.put("Tree.closedIcon", new ImageIcon(getImage("hidden1.png")));
        //UIManager.put("Tree.openIcon", new ImageIcon(getImage("show.png")));
        //UIManager.put("Tree.leafIcon", new ImageIcon(getImage("chip1.png")));
        
        initComponents(); 
        
        try {
            lblServerIP.setText(ServidorIP());
        } catch (Exception ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        audioOk = new AudioPlayer("ok.wav");
        audioFail = new AudioPlayer("fail.wav");
        //audioOk.play();
        
        try {
            //this.setTitle("Programador -by Squall : " + br.com.squallsoft.jtag.adapter.FileProperties.Servidor_URL());
            
            lstImagens = new ArrayList<>();
            lstImagens.add(new ImageIcon(getImage("IMG-00.png")));
            lstImagens.add(new ImageIcon(getImage("IMG-01.png")));
            lstImagens.add(new ImageIcon(getImage("IMG-02.png")));
            lstImagens.add(new ImageIcon(getImage("IMG-03.png")));
            
            lstImagens.add(new ImageIcon(getImage("IMG.png")));
            
            lblJmp.setIcon(lstImagens.get(4));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.err.println(System.getProperty("user.dir")+File.separator);
        localLog = new LocalLog();
        
        // Cria fake registro de log para testar contador na UI
        /*
        LogEntity le = new LogEntity();
        le.setBurnID(Long.MIN_VALUE);
        le.setDataTime(LocalDateTime.MIN);
        le.setLog("Blah");
        le.setMapper("MMC3");
        le.setPedidoId(1000000);
        le.setSuccess(Boolean.TRUE);
                
        try {
            localLog.adiciona(le);
        } catch (IOException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        serial = new SerialHelper();
        SerialPort[] ports = SerialHelper.getPorts();
        for(SerialPort p : ports){
            cbPorts.addItem(p.getSystemPortName());
        }
        
        try {
            RESTEasyClient.loadAllFirmware();
            for(Mapper e : RESTEasyClient.listFirmware()){
                cbFirmware.addItem(e.getChip_name());                
            }
            if(cbPorts.getItemCount() > 0 && cbFirmware.getItemCount() > 0)
                btnProgram.setEnabled(true);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Error carregando lista de firmware: " + ex.getLocalizedMessage());
            jdialogServidorConfig.setVisible(false);
        }
        
        // carrega lista
        updateListLogs();

        DefaultMutableTreeNode n = NodeService.getAllNodesByDay(listLog, "Histórico"); 
        treeLog.setModel(new DefaultTreeModel(n));  

        treeLog.setCellRenderer(new LogTreeCellRenderer());
        
        jsQtd.setValue(1);
        
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        
        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        
        ERROR = new SimpleAttributeSet(); 
        INFO  = new SimpleAttributeSet(); 
        AVISO = new SimpleAttributeSet(); 
        SUCCESS = new SimpleAttributeSet(); 
               
        StyleConstants.setForeground(ERROR, Color.RED);  
        StyleConstants.setForeground(INFO , Color.BLACK);  
        StyleConstants.setForeground(AVISO, Color.BLUE); 
        StyleConstants.setForeground(SUCCESS, Color.GREEN); 
        
        log = textPane.getStyledDocument();
        
        xsvfplay = new XsvfPlay(this, serial);
        api = new APITask(this);
        
        
    }
    
    private void updateListLogs(){
        try {
            listLog = localLog.loadListLog();
            Integer qtdSuccess=0;
            for(LogEntity logg : listLog)
                if(logg.getSuccess())
                    qtdSuccess++;
                
            Float total = qtdSuccess * 20.0f;
            tfQtd.setText(Integer.toString(qtdSuccess));
            tfTotal.setText(Float.toString(total));
            
        } catch (IOException |ClassNotFoundException ex) {
            logger.error("Erro atualizando ListLogs: " + ex.getMessage());
        } 
    }    

    private void appendToLog(String text, SimpleAttributeSet style) {
        try {
            log.insertString(log.getLength(), text+"\n", style);       
            textPane.setCaretPosition(textPane.getDocument().getLength());
        } catch (BadLocationException e) {
            logger.error("Erro escrevendo no form log: " + e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogChave = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jdialogServidorConfig = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tfNovaPorta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tfCurrentIP = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfCurrentPort = new javax.swing.JTextField();
        btnFechar = new javax.swing.JButton();
        btnAtualizarServidorIP = new javax.swing.JButton();
        tfNovoIP = new javax.swing.JFormattedTextField();
        cbPorts = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        cbFirmware = new javax.swing.JComboBox<>();
        btnProgram = new javax.swing.JButton();
        pbTask0 = new javax.swing.JProgressBar();
        jsQtd = new javax.swing.JSpinner();
        pbTask1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        treeLog = new javax.swing.JTree();
        cbPcbs = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        lblJmp = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taListPcbs = new javax.swing.JTextArea();
        tfInfo = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfQtd = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        tfTotal = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblServerIP = new javax.swing.JLabel();

        jLabel7.setText("Chave #01:");

        jLabel8.setText("Chave #02:");

        jLabel9.setText("Chave #03:");

        jTextField2.setText("00000000");
        jTextField2.setEnabled(false);

        jTextField3.setText("00000000");
        jTextField3.setEnabled(false);

        jTextField4.setText("00000000");
        jTextField4.setEnabled(false);

        javax.swing.GroupLayout dialogChaveLayout = new javax.swing.GroupLayout(dialogChave.getContentPane());
        dialogChave.getContentPane().setLayout(dialogChaveLayout);
        dialogChaveLayout.setHorizontalGroup(
            dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogChaveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel9)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField2)
                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(jTextField3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogChaveLayout.setVerticalGroup(
            dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogChaveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogChaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextPane1.setEditable(false);
        jTextPane1.setContentType("text/plain\n"); // NOI18N
        jTextPane1.setText("Não foi possivel acessar API.\nPor favor, verifique sua conexão com a internet.\nCaso essa não seja a primeira vez, ligue para\n                       (71)98156-1905.");
        jTextPane1.setFocusable(false);
        jScrollPane5.setViewportView(jTextPane1);

        jLabel4.setText("Deseja atualizar o endereço do servidor?");

        jLabel6.setText("IP:");

        jLabel11.setText("Porta:");

        jLabel12.setText("IP:");

        tfCurrentIP.setText("255.255.255.255");
        tfCurrentIP.setEnabled(false);

        jLabel13.setText("Porta:");

        tfCurrentPort.setText("6969");
        tfCurrentPort.setEnabled(false);

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        btnAtualizarServidorIP.setText("Salvar");
        btnAtualizarServidorIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarServidorIPActionPerformed(evt);
            }
        });

        try {
            tfNovoIP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###.###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfNovoIP.setText("000.000.000.000");

        javax.swing.GroupLayout jdialogServidorConfigLayout = new javax.swing.GroupLayout(jdialogServidorConfig.getContentPane());
        jdialogServidorConfig.getContentPane().setLayout(jdialogServidorConfigLayout);
        jdialogServidorConfigLayout.setHorizontalGroup(
            jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdialogServidorConfigLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jdialogServidorConfigLayout.createSequentialGroup()
                            .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdialogServidorConfigLayout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfCurrentIP, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jdialogServidorConfigLayout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfNovoIP)))
                            .addGap(10, 10, 10)
                            .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tfNovaPorta, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfCurrentPort, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdialogServidorConfigLayout.createSequentialGroup()
                        .addComponent(btnAtualizarServidorIP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFechar)))
                .addContainerGap())
        );
        jdialogServidorConfigLayout.setVerticalGroup(
            jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdialogServidorConfigLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(7, 7, 7)
                .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfCurrentIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(tfCurrentPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNovoIP, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jLabel11)
                        .addComponent(tfNovaPorta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jdialogServidorConfigLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFechar)
                    .addComponent(btnAtualizarServidorIP))
                .addContainerGap())
        );

        jdialogServidorConfigLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel13, jLabel6, tfCurrentIP, tfCurrentPort, tfNovaPorta, tfNovoIP});

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Programador -by Squall");
        setResizable(false);

        cbPorts.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---" }));
        cbPorts.setToolTipText("Duplo clique para atualizar a lista");
        cbPorts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbPortsMouseClicked(evt);
            }
        });

        jLabel1.setText("Porta:");
        jLabel1.setToolTipText("");

        cbFirmware.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFirmwareItemStateChanged(evt);
            }
        });

        btnProgram.setText("Programar");
        btnProgram.setEnabled(false);
        btnProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProgramActionPerformed(evt);
            }
        });

        pbTask0.setStringPainted(true);

        jsQtd.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        pbTask1.setStringPainted(true);

        textPane.setEditable(false);
        textPane.setFocusable(false);
        jScrollPane2.setViewportView(textPane);

        jScrollPane3.setViewportView(treeLog);

        cbPcbs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbPcbs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbPcbsItemStateChanged(evt);
            }
        });

        jLabel2.setText("Pcbs Suportadas:");

        lblJmp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        taListPcbs.setEditable(false);
        taListPcbs.setColumns(20);
        taListPcbs.setFont(new java.awt.Font("Calibri Light", 0, 12)); // NOI18N
        taListPcbs.setLineWrap(true);
        taListPcbs.setRows(1);
        taListPcbs.setWrapStyleWord(true);
        taListPcbs.setEnabled(false);
        taListPcbs.setFocusable(false);
        jScrollPane4.setViewportView(taListPcbs);

        tfInfo.setEditable(false);
        tfInfo.setFocusable(false);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Sucesso:");

        tfQtd.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tfQtd.setForeground(new java.awt.Color(0, 102, 51));
        tfQtd.setText("050");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Valor R$");

        tfTotal.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tfTotal.setForeground(new java.awt.Color(0, 102, 51));
        tfTotal.setText("1.280,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(tfTotal)
                    .addComponent(jLabel5)
                    .addComponent(jSeparator1)
                    .addComponent(tfQtd)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel3, jLabel5, tfQtd, tfTotal});

        jLabel10.setText("Servidor:");

        lblServerIP.setText(" http://201.8.41.97/");
        lblServerIP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pbTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblJmp, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbPcbs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPorts, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbFirmware, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jsQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(pbTask0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfInfo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblServerIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbPorts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbFirmware, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jsQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnProgram))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pbTask0, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addGap(7, 7, 7)
                        .addComponent(pbTask1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblJmp, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(cbPcbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblServerIP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pbTask0, pbTask1});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel10, lblServerIP});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbPortsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbPortsMouseClicked
        if(evt.getClickCount() >= 2){
            cbPorts.removeAllItems();
            SerialPort[] ports = SerialHelper.getPorts();
            for(SerialPort p : ports){
                cbPorts.addItem(p.getSystemPortName());
            }
        }
    }//GEN-LAST:event_cbPortsMouseClicked

    private void cbFirmwareItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFirmwareItemStateChanged
        
        Long mapperId = RESTEasyClient.listFirmware().get(cbFirmware.getSelectedIndex()).getId();
        try {
            boards = RESTEasyClient.getSupportedBoardsByMapperId(mapperId);
            
            
            String txt = "";
            
            cbPcbs.removeAllItems();
            
            cbPcbs.addItem("Selecione...");
            
            for(Board b : boards){  
                txt += b.getBoard_name() + " : ";                
                cbPcbs.addItem(b.getBoard_name());               
            }            
            
            taListPcbs.setText(txt);
        } catch (Exception ex) {
            logger.error("Erro recuperando lista de board_supportes: " + ex.getLocalizedMessage());
        }
        
        
    }//GEN-LAST:event_cbFirmwareItemStateChanged

    private void btnProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProgramActionPerformed
        try {
            if(cbPorts.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this, "Selecione uma porta serial antes de iniciar a gravação.", "Porta serial não selecionada.", JOptionPane.WARNING_MESSAGE);
                return;
            }
            pbTask1.setValue(0);            
            pbTask0.setValue(0);
            int selectedID = cbFirmware.getSelectedIndex();
            api.executeTask(selectedID, (int)jsQtd.getValue());
            // xsvf sera chamado no retorno do resapi
            
        } catch (Exception ex) {
            logger.error("Erro executando api: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnProgramActionPerformed

    private void cbPcbsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPcbsItemStateChanged
        if(boards == null) return;
        Integer selectedItem = cbPcbs.getSelectedIndex();
        if(selectedItem <= 0) 
            lblJmp.setIcon(lstImagens.get(4));
        else{               
            Board board = boards.get(selectedItem-1);
            Integer jmp = board.getJumpers();
            lblJmp.setIcon(lstImagens.get(jmp));   
            
            System.out.println("Selected: " + selectedItem + " Board: " + board.getJumpers());
        }
        
        
    }//GEN-LAST:event_cbPcbsItemStateChanged

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnAtualizarServidorIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarServidorIPActionPerformed
        try {
            FileProperties.writeProperties("SERVIDOR_IP", String.format("http%s%s%s", ":", "//", tfNovoIP.getText()));
            FileProperties.writeProperties("SERVIDOR_PORT", tfNovaPorta.getText());
        } catch (IOException ex) {
            Logger.getLogger(mainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jdialogServidorConfig.setVisible(false);
                
        JOptionPane.showMessageDialog(this, "Atualizado, abra o programa novamente.");
        
        System.exit(0);
    }//GEN-LAST:event_btnAtualizarServidorIPActionPerformed

    public void setProgress(int value, int maxValue){
        int progress = value * 100 / maxValue;
        pbTask1.setValue(progress);
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarServidorIP;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnProgram;
    private javax.swing.JComboBox<String> cbFirmware;
    private javax.swing.JComboBox<String> cbPcbs;
    private javax.swing.JComboBox<String> cbPorts;
    private javax.swing.JDialog dialogChave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JDialog jdialogServidorConfig;
    private javax.swing.JSpinner jsQtd;
    private javax.swing.JLabel lblJmp;
    private javax.swing.JLabel lblServerIP;
    private javax.swing.JProgressBar pbTask0;
    private javax.swing.JProgressBar pbTask1;
    private javax.swing.JTextArea taListPcbs;
    private javax.swing.JTextPane textPane;
    private javax.swing.JTextField tfCurrentIP;
    private javax.swing.JTextField tfCurrentPort;
    private javax.swing.JTextField tfInfo;
    private javax.swing.JTextField tfNovaPorta;
    private javax.swing.JFormattedTextField tfNovoIP;
    private javax.swing.JLabel tfQtd;
    private javax.swing.JLabel tfTotal;
    private javax.swing.JTree treeLog;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateProgress(int writted) {
        pbTask0.setValue(writted);
    }

    @Override
    public void printInfo(Status type, String arg) {
        System.out.println(type.getDescription()+" : "+arg);
    }

    @Override
    public void deviceQuit(StringTokenizer token) {
        while(token.hasMoreTokens())
            System.out.println("Received Device Quit: [" + token.nextToken() + "] [" + token.nextToken() + "]");
    }

    @Override
    public void printLog(String txt, SimpleAttributeSet style) {
        appendToLog(txt, style);
    }

    @Override
    public void setIndeterminated(boolean value) {
        pbTask0.setIndeterminate(value);
    }

    @Override
    public void setAPIInfo(String value) {
        tfInfo.setText(value);
    }

    @Override
    public void setFirmware(FirmwareToBurn firm) {
        btnProgram.setEnabled(false);
        // número de gravações
        int qtdBurn = (int)jsQtd.getValue();
        String tbegin;
        int pBarValue = 0, qtdCancel=0, followLoop=qtdBurn;
        try{
            BurnLogTransient logTransient = new BurnLogTransient();
            // #1 Burn
            for(int i = 0; i < qtdBurn; i++){                
                int resposta = JOptionPane.showConfirmDialog(this, "Pronto para iniciar?", "Confirmar.", JOptionPane.OK_CANCEL_OPTION);
                
                if(resposta == JOptionPane.OK_OPTION){
                    followLoop--;
                    
                    tbegin = fmt.format(GregorianCalendar.getInstance().getTime())+ " : Task Begin.\n";                    
                    
                    // obtem numero do log burn
                    Long logBurn = RESTEasyClient.getNewBurnID(firm.getPedidoId());
                    
                    if(logBurn == 0){
                        RESTEasyClient.closePedido(firm.getPedidoId(), qtdBurn);
                        logger.error("Não foi possivel ober um BurnID.");
                        return;
                    }
                    logTransient.setBurnID(logBurn);
                    logTransient.setPedidoID(firm.getPedidoId());
                    logTransient.setLog(new StringBuilder(tbegin));
                    // Burn Chip
                    xsvfplay.executeTask(firm.getXsvf_file(), logTransient, cbFirmware.getSelectedItem().toString(), cbPorts.getSelectedItem().toString());
                    
                    // espera task terminar
                    while(!xsvfplay.IsDone()){}
                    
                    pBarValue = (i+1) * 100 / qtdBurn;
                    pbTask1.setValue(pBarValue);
                }else{
                    // termina loop
                    i = qtdBurn;                
                    pbTask0.setIndeterminate(false);
                    pbTask1.setIndeterminate(false);
                } 
            } // end for
            // Cancela o que restou
            qtdCancel = followLoop;
        } catch (Exception ex) {
            // Se ocorreu erro
            qtdCancel = followLoop + 1;            
            ex.printStackTrace();
            logger.error("Erro: " + ex.getMessage());
        }finally{
            try {                
                String cancelReturn = RESTEasyClient.closePedido(firm.getPedidoId(), qtdCancel);
                //JOptionPane.showMessageDialog(this, cancelReturn);
                btnProgram.setEnabled(true);
            } catch (Exception ex) {
                logger.error("Pedido: " + firm.getPedidoId() + " erro: " + ex.getMessage());
            }
        }
    }

    @Override
    public void updateTree() {
        
        System.err.println("Call updateTree");
        treeLog.setModel(null);

        // a lista de logs foi atualizada na chamada updateInfoBar > updateListLogs

        // Carrega organizando
        DefaultMutableTreeNode n = NodeService.getAllNodesByDay(listLog, "Histórico"); 
        treeLog.setModel(new DefaultTreeModel(n));
        treeLog.revalidate();
        treeLog.updateUI();
    }

    @Override
    public void updateInfoBar(){
        updateListLogs();
    }
    
    @Override
    public void playSound(boolean b) {
        if(b)
            audioOk.play();
        else
            audioFail.play();
    }
}
