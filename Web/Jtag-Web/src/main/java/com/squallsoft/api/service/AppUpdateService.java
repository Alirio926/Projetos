package com.squallsoft.api.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import com.squallsoft.api.util.AppProperties;

@Service
public class AppUpdateService {

	private static final String file_dir = "/home/alirio/update/";
	
	private AppProperties appProps = new AppProperties();
	
	public File getFile(String filename) throws FileNotFoundException {
		return new File(file_dir + filename);
	}
	
	public String getVersion() throws FileNotFoundException, Exception {
		
		return appProps.appVersion(); 
	}

	public String getHistory() throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		return appProps.appHistory();
	}
}
