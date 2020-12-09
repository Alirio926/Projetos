/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.expedicao.presentation;

import br.com.gma.cliente.common.AutenticadorInterface;
import br.com.gma.cliente.common.MainFormInterface;
import br.com.gma.cliente.common.MyTableModel;
import br.com.gma.server.common.entity.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class CadastroForm extends javax.swing.JInternalFrame {

    /**
     * Creates new form CadastroForm
     */
    AutenticadorInterface ai;
    MainFormInterface mainInterface;
    Sessao sessao;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm"); 
    private final Long myID;
    private String[] col;
    private boolean [] edit;
    private ArrayList arrayPlacas = new ArrayList();
    
    public CadastroForm(AutenticadorInterface a, MainFormInterface mi, Long id) {
        try{
            initComponents();
        }catch(Exception p){}
        myID = id;
        ai = a;
        mainInterface = mi;
        col1 = new String[] { "Clientes" };
        edit1 = new boolean[]{false};
        tblPlacas.setModel(new MyTableModel(arrayPlacas,col1,edit1));
        loadUsuario();
        loadClientes();
        loadProdutos();
        loadCargos();
    }

    /* Responsavel por carregar todos os dados no form
     * buscando fazendo query e loop para preencher 
     * todos os campos necessario no cliente
     */
    private String[] col1;
    private boolean [] edit1;
    private ArrayList user = new ArrayList();
    private ArrayList clie = new ArrayList();
    private ArrayList prod = new ArrayList();
    private ArrayList<Cargo> lstCargos = new ArrayList<Cargo>();
    private ArrayList<Produto> lstProdutos = new ArrayList<Produto>();
    private ArrayList<Usuario> lstUsuario = new ArrayList<Usuario>();
    private ArrayList<Cliente> lstCliente = new ArrayList<Cliente>();
    private int row;
    private Long myId;
    
   
    private void loadUsuario(){
        
        
        Usuario usuario;
        user.clear();
        lstUsuario.clear();
        // preenchimento da Table usuario
        try{
            
            Iterator it = mainInterface.obterUsuarioRemote().listAllUsuarios(Long.MIN_VALUE).iterator();
            while(it.hasNext()){
                usuario = (Usuario)it.next();
                if(usuario.isStatus()){
                    try{
                        lstUsuario.add(usuario);
                        user.add(new String[]{usuario.getUsername(),usuario.getGma(),usuario.getCargo().getCargo()});
                    }catch(NullPointerException ne){ continue; }
                }
            }                        
            col = new String[] { "Nome","GMA","Cargo"};
            edit = new boolean[]{false, false, false};
            tblUsuario.setModel(new MyTableModel(user,col,edit));
        }catch(RemoteException ex){ex.printStackTrace();}
        
    }
    private void loadClientes(){
        Cliente cliente;
        clie.clear();
        lstCliente.clear();
        // preenchimento Table cliente e Table Placa
        try{
            Iterator it = mainInterface.obterClienteRemote().listAllClientes(Long.MIN_VALUE).iterator();
            
            while(it.hasNext()){
                cliente = (Cliente)it.next();
                if(cliente.Status()){
                    clie.add(new String[]{cliente.getNome_cliente()});
                    lstCliente.add(cliente);
                }
            }
            col = new String[] {"Clientes"};
            edit = new boolean[]{false};
            tblClientes.setModel(new MyTableModel(clie,col,edit));            
        }catch(RemoteException ex){ex.printStackTrace();}
    }
    private void loadProdutos(){
        prod.clear();
        lstProdutos.clear();
        // preenchimento Table Produtos
        try{
            Iterator it = mainInterface.obterProdutoRemote().listAllProdutos(Long.MIN_VALUE).iterator();
            Produto produto;
            prod.clear();
            while(it.hasNext()){
                produto = (Produto)it.next();
                if(produto.isStatus()){
                    prod.add(new String[]{produto.getCod_produto().toString(),produto.getNome_produto()});                
                    lstProdutos.add(produto);
                }
            }
            col = new String[] {"Código","Produto"};
            edit = new boolean[]{false,false};
            tblProdutos.setModel(new MyTableModel(prod,col,edit));
        }catch(RemoteException ex){ex.printStackTrace(); }
    }
    private void loadCargos(){
       
        cbCargo.removeAllItems();
        // preenchimento do combobox cargo
        try {
            Iterator it = mainInterface.obterCargoRemote().listarCargos().iterator();
            while(it.hasNext()){cbCargo.addItem(((Cargo)it.next()).getCargo());}
            
        } catch (RemoteException ex) { ex.printStackTrace(); }
        
        // preenchimento Table Cargos
        try{
            Iterator it = mainInterface.obterCargoRemote().listarCargos().iterator();
            Cargo cargo;
            DefaultTableModel cargosModel = new DefaultTableModel();
            cargosModel.addColumn("Cargo");
            lstCargos.clear();
            while(it.hasNext()){
                cargo = (Cargo)it.next();
                cargosModel.addRow(new String[]{cargo.getCargo()});
                lstCargos.add(cargo);
            }            
            tblCargos.setModel(cargosModel);
        }catch(RemoteException ex){ex.printStackTrace();}
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLog = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        lblDataDoRegistro = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblHoraDoRegistro = new javax.swing.JLabel();
        lblUsuarioDoRegistro = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblDataUpdateDoRegistro = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblHoraUpdateDoRegistro = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblUsuarioUpdateDoRegistro = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        mainTablePane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfNovoUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfPassword = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        cbCargo = new javax.swing.JComboBox();
        tfGma = new javax.swing.JFormattedTextField();
        btnEditarUsuario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        btnDesativarUsuario = new javax.swing.JButton();
        btnNovoUsuario = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        tfNovoCliente = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPlacas = new javax.swing.JTable();
        btnEditarCliente = new javax.swing.JButton();
        btnNovoCliente = new javax.swing.JButton();
        btnDesativarCliente = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnNovaPlaca = new javax.swing.JButton();
        tfEditarPlaca = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tfNovoProduto = new javax.swing.JTextField();
        tfNovoCodProduto = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        cbProduto = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        btnEditarProduto = new javax.swing.JButton();
        btnNovoProduto = new javax.swing.JButton();
        btnDesativarProduto = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        novoUsuario = new javax.swing.JCheckBox();
        editUsuario = new javax.swing.JCheckBox();
        enableUsuario = new javax.swing.JCheckBox();
        eraseUsuario = new javax.swing.JCheckBox();
        jPanel10 = new javax.swing.JPanel();
        novoProduto = new javax.swing.JCheckBox();
        editProduto = new javax.swing.JCheckBox();
        enableProduto = new javax.swing.JCheckBox();
        eraseProduto = new javax.swing.JCheckBox();
        jPanel11 = new javax.swing.JPanel();
        novoCliente = new javax.swing.JCheckBox();
        editCliente = new javax.swing.JCheckBox();
        enableCliente = new javax.swing.JCheckBox();
        eraseCliente = new javax.swing.JCheckBox();
        btnCancelarEditPermissao = new javax.swing.JButton();
        btnSalvarPermissao = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        novoPedido = new javax.swing.JCheckBox();
        finalizarPedido = new javax.swing.JCheckBox();
        expedirPedido = new javax.swing.JCheckBox();
        atenderPedido = new javax.swing.JCheckBox();
        cancelarPedido = new javax.swing.JCheckBox();
        especialPedido = new javax.swing.JCheckBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblCargos = new javax.swing.JTable();
        btnLogin = new javax.swing.JButton();

        jLog.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel7.setText("Data de criação");

        lblDataDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel9.setText("Hora de criação");

        lblHoraDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        lblUsuarioDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel14.setText("Usuario");

        jLabel15.setText("Data de criação");

        lblDataUpdateDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel17.setText("Hora de criação");

        lblHoraUpdateDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel19.setText("Usuario");

        lblUsuarioUpdateDoRegistro.setFont(new java.awt.Font("Calibri", 1, 12)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Data update");

        jLabel22.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Data criação");

        javax.swing.GroupLayout jLogLayout = new javax.swing.GroupLayout(jLog.getContentPane());
        jLog.getContentPane().setLayout(jLogLayout);
        jLogLayout.setHorizontalGroup(
            jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLogLayout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jLogLayout.createSequentialGroup()
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHoraDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuarioDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
                .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jLogLayout.createSequentialGroup()
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblDataUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jLogLayout.createSequentialGroup()
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoraUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuarioUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jLogLayout.setVerticalGroup(
            jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jLogLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jLogLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jLogLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblDataDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblHoraDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUsuarioDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jLogLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDataUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHoraUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUsuarioUpdateDoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);

        mainTablePane.setFocusCycleRoot(true);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel1.setText("Nome");

        jLabel2.setText("GMA");

        jLabel3.setText("Senha");

        jLabel5.setText("Cargo");

        try {
            tfGma.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("Gma#####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfGma.setMaximumSize(new java.awt.Dimension(6, 20));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfGma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(tfNovoUsuario)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(cbCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(tfGma, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEditarUsuario.setText("Editar");
        btnEditarUsuario.setMaximumSize(new java.awt.Dimension(75, 23));
        btnEditarUsuario.setMinimumSize(new java.awt.Dimension(75, 23));
        btnEditarUsuario.setPreferredSize(new java.awt.Dimension(75, 23));
        btnEditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarUsuarioActionPerformed(evt);
            }
        });

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "GMA", "Cargo"
            }
        ));
        tblUsuario.setMaximumSize(new java.awt.Dimension(225, 64));
        tblUsuario.setMinimumSize(new java.awt.Dimension(225, 64));
        tblUsuario.setRowHeight(20);
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);

        btnDesativarUsuario.setText("Desativar");
        btnDesativarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesativarUsuarioActionPerformed(evt);
            }
        });

        btnNovoUsuario.setText("Cadastrar");
        btnNovoUsuario.setMaximumSize(new java.awt.Dimension(75, 23));
        btnNovoUsuario.setMinimumSize(new java.awt.Dimension(75, 23));
        btnNovoUsuario.setPreferredSize(new java.awt.Dimension(75, 23));
        btnNovoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNovoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDesativarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNovoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDesativarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 32, Short.MAX_VALUE)))
                .addContainerGap())
        );

        mainTablePane.addTab("Usuario", jPanel1);

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel6.setText("Cliente");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(tfNovoCliente)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNovoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setMaximumSize(new java.awt.Dimension(380, 209));
        jSplitPane1.setMinimumSize(new java.awt.Dimension(380, 209));

        tblClientes.setAutoCreateRowSorter(true);
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Marcelo Oliveira Ferreira"},
                {"Marcus Vinicius Silva"},
                {"Alirio Freire de Oliveira Filho"}
            },
            new String [] {
                "Clientes"
            }
        ));
        tblClientes.setRowHeight(20);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel13);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setEnabled(false);
        jScrollPane2.setFocusable(false);

        tblPlacas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Placas"
            }
        ));
        tblPlacas.setEnabled(false);
        tblPlacas.setRowHeight(20);
        tblPlacas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblPlacas);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel14);

        btnEditarCliente.setText("Editar");
        btnEditarCliente.setMaximumSize(new java.awt.Dimension(75, 23));
        btnEditarCliente.setMinimumSize(new java.awt.Dimension(75, 23));
        btnEditarCliente.setPreferredSize(new java.awt.Dimension(75, 23));
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnNovoCliente.setText("Cadastrar");
        btnNovoCliente.setMaximumSize(new java.awt.Dimension(75, 23));
        btnNovoCliente.setMinimumSize(new java.awt.Dimension(75, 23));
        btnNovoCliente.setPreferredSize(new java.awt.Dimension(75, 23));
        btnNovoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoClienteActionPerformed(evt);
            }
        });

        btnDesativarCliente.setText("Desativar");
        btnDesativarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesativarClienteActionPerformed(evt);
            }
        });

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel10.setText("Placa");

        btnNovaPlaca.setText("Adicionar Placa");
        btnNovaPlaca.setMaximumSize(new java.awt.Dimension(75, 23));
        btnNovaPlaca.setMinimumSize(new java.awt.Dimension(75, 23));
        btnNovaPlaca.setPreferredSize(new java.awt.Dimension(75, 23));
        btnNovaPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaPlacaActionPerformed(evt);
            }
        });

        try {
            tfEditarPlaca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("***-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfEditarPlaca.setMaximumSize(new java.awt.Dimension(6, 20));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfEditarPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNovaPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovaPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(tfEditarPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnNovoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDesativarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnDesativarCliente, btnEditarCliente, btnNovoCliente});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNovoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDesativarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDesativarCliente, btnEditarCliente, btnNovoCliente});

        mainTablePane.addTab("Cliente", jPanel2);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel4.setText("Código");

        jLabel11.setText("Produto");

        tfNovoCodProduto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        tfNovoCodProduto.setMaximumSize(new java.awt.Dimension(6, 20));

        jLabel8.setText("Tipo");

        cbProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Farinha Industrial", "Farinha Domestica", "Farelo", "Big Bag", "" }));
        cbProduto.setSelectedIndex(4);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNovoCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNovoCodProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbProduto, tfNovoCodProduto, tfNovoProduto});

        tblProdutos.setAutoCreateRowSorter(true);
        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Código", "Produto"
            }
        ));
        tblProdutos.setMaximumSize(new java.awt.Dimension(225, 64));
        tblProdutos.setMinimumSize(new java.awt.Dimension(225, 64));
        tblProdutos.setRowHeight(20);
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblProdutos);

        btnEditarProduto.setText("Editar");
        btnEditarProduto.setMaximumSize(new java.awt.Dimension(75, 23));
        btnEditarProduto.setMinimumSize(new java.awt.Dimension(75, 23));
        btnEditarProduto.setPreferredSize(new java.awt.Dimension(75, 23));
        btnEditarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarProdutoActionPerformed(evt);
            }
        });

        btnNovoProduto.setText("Cadastrar");
        btnNovoProduto.setMaximumSize(new java.awt.Dimension(75, 23));
        btnNovoProduto.setMinimumSize(new java.awt.Dimension(75, 23));
        btnNovoProduto.setPreferredSize(new java.awt.Dimension(75, 23));
        btnNovoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoProdutoActionPerformed(evt);
            }
        });

        btnDesativarProduto.setText("Desativar");
        btnDesativarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesativarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnEditarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnDesativarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNovoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDesativarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );

        mainTablePane.addTab("Produto", jPanel3);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Permissões"));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Usuario"));

        novoUsuario.setText("Cadastrar");
        novoUsuario.setBorderPainted(true);
        novoUsuario.setEnabled(false);
        novoUsuario.setFocusable(false);

        editUsuario.setText("Editar");
        editUsuario.setBorderPainted(true);
        editUsuario.setEnabled(false);
        editUsuario.setFocusable(false);

        enableUsuario.setText("Desativar");
        enableUsuario.setBorderPainted(true);
        enableUsuario.setEnabled(false);
        enableUsuario.setFocusable(false);

        eraseUsuario.setText("Deletar");
        eraseUsuario.setBorderPainted(true);
        eraseUsuario.setEnabled(false);
        eraseUsuario.setFocusable(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(novoUsuario)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(eraseUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enableUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(editUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(novoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eraseUsuario))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Produto"));

        novoProduto.setText("Cadastrar");
        novoProduto.setBorderPainted(true);
        novoProduto.setEnabled(false);
        novoProduto.setFocusable(false);

        editProduto.setText("Editar");
        editProduto.setBorderPainted(true);
        editProduto.setEnabled(false);
        editProduto.setFocusable(false);

        enableProduto.setText("Desativar");
        enableProduto.setBorderPainted(true);
        enableProduto.setEnabled(false);
        enableProduto.setFocusable(false);

        eraseProduto.setText("Deletar");
        eraseProduto.setBorderPainted(true);
        eraseProduto.setEnabled(false);
        eraseProduto.setFocusable(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(eraseProduto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editProduto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enableProduto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(novoProduto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(novoProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eraseProduto)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        novoCliente.setText("Cadastrar");
        novoCliente.setBorderPainted(true);
        novoCliente.setEnabled(false);
        novoCliente.setFocusable(false);

        editCliente.setText("Editar");
        editCliente.setBorderPainted(true);
        editCliente.setEnabled(false);
        editCliente.setFocusable(false);

        enableCliente.setText("Desativar");
        enableCliente.setBorderPainted(true);
        enableCliente.setEnabled(false);
        enableCliente.setFocusable(false);

        eraseCliente.setText("Deletar");
        eraseCliente.setBorderPainted(true);
        eraseCliente.setEnabled(false);
        eraseCliente.setFocusable(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(eraseCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enableCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(novoCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(novoCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eraseCliente))
        );

        btnCancelarEditPermissao.setText("Cancelar");
        btnCancelarEditPermissao.setEnabled(false);
        btnCancelarEditPermissao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarEditPermissaoActionPerformed(evt);
            }
        });

        btnSalvarPermissao.setText("Salvar");
        btnSalvarPermissao.setEnabled(false);
        btnSalvarPermissao.setMaximumSize(new java.awt.Dimension(75, 23));
        btnSalvarPermissao.setMinimumSize(new java.awt.Dimension(75, 23));
        btnSalvarPermissao.setPreferredSize(new java.awt.Dimension(75, 23));
        btnSalvarPermissao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarPermissaoActionPerformed(evt);
            }
        });

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Pedido"));

        novoPedido.setText("Cadastrar");
        novoPedido.setBorderPainted(true);
        novoPedido.setEnabled(false);
        novoPedido.setFocusable(false);

        finalizarPedido.setText("Finalizar");
        finalizarPedido.setBorderPainted(true);
        finalizarPedido.setEnabled(false);
        finalizarPedido.setFocusable(false);

        expedirPedido.setText("Expedir");
        expedirPedido.setBorderPainted(true);
        expedirPedido.setEnabled(false);
        expedirPedido.setFocusable(false);

        atenderPedido.setText("Atender");
        atenderPedido.setBorderPainted(true);
        atenderPedido.setEnabled(false);
        atenderPedido.setFocusable(false);

        cancelarPedido.setText("Cancelar");
        cancelarPedido.setBorderPainted(true);
        cancelarPedido.setEnabled(false);
        cancelarPedido.setFocusable(false);

        especialPedido.setText("Especial");
        especialPedido.setEnabled(false);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(expedirPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(atenderPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelarPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(finalizarPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(novoPedido)
                            .addComponent(especialPedido))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(novoPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(finalizarPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expedirPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelarPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atenderPedido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(especialPedido))
        );

        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setEnabled(false);
        jScrollPane8.setFocusable(false);

        tblCargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Cordenador de Manutenção"},
                {"Supervisor de Planejamento Industrial"},
                {"Cordenador de Produção"},
                {"Encarregado de Expedição"},
                {"Monitor de Manutenção"},
                {"Planejador de Manutenção"}
            },
            new String [] {
                "Cargos"
            }
        ));
        tblCargos.setFocusable(false);
        tblCargos.setRowHeight(20);
        tblCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCargosMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblCargos);

        btnLogin.setText("Admin");
        btnLogin.setMaximumSize(new java.awt.Dimension(75, 23));
        btnLogin.setMinimumSize(new java.awt.Dimension(75, 23));
        btnLogin.setPreferredSize(new java.awt.Dimension(75, 23));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvarPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarEditPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSalvarPermissao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelarEditPermissao, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancelarEditPermissao, btnLogin, btnSalvarPermissao});

        mainTablePane.addTab("Adminstrador", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainTablePane, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoUsuarioActionPerformed
        // TODO add your handling code here:
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoCadastrarUsuario()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String senha = "";
        for(char c : tfPassword.getPassword()){
            senha += c;
        }
        Cargo cargo = new Cargo();
        try {
            cargo = mainInterface.obterCargoRemote().pesquisarCargoByNome(cbCargo.getSelectedItem().toString(), myID);
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }
        Usuario toSave = new Usuario();
        toSave.setUsername(tfNovoUsuario.getText());
        toSave.setStatus(Boolean.TRUE);
        toSave.setSessaoId(myID);
        toSave.setSenha(senha);
        toSave.setGma(tfGma.getText().toUpperCase());
        toSave.setCargo(cargo);
        
        Date data = new Date();
        toSave.setUsuario_registro(sessao.getUsuario());
        toSave.setHora_registro(fmt.format(data));
        toSave.setData_registro(data);
        
        toSave.setUsuario_update(sessao.getUsuario());
        toSave.setHora_update(fmt.format(data));
        toSave.setData_update(data);
        try {
            mainInterface.obterUsuarioRemote().cadastrarUsuario(toSave, myID);
            
            loadUsuario();
        } catch (RemoteException ex) {
           ex.printStackTrace();
        }finally{
           sessao.setSession_valida(false);
        }
    }//GEN-LAST:event_btnNovoUsuarioActionPerformed

    private void btnNovoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoProdutoActionPerformed
        // TODO add your handling code here:
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoCadastrarProduto()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }        
        /* utilizar formatted texfild com istancia de NumbrFormat para evitar escrita de letras */
        Produto produto = new Produto();
        produto.setCod_produto(Integer.decode(tfNovoCodProduto.getText()));
        produto.setNome_produto(tfNovoProduto.getText());
        produto.setSessaoId(myID);
        produto.setStatus(Boolean.TRUE);
        if(cbProduto.getSelectedIndex() == 4)
            return;
        produto.setClasse_produto(cbProduto.getSelectedIndex());
        
        Date data = new Date();
        produto.setUsuario_registro(sessao.getUsuario());
        produto.setHora_registro(fmt.format(data));
        produto.setData_registro(data);
        
        produto.setUsuario_update(sessao.getUsuario());
        produto.setHora_update(fmt.format(data));
        produto.setData_update(data);
        try {
            mainInterface.obterProdutoRemote().cadastrarProduto(produto, produto.getSessaoId());
            loadProdutos();
            tfNovoCodProduto.setText("");
            tfNovoProduto.setText("");
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Erro: "+ex.getMessage(),
                        "RemoteException!", JOptionPane.ERROR_MESSAGE);
        }finally{
           sessao.setSession_valida(false);
        }
    }//GEN-LAST:event_btnNovoProdutoActionPerformed

    /* Metodo abaixo carrega privilegios conforme selecionado na tabela
     * ao lado
     */
    private void tblCargosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCargosMouseClicked
        // TODO add your handling code here:
        int row = tblCargos.getSelectedRow();
        if(row == -1)
            return;
                
       eraseCliente.setSelected(lstCargos.get(row).isPermissaoApagarCliente());
       eraseProduto.setSelected(lstCargos.get(row).isPermissaoApagarProduto());
       eraseUsuario.setSelected(lstCargos.get(row).isPermissaoApagarUsuario());
       
       novoCliente.setSelected(lstCargos.get(row).isPermissaoCadastrarCliente());
       novoProduto.setSelected(lstCargos.get(row).isPermissaoCadastrarProduto());
       novoUsuario.setSelected(lstCargos.get(row).isPermissaoCadastrarUsuario());
       novoPedido.setSelected(lstCargos.get(row).isPermissaoCadastrarPedido());
       
       enableCliente.setSelected(lstCargos.get(row).isPermissaoDesativarCliente());
       enableProduto.setSelected(lstCargos.get(row).isPermissaoDesativarProduto());
       enableUsuario.setSelected(lstCargos.get(row).isPermissaoDesativarUsuario());
       
       editCliente.setSelected(lstCargos.get(row).isPermissaoEditarCliente());
       editProduto.setSelected(lstCargos.get(row).isPermissaoEditarProduto());
       editUsuario.setSelected(lstCargos.get(row).isPermissaoEditarUsuario());
       
       finalizarPedido.setSelected(lstCargos.get(row).isPermissaoFinalizarPedido());
       expedirPedido.setSelected(lstCargos.get(row).isPermissaoExpedirPedido());
       atenderPedido.setSelected(lstCargos.get(row).isPermissaoAtenderPedido());
       cancelarPedido.setSelected(lstCargos.get(row).isPermissaoCancelarPedido());
       
       especialPedido.setSelected(lstCargos.get(row).isPermissaoEspecialPedido());
       
    }//GEN-LAST:event_tblCargosMouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        int row = tblCargos.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um cargo",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().getCargo().equals("Administrador")){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        novoUsuario.setEnabled(true);
        editUsuario.setEnabled(true);
        enableUsuario.setEnabled(true);
        eraseUsuario.setEnabled(true);
        
        novoCliente.setEnabled(true);
        editCliente.setEnabled(true);
        enableCliente.setEnabled(true);
        eraseCliente.setEnabled(true);
        
        novoProduto.setEnabled(true);
        editProduto.setEnabled(true);
        eraseProduto.setEnabled(true);
        enableProduto.setEnabled(true);
        
        novoPedido.setEnabled(true);
        finalizarPedido.setEnabled(true);
        expedirPedido.setEnabled(true);
        atenderPedido.setEnabled(true);
        cancelarPedido.setEnabled(true);
        
        especialPedido.setEnabled(true);
        
        mainTablePane.setEnabled(false);
        btnSalvarPermissao.setEnabled(true);
        btnCancelarEditPermissao.setEnabled(true);  
        btnLogin.setEnabled(false);
        tblCargos.setEnabled(false);
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnCancelarEditPermissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarEditPermissaoActionPerformed
        // TODO add your handling code here:
        novoUsuario.setEnabled(false);
        editUsuario.setEnabled(false);
        enableUsuario.setEnabled(false);
        eraseUsuario.setEnabled(false);
        
        novoCliente.setEnabled(false);
        editCliente.setEnabled(false);
        enableCliente.setEnabled(false);
        eraseCliente.setEnabled(false);
        
        novoProduto.setEnabled(false);
        editProduto.setEnabled(false);
        eraseProduto.setEnabled(false);
        enableProduto.setEnabled(false);
        
        novoPedido.setEnabled(false);
        finalizarPedido.setEnabled(false);
        atenderPedido.setEnabled(false);
        expedirPedido.setEnabled(false);
        cancelarPedido.setEnabled(false);
        
        especialPedido.setEnabled(false);
        
        mainTablePane.setEnabled(true);
        btnSalvarPermissao.setEnabled(false);
        btnCancelarEditPermissao.setEnabled(false);  
        btnLogin.setEnabled(true);
        tblCargos.setEnabled(true);
    }//GEN-LAST:event_btnCancelarEditPermissaoActionPerformed

    /*
     * Salva as alterações em cargos
     */
    private void btnSalvarPermissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarPermissaoActionPerformed
        // TODO add your handling code here:
        int row = tblCargos.getSelectedRow();
        Cargo cargo = (Cargo)lstCargos.get(row);
        cargo.setPermissaoApagarCliente(eraseCliente.isSelected());
        cargo.setPermissaoApagarProduto(eraseProduto.isSelected());
        cargo.setPermissaoApagarUsuario(eraseUsuario.isSelected());
        
        cargo.setPermissaoCadastrarCliente(novoCliente.isSelected());
        cargo.setPermissaoCadastrarProduto(novoProduto.isSelected());
        cargo.setPermissaoCadastrarUsuario(novoUsuario.isSelected());
        
        cargo.setPermissaoDesativarCliente(enableCliente.isSelected());
        cargo.setPermissaoDesativarProduto(enableProduto.isSelected());
        cargo.setPermissaoDesativarUsuario(enableUsuario.isSelected());
        
        cargo.setPermissaoEditarCliente(editCliente.isSelected());
        cargo.setPermissaoEditarProduto(editProduto.isSelected());
        cargo.setPermissaoEditarUsuario(editUsuario.isSelected());
        
        cargo.setPermissaoCadastrarPedido(novoPedido.isSelected());
        cargo.setPermissaoFinalizarPedido(finalizarPedido.isSelected());
        cargo.setPermissaoExpedirPedido(expedirPedido.isSelected());
        cargo.setPermissaoAtenderPedido(atenderPedido.isSelected());
        cargo.setPermissaoCancelarPedido(cancelarPedido.isSelected());
        
        cargo.setPermissaoEspecialPedido(especialPedido.isSelected());
        
        try {
            mainInterface.obterCargoRemote().updateCargo(cargo, myID);
        } catch (RemoteException ex) { ex.printStackTrace(); }
        finally{
            novoUsuario.setEnabled(false);
            editUsuario.setEnabled(false);
            enableUsuario.setEnabled(false);
            eraseUsuario.setEnabled(false);
        
            novoCliente.setEnabled(false);
            editCliente.setEnabled(false);
            enableCliente.setEnabled(false);
            eraseCliente.setEnabled(false);
        
            novoProduto.setEnabled(false);
            editProduto.setEnabled(false);
            eraseProduto.setEnabled(false);
            enableProduto.setEnabled(false);
        
            novoPedido.setEnabled(false);
            finalizarPedido.setEnabled(false);
            atenderPedido.setEnabled(false);
            expedirPedido.setEnabled(false);
            cancelarPedido.setEnabled(false);
            
            especialPedido.setEnabled(false);
            
            mainTablePane.setEnabled(true);
            btnSalvarPermissao.setEnabled(false);
            btnCancelarEditPermissao.setEnabled(false);  
            btnLogin.setEnabled(true);
            tblCargos.setEnabled(true);
             sessao.setSession_valida(false);
        }
        
    }//GEN-LAST:event_btnSalvarPermissaoActionPerformed

    private boolean isEditing = false;
    private void btnEditarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdutoActionPerformed
        // TODO add your handling code here:
        row = tblProdutos.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um produto",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoEditarProduto()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        btnDesativarProduto.setText("Salvar");
        btnNovoProduto.setEnabled(false);
        mainTablePane.setEnabled(false);
        tblProdutos.setEnabled(false);
        isEditing = true;
        
        tfNovoCodProduto.setText(tblProdutos.getValueAt(row, 0).toString());
        tfNovoProduto.setText(tblProdutos.getValueAt(row, 1).toString());
    }//GEN-LAST:event_btnEditarProdutoActionPerformed

    private void btnDesativarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesativarProdutoActionPerformed
        // TODO add your handling code here:
        if(isEditing){            
            editingProduto();
        }else{
            row = tblProdutos.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Selecione um produto",
                         "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            ai.autenticarUsuario();
            sessao = ai.getSessao();
            //checa se login é valido
            if(!sessao.getSession_valida()){
                JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
                return;            
            }
            
            // checa se usuario tem permissão para efetuar transação
            if(!sessao.getUsuario().getCargo().isPermissaoDesativarProduto()){
                JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                            "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Produto produto = lstProdutos.get(row);
            produto.setStatus(false);
        
            Date data = new Date();        
            produto.setUsuario_update(sessao.getUsuario());
            produto.setHora_update(fmt.format(data));
            produto.setData_update(data);
            try {
                mainInterface.obterProdutoRemote().updateProduto(produto, produto.getSessaoId());
                loadProdutos();
            } catch (RemoteException ex) { ex.printStackTrace(); }           
            finally{
                sessao.setSession_valida(false);
            }
        }
    }//GEN-LAST:event_btnDesativarProdutoActionPerformed
    private void editingProduto(){
        Produto produto = lstProdutos.get(row);
            produto.setCod_produto(Integer.decode(tfNovoCodProduto.getText()));
            produto.setNome_produto(tfNovoProduto.getText());
            produto.setSessaoId(myID);
            try {
                mainInterface.obterProdutoRemote().updateProduto(produto, produto.getSessaoId());
                loadProdutos();
            } catch (RemoteException ex) { ex.printStackTrace(); }
            
            btnDesativarProduto.setText("Desativar");
            btnNovoProduto.setEnabled(true);
            mainTablePane.setEnabled(true);
            tblProdutos.setEnabled(true);
            isEditing = false;
            tfNovoCodProduto.setText("");
            tfNovoProduto.setText("");
            return;
    }
    
    private void btnEditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsuarioActionPerformed
        // TODO add your handling code here:
        row = tblUsuario.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um Usuario",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoEditarUsuario()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        btnDesativarUsuario.setText("Salvar");
        btnNovoUsuario.setEnabled(false);
        mainTablePane.setEnabled(false);
        tblUsuario.setEnabled(false);
        isEditing = true;
        tfNovoUsuario.setText(tblUsuario.getValueAt(row, 0).toString());
        tfGma.setText(tblUsuario.getValueAt(row,1).toString());
        for(int i = 0; i < cbCargo.getItemCount(); i++){
            if(lstCargos.get(i).getCargoId() == lstUsuario.get(row).getCargo().getCargoId()){
                cbCargo.setSelectedIndex(i);
                break;
            }
        }
    }//GEN-LAST:event_btnEditarUsuarioActionPerformed

    private void btnDesativarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesativarUsuarioActionPerformed
        // TODO add your handling code here:
        if(isEditing){
            editingUsuario();
        }else{
            // função desativar aqui 
            row = tblUsuario.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Selecione um produto",
                         "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            ai.autenticarUsuario();
            sessao = ai.getSessao();
            //checa se login é valido
            if(!sessao.getSession_valida()){
                JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
                return;            
            }
            
            // checa se usuario tem permissão para efetuar transação
            if(!sessao.getUsuario().getCargo().isPermissaoDesativarUsuario()){
                JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                            "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Usuario usuario = lstUsuario.get(row);
            usuario.setStatus(false);
        
            Date data = new Date();        
            usuario.setUsuario_update(sessao.getUsuario());
            usuario.setHora_update(fmt.format(data));
            usuario.setData_update(data);
            try {
                mainInterface.obterUsuarioRemote().updateUsuario(usuario, usuario.getSessaoId());
                loadUsuario();
            } catch (RemoteException ex) { ex.printStackTrace(); } 
            finally{
                sessao.setSession_valida(false);
            }
        }
        
    }//GEN-LAST:event_btnDesativarUsuarioActionPerformed
    private void editingUsuario(){
        
            Usuario usuario = (Usuario)lstUsuario.get(row);
            String senha = "";
            for(char c : tfPassword.getPassword()){
                senha += c;
            }
            Cargo cargo = new Cargo();
            try {
                cargo = mainInterface.obterCargoRemote().pesquisarCargoByNome(cbCargo.getSelectedItem().toString(), myID);
            } catch (RemoteException ex) { ex.printStackTrace(); }
            
            usuario.setUsername(tfNovoUsuario.getText());
            usuario.setSessaoId(myID);
            usuario.setSenha(senha);
            usuario.setGma(tfGma.getText().toUpperCase());
            usuario.setCargo(cargo);
            
            Date data = new Date();
            usuario.setUsuario_update(sessao.getUsuario());
            usuario.setHora_update(fmt.format(data));
            usuario.setData_update(data);
            try {                
                mainInterface.obterUsuarioRemote().updateUsuario(usuario, myID);
                loadUsuario();
            } catch (RemoteException ex) { ex.printStackTrace(); }
            finally {
                 btnDesativarUsuario.setText("Desativar");
                 btnNovoUsuario.setEnabled(true);
                 mainTablePane.setEnabled(true);
                 tblUsuario.setEnabled(true);
                 isEditing = false;
                 tfNovoUsuario.setText(lstUsuario.get(row).getUsername());
            }
        
    }
    
    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        row = tblClientes.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um produto",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoEditarCliente()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        mainTablePane.setEnabled(false);
        tblClientes.setEnabled(false);
        isEditing = true;
        btnNovoCliente.setEnabled(false);
        btnDesativarCliente.setText("Salvar");
        tfNovoCliente.setText(lstCliente.get(row).getNome_cliente());
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnDesativarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesativarClienteActionPerformed
        // TODO add your handling code here:
        if(isEditing){
            editingCliente();
        }else{
            // desativar função
            row = tblClientes.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(this, "Selecione um produto",
                         "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            ai.autenticarUsuario();
            sessao = ai.getSessao();
            //checa se login é valido
            if(!sessao.getSession_valida()){
                JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
                return;            
            }
            
            // checa se usuario tem permissão para efetuar transação
            if(!sessao.getUsuario().getCargo().isPermissaoDesativarCliente()){
                JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                            "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Cliente cliente = lstCliente.get(row);
            cliente.setStatus(false);
        
            Date data = new Date();        
            cliente.setUsuario_update(sessao.getUsuario());
            cliente.setHora_update(fmt.format(data));
            cliente.setData_update(data);
            try {
                mainInterface.obterClienteRemote().updateCliente(cliente, cliente.getSessaoId());
                loadClientes();
            } catch (RemoteException ex) { ex.printStackTrace(); } 
            finally{
                sessao.setSession_valida(false);
            }
        }
    }//GEN-LAST:event_btnDesativarClienteActionPerformed
    private void editingCliente(){
        Cliente cliente = lstCliente.get(row);
            cliente.setNome_cliente(tfNovoCliente.getText());
            try {
                mainInterface.obterClienteRemote().updateCliente(cliente, myID);
            } catch (RemoteException ex) { ex.printStackTrace(); }
            finally {
                mainTablePane.setEnabled(true);
                tblClientes.setEnabled(true);
                isEditing = false;
                btnNovoCliente.setEnabled(true);
                btnDesativarCliente.setText("Desativar");
                loadClientes();
            }
    }
    
    /* 
     * Adiciona um novo cliente ao banco de dados
     */
    private void btnNovoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoClienteActionPerformed
        // TODO add your handling code here:
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }
            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoCadastrarCliente()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Cliente cliente = new Cliente();
        cliente.setNome_cliente(tfNovoCliente.getText());
        cliente.setSessaoId(myID);
        cliente.setStatus(true);
        if(!tfEditarPlaca.getText().equalsIgnoreCase("   -    ")){
            cliente.setPlaca(tfEditarPlaca.getText());
        }
        Date data = new Date();
        cliente.setUsuario_registro(sessao.getUsuario());
        cliente.setHora_registro(fmt.format(data));
        cliente.setData_registro(data);
        
        cliente.setUsuario_update(sessao.getUsuario());
        cliente.setHora_update(fmt.format(data));
        cliente.setData_update(data);
        try {
            mainInterface.obterClienteRemote().cadastrarCliente(cliente, myID);
        } catch (RemoteException ex) { ex.printStackTrace(); }
        finally {
            sessao.setSession_valida(false); 
            tfNovoCliente.setText("");
            tfEditarPlaca.setText("   -    ");
            loadClientes();
        }
    }//GEN-LAST:event_btnNovoClienteActionPerformed

    /* 
     * 1 click Mostra placa do cliente selecionado
     * 2 clicks mostra dialog com log do registro 
     */
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 1){
                   
            int row = tblClientes.getSelectedRow();
            Cliente c = lstCliente.get(row);
            Iterator it = c.getPlaca().iterator();
            arrayPlacas.clear();
            while(it.hasNext())
                arrayPlacas.add(new String[]{it.next().toString()});
            tblPlacas.setModel(new MyTableModel(arrayPlacas,col1,edit1));
            
            // se duplo click chama Dialog com dados do registro
        }else if(evt.getClickCount() == 2){
            if(evt.getClickCount() == 2){
                int r = tblClientes.getSelectedRow();
                if(r != -1){
                    Log log = lstCliente.get(r);
                    try{
                        lblHoraDoRegistro.setText(log.getHora_registro());
                        lblDataDoRegistro.setText(log.getData_registro().toString());
                        lblUsuarioDoRegistro.setText(log.getUsuario_registro().getUsername());
                        lblHoraUpdateDoRegistro.setText(log.getHora_update());
                        lblDataUpdateDoRegistro.setText(log.getData_update().toString());
                        lblUsuarioUpdateDoRegistro.setText(log.getUsuario_update().getUsername());
                        jLog.pack();
                        jLog.setVisible(Boolean.TRUE);
                    }catch(NullPointerException ne){}
                }
            }
        }
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnNovaPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaPlacaActionPerformed
        // TODO add your handling code here:
        row = tblClientes.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um Cliente",
                        "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(tfEditarPlaca.getText().equalsIgnoreCase("   -    "))
                return;
        
        ai.autenticarUsuario();
        sessao = ai.getSessao();
        //checa se login é valido
        if(!sessao.getSession_valida()){
            JOptionPane.showMessageDialog(this, sessao.getMsg(), "Erro!", JOptionPane.INFORMATION_MESSAGE);
            return;            
        }            
        // checa se usuario tem permissão para efetuar transação
        if(!sessao.getUsuario().getCargo().isPermissaoCadastrarCliente()){
            JOptionPane.showMessageDialog(this, "Você não tem permissão para efetuar essa operação.",
                        "Permissão negada!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try{
            Cliente c = lstCliente.get(row);
            c.setPlaca(tfEditarPlaca.getText());
            Date data = new Date();        
            c.setUsuario_update(sessao.getUsuario());
            c.setHora_update(fmt.format(data));
            c.setData_update(data);
            c.setUsuario_update(sessao.getUsuario());
            mainInterface.obterClienteRemote().updateCliente(c, myID);
            tfEditarPlaca.setText("   -    ");
            loadClientes();
        }catch(Exception e){ e.printStackTrace(); }
    }//GEN-LAST:event_btnNovaPlacaActionPerformed

     /* 
      * 2 clicks mostra dialog com log do registro 
      */
    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int r = tblUsuario.getSelectedRow();
            if(r != -1){
                Log log = lstUsuario.get(r);
                System.out.println(r);
                try{
                    lblHoraDoRegistro.setText(log.getHora_registro());
                    lblDataDoRegistro.setText(log.getData_registro().toString());
                    lblUsuarioDoRegistro.setText(log.getUsuario_registro().getUsername());
                    lblHoraUpdateDoRegistro.setText(log.getHora_update());
                    lblDataUpdateDoRegistro.setText(log.getData_update().toString());
                    lblUsuarioUpdateDoRegistro.setText(log.getUsuario_update().getUsername());
                    jLog.pack();
                    jLog.setVisible(Boolean.TRUE);
                }catch(NullPointerException ne){}
            }
        }
    }//GEN-LAST:event_tblUsuarioMouseClicked
     /* 
      * 2 clicks mostra dialog com log do registro 
      */
    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2){
            int r = tblProdutos.getSelectedRow();
            if(r != -1){
                Log log = lstProdutos.get(r);
                System.out.println(r);
                try{
                    lblHoraDoRegistro.setText(log.getHora_registro());
                    lblDataDoRegistro.setText(log.getData_registro().toString());
                    lblUsuarioDoRegistro.setText(log.getUsuario_registro().getUsername());
                    lblHoraUpdateDoRegistro.setText(log.getHora_update());
                    lblDataUpdateDoRegistro.setText(log.getData_update().toString());
                    lblUsuarioUpdateDoRegistro.setText(log.getUsuario_update().getUsername());
                    jLog.pack();
                    jLog.setVisible(Boolean.TRUE);
                }catch(NullPointerException ne){}
            }
        }
    }//GEN-LAST:event_tblProdutosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox atenderPedido;
    private javax.swing.JButton btnCancelarEditPermissao;
    private javax.swing.JButton btnDesativarCliente;
    private javax.swing.JButton btnDesativarProduto;
    private javax.swing.JButton btnDesativarUsuario;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarProduto;
    private javax.swing.JButton btnEditarUsuario;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnNovaPlaca;
    private javax.swing.JButton btnNovoCliente;
    private javax.swing.JButton btnNovoProduto;
    private javax.swing.JButton btnNovoUsuario;
    private javax.swing.JButton btnSalvarPermissao;
    private javax.swing.JCheckBox cancelarPedido;
    private javax.swing.JComboBox cbCargo;
    private javax.swing.JComboBox cbProduto;
    private javax.swing.JCheckBox editCliente;
    private javax.swing.JCheckBox editProduto;
    private javax.swing.JCheckBox editUsuario;
    private javax.swing.JCheckBox enableCliente;
    private javax.swing.JCheckBox enableProduto;
    private javax.swing.JCheckBox enableUsuario;
    private javax.swing.JCheckBox eraseCliente;
    private javax.swing.JCheckBox eraseProduto;
    private javax.swing.JCheckBox eraseUsuario;
    private javax.swing.JCheckBox especialPedido;
    private javax.swing.JCheckBox expedirPedido;
    private javax.swing.JCheckBox finalizarPedido;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JDialog jLog;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblDataDoRegistro;
    private javax.swing.JLabel lblDataUpdateDoRegistro;
    private javax.swing.JLabel lblHoraDoRegistro;
    private javax.swing.JLabel lblHoraUpdateDoRegistro;
    private javax.swing.JLabel lblUsuarioDoRegistro;
    private javax.swing.JLabel lblUsuarioUpdateDoRegistro;
    private javax.swing.JTabbedPane mainTablePane;
    private javax.swing.JCheckBox novoCliente;
    private javax.swing.JCheckBox novoPedido;
    private javax.swing.JCheckBox novoProduto;
    private javax.swing.JCheckBox novoUsuario;
    private javax.swing.JTable tblCargos;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblPlacas;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JFormattedTextField tfEditarPlaca;
    private javax.swing.JFormattedTextField tfGma;
    private javax.swing.JTextField tfNovoCliente;
    private javax.swing.JFormattedTextField tfNovoCodProduto;
    private javax.swing.JTextField tfNovoProduto;
    private javax.swing.JTextField tfNovoUsuario;
    private javax.swing.JPasswordField tfPassword;
    // End of variables declaration//GEN-END:variables
}
