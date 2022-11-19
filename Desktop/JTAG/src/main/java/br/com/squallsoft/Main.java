package br.com.squallsoft;


import br.com.squallsoft.api.resteasy.RESTEasyClient;
import br.com.squallsoft.jtag.adapter.FileProperties;
import br.com.squallsoft.jtag.presentation.SplashScreenDemo;
import br.com.squallsoft.jtag.presentation.UpdateInfo;
import br.com.squallsoft.jtag.presentation.WaitForm;
import br.com.squallsoft.jtag.presentation.mainForm;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aliri
 */
public class Main {
    private static String version = "0.4";
    private static Logger logger = Logger.getLogger(Main.class);
    private static FileProperties fProps;
    
    public static void main(String args[]){
        setLookAndFeels();
        
        fProps = new FileProperties();
        
        //SplashScreenDemo splash = new SplashScreenDemo();
            
        //PropertiesConfigurator is used to configure logger from properties file
        PropertyConfigurator.configure("log4j.properties");
        try{
            if(RESTEasyClient.getVersion().equalsIgnoreCase(version)){
                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new mainForm().setVisible(true);
                    }
                });
            }else{
                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new UpdateInfo().setVisible(true);
                    }
                });
            }  
        }catch(Exception e){
            WaitForm f = new WaitForm();
            f.setVisible(true);
            logger.error("Erro inicando app: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    
    public static void setLookAndFeels(){
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    //break;
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
    }
}
