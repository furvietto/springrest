package com.azienda.esercizioSpringRestBoot.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.azienda.esercizioSpringRestBoot.exception.LocalitaEsistenteException;
import com.azienda.esercizioSpringRestBoot.exception.LocalitaNonEsistenteException;
import com.azienda.esercizioSpringRestBoot.exception.LocalitaNonValidaException;
import com.azienda.esercizioSpringRestBoot.model.Localita;
import com.azienda.esercizioSpringRestBoot.service.LocalitaService;

@RestController
@RequestMapping(path = "/rest/localita", produces = "application/json")
@CrossOrigin(origins = "*")
public class LocalitaRestController {

	@Autowired
	private LocalitaService localitaService;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Localita>> getAll(){
		try {
			List<Localita> data = localitaService.recuperaTutte();
			if ( data.size() > 0 ) {
				return new ResponseEntity<List<Localita>>(data,HttpStatus.OK);
			}
			return new ResponseEntity<List<Localita>>(HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<Localita> getById(@PathVariable("id") Integer id) {
		try	{
			Localita localita = localitaService.ricercaPerId(id);
			HttpStatus httpStatus = localita != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<Localita>(localita,httpStatus);
		}
		catch (LocalitaNonValidaException e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByNome/{nome}")
	public ResponseEntity<List<Localita>> getByNome(@PathVariable("nome") String nome) {
		try	{
			List<Localita> localita = localitaService.ricercaPerNome(nome);
			HttpStatus httpStatus = localita != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Localita>>(localita,httpStatus);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByTemperaturaInferiore/{temperatura}")
	public ResponseEntity<List<Localita>> getByTemperaturaInferiore(@PathVariable("temperatura") Float temperatura) {
		try	{
			List<Localita> listaLocalita = localitaService.ricercaPerTemperaturaInferiore(temperatura);
			HttpStatus httpStatus = listaLocalita != null && listaLocalita.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Localita>>(listaLocalita,httpStatus);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(new ArrayList<Localita>(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getByTemperaturaSuperiore/{temperatura}")
	public ResponseEntity<List<Localita>> getByTemperaturaSuperiore(@PathVariable("temperatura") Float temperatura) {
		try	{
			List<Localita> listaLocalita = localitaService.ricercaPerTemperaturaSuperiore(temperatura);
			HttpStatus httpStatus = listaLocalita != null && listaLocalita.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND;
			return new ResponseEntity<List<Localita>>(listaLocalita,httpStatus);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Localita>>(new ArrayList<Localita>(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/registraLocalita",consumes = "application/json" )
	public ResponseEntity<Localita> registra(@RequestBody Localita localita) {
		try {
			Localita localitaSalvata = localitaService.crea(localita);
			return new ResponseEntity<Localita>(localitaSalvata,HttpStatus.CREATED);
		}
		catch (LocalitaNonValidaException | LocalitaEsistenteException e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/registraLocalitaMassivo",consumes = "application/json" )
	public ResponseEntity<Void> registraMassivo(@RequestBody List<Localita> listaLocalita) {
		try {
			localitaService.creaMassivo(listaLocalita);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		catch (LocalitaNonValidaException | LocalitaEsistenteException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping( path = "/aggiornamentoCompleto", consumes = "application/json" )
	public ResponseEntity<Localita> aggiornamentoCompleto(@RequestBody Localita localita) {
		try {
			Localita localitaDb = localitaService.aggiornamentoCompleto(localita);
			return new ResponseEntity<Localita>(localitaDb,HttpStatus.OK);
		}
		catch (LocalitaNonValidaException | LocalitaNonEsistenteException e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping( path = "/aggiornamentoParziale", consumes = "application/json" )
	public ResponseEntity<Localita> aggiornamentoParziale(@RequestBody Localita localita) {
		try {
			Localita localitaDb = localitaService.aggiornamentoParziale(localita);
			return new ResponseEntity<Localita>(localitaDb,HttpStatus.OK);
		}
		catch (LocalitaNonValidaException | LocalitaNonEsistenteException e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Localita>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path="/cancellazione/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		try {
			localitaService.cancellaPerId(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (LocalitaNonEsistenteException e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(path="/cancellazionePerTemperaturaInferiore/{temperatura}")
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void cancellazionePerTemperaturaInferiore(@PathVariable("temperatura") Float temperatura) {
		localitaService.cancellaPerTemperaturaInferiore(temperatura);
	}

	@DeleteMapping(path="/cancellazionePerTemperaturaMaggiore/{temperatura}")
	@ResponseStatus( HttpStatus.NO_CONTENT )
	public void cancellazionePerTemperaturaMaggiore(@PathVariable("temperatura") Float temperatura) {
		localitaService.cancellaPerTemperaturaSuperiore(temperatura);
	}

}