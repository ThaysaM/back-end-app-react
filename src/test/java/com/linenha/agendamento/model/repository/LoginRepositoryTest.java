package com.linenha.agendamento.model.repository;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.linenha.agendamento.model.entity.Login;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LoginRepositoryTest {
	
	@Autowired
	private LoginRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail(){
		//cenario
		Login login = criarLogin();
		entityManager.persist(login);
		
		//acao / execucao
		boolean result = repository.existsByEmail("thaysa@gmail.com");
		
		//verificacao
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverLoginCadastradoComOEmail() {
		//cenario
		
		//acao
		boolean result = repository.existsByEmail("thaysa@gmail.com");
	
		//verificacao
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void devePersistirUmLoginNaBaseDeDados() {
		//cenario
		Login login = criarLogin();
		
		//acao
		Login loginSalvo = repository.save(login);
		
		//verificacao
		Assertions.assertThat(loginSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUmLoginPorEmail() {
		//cenario
		Login login = criarLogin();
		entityManager.persist(login);
		
		//verificacao
		Optional<Login> result = repository.findByEmail("thaysa@gmail.com");
	
		Assertions.assertThat(result.isPresent() ).isTrue();
	}
	
	@Test
	public void deveRetornarVazioAoBuscarLoginPorEmailQuandoNaoExistirNaBase() {
		//verificacao
		Optional<Login> result = repository.findByEmail("thaysa@gmail.com");
	
		Assertions.assertThat(result.isPresent() ).isFalse();
	}

	public static  Login criarLogin() {
		return Login
				.builder()
				.nome("usuario")
				.email("thaysa@gmail.com")
				.senha("senha")
				.build();
	}
}
