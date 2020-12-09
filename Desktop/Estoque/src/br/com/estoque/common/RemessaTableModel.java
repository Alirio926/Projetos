/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.common;

import br.com.estoque.entity.RemessaEntity;
import br.com.estoque.entity.RoloEntity;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alirio
 */
public class RemessaTableModel  extends AbstractTableModel{
    
    private final int COL_DATA      = 0;
    private final int COL_REMESSA   = 1;
    private final int COL_NF        = 2;
    private final int COL_DESTINO   = 3;
    private final int COL_QUANT     = 4;
    private final int COL_QUAT_PEN  = 5;
    private List<RemessaEntity> dataTable;
    private SimpleDateFormat sdf;

    public RemessaTableModel(List<RemessaEntity> dataTable) {
        this.dataTable.addAll(dataTable);
        sdf = new SimpleDateFormat("dd/MMM/yyyy");
    }
    public RemessaTableModel() {
        dataTable = new ArrayList<>();
        sdf = new SimpleDateFormat("dd/MMM/yyyy");
    }
    public RemessaTableModel(Collection<RemessaEntity> re) {
        dataTable = new ArrayList<>(re);
        sdf = new SimpleDateFormat("dd/MMM/yyyy");
    }
    @Override
    public int getRowCount() { return dataTable.size(); }
    @Override
    public int getColumnCount() { return 6; }
    @Override
    public String getColumnName(int column) {
        switch(column){
            case COL_DATA:
                return "Data envio";
            case COL_REMESSA:
                return "Remessa";
            case COL_NF:
                return "Nota fiscal";
            case COL_DESTINO:
                return "Destino";
            case COL_QUANT:
                return "Quant.";
            case COL_QUAT_PEN:
                return "Pendentes";
            default:
                return "Fora do ranger";
        }
    }
    @Override
     public Class<?> getColumnClass(int columnIndex) { 
        switch(columnIndex){
            case COL_DATA:
                return String.class;
            case COL_REMESSA:
                return Long.class;
            case COL_NF:
                return String.class;
            case COL_DESTINO:
                return String.class;
            case COL_QUANT:
                return Integer.class;
            case COL_QUAT_PEN:
                return Integer.class;
            default:
                return Object.class;
        }
     }
     
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RemessaEntity entity = dataTable.get(rowIndex);
        switch(columnIndex){
            case COL_DATA:
                return sdf.format(entity.getDataEnvio());
            case COL_REMESSA:
                return entity.getNumRemessa();
            case COL_NF:
                return entity.getNumNotaFiscal();
            case COL_DESTINO:
                return entity.getDestino();
            case COL_QUANT:
                return entity.getQtdRolos();
            case COL_QUAT_PEN:
                return entity.getQtdRolosPendentes();
            default:
                return "";
        }
    }
    @Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        return Boolean.FALSE;
    }
    public void adicionarArryRemessaEntity(List<RemessaEntity> lstRemessas){
        int rowI = dataTable.size();
        dataTable.addAll(dataTable);
        int rowF = dataTable.size();
        fireTableRowsInserted(rowI, rowF);               
    }
    public void adicionarRemessa(RemessaEntity re){
        dataTable.add(re);
        int linha = dataTable.size()-1;
	fireTableRowsInserted(linha,linha);
    }
    public void removerLinha(int r){
        dataTable.remove(r);
        fireTableRowsDeleted(r,r);
    }
    public void removerRemessaEntity(RemessaEntity re){
        dataTable.remove(re);
        fireTableDataChanged();
    }
    public void limparTabela(){
        dataTable.clear();
        fireTableDataChanged();
    }
    public RemessaEntity getRemessa(int i){
        return dataTable.get(i);
    }
}
