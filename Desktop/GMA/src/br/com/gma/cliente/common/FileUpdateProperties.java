/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.gma.cliente.common;

import br.com.gma.server.business.facade.remote.RemoteUpdate;
import static br.com.gma.server.common.entity.CONSTANTES.FILE_INFO_DEST;
import static br.com.gma.server.common.entity.CONSTANTES.FILE_INFO_ORIG;
import java.io.BufferedOutputStream;
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
 *
 * @author Alirio
 */
public class FileUpdateProperties {
    private File f;
    private byte[] filedata;
    private final RemoteUpdate versionControl;

    public FileUpdateProperties(RemoteUpdate versionControl){
        analizarFile();
        this.versionControl=versionControl;
    }
    private void analizarFile(){
        System.out.println(FILE_INFO_DEST);
        f = new File(FILE_INFO_DEST);        
        if(!f.isFile())
            newFile();         
    }
    private void newFile(){
        try {            
            f.createNewFile();            
            //writePropertiesInterno("127.000.000.001", 1098, true);               
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
    public String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {

        try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
            
                    //getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();
            /**Lendo o arquivo dentro do projeto**/
            InputStream configuracao = new FileInputStream(FILE_INFO_DEST);

            /**carregando o arquivo**/
            config.load(configuracao);

            return config.getProperty(nome_properties);
        } catch (IOException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    public Boolean downloadFiles(String org,String dst){
        try{
            filedata = versionControl.downloadFile(org);
            File file = new File(dst);
            if(file.exists())
                file.delete();
            System.out.println(dst);
            file.createNewFile();
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file, false));
            output.write(filedata, 0, filedata.length);
            output.flush();
            output.close();
            return Boolean.TRUE;
        }catch(IOException ioex){
            System.err.println("Update erro: "+ioex.getMessage());
            ioex.printStackTrace();
            return Boolean.FALSE;        
        }
    }
    public boolean downloadFileInfo(){
        try{
            filedata = versionControl.downloadFile(FILE_INFO_ORIG);
            File file = new File(FILE_INFO_ORIG);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
            output.write(filedata, 0, filedata.length);
            output.flush();
            output.close();
            return Boolean.TRUE;
        }catch(IOException ioex){
            System.err.println("Update erro: "+ioex.getMessage());
            return Boolean.FALSE;        
        }
    }
}
