/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

import com.squallsoft.api.dominios.FirmwareToBurn;
import java.util.StringTokenizer;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author alirio.filho
 */
public interface FormUpdate {
    public void setIndeterminated(boolean value);
    public void setAPIInfo(String value);
    public void setFirmware(FirmwareToBurn firm);
    
    public void updateProgress(int writted);    
    
    public void printInfo(Status type, String arg);
    public void printLog(String txt, SimpleAttributeSet style);
    public void deviceQuit(StringTokenizer token);
    public void updateTree();
    public void updateInfoBar();
    public void playSound(boolean b);
}
