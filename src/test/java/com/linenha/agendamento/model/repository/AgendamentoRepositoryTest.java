package com.linenha.agendamento.model.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.linenha.agendamento.model.entity.Agendamento;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.enums.TipoDaSessao;
import com.linenha.agendamento.model.enums.TipoDeTema;
import com.linenha.agendamento.model.enums.TipoDoSexo;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class AgendamentoRepositoryTest {
	
	@Autowired
	AgendamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarUmAgendamento() {
		Agendamento agendamento = criarAgendamento();
			
		agendamento = repository.save(agendamento);
		assertThat(agendamento.getId()).isNotNull();
	}
	
	@Test
	public void deveDeletarUmLancamento() {
		Agendamento agendamento = criarEPersistirUmAgendamento();
		
		agendamento = entityManager.find(Agendamento.class, agendamento.getId());
		
		repository.delete(agendamento);
		
		Agendamento agendamentoInexistente = entityManager.find(Agendamento.class, agendamento.getId());
		assertThat(agendamentoInexistente).isNull();
		
	}
	
	@Test
	public void deveAtualizarUmAgendamento() {
		Agendamento agendamento = criarEPersistirUmAgendamento();
		
		agendamento.setAno(2018);
		agendamento.setDia(1);
		agendamento.setStatus(StatusAgendamento.EFETIVADO);
		
		repository.save(agendamento);
		
		Agendamento agendamentoAtualizado = entityManager.find(Agendamento.class, agendamento.getId());
		
		assertThat(agendamentoAtualizado.getAno()).isEqualTo(2018);
		assertThat(agendamentoAtualizado.getDia()).isEqualTo(1);
		assertThat(agendamentoAtualizado.getStatus()).isEqualTo(StatusAgendamento.EFETIVADO);
	
	}
	
	@Test
	public void deveBuscarUmAgendamentoPorId() {
		Agendamento agendamento = criarEPersistirUmAgendamento();
		
		Optional<Agendamento> agendamentoEncontrado = repository.findById(agendamento.getId());
		
		assertThat(agendamentoEncontrado.isPresent()).isTrue();
	
	}
	
	private Agendamento criarEPersistirUmAgendamento() {
		Agendamento agendamento = criarAgendamento();
		entityManager.persist(agendamento);
		return agendamento;
	}
	
	public static Agendamento criarAgendamento() {
		return Agendamento.builder()
						.nomeDaMae("Thaysa")
						.telefone("35330221")
						.nomeDaCrianca("José")
						.idadeDaCrianca(5)
						.sexoDaCrianca(TipoDoSexo.MENINO)
						.tipoDaSessao(TipoDaSessao.NEWBORN)
						.tema(TipoDeTema.ANJINHO)
						.hora_agendada("10:00")
						.dia(8)
						.mes(12)
						.ano(2020)
						.status(StatusAgendamento.PENDENTE).build();
	}
}
