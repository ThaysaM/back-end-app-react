package com.linenha.agendamento.service.exceptions;

public class ErroDeAutenticacao extends RuntimeException{

	public ErroDeAutenticacao (String mensagem) {
		super(mensagem);
	}
}
