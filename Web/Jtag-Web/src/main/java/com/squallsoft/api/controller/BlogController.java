package com.squallsoft.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.squallsoft.api.service.BlogService;


@Controller
@RequestMapping("/blog")
public class BlogController {

	@Autowired
	public BlogService blogService;
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public String getPosts(Model model) {
		model.addAttribute("posts", blogService.findAll());
		return "blog.posts.tiles";
	}
	
}
