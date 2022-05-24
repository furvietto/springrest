package com.azienda.esercizioSpringRestBoot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azienda.esercizioSpringRestBoot.exception.LocalitaEsistenteException;
import com.azienda.esercizioSpringRestBoot.exception.LocalitaNonEsistenteException;
import com.azienda.esercizioSpringRestBoot.exception.LocalitaNonValidaException;
import com.azienda.esercizioSpringRestBoot.model.Localita;
import com.azienda.esercizioSpringRestBoot.repository.LocalitaRepository;

@Service
@Transactional
public class LocalitaService {
	
	@Autowired
	private LocalitaRepository localitaRepository;
	
	public List<Localita> recuperaTutte(){
		return localitaRepository.findAll();
	}
	
	public Localita ricercaPerId(Integer id) throws LocalitaNonValidaException {
		if (id == null) {
			throw new LocalitaNonValidaException("Non e' stato fornito l'id");
		}
		return localitaRepository.findById(id).orElse(null);
	}
	
	public List<Localita> ricercaPerNome(String nome){
		return localitaRepository.findByNomeContains(nome);
	}
	
	public List<Localita> ricercaPerTemperaturaInferiore(Float temperatura){
		return localitaRepository.findByTemperaturaInferiore(temperatura);
	}
	
	public List<Localita> ricercaPerTemperaturaSuperiore(Float temperatura){
		return localitaRepository.findByTemperaturaSuperiore(temperatura);
	}
	
	public Localita crea(Localita localita) throws LocalitaNonValidaException, LocalitaEsistenteException {
		if ( localita.getNome() == null || localita.getNome().isEmpty() || localita.getTemperatura() == null ) {
			throw new LocalitaNonValidaException("Fornita una località con dati incompleti");
		}
		if ( localitaRepository.findByNome(localita.getNome()) != null ) {
			throw new LocalitaEsistenteException("La località con nome: " + localita.getNome() + " esiste già");
		}
		return localitaRepository.save(localita);
	}
	
	public void creaMassivo(List<Localita> listaLocalita) throws LocalitaNonValidaException, LocalitaEsistenteException {
		for( Localita localita : listaLocalita ) {
			if ( localita.getNome() == null || localita.getNome().isEmpty() || localita.getTemperatura() == null ) {
				throw new LocalitaNonValidaException("Fornita almeno una località con dati incompleti");
			}
			if ( localitaRepository.findByNome(localita.getNome()) != null ) {
				throw new LocalitaEsistenteException("La località con nome: " + localita.getNome() + " esiste già");
			}
		}
		localitaRepository.saveAll(listaLocalita);
	}
	
	public Localita aggiornamentoCompleto(Localita localita) throws LocalitaNonValidaException, LocalitaNonEsistenteException {
		if ( localita.getId() == null ) {
			throw new LocalitaNonValidaException("Fornita una località con dati incompleti");
		}
		Localita localitaDb = localitaRepository.findById(localita.getId()).orElse(null);
		if ( localitaDb == null ) {
			throw new LocalitaNonEsistenteException("La località con id: " + localita.getId() + " non esiste");
		}
		localitaDb.setNome(localita.getNome());
		localitaDb.setTemperatura(localita.getTemperatura());
		return localitaDb;
	}
	
	public Localita aggiornamentoParziale(Localita localita) throws LocalitaNonValidaException, LocalitaNonEsistenteException {
		if( localita.getId()==null ) {
			throw new LocalitaNonValidaException("Fornita una località con dati incompleti");
		}
		Localita localitaDb = localitaRepository.findById(localita.getId()).orElse(null);
		if( localitaDb == null ) {
			throw new LocalitaNonEsistenteException("La località con id: " + localita.getId() + " non esiste");
		}
		if( localita.getNome() != null && !localita.getNome().isEmpty() ) {
			localitaDb.setNome(localita.getNome());
		}
		if( localita.getTemperatura() != null ) {
			localitaDb.setTemperatura(localita.getTemperatura());
		}
		return localitaDb;
	}
	
	public void cancellaPerId(Integer id) throws LocalitaNonEsistenteException {
		Localita localitaDb = localitaRepository.findById(id).orElse(null);
		if( localitaDb == null ) {
			throw new LocalitaNonEsistenteException("La località con id: " + id + " non esiste");
		}
		localitaRepository.deleteById(id);
	}
	
	public void cancellaPerTemperaturaInferiore(Float temperatura) {
		localitaRepository.cancellaByTemperaturaInferiore(temperatura);
	}
	
	public void cancellaPerTemperaturaSuperiore(Float temperatura) {
		localitaRepository.cancellaByTemperaturaSuperiore(temperatura);
	}

}
