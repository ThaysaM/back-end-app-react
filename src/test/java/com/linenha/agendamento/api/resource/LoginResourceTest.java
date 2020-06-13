package com.linenha.agendamento.api.resource;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linenha.agendamento.api.dto.LoginDTO;
import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.service.AgendamentoService;
import com.linenha.agendamento.service.LoginService;
import com.linenha.agendamento.service.exceptions.ErroDeAutenticacao;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest( controllers = LoginResource.class)
@AutoConfigureMockMvc
public class LoginResourceTest {

	static final String API = "/api/logins";
	static final MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	LoginService servise;
	
	@MockBean
	AgendamentoService lancamentoService;
	
	@Test
	public void deveAutenticarUmLogin() throws Exception {
		//cenario
		String email = "usuario@email.com";
		String senha = "123";
		
		LoginDTO dto = LoginDTO.builder().email(email).senha(senha).build();
		Login login = Login.builder().id(1l).email(email).senha(senha).build();
		
		Mockito.when(servise.autenticar(email, senha)).thenReturn(login);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execucao e verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API.concat("/autenticar"))
													.accept( JSON )
													.contentType( JSON )
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(login.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(login.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(login.getEmail()));
		
	}
	
	@Test
	public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
		//cenario
		String email = "usuario@email.com";
		String senha = "123";
		
		LoginDTO dto = LoginDTO.builder().email(email).senha(senha).build();
		
		Mockito.when(servise.autenticar(email, senha)).thenThrow(ErroDeAutenticacao.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execucao e verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API.concat("/autenticar"))
													.accept( JSON )
													.contentType( JSON )
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
			
		
	}
	
	
	@Test
	public void deveCriarUmNovoLogin() throws Exception {
		//cenario
		String email = "usuario@email.com";
		String senha = "123";
		
		LoginDTO dto = LoginDTO.builder().email("usuario@email.com").senha("123").build();
		Login login = Login.builder().id(1l).email(email).senha(senha).build();
		
		Mockito.when(servise.salvarUsuario(Mockito.any(Login.class))).thenReturn(login);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execucao e verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post( API )
													.accept( JSON )
													.contentType( JSON )
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("id").value(login.getId()))
			.andExpect(MockMvcResultMatchers.jsonPath("nome").value(login.getNome()))
			.andExpect(MockMvcResultMatchers.jsonPath("email").value(login.getEmail()));
		
	}
	
	@Test
	public void deveRetornarUmBadRequestAoTentarCriarUmLoginInvalido() throws Exception {
		//cenario
		String email = "usuario@email.com";
		String senha = "123";
		
		LoginDTO dto = LoginDTO.builder().email("usuario@email.com").senha("123").build();
		
		Mockito.when(servise.salvarUsuario(Mockito.any(Login.class))).thenThrow(RegraNegocioException.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execucao e verificacao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post( API )
													.accept( JSON )
													.contentType( JSON )
													.content(json);
		
		mvc
			.perform(request)
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	

	
}
