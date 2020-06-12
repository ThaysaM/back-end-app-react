package com.linenha.agendamento.service;

import java.util.Optional;

import com.linenha.agendamento.model.entity.Login;

public interface LoginService {

	Login autenticar(String email, String senha);
	
	Login salvarUsuario(Login login);
	
	void validarEmail(String email);
	
	Optional<Login> obterPorId(Long id);
}
