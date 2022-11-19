package com.squallsoft.api.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MapperLoader {
	
    private static String pathToFile = "/home/alirio/mappers/";
	//private static String pathToFile = "D:\\Spring_Workspace\\firmware\\";
	private static String ext = ".mapper";
	private boolean mapperFound;
	private String filename;
	
	public void readMapperFile(String mapperfilename) throws IOException {
		File f = new File(pathToFile + mapperfilename + ext);        
        if(f.exists() && f.isFile()) {
        	mapperFound = true;
        	filename = pathToFile + mapperfilename + ext;
        }else {
        	mapperFound = false;
        }
	} 
	
	private String readPropertiesInterno(String nome_properties) throws FileNotFoundException, Exception {

        try {
            /**Instanciando objeto da classe Properties**/
            Properties config = new Properties();
            
                    //getClass().getResource("/br/com/gma/main/config.properties").toURI().getPath();
            /**Lendo o arquivo dentro do projeto**/
            InputStream configuracao = new FileInputStream(filename);

            /**carregando o arquivo**/
            config.load(configuracao);

            return config.getProperty(nome_properties);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        return null;
    }
		
	public boolean mapperFound() { return mapperFound; }
	
	///////////////////////////////////
	public String getChipName() throws FileNotFoundException, Exception { return readPropertiesInterno("CHIP_NAME"); }
	public String getSupportedBoards() throws FileNotFoundException, Exception { return readPropertiesInterno("SUPPORTED_BOARDS"); }	
	public String getMapperFilePath() throws FileNotFoundException, Exception { return readPropertiesInterno("MAPPER_FILE"); }	
	public String getMapperINES() throws FileNotFoundException, Exception { return readPropertiesInterno("MAPPER_INES"); }
	
	
	
	
	
	
	
	
}
