package com.azienda.esercizioSpringRestBoot.exception;

public class LocalitaNonEsistenteException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public LocalitaNonEsistenteException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
	public LocalitaNonEsistenteException(String message) {
		super(message);
	}

}