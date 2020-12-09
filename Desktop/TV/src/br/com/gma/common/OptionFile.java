/*
 * OptionFile.java
 *
 * Created on 12 de Julho de 2008, 04:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.gma.common;
import java.io.*;
import java.util.Date;
/**
 *
 * @author Alirio Oliveira
 */
public class OptionFile {
    
    private File f;
    private FileOutputStream out = null;
    private boolean fileQuinzeMinutes = false;
    private boolean fileRelatorioProducao = false;
    private boolean fileRelatorioParada = false;
    private boolean fileRelatorioHora = false;
    private boolean fileRelatorioMeiaHora = false;
    /** Creates a new instance of OptionFile */
    public OptionFile() {
        
    }
    public void setFileName(){ }
    public boolean arquivoExiste(String p){
        f = new File(p);        
        return f.isFile();      
    }
    private void newFile(){
        try {            
            f.createNewFile();         
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void saveHoraHora(int index, int valor) throws FileNotFoundException, IOException{
        Date time = new Date();
        String pathfile = "Relatorio hora hora "+time.getDate()+".csv";
        fileRelatorioHora = arquivoExiste(pathfile);
        // arquivo não existe entre 0 e 6 horas
        if(!fileRelatorioHora && time.getHours() >= 0 && time.getHours() <= 6){
            int date = time.getDate()-1;
            fileRelatorioHora = arquivoExiste("Relatorio hora hora "+date+".csv"); 
            // verifica se existe arquivo com a data anterior
            if(fileRelatorioHora){
                pathfile = "Relatorio hora hora "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }else{
                date++;// caso não exista, cria arquivo com a data atual.
                pathfile = "Relatorio hora hora "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }
        }else if(!fileRelatorioHora && time.getHours() > 6){// se existir o arquivo com a data atual
            out = new FileOutputStream(pathfile,true);
            // fazer chamada de motodo para inserir cabeçalho do arquivo
        }else{// se o arquivo existir
            out = new FileOutputStream(pathfile,true);
        }        
        StringBuilder dat = new StringBuilder();
        dat.append("\n");
        if(index < 10)
            dat.append("0");
        dat.append(index);
        dat.append(":00;");
        dat.append(valor);
        out.write(dat.toString().getBytes());
        out.flush();
        
    }
    public void saveMeiaHora(int hh, int mm, int valor, int a) throws FileNotFoundException, IOException{
        Date time = new Date();
        String pathfile = "Relatorio meia hora "+time.getDate()+".csv";
        fileRelatorioMeiaHora = arquivoExiste(pathfile);
        // arquivo não existe entre 0 e 6 horas
        if(!fileRelatorioMeiaHora && time.getHours() >= 0 && time.getHours() <= 6){
            int date = time.getDate()-1;
            fileRelatorioMeiaHora = arquivoExiste("Relatorio meia hora "+date+".csv"); 
            // verifica se existe arquivo com a data anterior
            if(fileRelatorioMeiaHora){
                pathfile = "Relatorio meia hora "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }else{
                date++;// caso não exista, cria arquivo com a data atual.
                pathfile = "Relatorio meia hora "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }
        }else if(!fileRelatorioMeiaHora && time.getHours() > 6){// se existir o arquivo com a data atual
            out = new FileOutputStream(pathfile,true);
            // fazer chamada de motodo para inserir cabeçalho do arquivo
        }else{// se o arquivo existir
            out = new FileOutputStream(pathfile,true);
        }        
        StringBuilder dat = new StringBuilder();
        dat.append("\n");
        if(hh < 10)
            dat.append("0");
        dat.append(hh);//hora
        dat.append(":");
        if(mm < 10)
            dat.append("0");
        dat.append(mm);//min
        dat.append(";");
        dat.append(valor);// produzido
        dat.append(";");
        dat.append(a);//avaria
        out.write(dat.toString().getBytes());
        out.flush();
        
    }
    public void horahoraFileClose() throws IOException{out.close();}
    public void addComment(StringBuilder sb) throws FileNotFoundException, IOException{
        Date time = new Date();
        String pathfile = "Relatorio Enc "+time.getDate()+".csv";
        fileQuinzeMinutes = arquivoExiste(pathfile);
        // arquivo não existe entre 0 e 6 horas
        if(!fileQuinzeMinutes && time.getHours() >= 0 && time.getHours() <= 6){
            int date = time.getDate()-1;
            fileQuinzeMinutes = arquivoExiste("Relatorio Enc "+date+".csv"); 
            // verifica se existe arquivo com a data anterior
            if(fileQuinzeMinutes){
                pathfile = "Relatorio Enc "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }else{
                date++;// caso não exista, cria arquivo com a data atual.
                pathfile = "Relatorio Enc "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }
        }else if(!fileQuinzeMinutes && time.getHours() > 6){// se existir o arquivo com a data atual
            out = new FileOutputStream(pathfile,true);
            // fazer chamada de motodo para inserir cabeçalho do arquivo
            /* Carrossel MWPL, Máquina de costura, Esteira saída, Video jet 01 */
            out.write("Pergunta;Resposta;OS;;Pergunta;Resposta;OS;;Pergunta;Resposta;OS;;Pergunta;Resposta;OS;;\n".getBytes());
        }else{// se o arquivo existir
            out = new FileOutputStream(pathfile,true);
        }
                    
        out.write(sb.toString().getBytes()); 
        out.flush();
        out.close();
    }
    public void addLinhaProducao(StringBuilder sb) throws FileNotFoundException, IOException{
        Date time = new Date();
        String pathfile = "Relatorio proucao "+time.getDate()+".csv";
        fileRelatorioProducao = arquivoExiste(pathfile);
        // arquivo não existe entre 0 e 6 horas
        if(!fileRelatorioProducao && time.getHours() >= 0 && time.getHours() <= 6){
            int date = time.getDate()-1;
            fileRelatorioProducao = arquivoExiste("Relatorio producao "+date+".csv"); 
            // verifica se existe arquivo com a data anterior
            if(fileRelatorioProducao){
                pathfile = "Relatorio producao "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }else{
                date++;// caso não exista, cria arquivo com a data atual.
                pathfile = "Relatorio producao "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }
        }else if(!fileRelatorioProducao && time.getHours() > 6){// se existir o arquivo com a data atual
            out = new FileOutputStream(pathfile,true);
            // fazer chamada de motodo para inserir cabeçalho do arquivo
            out.write("Hora de Inicio;Hora de Final;Produto;Avaria;Quant.;Estoque;;\n".getBytes());
        }else{// se o arquivo existir
            out = new FileOutputStream(pathfile,true);
        }
                    
        out.write(sb.toString().getBytes()); 
        out.flush();
        out.close();
    }
    public void addLinhaParada(StringBuilder sb) throws FileNotFoundException, IOException{
        Date time = new Date();
        String pathfile = "Relatorio parada "+time.getDate()+".csv";
        fileRelatorioParada = arquivoExiste(pathfile);
        // arquivo não existe entre 0 e 6 horas
        if(!fileRelatorioParada && time.getHours() >= 0 && time.getHours() <= 6){
            int date = time.getDate()-1;
            fileRelatorioParada = arquivoExiste("Relatorio parada "+date+".csv"); 
            // verifica se existe arquivo com a data anterior
            if(fileRelatorioParada){
                pathfile = "Relatorio parada "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }else{
                date++;// caso não exista, cria arquivo com a data atual.
                pathfile = "Relatorio parada "+date+".csv";
                out = new FileOutputStream(pathfile,true); 
            }
        }else if(!fileRelatorioParada && time.getHours() > 6){// se existir o arquivo com a data atual
            out = new FileOutputStream(pathfile,true);
            // fazer chamada de motodo para inserir cabeçalho do arquivo
            out.write("Hora de Inicio;Hora de Final;Produto;Descrição da ocorrência;;\n".getBytes());
        }else{// se o arquivo existir
            out = new FileOutputStream(pathfile,true);
        }
                    
        out.write(sb.toString().getBytes()); 
        out.flush();
        out.close();
    }
    private String convert(int i){return Integer.toString(i);}
}