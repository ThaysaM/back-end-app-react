package com.linenha.agendamento.service.implement;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.repository.LoginRepository;
import com.linenha.agendamento.service.LoginService;
import com.linenha.agendamento.service.exceptions.ErroDeAutenticacao;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;

@Service
public class LoginServiceImplement implements LoginService {
	
	private LoginRepository repository;
	
	public LoginServiceImplement(LoginRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Login autenticar(String email, String senha) {
		Optional<Login> login = repository.findByEmail(email);
		
		if(!login.isPresent()) {
			throw new ErroDeAutenticacao("Usuario nao encontrado para o email informado.");
		}
		
		if(!login.get().getSenha().equals(senha)) {
			throw new ErroDeAutenticacao("Senha invalida.");
		}
		return login.get();
	}

	@Override
	@Transactional
	public Login salvarUsuario(Login login) {
		validarEmail(login.getEmail());
		return repository.save(login);
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Ja existe um usuario cadastrado com este email.");
		}
	}
	
}
