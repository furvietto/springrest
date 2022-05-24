package com.azienda.esercizioSpringRestBoot.exception;

public class LocalitaEsistenteException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public LocalitaEsistenteException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
	public LocalitaEsistenteException(String message) {
		super(message);
	}

}