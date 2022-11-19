package com.squallsoft.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.squallsoft.api.dominios.FirmwareEntity;

@Repository
public interface RepositorioFirmware extends JpaRepository<FirmwareEntity, Long>{

}
