/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.cliente.common;

import static br.com.gma.server.common.entity.CONSTANTES.BIG_BAG;
import static br.com.gma.server.common.entity.CONSTANTES.DOMESTICA;
import static br.com.gma.server.common.entity.CONSTANTES.FARELO;
import static br.com.gma.server.common.entity.CONSTANTES.INDUSTRIAL;
import br.com.gma.server.common.entity.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alirio
 */
public class ProdutoTableModel extends AbstractTableModel{
    
    private final int COL_PRODUTO     = 0; //STRING  
    private final int COL_TIPO        = 1; //STRING  
    private final int COL_EST_ATUL    = 2; // FLOAT
    private final int COL_EST_FINAL   = 3; // FLOAT
    private final int COL_AJUST_EST   = 4; // FLOAT
    
    private List<Produto> produtos;

    public ProdutoTableModel(){
        produtos = new ArrayList<Produto>();
    }
    public ProdutoTableModel(List<Produto> lista) {  
        this();          
        produtos.addAll(lista);
    }
    @Override
    public int getRowCount() { return produtos.size(); }
    @Override
    public int getColumnCount() { return 5; }
    @Override  
    public String getColumnName(int column) {          
        switch(column){
            case COL_PRODUTO:
                return "Produto";
            case COL_TIPO:
                return "Tipo";
            case COL_EST_ATUL:
                return "Estoque Atual";
            case COL_AJUST_EST:
                return "Estoque desejado.";
            case COL_EST_FINAL:
                return "Estoque final";
        }
        return "";  
    }  
    @Override  
    public Class<?> getColumnClass(int columnIndex) {    
        switch(columnIndex){
            case COL_PRODUTO:
                return String.class;
            case COL_TIPO:
                return String.class;
            case COL_EST_ATUL:
                return Float.class;
            case COL_EST_FINAL:
                return Float.class;
            case COL_AJUST_EST:
                return Float.class;
        }
        return super.getColumnClass(columnIndex);  
    } 

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto p = produtos.get(rowIndex);
        switch(columnIndex){
            case COL_PRODUTO:
                return p.getNome_produto();
            case COL_TIPO:
                switch(p.getClasse_produto()){
                    case INDUSTRIAL:
                        return "IND";
                    case DOMESTICA:
                        return "DOM";
                    case BIG_BAG:
                        return "BAG";
                    case FARELO:
                        return "FAR";
                    default:
                        return "???";
                }
            case COL_EST_ATUL:
                return p.getEstoque();
            case COL_EST_FINAL:
                return p.getEst_final();
            case COL_AJUST_EST:
                return p.getEst_desj();
        }
        return "";
    }
    @Override  
    public boolean isCellEditable(int rowIndex, int columnIndex) {  
        switch(columnIndex){
            case COL_PRODUTO:
                return false;
            case COL_TIPO:
                return false;
            case COL_EST_ATUL:
                return false;
            case COL_EST_FINAL:
                return false;
            case COL_AJUST_EST:
                return true;
            default:
                return false;
        }
    }
    @Override  
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {  
        Produto p = produtos.get(rowIndex);
        switch(columnIndex){
            case COL_PRODUTO:
                p.setNome_produto((String)aValue);
                break;
            case COL_TIPO:
                p.setClasse_produto((Integer)aValue);
                break;
            case COL_EST_ATUL:
                p.setEstoque((Float)aValue);
                break;
            case COL_EST_FINAL:
                p.setEst_final((Float)aValue);
                break;
            case COL_AJUST_EST:
                p.setEst_desj((Float)aValue);
                break;                
        } 
        fireTableDataChanged();  
    } 
    public void adicionarArryProduto(List<Produto> lstProdutos){
        int rowI = produtos.size();
        produtos.addAll(lstProdutos);
        int rowF = produtos.size()-1; 
        fireTableRowsInserted(rowI, rowF);
    }
    public void adicionarProduto(Produto pe){
        produtos.add(pe);
        int linha = produtos.size()-1;
	fireTableRowsInserted(linha,linha);
    }
    public void removerLinha(int r){
        produtos.remove(r);
        fireTableRowsDeleted(r,r);
    }
    public void removerProduto(Produto pe){
        produtos.remove(pe);
        fireTableDataChanged();
    }
    public Produto getProduto(int rowIndex){
        Produto p = produtos.get(rowIndex);
        return p;       
    }
}
