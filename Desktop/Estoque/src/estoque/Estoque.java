/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estoque;

import br.com.estoque.presentation.MainForm;
import java.io.File;

/**
 *
 * @author Alirio
 */
public class Estoque {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Properties: "+System.getProperty("user.dir"));
        System.out.println("New File: "+new File(".").getAbsolutePath());
        System.out.println("Warp");
        // TODO code application logic here
        new MainForm().setVisible(true);        
    }
    
}
