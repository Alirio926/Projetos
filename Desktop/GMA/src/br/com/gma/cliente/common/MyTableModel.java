/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.common;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Administrador
 */
public class MyTableModel extends AbstractTableModel{
        private ArrayList linhas = null;
	private String [] colunas = null;
	private boolean [] colsEdicao;
	
	/**
	 * Contrutor para a classe, recebendo os dados a serem exibidos
	 * e as colunas que devem ser criadas.
	 * @param dados
	 * @param colunas
	 */
	public MyTableModel(ArrayList dados, String[] colunas, boolean [] edicao){
		setLinhas(dados);
		setColunas(colunas);
		colsEdicao = edicao;
	}

	/**
	 * Retorna o numero de colunas no modelo
	 * @see javax.swing.table.MyTableModel#getColumnCount()
	 */
    @Override
	public int getColumnCount() {return getColunas().length;}

	/**
	 * Retorna o numero de linhas existentes no modelo
	 * @see javax.swing.table.MyTableModel#getRowCount()
	 */
    @Override
	public int getRowCount() {return getLinhas().size();}

	/**
	 * Obtem o valor na linha e coluna
	 * @see javax.swing.table.MyTableModel#getValueAt(int, int)
	 */
    @Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// Obtem a linha, que é uma String []
		String [] linha = (String [])getLinhas().get(rowIndex);
		// Retorna o objeto que esta na coluna
		return linha[columnIndex];
	}

	public String[] getColunas() {return colunas;}
	public ArrayList getLinhas() {return linhas;}
	public void setColunas(String[] strings) {colunas = strings;}
	public void setLinhas(ArrayList list) {linhas = list;}
	
	/**
	 * Seta o valor na linha e coluna
	 * @see javax.swing.table.MyTableModel#setValueAt(java.lang.Object, int, int)
	 */
    @Override
	public void setValueAt(Object value, int row, int col){
		// Obtem a linha, que é uma String []
		String [] linha = (String [])getLinhas().get(row);
		// Altera o conteudo no indice da coluna passado
                if(col == getColumnCount())
                    linha[col] = (String)((Boolean)value?"SIM":"NÃO");
                else
                    linha[col] = (String)value;
		// dispara o evento de celula alterada
		fireTableCellUpdated(row,col);
	}
	/**
	 * Retorna se a celula pode ser editada
	 * @see javax.swing.table.MyTableModel#isCellEditable(int, int)
	 */
    @Override
	public boolean isCellEditable(int row, int col){
		return colsEdicao[col];
	}
	
	/**
	 * Adiciona o array na linha
	 */
	public void addRow( String [] dadosLinha){
		getLinhas().add(dadosLinha);
		// Informa a jtable de que houve linhas incluidas no modelo
		// COmo adicionamos no final, pegamos o tamanho total do modelo
		// menos 1 para obter a linha incluida.
		int linha = getLinhas().size()-1;
		fireTableRowsInserted(linha,linha);
	}
	
	/**
	 * Remove a linha pelo indice informado
	 * @param row
	 */
	public void removeRow(int row){
		getLinhas().remove(row);
		// informa a jtable que houve dados deletados passando a 
		// linha reovida
		fireTableRowsDeleted(row,row);
	}
	
	/**
	 * Remove a linha pelo valor da coluna informada
	 * @param val
	 * @param col
	 * @return
	 */
	public boolean removeRow(String val, int col){
		// obtem o iterator
		Iterator i = getLinhas().iterator();
		int linha = 0;
		// Faz um looping em cima das linhas
		while(i.hasNext()){
			// Obtem as colunas da linha atual
			String[] linhaCorrente = (String[])i.next();
			linha++;
			// compara o conteudo String da linha atual na coluna desejada
			// com o valor informado
			if( linhaCorrente[col].equals(val) ){
				getLinhas().remove(linha);
				// informa a jtable que houve dados deletados passando a 
				// linha removida
				fireTableRowsDeleted(linha,linha);
				return true;				
			}
		}
		// Nao encontrou nada
		return false;
	}
	
	/**
	 * Retorna o nome da coluna.
	 * @see javax.swing.table.MyTableModel#getColumnName(int)
	 */
    @Override
	public String getColumnName(int col){
            return getColunas()[col];
	}
         @Override  
    public Class<?> getColumnClass(int columnIndex) {    
        if (columnIndex == getColumnCount()){  
            return Boolean.class;  
        }          
        return super.getColumnClass(columnIndex);  
    }
}
