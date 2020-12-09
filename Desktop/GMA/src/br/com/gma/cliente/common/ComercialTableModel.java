/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.cliente.common;

import br.com.gma.server.common.entity.Pedido;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alirio
 */
public class ComercialTableModel extends AbstractTableModel{
    
    private final int COL_PEDIDO    = 0;//INT
    private final int COL_PRODUTO   = 1;//STRING  
    private final int COL_QUANT     = 2;//FLOAT  
    private final int COL_CLIENTE   = 3;//STRING  
    private final int COL_PLACA     = 4;//STRING  
    private final int COL_HORA      = 5;//STRING  
    private final int COL_CARGA     = 6;//INTEGER  
    private final int COL_STATUS    = 7;//STRING
    private final SimpleDateFormat sdf;
    private List<Pedido> pedidos;

    public ComercialTableModel(){
        pedidos = new ArrayList<Pedido>();
        this.sdf = new SimpleDateFormat("dd/MMM  hh:mm");
    }
    public ComercialTableModel(List<Pedido> lista) {  
        this();          
        pedidos.addAll(lista);
    }
    @Override
    public int getRowCount() { return pedidos.size(); }
    @Override
    public int getColumnCount() { return 8; }
    @Override  
    public String getColumnName(int column) {          
        switch(column){
            case COL_PEDIDO:
                return "#";
            case COL_PRODUTO:
                return "Produto";
            case COL_QUANT:
                return "Quant.";
            case COL_CLIENTE:
                return "Cliente";
            case COL_PLACA:
                return "Placa";
            case COL_HORA:
                return "Hora";
            case COL_CARGA:
                return "Carga";
            case COL_STATUS:
                return "Status";
        }
        return "";  
    }  
    @Override  
    public Class<?> getColumnClass(int columnIndex) {    
        switch(columnIndex){
            case COL_PEDIDO:
                return Integer.class;
            case COL_PRODUTO:
                return String.class;
            case COL_QUANT:
                return Float.class;
            case COL_CLIENTE:
                return String.class;
            case COL_PLACA:
                return String.class;
            case COL_HORA:
                return String.class;
            case COL_CARGA:
                return Integer.class;
            case COL_STATUS:
                return String.class;
        }
        return super.getColumnClass(columnIndex);  
    } 

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pedido p = pedidos.get(rowIndex);
        switch(columnIndex){
            case COL_PEDIDO:
                return p.getCod_pedido();
            case COL_PRODUTO:
                return p.getProduto().getNome_produto();
            case COL_QUANT:
                return p.getQuant();
            case COL_CLIENTE:
                return p.getCliente().getNome_cliente();
            case COL_PLACA:
                return p.getPlaca_do_pedido();
            case COL_HORA:
                return sdf.format(p.getPedido_pendente());
            case COL_CARGA:
                return p.getCarga();
            case COL_STATUS:
                return p.getStatus_pedido();
        }
        return "";
    }
    @Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        return /*(columnIndex == COL_CANCEL)*/false;  
    }
    /*@Override  
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {  
        Pedido p = pedidos.get(rowIndex);
        if(columnIndex == COL_CANCEL){
            p.setToCancel((Boolean)aValue);
        }  
        fireTableDataChanged();  
    } */
    public void adicionarArryPedidos(List<Pedido> lstPedidos){
        int rowI = pedidos.size();
        pedidos.addAll(lstPedidos);
        int rowF = pedidos.size()-1;
        fireTableRowsInserted(rowI, rowF);
    }
    public void adicionarPedido(Pedido pe){
        pedidos.add(pe);
        int linha = pedidos.size()-1;
	fireTableRowsInserted(linha,linha);
    }
    public void removerLinha(int r){
        pedidos.remove(r);
        fireTableRowsDeleted(r,r);
    }
    public void removerPedido(Pedido pe){
        pedidos.remove(pe);
        fireTableDataChanged();
    }
}
