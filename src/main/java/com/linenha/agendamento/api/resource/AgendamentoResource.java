package com.linenha.agendamento.api.resource;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linenha.agendamento.api.dto.AgendamentoDTO;
import com.linenha.agendamento.api.dto.AtualizaStatusDTO;
import com.linenha.agendamento.model.entity.Agendamento;
import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.enums.TipoDaSessao;
import com.linenha.agendamento.model.enums.TipoDeTema;
import com.linenha.agendamento.model.enums.TipoDoSexo;
import com.linenha.agendamento.service.AgendamentoService;
import com.linenha.agendamento.service.LoginService;
import com.linenha.agendamento.service.exceptions.RegraNegocioException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agendamento")
@RequiredArgsConstructor
public class AgendamentoResource {
	
	private final AgendamentoService service;
	private final LoginService loginService;
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "dia", required = false) Integer dia,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("login") Long idLogin
			) {
		Agendamento agendamentoFiltro = new Agendamento();
		agendamentoFiltro.setDia(dia);
		agendamentoFiltro.setMes(mes);
		agendamentoFiltro.setAno(ano);
		
		Optional<Login> login = loginService.obterPorId(idLogin);
		if(!login.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Login não encontado para o Id informado.");
		}else {
			agendamentoFiltro.setLogin(login.get());
		}
		List<Agendamento> agendamentos = service.buscar(agendamentoFiltro);
		return ResponseEntity.ok(agendamentos);
	}
	
	@PostMapping
	public ResponseEntity salvar (@RequestBody AgendamentoDTO dto) {
		
		try {
			Agendamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AgendamentoDTO dto) {
		return service.obterPoId(id).map( entity -> {
			try {
				Agendamento agendamento = converter(dto);
				agendamento.setId(entity.getId());
				service.atualizar(agendamento);
				return ResponseEntity.ok(agendamento);
			}catch(RegraNegocioException e){
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> 
			new ResponseEntity("Agendamento nao encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}	
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable Long id ,@RequestBody AtualizaStatusDTO dto) {
		return service.obterPoId(id).map(entity ->{
			StatusAgendamento statusSelecionado = StatusAgendamento.valueOf(dto.getStatus());
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido");
			}
			try {
				entity.setStatus(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
				new ResponseEntity("Lançamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.obterPoId(id).map(entidade -> {
			service.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> 
			new ResponseEntity("Agendamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
	}
	
	
	private Agendamento converter(AgendamentoDTO dto) {
		Agendamento agendamento = new Agendamento();
		agendamento.setId(dto.getId());
		agendamento.setNomeDaMae(dto.getNomeDaMae());
		agendamento.setTelefone(dto.getTelefone());
		agendamento.setNomeDaCrianca(dto.getNomeDaCrianca());
		agendamento.setIdadeDaCrianca(dto.getIdadeDaCrianca());
		
		if(dto.getSexoDaCrianca() != null) {
			agendamento.setSexoDaCrianca(TipoDoSexo.valueOf(dto.getSexoDaCrianca()));
		}
		
		if(dto.getTipoDaSessao() != null) {
			agendamento.setTipoDaSessao(TipoDaSessao.valueOf(dto.getTipoDaSessao()));
		}
		
		if(dto.getTema() != null) {
			agendamento.setTema(TipoDeTema.valueOf(dto.getTema()));
		}
		
		Login login = loginService
				.obterPorId(dto.getLogin())
				.orElseThrow( () -> new RegraNegocioException("Login não encontrado para o ID informado"));
		
		agendamento.setLogin(login);
		
		if(dto.getStatus() != null) {
			agendamento.setStatus(StatusAgendamento.valueOf(dto.getStatus()));
		}
		
		agendamento.setHora_agendada(dto.getHora_agendada());
		agendamento.setDia(dto.getDia());
		agendamento.setMes(dto.getMes());
		agendamento.setAno(dto.getAno());
		
		return agendamento;
	}
}
