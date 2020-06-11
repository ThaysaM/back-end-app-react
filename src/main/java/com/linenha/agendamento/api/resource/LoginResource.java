package com.linenha.agendamento.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linenha.agendamento.api.dto.LoginDTO;
import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.repository.LoginRepository;
import com.linenha.agendamento.service.LoginService;
import com.linenha.agendamento.service.exceptions.ErroDeAutenticacao;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;
import com.linenha.agendamento.service.implement.LoginServiceImplement;

@RestController
@RequestMapping("/api/login")
public class LoginResource {
	
	private LoginService service;
	
	public LoginResource(LoginService service) {
		this.service = service;
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody LoginDTO dto) {
		try {
			Login loginAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(loginAutenticado);
		} catch (ErroDeAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody LoginDTO dto) {
		
		Login login = Login.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha()).build();
		
		try {
			Login loginSalvo = service.salvarUsuario(login);
			return new ResponseEntity(loginSalvo, HttpStatus.CREATED);
			
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	}
}
