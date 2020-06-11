package com.linenha.agendamento.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginDTO { // entidade login
	
	private String email;
	private String nome;
	private String senha;
	
}
