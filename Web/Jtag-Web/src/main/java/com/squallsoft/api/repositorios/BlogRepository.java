package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squallsoft.api.dominios.Post;


public interface BlogRepository extends JpaRepository<Post, Long>{

	List<Post> findByPermalink(String permalink);
}
