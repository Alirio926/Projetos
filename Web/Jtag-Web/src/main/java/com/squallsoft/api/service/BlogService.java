package com.squallsoft.api.service;

import java.util.List;

import com.squallsoft.api.dominios.Post;

public interface BlogService {
	public List<Post> findAll();
	public Post findById(Long id);
	public void save(Post post);
	
	public Post getPostByPermalink(String perma);
}
