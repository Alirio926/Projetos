/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.facade;

import br.com.squallsoft.api.resteasy.RESTEasyClient;
import br.com.squallsoft.jtag.adapter.FormUpdate;
import br.com.squallsoft.jtag.security.Security;
import com.squallsoft.api.dominios.FirmwareToBurn;
import java.util.Arrays;
import javax.swing.SwingWorker;
import org.springframework.util.SerializationUtils;

/**
 *
 * @author aliri
 */
public class APITask {
    private FormUpdate form;
    private long idx;
    private int qtd;
    
    public APITask(FormUpdate f){
        form = f;
    }
    public void executeTask(long i, int q){
        idx = i;
        qtd = q;
        API api = new API();
        api.execute();
    }
    
    class API extends SwingWorker<Void, Integer>{
        
        @Override
        protected Void doInBackground() throws Exception {
            form.setIndeterminated(true);
            boolean allOk = true;
            try{
                form.setAPIInfo("Procurando id ["+idx+"]");
                Long id = RESTEasyClient.listFirmware().get((int) idx).getId();            

                // #2 Read data class da API
                form.setAPIInfo("Obtendo firmware.");
                byte[] data = RESTEasyClient.findFirmware(id, qtd);
                
                // #3 Decompress
                form.setAPIInfo("Abrindo firmware");
                byte[] decompressed = Security.decompressByteArray(data);

                // #5 Deserialize class
                form.setAPIInfo("Deserialize.");
                FirmwareToBurn entity = (FirmwareToBurn) SerializationUtils.deserialize(decompressed);

                // #6 Check Licença
                //System.out.println("Checando validade: " + entity.getMessage() + " : " + entity.getIsValid());
                if(!entity.getIsValid()){
                    form.printLog(entity.getMessage(), br.com.squallsoft.jtag.presentation.mainForm.ERROR);
                    allOk = false;
                }
                
                // #7 Check Machine code
                form.setAPIInfo("Check code.");
                if(!entity.getCode().equalsIgnoreCase(Security.getProcessadorID())){
                    form.printLog("Machine code: "+entity.getCode()+" Found Machine code: "+Security.getProcessadorID(), br.com.squallsoft.jtag.presentation.mainForm.ERROR);
                    allOk = false;
                }     
                
                if(allOk){
                    form.setAPIInfo("Firmware ok, burn init...");
                    form.setIndeterminated(false);
                    form.setFirmware(entity);
                }else{
                    form.setAPIInfo("Contate a Squallsoft.");
                    form.setIndeterminated(false);
                }
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return null;
        }       
    } 
}
