/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.facade;

import br.com.squallsoft.api.dominios.BurnLogTransient;
import br.com.squallsoft.api.resteasy.RESTEasyClient;
import br.com.squallsoft.jtag.adapter.FormUpdate;
import br.com.squallsoft.jtag.adapter.LocalLog;
import br.com.squallsoft.jtag.adapter.SerialHelper;
import static br.com.squallsoft.jtag.presentation.mainForm.AVISO;
import static br.com.squallsoft.jtag.presentation.mainForm.SUCCESS;
import static br.com.squallsoft.jtag.presentation.mainForm.ERROR;
import com.squallsoft.api.dominios.LogEntity;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;

/**
 *
 * @author aliri
 */
public class XsvfPlay {
    
    private static Logger logger = Logger.getLogger(XsvfPlay.class);
    private FormUpdate form;
    private SerialHelper Serial;
    private byte[] firmware;
    private String mapper;
    private BurnFirmware burn;
    private DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy 'as' H':'mm':'ss"); 
    private LocalLog localLog;
    private String port;
    private BurnLogTransient logTransient;
    
    public XsvfPlay(FormUpdate f, SerialHelper serial){
        form = f;
        Serial = serial;
    }
    
    public void executeTask(byte[] firm, BurnLogTransient log, String m, String p){          
        burn = new BurnFirmware();
        form.updateProgress(0);
        firmware = firm;
        logTransient = log;
        mapper = m;
        port = p;
        localLog = new LocalLog();
        burn.execute();        
    }
    
    public boolean IsDone(){ return burn.isDone(); }
    
    class BurnFirmware extends SwingWorker<Void, Integer>{

        
        private long start, end = 0;
        private int writeProgress = 0;
        private int sum;
        private int xsvfSize=0;
        //private StringBuilder sbBurnLog;
        private boolean isDone;
        private boolean keep = true;
        
        @Override
        protected Void doInBackground() throws Exception {             
            String line;
            String command;
            String argument;
            int numBytes;
            int numWrite;
            int bytesWritten=0;
            xsvfSize = firmware.length;
            byte xsvf_data[];
            
            isDone = false;
            
            System.err.println("XSVF size: "+xsvfSize);
            //sbBurnLog = new StringBuilder();
            keep = true;
            
            form.printLog(String.format("%s Task Begin [ %d ] -->", fmt.format(GregorianCalendar.getInstance().getTime()), logTransient.getBurnID()), AVISO);
            try{
                
                Serial.conectar(port, 115200);                
                start = System.currentTimeMillis();
                while(keep){
                    line        = Serial.readLine();
                    command     = line.substring(0,1);
                    argument    = line.substring(1).trim();
                    
                    switch(command){
                        case "S":{
                            // Numero de bytes para enviar
                            numBytes = Integer.decode(argument);
                            // re seta tamanho do buffer de acordo com o novo tamanha da solicitação
                            xsvf_data= new byte[numBytes];
                            
                            // Se os dados restantes foram maior que o numero de bytes solicitados
                            if((xsvfSize - bytesWritten) >= numBytes){
                                System.arraycopy(firmware, bytesWritten, xsvf_data, 0, numBytes);
                                numWrite = numBytes;
                            // Se o número de bytes restantes forem menor que o numero de bytes solicitados no buffer                                 
                            }else{
                                System.arraycopy(firmware, bytesWritten, xsvf_data, 0, (int)(xsvfSize - bytesWritten));
                                numWrite = (int)(xsvfSize - bytesWritten);
                            }
                            // faz o envio pela porta serial
                            Serial.writeBlock(xsvf_data, 0, numWrite);
                            // atualiza o numero de bytes enviados
                            bytesWritten += numWrite;
                            // atualiza o hash
                            updateHash(xsvf_data);
                            // calcula progresso da gravação atual
                            writeProgress = bytesWritten * 100 / xsvfSize;
                            // Informa
                            publish(writeProgress);
                            setProgress(writeProgress); 
                        }break;
                        case "R":{
                            initHash();
                        }break;
                        case "Q":{
                            StringTokenizer st = new StringTokenizer(argument, ",");
                            while(st.hasMoreTokens()){
                                logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                                logTransient.getLog().append(" : Received Device Quit: [");
                                logTransient.getLog().append(st.nextToken());
                                logTransient.getLog().append("] [");
                                logTransient.getLog().append(st.nextToken());
                                logTransient.getLog().append("]");
                                logTransient.getLog().append("\n");
                            }
                            //    System.out.println("Received Device Quit: [" + st.nextToken() + "] [" + st.nextToken() + "]");
                            //form.deviceQuit(st);                        
                            //form.printInfo(Status.HASH, Integer.toString(xsvfSize));
                            //if(bytesWritten > 120)
                            keep = false;
                        }break;
                        case "D":{
                            logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                            logTransient.getLog().append(" : Device: ");
                            logTransient.getLog().append(argument);
                            logTransient.getLog().append("\n");
                            //form.printInfo(Status.DEVICE, "Device: " + argument);
                        }break;
                        case "!":{
                            logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                            logTransient.getLog().append(" : IMPORTANT: ");
                            logTransient.getLog().append(argument);
                            logTransient.getLog().append("\n");
                        }break;
                        default:{
                            logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                            logTransient.getLog().append(" : Comando desconhecido");
                            logTransient.getLog().append("\n");
                            //form.printInfo(Status.DESCONHECIDO, "Comando desconhecido");
                        }
                    }
                }
                publish(100);
                setProgress(100);
                
                RESTEasyClient.sendLog(logTransient);  
                
            }catch(com.fazecast.jSerialComm.SerialPortIOException s){
                logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                logTransient.getLog().append(" Task SerialPortIOException : ");
                logTransient.getLog().append(s.getMessage());
                logTransient.getLog().append("\n");
                
                logger.error("SerialPortIOException: " + s.getMessage());
                
                RESTEasyClient.sendLog(logTransient);  
            }catch(IOException e){
                logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                logTransient.getLog().append(" Task Erro Exception : ");
                logTransient.getLog().append(e.getMessage());                
                logTransient.getLog().append("\n");
                
                logger.error("IOException: " + e.getMessage());
                
                RESTEasyClient.sendLog(logTransient);
            }catch(NumberFormatException n){
                logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                logTransient.getLog().append(" Task Erro NumberFormatException : ");
                logTransient.getLog().append(n.getMessage());
                logTransient.getLog().append("\n");
                
                logger.error("NumberFormatException: " + n.getMessage());
                
                RESTEasyClient.sendLog(logTransient); 
            }finally{
                logTransient.getLog().append(fmt.format(GregorianCalendar.getInstance().getTime()));
                logTransient.getLog().append(" Task End");                              
                logTransient.getLog().append("\n");
                
                logger.info("Task finish progress: " + writeProgress);
                
                RESTEasyClient.sendLog(logTransient);
            }
            
            return null;
        }// end doInBackground   
                
        @Override
        protected void process(List<Integer> chunks){
            Integer p = chunks.get(chunks.size() - 1);
            form.updateProgress(p);
            
            if(p == 75){
                try {
                    RESTEasyClient.sendLog(logTransient);
                    logger.info(String.format("Progress Success hit: %d percent", logTransient.getBurnID(), 75));
                } catch (Exception ex) {
                    logTransient.getLog().append("\n").append(fmt.format(GregorianCalendar.getInstance().getTime()));
                    logTransient.getLog().append(" API Exception : ");
                    logTransient.getLog().append(ex.getMessage());
                    logTransient.getLog().append("\n");
                    
                    logger.error("API Excetpion: " + ex.getMessage());
                    
                    keep = false;
                }
            }
        }
        
        @Override
        public void done() {
            Boolean success = false;
            try {
                RESTEasyClient.sendLog(logTransient);
                
                form.updateProgress(100);
                
                success = logTransient.getLog().toString().contains("Success") && (writeProgress == 100);                
                
                form.playSound(success);
                
                form.printLog(logTransient.getLog().toString(), success == true ? SUCCESS : ERROR);
                
                LogEntity l = new LogEntity();
                l.setDataTime(LocalDateTime.now());
                l.setMapper(mapper);
                l.setPedidoId(logTransient.getPedidoID());
                l.setLog(logTransient.getLog().toString());
                l.setBurnID(logTransient.getBurnID());
                l.setSuccess(success);
                
                logger.info(String.format("Done [%d] result: %s", logTransient.getBurnID(), success ? "SIM":"NÃO"));
                
                localLog.adiciona(l);
                
                if(Serial.isConnected())
                    Serial.desconectar();                
                
                form.updateInfoBar();
                
                form.updateTree();    
                
                isDone = true; 
                
            } catch (NullPointerException | IOException | ClassNotFoundException ex) {
                logger.error("Process Done error: " + ex.getMessage());
            } catch (Exception ex) {
                logger.error("Process Done error: " + ex.getMessage());
            }
        }
                
        private void updateHash(byte[] xsvf_data) {
            for(byte d : xsvf_data){
                sum += (0xFF & d);
            }
        }

        private void initHash() {
            sum = 0;
        }
    }
}
