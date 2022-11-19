package com.squallsoft.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squallsoft.api.service.AppUpdateService;

@RestController
@RequestMapping("/myapp")
public class AppController {

	Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private AppUpdateService appUpdate;
	 	
	@GetMapping
	public ResponseEntity<String> infoBarUpdate(){
		return ResponseEntity.ok().body("00;0.00");	
	}
	
	

	@GetMapping("/version")
	public ResponseEntity<String> appVersion(){
		try {
			return ResponseEntity.ok().body(appUpdate.getVersion());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Error lendo app.properties.");
	}
	
	
	@GetMapping("/history")
	public ResponseEntity<String> appHistory(){
		try {
			return ResponseEntity.ok().body(appUpdate.getHistory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Error lendo app.properties.");
	} 

	@GetMapping("/download/{filename}")
	public ResponseEntity<InputStreamResource> downloadApp(@PathVariable("filename") String filename){
		
		
		MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;//.getMediaTypeForFileName(this.servletContext, filename);
        System.out.println("fileName: " + filename);
        System.out.println("mediaType: " + mediaType);
        
        try {
        	File file = appUpdate.getFile(filename);
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
			
			return ResponseEntity.ok() 
					// Content-Disposition
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+"update.zip")
					// Content-Type
	                .contentType(mediaType)
	                // Contet-Length
	                .contentLength(file.length()) 
	                // Content
	                .body(resource);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(String.format("O filename informado não é um arquivo {%s}. Error: %s", filename, e.getMessage()));
		}
        
        return ResponseEntity.ok().body(null);
        
	}
}
