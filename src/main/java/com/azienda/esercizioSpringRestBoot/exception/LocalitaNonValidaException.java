package com.azienda.esercizioSpringRestBoot.exception;

public class LocalitaNonValidaException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public LocalitaNonValidaException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
	public LocalitaNonValidaException(String message) {
		super(message);
	}

}