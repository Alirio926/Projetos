package com.squallsoft.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

public class MapperList {

	private final String mapperList = "/home/alirio/mappers/nes.mapper";
	//private final String mapperList = "D:\\Spring_Workspace\\firmware\\nes.mapper";
	private boolean listMapper;
	
	public void MapperListLoad() {
		File f = new File(mapperList);
		if(f.exists() && f.isFile()) {
			listMapper = true;
		}else {
			listMapper = false;
		}
	}
	
	private String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {
		try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
                               
            /**Lendo o arquivo dentro do projeto**/
            InputStream configuracao = new FileInputStream(mapperList);

            /**carregando o arquivo**/
            config.load(configuracao);

            return config.getProperty(nome_properties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
        return null;
	}
	
	private void writePropertiesInterno(String props, String value) throws IOException  {
        
        try {
             /**Lendo o arquivo dentro do projeto**/
            //String arquivo = getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();

            /**Instanciando objeto do tipo File**/
            File file = new File(mapperList);


            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();

            /**Instanciando objeto do tipo FileInpusStream **/
            FileInputStream fis = new FileInputStream(file);

            /**carregando o arquivo**/
            config.load(fis);

            /**setando as propriedades no arquivo properties**/
            config.setProperty(props, value);
            
            /**Instanciando objeto do tipo FileOutputStream **/
            FileOutputStream fos = new FileOutputStream(file);

            /**Salvando os valores alterados no arquivo properties**/
            config.store(fos, "Configuracaoo Usuario e Senha:");

            fos.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
	
	public boolean listMapperFound() { return listMapper; }
	
	public boolean isNewList() throws Exception {
		return readPropertiesInterno("NEW_LIST").equalsIgnoreCase("TRUE");
	}
	
	public void removeFlagNewList() throws IOException {
		writePropertiesInterno("NEW_LIST","FALSE");
	}
	
	public StringTokenizer getListMappers() throws FileNotFoundException, Exception {
		return new StringTokenizer(readPropertiesInterno("MAPPER_FILE_NAMES"), ";");
	}
}
