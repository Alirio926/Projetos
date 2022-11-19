package com.squallsoft.api.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.licenca.Licenca;
import com.squallsoft.api.dominios.licenca.enums.LicencaStatus;
import com.squallsoft.api.dominios.licenca.enums.Produto;

@Repository
public interface RepositorioLicenca extends JpaRepository<Licenca, Long>{

	public List<Licenca> findByProduto(Produto produto);
	
	public List<Licenca> findByClienteId(Long clienteId);
	
	
	public Licenca findByLicense(String license);
	
	@Query("SELECT lic FROM Licenca lic "
		 + "WHERE "
		 + "lic.status = :#{#status} and "
		 + "lic.machineCode = :#{#machineCode}")
	public Licenca findByMachineCode(String machineCode, LicencaStatus status);
	
	// precisa de ajuste
	@Query("SELECT l "
		 + "FROM Licenca l, Cliente c "
		 + "WHERE l.status = 'OPEN' and l.clienteId = :#{#cliente_id}")
	public Licenca findValidLicense(Long cliente_id);
	
}
