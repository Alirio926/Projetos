/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.main;

import br.com.gma.comercial.presentation.ComercialForm;
import br.com.gma.expedicao.presentation.ExpedicaoForm;
import br.com.gma.moagem.presentation.MoagemForm;
import br.com.gma.server.persistence.Factory;
import br.com.gma.server.rmi.ServidorRMI;

/**
 *
 * @author Administrador
 */
public class SisCEPA {
    
    public static void main(String[] args){
        
        if(args.length>0)
            switch(Integer.parseInt(args[0])){
                case 0:
                    new ExpedicaoForm();
                    break; 
                case 1:
                    new MoagemForm();
                    break;
                case 2:
                    new ServidorRMI(1099,new Factory());
                    break;
                case 3:
                    new ComercialForm(args[1]);
                    break;
                default:
                    System.out.println("Opção não reconhecida");
                    break;
            }
        else{
            DOSMenu menu = new DOSMenu();
        }
        /*if(args.length < 1){
            System.out.println("Especifique os argumentos");
            System.exit(0);
        }
        switch(Integer.parseInt(args[0])) {
            case 0:
                if(args.length > 1){
                    new ServidorRMI(Integer.parseInt(args[1]));
                    break;
                }else{
                    new ServidorRMI();                
                    break;
                }
            case 1:
                new ExpedicaoForm();
                break;
            case 2:
                // Moagem
        }
        
    }*/
    }
}