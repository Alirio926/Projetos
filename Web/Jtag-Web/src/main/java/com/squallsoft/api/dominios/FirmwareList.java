package com.squallsoft.api.dominios;

import java.util.ArrayList;
import java.util.List;

public class FirmwareList {
	private List<FirmwareEntity> firmwareList;
    
    public List<FirmwareEntity> getFirmwareList() {
        if(firmwareList == null) {
        	firmwareList = new ArrayList<>();
        }
        return firmwareList;
    }
  
    public void setFirmwareList(List<FirmwareEntity> fl) {
        this.firmwareList = fl;
    }
}
