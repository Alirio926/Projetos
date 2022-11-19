/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.api.resteasy;

import br.com.squallsoft.api.dominios.BurnLogTransient;
import br.com.squallsoft.jtag.adapter.FileProperties;
import com.squallsoft.api.dominios.Mapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static br.com.squallsoft.jtag.adapter.FileProperties.AppHistoryURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.AppVersionURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.BoardURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.ClosePedidoURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.FirmwareURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.InfoBarValue;
import static br.com.squallsoft.jtag.adapter.FileProperties.NovaTaskURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.NovoPedidoURL;
import static br.com.squallsoft.jtag.adapter.FileProperties.Servidor_URL;
import static br.com.squallsoft.jtag.adapter.FileProperties.UpdateTaskURL;
import com.squallsoft.api.dominios.FirmwareEntity;
import br.com.squallsoft.jtag.security.Security;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.squallsoft.api.dominios.Board;
import java.util.List;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;
/**
 *
 * @author aliri
 */
public class RESTEasyClient {
    // remote ip 201.8.41.97
    static String username = "firmware";
    static String password = "firmware";
    static List<FirmwareEntity> listFirm;
    private static List<Mapper> listMapper;
        
    //https://mkyong.com/java/jackson-2-convert-java-object-to-from-json/
    //https://mkyong.com/java/jackson-convert-json-array-string-to-list/
    private static ClientRequest openConnection(String apiURL) throws Exception{
        DefaultHttpClient client = new DefaultHttpClient();
        client.getCredentialsProvider().setCredentials(AuthScope.ANY,
                                new UsernamePasswordCredentials(username, password));
        ApacheHttpClient4Executor executer = new ApacheHttpClient4Executor(client);
            
        ClientRequest request = new ClientRequest(
                apiURL, executer);
                
        request.followRedirects(true).accept("application/json");

        return request;
    }
    
    //Load list from api rest
    public static void loadAllFirmware() throws Exception{
        //ClientResponse<String> response = openConnection(apiURL+"/lstAllFirmware").get(String.class);
        ClientResponse<String> response = openConnection(
                Servidor_URL() + FirmwareURL()
                ).get(String.class);
             
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        
        mapper.registerModule(new JavaTimeModule());
        //listFirm = mapper.readValue(response.getEntity(), new TypeReference<List<FirmwareEntity>>() {});
        listMapper = mapper.readValue(response.getEntity(), new TypeReference<List<Mapper>>() {});
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                + response.getStatus());
        }
    }
    
    // Load boards by mapper id
    public static List<Board> getSupportedBoardsByMapperId(Long mapperId)throws Exception{
        List<Board> listBoard = null;
        ClientResponse<String> response = openConnection(
                Servidor_URL() + String.format(BoardURL(), mapperId)
                ).get(String.class);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        
        //mapper.registerModule(new JavaTimeModule());
        
        listBoard = mapper.readValue(response.getEntity(), new TypeReference<List<Board>>(){});
        
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                + response.getStatus());
        }        
        
        return listBoard;
    }
    
    // return list loaded previous
    public static List<Mapper> listFirmware(){return listMapper;}
    
    // baixa firmware
    public static byte[] findFirmware(Long firmwareId, int qtd) throws Exception{
        String code = Security.getProcessadorID();
        
        //System.out.println(Servidor_URL() + String.format(NovoPedidoURL(), firmwareId, code, qtd));
        //ClientResponse<byte[]> response = openConnection(apiURL+"/getFirmware/"+firmwareId+"/"+code+"/"+qtd).get(byte[].class);
        ClientResponse<byte[]> response = openConnection(Servidor_URL() + String.format(NovoPedidoURL(), firmwareId, code, qtd)).get(byte[].class);
                
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        byte[] data = response.getEntity();
               
        return data;        
    }
    
    public static String closePedido(Long firmwareId, int qtdCancel) throws Exception{
        //System.out.println(Servidor_URL() + String.format(ClosePedidoURL(), firmwareId, qtdCancel));
        ClientResponse<String> response = openConnection(Servidor_URL() + String.format(ClosePedidoURL(), firmwareId, qtdCancel)).get(String.class);
        
        return response.getEntity();
    }
    
    // get session
    public static String getSessionId() throws Exception{
        return openConnection(Servidor_URL()+"/uui").toString();
    }
    
    public static void sendLog(BurnLogTransient logg) throws Exception{
        ObjectMapper toJson = new ObjectMapper();        
        
        ClientRequest request = openConnection(Servidor_URL() + UpdateTaskURL());
        
        String json = toJson.writeValueAsString(logg);
        
        request.accept("application/json");
        request.setHttpMethod("POST");
        request.body("application/json", json);
        
        request.post();
    }
    
    public static Long getNewBurnID(Long pedidoID) throws Exception{
        ClientResponse<String> response = openConnection(Servidor_URL() + String.format(NovaTaskURL(), pedidoID)).get(String.class);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
                  
        Long d = mapper.readValue(response.getEntity(), new TypeReference<Long>() {});
        
        return d;
    }
    
    public static String getInfoBarValues() throws Exception{
        //System.out.println(Servidor_URL() + InfoBarValue());
        ClientResponse<String> response = openConnection(Servidor_URL() + InfoBarValue()).get(String.class);
        
        return response.getEntity();
    }
    
    public static String getVersion() throws Exception{
        //System.out.println(Servidor_URL() + AppVersionURL());
        ClientResponse<String> response = openConnection(Servidor_URL() + AppVersionURL()).get(String.class);
        
        return response.getEntity();
    }

    public static String getVersionHistory() throws Exception {
        ClientResponse<String> response = openConnection(Servidor_URL() + AppHistoryURL()).get(String.class);
        
        return response.getEntity();
    }
}
