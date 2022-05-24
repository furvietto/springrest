package com.azienda.esercizioSpringRestBoot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.azienda.esercizioSpringRestBoot.model.Localita;

public interface LocalitaRepository extends JpaRepository<Localita,Integer>{

	public Localita findByNome(String nome);
	
	public List<Localita> findByNomeContains(String nome);
	
	@Query("select loc from Localita loc where loc.temperatura <= :par")
	public List<Localita> findByTemperaturaInferiore(@Param("par") Float temperatura);
	
	@Query("select loc from Localita loc where loc.temperatura >= :par")
	public List<Localita> findByTemperaturaSuperiore(@Param("par") Float temperatura);
	
	@Modifying
	@Query("delete from Localita loc where loc.temperatura <= :par")
	public void cancellaByTemperaturaInferiore(@Param("par") Float temperatura);
	
	@Modifying
	@Query("delete from Localita loc where loc.temperatura >= :par")
	public void cancellaByTemperaturaSuperiore(@Param("par") Float temperatura);
	
	@Modifying
	@Query("delete from Localita loc where loc.temperatura >= :par")
	public void cancellaByTemperaturaSuperiorea(@Param("par") Float temperatura);
		
}