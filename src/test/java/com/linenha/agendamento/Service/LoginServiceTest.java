package com.linenha.agendamento.Service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.repository.LoginRepository;
import com.linenha.agendamento.service.LoginService;
import com.linenha.agendamento.service.exceptions.ErroDeAutenticacao;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;
import com.linenha.agendamento.service.implement.LoginServiceImplement;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LoginServiceTest {
	
	@SpyBean
	LoginServiceImplement service;
	
	@MockBean
	LoginRepository repository;
	
	
	@Test(expected = Test.None.class)
	public void deveSalvarUmUsuario() {
		//cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Login login = Login.builder()
				.id(1)
				.nome("nome")
				.email("email@email.com")
				.senha("senha").build();
		
		Mockito.when(repository.save(Mockito.any(Login.class))).thenReturn(login);
		
		//acao
		Login loginSalvo = service.salvarUsuario(new Login());
		
		//verificacao
		Assertions.assertThat(loginSalvo).isNotNull();
		Assertions.assertThat(loginSalvo.getId()).isEqualTo(1);
		Assertions.assertThat(loginSalvo.getNome()).isEqualTo("nome");
		Assertions.assertThat(loginSalvo.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(loginSalvo.getSenha()).isEqualTo("senha");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarLoginComEmailCadastrado() {
		//cenario
		String email = "email@email.com";
		Login login = Login.builder().email(email).build();
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		//acao
		service.salvarUsuario(login);
		
		//verificacao
		Mockito.verify(repository, Mockito.never()).save(login);
		
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmLoginComSucesso() {
		//cenario
		String email = "email@email.com";
		String senha = "senha";
		
		Login login = Login.builder().email(email).senha(senha).id(1).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(login));
		
		//acao
		Login result = service.autenticar(email, senha);
		
		//verificacao
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarLoginCadastradoComEmailInformado() {
		//cenario
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//acao
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "senha"));
		
		//verificacao
		Assertions.assertThat(exception).isInstanceOf(ErroDeAutenticacao.class).hasMessage("Usuario nao encontrado para o email informado.");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenario
		String senha = "senha";
		Login login = Login.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(login));
		
		//acao
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("email@email.com", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErroDeAutenticacao.class).hasMessage("Senha invalida.");
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		//cenario		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//acao
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		service.validarEmail("email@email.com");
	}
}
