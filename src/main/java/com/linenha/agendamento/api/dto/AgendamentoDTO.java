package com.linenha.agendamento.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

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
