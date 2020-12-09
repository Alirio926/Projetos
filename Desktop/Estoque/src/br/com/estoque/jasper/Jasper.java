/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estoque.jasper;

import br.com.estoque.bussines.facade.TransacaoIn;
import br.com.estoque.entity.RelatorioDeRemessaEntity;
import br.com.estoque.entity.RemessaEntity;
import br.com.estoque.entity.RoloEntity;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
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
public class Jasper {

    private final String path; //Caminho base
    private final String fullpath;
    private final TransacaoIn dbConn;

    public Jasper(TransacaoIn in, String path) {
        dbConn = in;
        this.path = "br\\com\\estoque\\jasper\\relatorios\\";
        fullpath = this.getClass().getClassLoader().getResource("").getPath() + this.path;
    }

    public void print() throws JRException {
        Collection<RemessaEntity> entity = dbConn.listarRemessas();
        Collection<RelatorioDeRemessaEntity> relatorio = new HashSet<>();
        RelatorioDeRemessaEntity remessa = new RelatorioDeRemessaEntity();
        RoloEntity re = new RoloEntity();
        int count = 0;
        int nextrol = 0;
        for (RemessaEntity en : entity) {// loop principal por list de remessas
            
        }

        JasperReport report = JasperCompileManager.compileReport(fullpath + "Remessa.jrxml");
        JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relatorio));
        JDialog viewer = new JDialog(new javax.swing.JFrame(), "Visualização do Relatório", true);
        viewer.setSize(800, 600);
        viewer.setLocationRelativeTo(null);
        JasperViewer jrViewer = new JasperViewer(print, true);
        viewer.getContentPane().add(jrViewer.getContentPane());
        viewer.setVisible(true);
    }
}
