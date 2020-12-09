    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.relatorios;

import br.com.gma.cliente.common.ProgressBarInterface;
import br.com.gma.relatorios.model.CarregamentoEntity;
import br.com.gma.relatorios.model.FarinhasEntity;
import br.com.gma.relatorios.model.ProducaoEntity;
import java.util.List;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Alirio
 */
public class Carregamento {
    private ProgressBarInterface inter;
    private final String path; //Caminho base
    private final String path2; //Caminho base
    private final String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
    //Recupera os caminhos para que a classe possa encontrar os relatórios
    public Carregamento(ProgressBarInterface in, String p, String p2) {
        inter = in;
        this.path2=p2;
    	this.path = p;//System.getProperty("user.dir");//new File(".").getAbsolutePath()/*D:/GMA/build/classes/";*/this.getClass().getClassLoader().getResource("").getPath();        
    	this.pathToReportPackage = this.path + "\\br\\com\\gma\\relatorios\\jasper\\";    	
    }
    public void imprimir(List<CarregamentoEntity> clientes, int t) throws Exception	
    {
        inter.setPercent(t, 35, "Compilando relatorio");
    	JasperReport report = JasperCompileManager.compileReport(/*this.getPathToReportPackage() + */path);
        inter.setPercent(t, 40, "Preenchendo com arraylist de dados");
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(clientes));
       inter.setPercent(t, 50, "Gerando visualização");
        JDialog viewer = new JDialog(new javax.swing.JFrame(),"Visualização do Relatório", true); 
        inter.setPercent(t, 60, "Definindo Tamanho");
        viewer.setSize(800,600); 
        inter.setPercent(t, 70, "Setando localização");
        viewer.setLocationRelativeTo(null); 
        inter.setPercent(t, 80, "Print");
        JasperViewer jrViewer = new JasperViewer(print, true); 
        inter.setPercent(t, 90,"");
        viewer.getContentPane().add(jrViewer.getContentPane()); 
        inter.setPercent(t, 100, "Concluido!");
        viewer.setVisible(true); 
        inter.setStateButton(t, true);
        inter.setPercent(t,0,"");        
 	//JasperExportManager.exportReportToPdfFile(print, "c:/Relatorio_de_Carregamento.pdf");		
    }
    public void imprimirProducao(List<ProducaoEntity> clientes, int t) throws Exception	
    {
        inter.setPercent(t, 35, "Compilando relatorio");
    	JasperReport report = JasperCompileManager.compileReport(/*this.getPathToReportPackage() + */path2);
        inter.setPercent(t, 40, "Preenchendo com arraylist de dados");
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(clientes));
       inter.setPercent(t, 50, "Gerando visualização");
        JDialog viewer = new JDialog(new javax.swing.JFrame(),"Visualização do Relatório", true); 
        inter.setPercent(t, 60, "Definindo Tamanho");
        viewer.setSize(800,600); 
        inter.setPercent(t, 70, "Setando localização");
        viewer.setLocationRelativeTo(null); 
        inter.setPercent(t, 80, "Print");
        JasperViewer jrViewer = new JasperViewer(print, true); 
        inter.setPercent(t, 90,"");
        viewer.getContentPane().add(jrViewer.getContentPane()); 
        inter.setPercent(t, 100, "Concluido!");
        viewer.setVisible(true); 
        inter.setStateButton(t, true);
        inter.setPercent(t,0,"");        
 	//JasperExportManager.exportReportToPdfFile(print, "c:/Relatorio_de_Carregamento.pdf");		
    }
    public void imprimirMoagem(List<FarinhasEntity> farinhas, int count)throws Exception{
        inter.setPercent(0, 35, "Compilando relatorio");
        JasperReport report = JasperCompileManager.compileReport(/*this.getPathToReportPackage() + */path);
        inter.setPercent(0,40, "Preenchendo com arraylist de dados");
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(farinhas));
        inter.setPercent(0,50, "Gerando visualização");
        JDialog viewer = new JDialog(new javax.swing.JFrame(),"Visualização do Relatório", true); 
        inter.setPercent(0,60, "Definindo Tamanho");
        viewer.setSize(800,600); 
        inter.setPercent(0,70, "Setando localização");
        viewer.setLocationRelativeTo(null); 
        inter.setPercent(0,80, "Print");
        JasperViewer jrViewer = new JasperViewer(print, true); 
        inter.setPercent(0,90,"");
        viewer.getContentPane().add(jrViewer.getContentPane()); 
        inter.setPercent(0,100, "Concluido!");
        viewer.setVisible(true); 
        inter.setStateButton(0, true);
        inter.setPercent(0,0,"");
    }    
    public String getPathToReportPackage() {
	return this.pathToReportPackage;
    }
    public String getPath() {
	return this.path;
    }
}
