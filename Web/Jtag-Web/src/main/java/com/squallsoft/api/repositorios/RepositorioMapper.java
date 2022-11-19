package com.squallsoft.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.Mapper;

@Repository
public interface RepositorioMapper extends JpaRepository<Mapper, Long>{

}
