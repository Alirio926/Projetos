package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.PedidoEntity;
import com.squallsoft.api.dominios.User;
import com.squallsoft.api.dominios.licenca.enums.PedidoStatus;
 
@Repository
public interface RepositorioPedido  extends JpaRepository<PedidoEntity, Long>{
	 
	@Query("SELECT p FROM PedidoEntity p " 
		 + "WHERE MONTH(p.data) = :#{#mes} and YEAR(p.data) = :#{#ano}")
	List<PedidoEntity> findByData(int mes, int ano);
	
	@Query("SELECT p FROM PedidoEntity p "
			 + "WHERE "
			 + "p.licenca.id = :#{#licenca_id} and "
			 + "p.status = :#{#status}"
				)
	List<PedidoEntity> findPedidoFromClinteByStatusAndLicenca(Long licenca_id, PedidoStatus status);
	
	
	@Query("SELECT p FROM PedidoEntity p, Licenca lic "
		+  "WHERE p.licenca.id = lic.id and "
		+  "lic.clienteId = :#{#cId}")
	List<PedidoEntity> findPedidoByCliente(Long cId);
	//List<PedidoEntity> findByLicenca(Licenca licenca);
	
	@Query("SELECT p FROM PedidoEntity p, Licenca lic, Cliente c, User u "
			+  "WHERE p.licenca.id = lic.id and "
			+  "lic.clienteId = c.id and "
			+  "c.user = :#{#user} and "
			+  "MONTH(p.data) = :#{#mes} and YEAR(p.data) = :#{#ano}")
	List<PedidoEntity> findPedidoByClienteUserName(User user, int mes, int ano);
	
	
	
	
	
	
	@Query("SELECT p FROM PedidoEntity p "
			 + "WHERE "
			 + "p.licenca.id = :#{#licenca_id}")
	List<PedidoEntity> findByLicenca(Long licenca_id);
}
