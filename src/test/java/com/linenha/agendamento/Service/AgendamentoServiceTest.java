package com.linenha.agendamento.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.linenha.agendamento.model.entity.Agendamento;
import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.enums.TipoDaSessao;
import com.linenha.agendamento.model.enums.TipoDeTema;
import com.linenha.agendamento.model.enums.TipoDoSexo;
import com.linenha.agendamento.model.repository.AgendamentoRepository;
import com.linenha.agendamento.model.repository.AgendamentoRepositoryTest;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;
import com.linenha.agendamento.service.implement.AgendamentoServiceImplemente;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AgendamentoServiceTest {

	@SpyBean
	AgendamentoServiceImplemente service;
	
	@MockBean
	AgendamentoRepository repository;
	
	@Test
	public void deveSalvarUmAgendamento() {
		//cenario
		Agendamento agendamentoASalvar = AgendamentoRepositoryTest.criarAgendamento();
		Mockito.doNothing().when(service).validar(agendamentoASalvar);
		
		Agendamento agendamentoSalvo = AgendamentoRepositoryTest.criarAgendamento();
		agendamentoSalvo.setId(1l);
		agendamentoSalvo.setStatus(StatusAgendamento.PENDENTE);
		Mockito.when(repository.save(agendamentoASalvar)).thenReturn(agendamentoSalvo);
		
		//execucao
		Agendamento agendamento = service.salvar(agendamentoASalvar);
		
		//verificacao
		Assertions.assertThat(agendamento.getId()).isEqualTo(agendamentoSalvo.getId());
		Assertions.assertThat(agendamento.getStatus()).isEqualTo(StatusAgendamento.PENDENTE);
		
	}
	
	@Test
	public void naoDeveSalvarUmAgendamento() {
		//cenario
		Agendamento agendamentoASalvar = AgendamentoRepositoryTest.criarAgendamento();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(agendamentoASalvar);
		
		//execucao e verificacao
		Assertions.catchThrowableOfType( () -> service.salvar(agendamentoASalvar), RegraNegocioException.class);
		Mockito.verify(repository, Mockito.never()).save(agendamentoASalvar);
		
	}
	
	@Test
	public void deveAtualizarUmAgendamento() {
		//cenario
		Agendamento agendamentoSalvo = AgendamentoRepositoryTest.criarAgendamento();
		agendamentoSalvo.setId(1l);
		agendamentoSalvo.setStatus(StatusAgendamento.PENDENTE);
		
		Mockito.doNothing().when(service).validar(agendamentoSalvo);
		
		Mockito.when(repository.save(agendamentoSalvo)).thenReturn(agendamentoSalvo);
		
		//execucao
		service.atualizar(agendamentoSalvo);
		
		//verificacao
		Mockito.verify(repository, Mockito.times(1)).save(agendamentoSalvo);
		
	}
	
	@Test
	public void deveLancarErroAoTentarAtualizarUmAgendamentoQueAindaNaoFoiSalvo() {
		//cenario
		Agendamento agendamentoASalvar = AgendamentoRepositoryTest.criarAgendamento();
		
		//execucao e verificacao
		Assertions.catchThrowableOfType(() -> service.atualizar(agendamentoASalvar), NullPointerException.class);
		Mockito.verify(repository, Mockito.never()).save(agendamentoASalvar);
		
	}
	
	@Test
	public void deveDeletarUmAgendamento() {
		//cenario
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
		agendamento.setId(1l);
		
		//execucao
		service.deletar(agendamento);
		
		//verificacao
		Mockito.verify(repository).delete(agendamento);
		
	}
	
	@Test
	public void naoDeveDeletarUmAgendamento() {
		//cenario
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
			
		//execucao
		Assertions.catchThrowableOfType( () -> service.deletar(agendamento), NullPointerException.class);
				
		//verificacao
		Mockito.verify(repository, Mockito.never()).delete(agendamento);
	}
	
	@Test
	public void deveFiltrarAgendamento() {
		//cenario
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
		agendamento.setId(1l);
		
		List<Agendamento> lista= Arrays.asList(agendamento);
		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);
		
		//execucao
		List<Agendamento> resultado = service.buscar(agendamento);
		
		//verificacao
		Assertions.assertThat(resultado)
							.isNotEmpty()
							.hasSize(1)
							.contains(agendamento);
		
	}
	
	@Test
	public void deveAtualizarOsStatusDeUmLancamento() {
		//cenario
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
		agendamento.setId(1l);
		agendamento.setStatus(StatusAgendamento.PENDENTE);
		
		StatusAgendamento novoStatus = StatusAgendamento.EFETIVADO;
		Mockito.doReturn(agendamento).when(service).atualizar(agendamento);
		
		//execucao
		service.atualizarStatus(agendamento, novoStatus);
		
		//verificacao
		Assertions.assertThat(agendamento.getStatus()).isEqualTo(novoStatus);
		Mockito.verify(service).atualizar(agendamento);
	
	}
	
	@Test
	public void deveObterUmAgendamentoPorID() {
		//cenario
		Long id = 1l;
		
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
		agendamento.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.of(agendamento));
		
		//execucao
		Optional<Agendamento> resultado = service.obterPoId(id);
		
		//verificacao
		Assertions.assertThat(resultado.isPresent()).isTrue();
	}
	
	@Test
	public void deveRetornarVazioQuandoOAgendamentoNaoExistir() {
		//cenario
		Long id = 1l;
		
		Agendamento agendamento = AgendamentoRepositoryTest.criarAgendamento();
		agendamento.setId(id);
		
		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
		
		//execucao
		Optional<Agendamento> resultado = service.obterPoId(id);
		
		//verificacao
		Assertions.assertThat(resultado.isPresent()).isFalse();
	}
	
	@Test
	public void deveLancarErroAoValidarUmLancamento() {
		Agendamento agendamento = new Agendamento();
		
		Throwable erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Nome valido.");
		
		agendamento.setNomeDaMae("Thaysa");
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Telefone valido.");
		
		agendamento.setTelefone("35331409");
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Nome valido.");
		
		agendamento.setNomeDaCrianca("José");
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Idade valida.");
		
		agendamento.setIdadeDaCrianca(0);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Idade valida.");
		
		agendamento.setIdadeDaCrianca(12);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Idade valida.");
		
		agendamento.setIdadeDaCrianca(5);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Tipo De Sexo.");
		
		agendamento.setSexoDaCrianca(TipoDoSexo.MENINA);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Tipo De Sessao.");
		
		agendamento.setTipoDaSessao(TipoDaSessao.ACOMPANHAMENTOMENSAL);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Tema valido.");
		
		agendamento.setTema(TipoDeTema.ABELHINHA);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Login.");
		
		agendamento.setLogin(new Login());
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Login.");
		
		agendamento.getLogin().setId(1l);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Hora valida.");
		
		agendamento.setHora_agendada("12:00");
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mes valido.");
		
		agendamento.setMes(0);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mes valido.");
		
		agendamento.setMes(13);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mes valido.");
		
		agendamento.setMes(1);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano valido.");
		
		agendamento.setAno(123);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano valido.");
		
		agendamento.setAno(2020);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Dia valido.");
		
		agendamento.setDia(0);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Dia valido.");
		
		agendamento.setDia(36);
		
		erro = Assertions.catchThrowable( () -> service.validar(agendamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Dia valido.");
		
		agendamento.setDia(12);
		
		
	}
	
}
