package com.linenha.agendamento.api.dto;

import com.linenha.agendamento.model.entity.Login;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.enums.TipoDaSessao;
import com.linenha.agendamento.model.enums.TipoDeTema;
import com.linenha.agendamento.model.enums.TipoDoSexo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgendamentoDTO {
	
	private Long id;
	private String nomeDaMae;
	private String telefone;
	private String nomeDaCrianca;
	private Integer idadeDaCrianca;
	private String sexoDaCrianca;
	private String tipoDaSessao;
	private String tema;
	private Long login;
	private String status;
	private String hora_agendada;
	private Integer dia;
	private Integer mes;
	private Integer ano;
	
}
