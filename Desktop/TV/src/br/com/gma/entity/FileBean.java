/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.entity;

/**
 *
 * @author Alirio
 */
public class FileBean {
    private String carrossel;
    private String esteira;
    private String maquina_costura;
    private String videojet;
    private String allinone;
    
    public void reset(){
        carrossel = "";
        esteira = "";
        maquina_costura = "";
        videojet = "";
    }
    public void setCarrosel(String perg, String resp, int os, String comment){carrossel += perg+";"+resp+";"+os+";"+comment+"\n";}
    public String getCarrossel(){return carrossel;}
    
    public void setEsteira(String perg, String resp, int os, String comment){esteira += perg+";"+resp+";"+os+";"+comment+"\n";}
    public String getEsteira(){return esteira;}
    
    public void setMaq_Costura(String perg, String resp, int os, String comment){maquina_costura += perg+";"+resp+";"+os+";"+comment+"\n";}
    public String getMaq_Costura(){return maquina_costura;}
    
    public void setVideojet(String perg, String resp, int os, String comment){videojet += perg+";"+resp+";"+os+";"+comment+"\n";}
    public String getVideojet(){return videojet;}
    
    public void addComment(String perg, String resp, int os, String comment, boolean last){
        if(last)
            allinone += perg+";";
    }
}
