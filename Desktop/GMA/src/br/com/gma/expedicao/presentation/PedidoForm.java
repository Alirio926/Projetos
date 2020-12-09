/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.expedicao.presentation;

import br.com.gma.cliente.common.AutenticadorInterface;
import br.com.gma.cliente.common.MainFormInterface;
import br.com.gma.cliente.common.MyTableModel;
import br.com.gma.server.common.entity.*;
import static br.com.gma.server.common.entity.CONSTANTES.*;  
import ca.odell.glazedlists.BasicEventList;  
import ca.odell.glazedlists.SortedList;  
import ca.odell.glazedlists.swing.AutoCompleteSupport;  
import ca.odell.glazedlists.swing.EventComboBoxModel;  
import ca.odell.glazedlists.util.concurrent.Lock;  
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Administrador
 */
public class PedidoForm extends javax.swing.JInternalFrame{

    /**
     * Creates new form PedidoForm
     */
    // referente ao combobox cnpj
    private SortedList<Cliente> sortedListCliente = new SortedList<Cliente>(new BasicEventList<Cliente>());  
    private EventComboBoxModel<Cliente> clienteComboBoxModel = new EventComboBoxModel<Cliente>(sortedListCliente);  
    private Collection<Cliente> arrayCliente;
    private Cliente cliente = null;
    
    private boolean isConnected = false; 
    AutenticadorInterface ai;
    MainFormInterface mainInterface;
    Sessao sessao;
    private ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
    private ArrayList<Produto> lstProduto = new ArrayList<Produto>();
    private MyTableModel myModel;
    private final Long pid;
        
    public PedidoForm(AutenticadorInterface a, MainFormInterface mi, Long id) throws RemoteException {
        mainInterface = mi;
        ai = a;
        pid = id;
        initComponents();
        //loadClientes();        
        loadTable();
        myModel.addRow(new String[]{"","","Remover"});
        int cod = 0;
        try{
            cod = mainInterface.obterPedidoRemote().obterUltimoCodigo(pid);
        }catch(Exception e){}
        
        if(cod == 0)
            jtfCod.setText(Integer.toString(cod+1));
        else if(cod > 0)
            jtfCod.setText(Integer.toString(cod+1));
        else
            jtfCod.setText(Integer.toString(0)+1);     
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PedidoForm.updateData(), 10, 1000);
    }
    private void JComboConfig()
    {
        jcbCliente.setModel(clienteComboBoxModel); 
        AutoCompleteSupport.install(jcbCliente, sortedListCliente);
        ComboBoxEditor cbe = jcbCliente.getEditor();
        Component editor = cbe.getEditorComponent();
        editor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    cbClienteKeyReleased(evt);
                } catch (RemoteException ex) {System.out.println("Erro comletando lista");}
            }
        });    
        editor.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbClienteFocusLost(evt);
            }
            
        });
    }
    public void cbClienteKeyReleased(java.awt.event.KeyEvent evt) throws RemoteException {
        if(jcbCliente.getEditor().getItem() == null)
            return;
        else{
            Lock writeLock = sortedListCliente.getReadWriteLock().writeLock();  
            try {  
                writeLock.lock();  
                sortedListCliente.clear();  
            } finally {  
                writeLock.unlock();  
            }
            
            arrayCliente = mainInterface.obterClienteRemote().autoCompleteClienteByNome(
                    jcbCliente.getEditor().getItem().toString(), pid);
            if(arrayCliente != null)
                sortedListCliente.addAll(arrayCliente);
        }
    }
    public void cbClienteFocusLost(FocusEvent evt) {
        if(jcbCliente.getSelectedIndex() == -1)
            return;
        
        Iterator it = arrayCliente.iterator();
        cliente = new Cliente();
        while(it.hasNext()){
            cliente = (Cliente) it.next();
            if(cliente.getNome_cliente().equals(jcbCliente.getSelectedItem().toString())){
                jcbPlaca.removeAllItems();
                for(int i=0;i<cliente.getPlaca().size();i++)
                {
                    jcbPlaca.addItem(cliente.getPlaca().get(i));
                }
                // encontrado o cliente
                // faz alguma coisa se necessario
                break;
            }
        }
    }
    private void loadClientes(){
        lstCliente.clear();
        jcbCliente.removeAllItems();
        Cliente cliente;
        try{
            Iterator it = mainInterface.obterClienteRemote().listAllClientes(pid).iterator();            
            while(it.hasNext()){
                cliente = (Cliente)it.next();
                if(cliente.Status()){
                    jcbCliente.addItem(cliente.getNome_cliente());
                    lstCliente.add(cliente);
                }                
            }   
            int i = jcbCliente.getSelectedIndex();
            jcbPlaca.removeAllItems();
            try{
                jcbPlaca.addItem(lstCliente.get(i).getPlaca());
            }catch(ArrayIndexOutOfBoundsException e){}
            catch(IndexOutOfBoundsException e){}
        }catch(RemoteException ex){ex.printStackTrace();}
    }
    
    private String[] colunas = new String[] { "Produto", "Quant.","Excluir"};
    private boolean [] edicao = {true,true,true};
    private ArrayList tableList = new ArrayList();
    private JComboBox jcbProduto = new JComboBox();
    
    // necessario criar interface para autocompletar combo box de produto
    private void loadTable(){
        tableList.clear();
        myModel = new MyTableModel(tableList,colunas,edicao);
        tablePedido.setModel(myModel);
             
        TableColumn produto = tablePedido.getColumnModel().getColumn(0);
        produto.setCellEditor(new DefaultCellEditor(jcbProduto));
                
        tablePedido.setSelectionMode(javax.swing.DefaultListSelectionModel.SINGLE_SELECTION);
        
        new ButtonColumn(tablePedido,2);        
        
        TableColumnModel cm = tablePedido.getColumnModel();
	cm.getColumn(0).setResizable(false);
	cm.getColumn(0).setPreferredWidth(80);// Código
        cm.getColumn(1).setResizable(false);
	cm.getColumn(1).setPreferredWidth(10);
        cm.getColumn(2).setResizable(false);
        cm.getColumn(2).setPreferredWidth(25);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbSilo = new javax.swing.JComboBox();
        cargaGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbCliente = new javax.swing.JComboBox();
        jcbPlaca = new javax.swing.JComboBox();
        jtfData = new javax.swing.JTextField();
        jtfCod = new javax.swing.JTextField();
        tfCarga = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbProduto = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jtfQuantBags = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        rbCargaOpen = new javax.swing.JRadioButton();
        rbCargaClose = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePedido = new javax.swing.JTable();
        btnEnviarPedido = new javax.swing.JButton();
        btnLinha = new javax.swing.JButton();

        jcbSilo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbSilo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Silo 100", "Silo 101", "Silo 102" }));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nº Pedido");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Placa");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Nome Cliente");

        jcbCliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbCliente.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbClienteItemStateChanged(evt);
            }
        });
        jcbCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jcbClienteFocusGained(evt);
            }
        });

        jcbPlaca.setEditable(true);
        jcbPlaca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jcbPlaca.setActionCommand("");
        jcbPlaca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbPlacaMouseClicked(evt);
            }
        });

        jtfData.setBackground(new java.awt.Color(204, 204, 204));
        jtfData.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jtfData.setForeground(new java.awt.Color(204, 0, 0));
        jtfData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfData.setText("23:29");
        jtfData.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtfData.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        jtfData.setEnabled(false);

        jtfCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtfCod.setEnabled(false);

        tfCarga.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("########"))));
        tfCarga.setText("0");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Carga nº");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Produto");

        cbProduto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cbProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Farinha Industrial", "Farinha Domestica", "Farelo", "Big Bag", "Big Bag Pequeno", "" }));
        cbProduto.setSelectedIndex(5);
        cbProduto.setToolTipText("");
        cbProduto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbProdutoItemStateChanged(evt);
            }
        });
        cbProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbProdutoActionPerformed(evt);
            }
        });

        jLabel6.setText("Qtd Bags");

        jtfQuantBags.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jtfQuantBags.setText("0");
        jtfQuantBags.setEnabled(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        cargaGroup.add(rbCargaOpen);
        rbCargaOpen.setText("Carga Aberta");
        rbCargaOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCargaOpenActionPerformed(evt);
            }
        });

        cargaGroup.add(rbCargaClose);
        rbCargaClose.setText("Carga Fechada");
        rbCargaClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCargaCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbCargaOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbCargaClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rbCargaClose)
                    .addComponent(rbCargaOpen))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(78, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jtfCod, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfData))
                            .addComponent(jcbCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbProduto, 0, 138, Short.MAX_VALUE)
                                    .addComponent(jcbPlaca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(10, 10, 10)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfCarga, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                                    .addComponent(jtfQuantBags))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCod, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfData, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jcbCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jcbPlaca)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(cbProduto)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addComponent(jtfQuantBags, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbProduto, jLabel1, jLabel2, jLabel3, jLabel4, jcbCliente, jcbPlaca, jtfCod, jtfQuantBags, tfCarga});

        tablePedido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tablePedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Produto", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablePedido.setRowHeight(23);
        tablePedido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tablePedidoFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(tablePedido);

        btnEnviarPedido.setText("Enviar");
        btnEnviarPedido.setMaximumSize(new java.awt.Dimension(75, 23));
        btnEnviarPedido.setMinimumSize(new java.awt.Dimension(75, 23));
        btnEnviarPedido.setPreferredSize(new java.awt.Dimension(75, 23));
        btnEnviarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarPedidoActionPerformed(evt);
            }
        });

        btnLinha.setText("Adicionar linha");
        btnLinha.setMaximumSize(new java.awt.Dimension(75, 23));
        btnLinha.setMinimumSize(new java.awt.Dimension(75, 23));
        btnLinha.setPreferredSize(new java.awt.Dimension(75, 23));
        btnLinha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLinhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnEnviarPedido, btnLinha});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnLinha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnEnviarPedido, btnLinha});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbClienteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbClienteItemStateChanged
        int i = jcbCliente.getSelectedIndex();
        jcbPlaca.removeAllItems();
        try{
            jcbPlaca.addItem(lstCliente.get(i).getPlaca());
        }catch(ArrayIndexOutOfBoundsException e){}
        catch(IndexOutOfBoundsException e){}
        
    }//GEN-LAST:event_jcbClienteItemStateChanged

    private Integer checkPlaca(Cliente c){
        System.out.println("Pass0");
        String pl = jcbPlaca.getSelectedItem().toString();
        String placa;
        MaskFormatter mask;  
        Boolean b=false;
            if(c.getNome_cliente().equals(jcbCliente.getSelectedItem().toString())){  // pega o cliente   
                for(int i=0;i<c.getPlaca().size();i++)// faz um loop por todas as placas
                    if(c.getPlaca().get(i).equalsIgnoreCase(pl)){// verifica se a placa ja esta cadastrada
                        b=true; // Cliente já possui esta placa
                        System.out.println("Placa existe");
                        return 0;
                    }
            }
        
        if(!b){
            System.out.println("Placa não existe");
            try {
                mask = new MaskFormatter("UUU-####");
                placa = mask.stringToValue(pl).toString().toUpperCase();
                // case a placa não exista no cliente selecionado
                c.setPlaca(placa);
                c.setUsuario_update(sessao.getUsuario());
                System.out.println("Adicionando placa ao cliente: "+placa);
                mainInterface.obterClienteRemote().updateCliente(c, pid);
                return 1;
            } catch (ParseException ex) {
                return 2;
            } catch (RemoteException ex) {
                Logger.getLogger(PedidoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 2;
    }
    private void btnEnviarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarPedidoActionPerformed
        /* 
         * A magica começa aqui
         */
        if(jcbCliente.getSelectedIndex() == -1)
            return;
        Boolean ok = false;
        Pedido pedido = new Pedido();
        for (Cliente c : arrayCliente) {/////////////////////
            if(c.getNome_cliente().equals(jcbCliente.getSelectedItem().toString())){
                pedido.setCliente(c);
                break;
            }
        }
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        pedido.setUnidade("OI.312");  
        Boolean isOk = true;
        switch(checkPlaca(pedido.getCliente())){
            case 0: // placa existe
                pedido.setPlaca_do_pedido(jcbPlaca.getSelectedItem().toString());
                break;
            case 1: // nova placa
                pedido.setPlaca_do_pedido(jcbPlaca.getSelectedItem().toString());
                break;
            case 2: // erro
                JOptionPane.showMessageDialog(this, "O formato da placa esta errado, escolha uma placa da lista do cliente\n ou digite uma placa no formato \"AAA-0000\" ");
                isOk = false;
                break;
        }
        // erro de formatação da placa
        if(!isOk)
            return;
        
        
        if(tfCarga.getText() == null || tfCarga.getText().equals("")){
             JOptionPane.showMessageDialog(this,"Necessario informar o número da Carga.", "Aviso",
                JOptionPane.INFORMATION_MESSAGE); 
             return;
        }
        pedido.setCarga(Integer.decode(tfCarga.getText()));
        pedido.setMoagemCanCancel(false);
        pedido.setExpedicaoCanCancel(true);
        Integer cod = null;
        try {
            cod = mainInterface.obterPedidoRemote().obterUltimoCodigo(pid);
            if(cod == null){
                cod = 0;
            }
        } catch (RemoteException ex) { ex.printStackTrace(); }
        
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoCadastrarPedido()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Object[] opcoes = {"SILO 092","SILO 093","SILO 102"};  
        String res = null;
        switch(cbProduto.getSelectedIndex()){
            case INDUSTRIAL:                               
                pedido.setTipoProduto(INDUSTRIAL);
                pedido.setQuant_bags(0);
                break;
            case DOMESTICA:
                pedido.setStatus_pedido("Expedindo");
                pedido.setTipoProduto(DOMESTICA);
                pedido.setUsuario_pedido(sessao.getUsuario());
                pedido.setUsuario_processamento(sessao.getUsuario());
                pedido.setUsuario_processamento(sessao.getUsuario());
                pedido.setExpedicaoCanCancel(false);
                pedido.setMoagemCanCancel(false);
                pedido.setQuant_bags(0);
                break;
            case BIG_BAG:
                pedido.setStatus_pedido("Pendente");
                pedido.setTipoProduto(BIG_BAG);
                pedido.setQuant_bags(Integer.parseInt(jtfQuantBags.getText()));
                pedido.setQuant_bags(0);
                res = (String)JOptionPane.showInputDialog(this, "Em qual silo deseja utilizar esse pedido?\n\n",
                    "Silo Big Bag!",JOptionPane.WARNING_MESSAGE , null ,opcoes,"");                 
                break;
            case FARELO:
                pedido.setStatus_pedido("Expedindo");
                pedido.setTipoProduto(FARELO);
                pedido.setUsuario_pedido(sessao.getUsuario());
                pedido.setUsuario_processamento(sessao.getUsuario());
                pedido.setUsuario_processamento(sessao.getUsuario());
                pedido.setExpedicaoCanCancel(false);
                pedido.setMoagemCanCancel(false);
                pedido.setQuant_bags(0);
                break;    
            case BIG_BAG_SMALL:
                pedido.setStatus_pedido("Pendente");
                pedido.setTipoProduto(BIG_BAG_SMALL);
                pedido.setQuant_bags(Integer.parseInt(jtfQuantBags.getText()));
                res = (String)JOptionPane.showInputDialog(this, "Em qual silo deseja utilizar esse pedido?\n\n",
                    "Silo Big Bag!",JOptionPane.WARNING_MESSAGE , null ,opcoes,"");  
                break;
            case 5:
                JOptionPane.showMessageDialog(this, "Selecione o tipo do produto que deseja efetuar o pedido.");
                return;
        }
        int choice = cbProduto.getSelectedIndex();
        if((choice == BIG_BAG || choice == BIG_BAG_SMALL) && res == null)
            return;
        else if(choice == BIG_BAG || choice == BIG_BAG_SMALL)
            pedido.setSilo(res);
        else
            pedido.setSilo("");
        
        /* Faz o loop pela tabela e persiste uma linha de cada vez
         * Sendo cada linha um pedido */
        for(int i = 0; i < tablePedido.getRowCount(); i++){
            try {
                if((tablePedido.getValueAt(i, 0).toString() == null) || tablePedido.getValueAt(i, 0).toString().equals("")){
                    JOptionPane.showMessageDialog(this,"Informe o produto. Linha: "+i, "Aviso",
                            JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                pedido.setProduto(mainInterface.obterProdutoRemote().pesquisarProdutoByNome(tablePedido
                        .getValueAt(i, 0).toString(), pid));
                /* tratar java.lang.NumberFormatException: empty String na linha abaixo*/
                if((tablePedido.getValueAt(i, 1).toString() == null) || tablePedido.getValueAt(i, 1).toString().equals("")){
                    JOptionPane.showMessageDialog(this,"O campo quantidade não pode ser nulo. Linha: "+i, "Aviso",
                            JOptionPane.INFORMATION_MESSAGE); 
                    return;
                }
                pedido.setQuant(Float.parseFloat(tablePedido.getValueAt(i, 1).toString()));
                // aumenta o número do pedido a cada interação de linha da tabela
                // assim cada pedido tem número unico
                pedido.setCod_pedido(cod+i+1);
                // faz a persistencia               
                // regra chata ...
                if(cbProduto.getSelectedIndex() == INDUSTRIAL){
                    if((pedido.getProduto().getCod_produto().compareTo(10036) == 0) || //Integral 40Kg
                       (pedido.getProduto().getCod_produto().compareTo(10019) == 0) || //Integral 10Kg
                       (pedido.getProduto().getCod_produto().compareTo(10028) == 0))   //Pão doce
                    {
                        pedido.setStatus_pedido("Expedindo");
                        pedido.setPedido_expedido(GregorianCalendar.getInstance().getTime());
                        pedido.setPedido_processando(GregorianCalendar.getInstance().getTime());
                        pedido.setPedido_efetivado(GregorianCalendar.getInstance().getTime());
                        pedido.setUsuario_pedido(sessao.getUsuario());
                        pedido.setUsuario_processamento(sessao.getUsuario());
                        pedido.setUsuario_processamento(sessao.getUsuario());
                    }else
                    {
                        pedido.setStatus_pedido("Pendente");
                    } 
                }
                pedido.setUsuario_pedido(sessao.getUsuario());
                if(rbCargaOpen.isSelected()){
                    pedido.setCarga_aberta(1);
                    pedido.setCarga_fechada(0);
                }else if(rbCargaClose.isSelected()){
                    pedido.setCarga_fechada(1);
                    pedido.setCarga_aberta(0);
                }else{
                    JOptionPane.showMessageDialog(this, "Informe se a carga é aberta ou fechada.");
                    return;
                }
                mainInterface.obterPedidoRemote().abrirPedido(pedido, pid);
            } catch (RemoteException ex) { System.err.println("Erro ao abrir novo pedido msg: "+ex.getMessage()); }
        }
        try {
            jtfCod.setText(Integer.toString(mainInterface.obterPedidoRemote().obterUltimoCodigo(pid)+1));
        } catch (RemoteException ex) { ex.printStackTrace(); }
        finally{
           sessao.setSession_valida(false);
           cbProduto.setSelectedIndex(5);
           pedido = new Pedido();
        }
        
    }//GEN-LAST:event_btnEnviarPedidoActionPerformed

    private void jcbClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcbClienteFocusGained
        JComboConfig();
    }//GEN-LAST:event_jcbClienteFocusGained

    private void btnLinhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLinhaActionPerformed
        myModel.addRow(new String[]{"","","Remover"});
    }//GEN-LAST:event_btnLinhaActionPerformed

    private void cbProdutoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbProdutoItemStateChanged
        lstProduto.clear();
        jcbProduto.removeAllItems();
        
        try{
            int t = cbProduto.getSelectedIndex();
            if(t==BIG_BAG_SMALL || t==BIG_BAG){ t=BIG_BAG; jtfQuantBags.setEnabled(true);}
            else{jtfQuantBags.setEnabled(false);}
            System.out.println(t);
            Iterator it = mainInterface.obterProdutoRemote().pesquisarProdutoByTipo(pid,t).iterator();
            Produto produto;
            while(it.hasNext()){
                produto = (Produto)it.next();
                lstProduto.add(produto);
                jcbProduto.addItem(produto.getNome_produto());                
            }
        }catch(RemoteException ex){ex.printStackTrace();}  
        finally{loadTable();}
    }//GEN-LAST:event_cbProdutoItemStateChanged

    private void cbProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbProdutoActionPerformed
        
    }//GEN-LAST:event_cbProdutoActionPerformed

    private void tablePedidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablePedidoFocusLost
        if(tablePedido.isEditing())
            System.out.println("Editando");
        
    }//GEN-LAST:event_tablePedidoFocusLost

    private void jcbPlacaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbPlacaMouseClicked
        
        
    }//GEN-LAST:event_jcbPlacaMouseClicked

    private void rbCargaOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCargaOpenActionPerformed
       
    }//GEN-LAST:event_rbCargaOpenActionPerformed

    private void rbCargaCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCargaCloseActionPerformed
        System.out.println("Fechada");
    }//GEN-LAST:event_rbCargaCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarPedido;
    private javax.swing.JButton btnLinha;
    private javax.swing.ButtonGroup cargaGroup;
    private javax.swing.JComboBox cbProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox jcbCliente;
    private javax.swing.JComboBox jcbPlaca;
    private javax.swing.JComboBox jcbSilo;
    private javax.swing.JTextField jtfCod;
    private javax.swing.JTextField jtfData;
    private javax.swing.JFormattedTextField jtfQuantBags;
    private javax.swing.JRadioButton rbCargaClose;
    private javax.swing.JRadioButton rbCargaOpen;
    private javax.swing.JTable tablePedido;
    private javax.swing.JFormattedTextField tfCarga;
    // End of variables declaration//GEN-END:variables

    private DateFormat fmt = new SimpleDateFormat("H':'mm':'ss':' dd/MM/yyyy"); 

    private class updateData extends TimerTask{        
        @Override
        public void run() {            
            jtfData.setText(fmt.format(GregorianCalendar.getInstance().getTime()));
        }
    }
    private class ButtonColumn extends AbstractCellEditor implements TableCellRenderer,
    TableCellEditor, ActionListener
    {
        JTable table;
        JButton renderButton, editButton;
        String text;
        public ButtonColumn(JTable table, int column)
        {
            super();
            this.table = table;
            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer( this );
            columnModel.getColumn(column).setCellEditor( this );
        }
        @Override
        public Object getCellEditorValue() {
            return text;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(hasFocus)
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            else if (isSelected)
            {
                renderButton.setForeground(table.getSelectionForeground());
                 renderButton.setBackground(table.getSelectionBackground());
            }
            else
            {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }

            renderButton.setText( (value == null) ? "" : value.toString() );
            return renderButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.setText( text );
            return editButton;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            fireEditingStopped();
            //list.remove(table.getSelectedRow());
            myModel.removeRow(table.getSelectedRow());
        }

    }
}
