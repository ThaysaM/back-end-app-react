package com.linenha.agendamento.service.implement;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Column;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linenha.agendamento.model.entity.Agendamento;
import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.repository.AgendamentoRepository;
import com.linenha.agendamento.service.AgendamentoService;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;

@Service
public class AgendamentoServiceImplemente implements AgendamentoService{

	private AgendamentoRepository repository;
	
	public AgendamentoServiceImplemente(AgendamentoRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Agendamento salvar(Agendamento agendamento) {
		validar(agendamento);
		agendamento.setStatus(StatusAgendamento.PENDENTE);
		return repository.save(agendamento);
	}

	@Override
	@Transactional
	public Agendamento atualizar(Agendamento agendamento) {
		Objects.requireNonNull(agendamento.getId());
		validar(agendamento);
		return repository.save(agendamento);
	}

	@Override
	@Transactional
	public void deletar(Agendamento agendamento) {
		Objects.requireNonNull(agendamento.getId());
		repository.delete(agendamento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Agendamento> buscar(Agendamento agendamentoFiltro) {
		Example example = Example.of(agendamentoFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Agendamento agendamento, StatusAgendamento status) {
		agendamento.setStatus(status);
		atualizar(agendamento);
	}
	
	@Override
	public void validar(Agendamento agendamento) {
		if(agendamento.getNomeDaMae() == null || agendamento.getNomeDaMae().trim().equals("")){
			throw new RegraNegocioException("Informe um Nome valido.");	
		}
		
		if(agendamento.getTelefone() == null || agendamento.getTelefone().trim().equals("")){
			throw new RegraNegocioException("Informe um Telefone valido.");	
		}
		
		if(agendamento.getNomeDaCrianca() == null || agendamento.getNomeDaCrianca().trim().equals("")){
			throw new RegraNegocioException("Informe um Nome valido.");	
		}
		
		if(agendamento.getIdadeDaCrianca() == null || agendamento.getIdadeDaCrianca() < 1 || agendamento.getIdadeDaCrianca() > 10){
			throw new RegraNegocioException("Informe uma Idade valida.");	
		}
		
		if(agendamento.getSexoDaCrianca() == null){
			throw new RegraNegocioException("Informe um Tipo De Sexo.");	
		}
		
		if(agendamento.getTipoDaSessao() == null){
			throw new RegraNegocioException("Informe um Tipo De Sessao.");	
		}
		
		if(agendamento.getTema() == null){
			throw new RegraNegocioException("Informe um Tema valido.");	
		}
		
		if(agendamento.getLogin() == null || agendamento.getLogin().getId() == 0l){
			throw new RegraNegocioException("Informe um Login.");	
		}
		
		if(agendamento.getHora_agendada() == null){
			throw new RegraNegocioException("Informe uma Hora valida.");	
		}
		
		if(agendamento.getMes() == null || agendamento.getMes() < 1 || agendamento.getMes() > 12){
			throw new RegraNegocioException("Informe um Mes valido.");	
		}
		
		if(agendamento.getAno() == null || agendamento.getAno().toString().length() != 4){
			throw new RegraNegocioException("Informe um Ano valido.");	
		}
		
		if(agendamento.getDia() == null || agendamento.getDia() < 1 || agendamento.getDia() > 31){
			throw new RegraNegocioException("Informe um Dia valido.");	
		}
	}

	@Override
	public Optional<Agendamento> obterPoId(Long id) {
		return repository.findById(id);
	}
	
}
