package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.User;
import com.squallsoft.api.dominios.licenca.Cliente;

@Repository
public interface RepositorioCliente  extends JpaRepository<Cliente, Long> {

	public Cliente findByUser(User user);
}
