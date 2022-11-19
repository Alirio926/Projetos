package com.squallsoft.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AppProperties {

	//private static final String arquivo = System.getProperty("user.dir")+File.separator+"config.properties";
	private  final String arquivo = "/home/alirio/update/app.properties";
    private  File f;

    public AppProperties(){
        analizarFile();
    }
    
    private  void analizarFile(){
        System.out.println(arquivo);
        f = new File(arquivo);        
        if(!f.isFile())
            newFile();         
    }
    
    private  void newFile(){
        try {            
            f.createNewFile(); 
            Map<String, String> map = new HashMap<>();
            
            /* Main */
            map.put("APP_VERSION", "0.1");
            map.put("APP_HISTORY", "Primeira versão do software.");
            
            writePropertiesInterno(map);               
        }catch (IOException ex) {
            ex.printStackTrace(); 
        }
    } 
    
    /**
     * Leitura de uma propriedade no arquivo
     * 
     * @param nome_properties
     * @return String
     * @throws FileNotFoundException 
     */
    private  String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {

        try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
            
                    //getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();
            /**Lendo o arquivo dentro do projeto**/
            InputStream configuracao = new FileInputStream(arquivo);

            /**carregando o arquivo**/
            config.load(configuracao);

            return config.getProperty(nome_properties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        return null;
    }
    
    /**
     * Método que edita o arquivo properties 
     * dentro do projeto (Não funcionará após gerado o jar)
     * 
     * @param ip
     * @param port 
     * @param list 
     */
    public  void writePropertiesInterno(Map<String, String> list) throws IOException  {        
        try {
            /**Instanciando objeto do tipo File**/
            File file = new File(arquivo);


            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            FileInputStream fis = new FileInputStream(file);

            /**carregando o arquivo**/
            config.load(fis);

            /**setando as propriedades no arquivo properties**/
            for(Map.Entry<String, String> entry : list.entrySet()){
                config.setProperty(entry.getKey(), entry.getValue());
            }

            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração Usuário e Senha:");

            fos.close();

        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    public  String appVersion() throws FileNotFoundException, Exception{
    	return readPropertiesInterno("APP_VERSION");
    }
    
    public  String appHistory() throws FileNotFoundException, Exception{
    	return readPropertiesInterno("APP_HISTORY");
    }
}
