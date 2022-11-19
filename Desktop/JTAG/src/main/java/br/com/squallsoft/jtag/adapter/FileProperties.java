/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

import br.com.squallsoft.jtag.security.AESEncryptionManager;
import br.com.squallsoft.jtag.security.Security;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que contém os métodos para manipulação
 * de arquivo properties
 * 
 * @author Alirio Oliveira
 */
public class FileProperties {
    
    private static final String arquivo = System.getProperty("user.dir")+File.separator+"config.properties";
    private static File f;
    protected static String password = "blahblahblah";

    public FileProperties(){
        analizarFile();
    }
    
    private static void analizarFile(){
        //System.out.println(arquivo);
        f = new File(arquivo);        
        if(!f.isFile())
            newFile();         
    }
    private static void newFile(){
        try {            
            f.createNewFile(); 
            Map<String, String> map = new HashMap();
            
            /* Main */
            map.put("SERVIDOR_IP", "http://squallsoft.ddns.net");
            map.put("SERVIDOR_PORT", "80");
            
            /* Firmware */
            map.put("FIRMWARE_LIST", "/mapper/listarMappers");
            map.put("BOARD_LIST_BY_MAPPER_ID", "/mapper/findBoardsByMapper/%d");
            
            /* Pedido */
            map.put("NOVO_PEDIDO", "/pedido/novo/%d/%s/%d");
            map.put("CLOSE_PEDIDO", "/pedido/novo/%d/%d");
            map.put("NOVO_TASK", "/pedido/newTask/%d");
            map.put("UPDATE_TASK", "/pedido/updateTask");
            
            /* Info Bar update */
            map.put("INFO_BAR", "/myapp/infobar");
            
            /* App */
            map.put("APP_VERSION", "/myapp/version");
            map.put("APP_HISTORY", "/myapp/history");
            map.put("NOVO_DOWNLOAD", "/myapp/download/%s");
            
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
    public static String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {

        try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
            
                    //getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();
            /**Lendo o arquivo dentro do projeto**/
            //InputStream configuracao = new FileInputStream(arquivo);
            byte[] fileBytes = AESEncryptionManager.readFile(arquivo);
            byte[] descryptFile = AESEncryptionManager.decryptData(password, fileBytes);
            /**carregando o arquivo**/
            //config.load(configuracao);
            config.load(new ByteArrayInputStream(descryptFile));

            return config.getProperty(nome_properties);
        } catch (IOException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
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
    public static void writePropertiesInterno(Map<String, String> list) throws IOException  {        
        try {
            /**Instanciando objeto do tipo File**/
            File file = new File(arquivo);

            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            //FileInputStream fis = new FileInputStream(file);

            byte[] fileBytes = AESEncryptionManager.readFile(arquivo);
            byte[] descryptFile = AESEncryptionManager.decryptData(password, fileBytes);
            
            /**carregando o arquivo**/
            config.load(new ByteArrayInputStream(descryptFile));

            /**setando as propriedades no arquivo properties**/
            for(Map.Entry<String, String> entry : list.entrySet()){
                config.setProperty(entry.getKey(), Arrays.toString(Security.encrypt(entry.getValue().getBytes(), "A1B2C3D4")));
            }

            /**Instanciando objeto do tipo FileOutputStream **/
            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração 2.0");

            fos.close();

        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Método que edita o arquivo properties 
     * dentro do projeto (Não funcionará após gerado o jar)
     * 
     * @param propertie 
     * @param value 
     */
    public static void writeProperties(String propertie, String value) throws IOException  {        
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
            config.setProperty(propertie, Arrays.toString(Security.encrypt(value.getBytes(), "A1B2C3D4")));

            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração 2.0");

            fos.close();

        } catch (IOException ex) {
            ex.getMessage();
        } catch (Exception ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String Servidor_URL() throws FileNotFoundException, Exception{
        if(Porta().equalsIgnoreCase("80"))
            return ServidorIP();
        else
            return ServidorIP()+":"+Porta();
    }
    
    public static String ServidorIP() throws FileNotFoundException, Exception{
        return readPropertiesInterno("SERVIDOR_IP");
    }
    
    private static String Porta() throws FileNotFoundException, Exception{
        return readPropertiesInterno("SERVIDOR_PORT");
    }
    
    public static String FirmwareURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("FIRMWARE_LIST");
    }
    
    public static String BoardURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("BOARD_LIST_BY_MAPPER_ID");
    }
    
    public static String NovoPedidoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("NOVO_PEDIDO");
    }
    
    public static String ClosePedidoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("CLOSE_PEDIDO");
    }
    
    public static String NovaTaskURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("NOVO_TASK");
    }
    
    public static String UpdateTaskURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("UPDATE_TASK");
    }
    
    public static String InfoBarValue() throws FileNotFoundException, Exception{
        return readPropertiesInterno("INFO_BAR");
    }
    
    public static String AppVersionURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("APP_VERSION");
    }
    
    public static String AppHistoryURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("APP_HISTORY");
    }
    
    public static String DownloadURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno("NOVO_DOWNLOAD");
    }
}
