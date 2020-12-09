package br.com.squallsoft.blog.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.squallsoft.blog.dominios.Avatar;

public interface RepositorioAvatar extends JpaRepository<Avatar, Long>{

}
