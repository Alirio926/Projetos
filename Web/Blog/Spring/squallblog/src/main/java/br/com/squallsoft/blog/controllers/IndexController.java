package br.com.squallsoft.blog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public ResponseEntity<?> index() {
		return new ResponseEntity<Object>("Olá", HttpStatus.OK);
	}
}
