/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.common;

import br.com.estoque.entity.RoloEntity;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alirio
 */
public class RoloTableModel extends AbstractTableModel{

    private final int COL_SERIAL    = 0;
    private final int COL_RAIACAO   = 1;
    private final int COL_PASSAGEM  = 2;
    private final int COL_LOCAL     = 3;
    private List<RoloEntity> roloEntity;

    public RoloTableModel() {
        roloEntity = new ArrayList<>();
    }
    public RoloTableModel(List<RoloEntity> lista) {  
        this();          
        roloEntity.addAll(lista);
    }
    
    @Override
    public int getRowCount() { return roloEntity.size(); }
    @Override
    public int getColumnCount() { return 4; }
    @Override
    public String getColumnName(int column) {
        switch(column){
            case COL_SERIAL:
                return "Serial";
            case COL_RAIACAO:
                return "Raiação";
            case COL_PASSAGEM:
                return "Passagem";
            case COL_LOCAL:
                return "Localização";
            default:
                return "Fora do ranger";             
        }
    }    
    @Override  
    public Class<?> getColumnClass(int columnIndex) { 
        switch(columnIndex){
            case COL_SERIAL:
                return String.class;
            case COL_RAIACAO:
                return String.class;
            case COL_PASSAGEM:
                return String.class;
            case COL_LOCAL:
                return String.class;
            default:
                return Object.class;       
        }
    }    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RoloEntity r = roloEntity.get(rowIndex);
        switch(columnIndex){
            case COL_SERIAL:
                return r.getSerial();
            case COL_RAIACAO:
                return r.getRaiacao();
            case COL_PASSAGEM:
                return r.getPassagem();
            case COL_LOCAL:
                return r.getLocalizacao();
            default:
                return "";
        }
    }   
    @Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        switch(columnIndex){
            case COL_SERIAL:
                return Boolean.FALSE;
            case COL_RAIACAO:
                return Boolean.FALSE;
            case COL_PASSAGEM:
                return Boolean.FALSE;
            case COL_LOCAL:
                return Boolean.FALSE;
            default:
                return Boolean.FALSE;
        }
    }   
    @Override  
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) { 
        RoloEntity r = roloEntity.get(rowIndex);
        switch(columnIndex){
            case COL_SERIAL:
                r.setSerial((String)aValue);
                break;
            case COL_RAIACAO:
                r.setRaiacao((String)aValue);
                break;
            case COL_PASSAGEM:
                r.setPassagem((String)aValue);
                break;
            case COL_LOCAL:
                r.setLocalizacao((String)aValue);
                break;
        }
        fireTableDataChanged();  
    }
    
    public void adicionarArryRoloEntity(List<RoloEntity> lstRolos){
        int rowI = roloEntity.size();
        roloEntity.addAll(lstRolos);
        int rowF = roloEntity.size();
        fireTableRowsInserted(rowI, rowF);
    }
    public void adicionarRolo(RoloEntity re){
        roloEntity.add(re);
        int linha = roloEntity.size()-1;
	fireTableRowsInserted(linha,linha);
    }
    public void removerLinha(int r){
        roloEntity.remove(r);
        fireTableRowsDeleted(r,r);
    }
    public void removerRoloEntity(RoloEntity re){
        roloEntity.remove(re);
        fireTableDataChanged();
    }
    public void limparTabela(){
        roloEntity.clear();
        fireTableDataChanged();
    }
    public RoloEntity getRoloEntity(int rowIndex){
        RoloEntity p = roloEntity.get(rowIndex);
        return p;       
    }
    public Boolean existe(RoloEntity rolo){ return roloEntity.contains(rolo); }
    public List<RoloEntity> getListRolosEntity(){ return roloEntity; }
    public void setRoloLocalizacao(String l, int r){ roloEntity.get(r).setLocalizacao(l); }
}
