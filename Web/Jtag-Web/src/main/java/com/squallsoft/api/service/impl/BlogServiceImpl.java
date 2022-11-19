package com.squallsoft.api.service.impl;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squallsoft.api.dominios.Post;
import com.squallsoft.api.repositorios.BlogRepository;
import com.squallsoft.api.service.BlogService;
import com.squallsoft.api.util.MyReplaceString;

@Service
public class BlogServiceImpl implements BlogService{

	@Autowired
	public BlogRepository repository;
	
	@Override
	public List<Post> findAll() {
		return repository.findAll();
	}

	@Override
	public Post findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public void save(Post post) {
		String permalink = MyReplaceString.formatarPermalink(post.getTitulo());
		post.setPermalink(permalink);
		 post.setData(LocalDate.now());
		repository.save(post);		
	}

	@Override
	public Post getPostByPermalink(String perma) {
		List<Post> lstPost = repository.findByPermalink(perma);
		if(lstPost.size() == 0) return null;

		return lstPost.get(0);
	}

}
