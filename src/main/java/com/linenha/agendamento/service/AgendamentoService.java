package com.linenha.agendamento.service;

import java.util.List;

import com.linenha.agendamento.model.entity.Agendamento;
import com.linenha.agendamento.model.enums.StatusAgendamento;

public interface AgendamentoService {
	
	Agendamento salvar(Agendamento agendamento);
	
	Agendamento atualizar(Agendamento agendamento);
	
	void deletar(Agendamento agendamento);
	
	List<Agendamento> buscar(Agendamento agendamentoFiltro);
	
	void atualizarStatus(Agendamento agendamento, StatusAgendamento status);
	
	void validar(Agendamento agendamento);
}
