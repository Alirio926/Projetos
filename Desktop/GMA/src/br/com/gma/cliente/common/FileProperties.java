/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gma.cliente.common;

import br.com.gma.server.common.entity.CONSTANTES;
import static br.com.gma.server.common.entity.CONSTANTES.AUTO_CONNECT;
import static br.com.gma.server.common.entity.CONSTANTES.CARGO_URL;
import static br.com.gma.server.common.entity.CONSTANTES.CLIENTE_URL;
import static br.com.gma.server.common.entity.CONSTANTES.PEDIDO_URL;
import static br.com.gma.server.common.entity.CONSTANTES.PRODUTO_URL;
import static br.com.gma.server.common.entity.CONSTANTES.RMI;
import static br.com.gma.server.common.entity.CONSTANTES.SERVIDOR_IP;
import static br.com.gma.server.common.entity.CONSTANTES.SERVIDOR_PORT;
import static br.com.gma.server.common.entity.CONSTANTES.SERVIDOR_URL;
import static br.com.gma.server.common.entity.CONSTANTES.USUARIO_URL;
import static br.com.gma.server.common.entity.CONSTANTES.VERSION_URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que contém os métodos para manipulação
 * de arquivo properties
 * 
 * @author Daniel Paulo de Assis
 */
public class FileProperties {
    
    private final String arquivo = System.getProperty("user.dir")+File.separator+"config.properties";
    File f;

    public FileProperties(){
        analizarFile();
    }
    private void analizarFile(){
        System.out.println(arquivo);
        f = new File(arquivo);        
        if(!f.isFile())
            newFile();         
    }
    private void newFile(){
        try {            
            f.createNewFile();            
            writePropertiesInterno("127.0.0.1", 1099, true);               
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
    private String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {

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
     * @param autoconnect
     */
    public void writePropertiesInterno(String ip, int port, boolean autoconnect) throws IOException  {
        
        try {
             /**Lendo o arquivo dentro do projeto**/
            //String arquivo = getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();

            /**Instanciando objeto do tipo File**/
            File file = new File(arquivo);


            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            FileInputStream fis = new FileInputStream(file);

            /**carregando o arquivo**/
            config.load(fis);

            /**setando as propriedades no arquivo properties**/
            config.setProperty(SERVIDOR_IP, ip);
            config.setProperty(SERVIDOR_PORT, Integer.toString(port));
            config.setProperty(AUTO_CONNECT, Boolean.toString(autoconnect));
            config.setProperty(PEDIDO_URL, RMI+ip+":"+port+"/PEDIDO");
            config.setProperty(PRODUTO_URL, RMI+ip+":"+port+"/PRODUTO");
            config.setProperty(USUARIO_URL, RMI+ip+":"+port+"/USUARIO");
            config.setProperty(CLIENTE_URL, RMI+ip+":"+port+"/CLIENTE");
            config.setProperty(SERVIDOR_URL, RMI+ip+":"+port+"/SERVIDOR");
            config.setProperty(CARGO_URL, RMI+ip+":"+port+"/CARGO");
            config.setProperty(VERSION_URL, RMI+ip+":"+port+"/VERSION");
            config.setProperty(CONSTANTES.URL_CARREGAMENTO, "d:\\gmacliente\\relatorios\\RelatorioCarregamento.jrxml");
            config.setProperty(CONSTANTES.URL_MOAGEM, "d:\\gmacliente\\relatorios\\RelatorioMoagem.jrxml");
            config.setProperty(CONSTANTES.URL_COMERCIAL, "d:\\gmacliente\\relatorios\\RelatorioComercial.jrxml");
            config.setProperty(CONSTANTES.URL_PRODUCAO, "d:\\gmacliente\\relatorios\\RelatorioProducao.jrxml");
            
            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuração Usuário e Senha:");

            fos.close();

        } catch (IOException ex) {
            ex.getMessage();
        }


    }
    public boolean isAutoConnect() throws FileNotFoundException, Exception{
        return Boolean.parseBoolean(readPropertiesInterno(AUTO_CONNECT));
    }
    public String ServidorIP() throws FileNotFoundException, Exception{
        return readPropertiesInterno(SERVIDOR_IP);
    }
    public int Porta() throws FileNotFoundException, Exception{
        return Integer.parseInt(readPropertiesInterno(SERVIDOR_PORT));
    }
    public String pedidoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(PEDIDO_URL);
    }
    public String produtoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(PRODUTO_URL);
    }
    public String usuarioURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(USUARIO_URL);
    }
    public String clienteURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CLIENTE_URL);
    }
    public String servidorURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(SERVIDOR_URL);
    }
    public String cargoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CARGO_URL);
    }
    public String carregamentoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CONSTANTES.URL_CARREGAMENTO);
    }
    public String comercialURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CONSTANTES.URL_COMERCIAL);
    }
    public String moagemURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CONSTANTES.URL_MOAGEM);
    }
    public String producaoURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(CONSTANTES.URL_PRODUCAO);
    }
    public String getVersionURL() throws FileNotFoundException, Exception{
        return readPropertiesInterno(VERSION_URL);
    }
}
