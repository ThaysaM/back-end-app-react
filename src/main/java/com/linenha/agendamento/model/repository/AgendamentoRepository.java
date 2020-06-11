package com.linenha.agendamento.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linenha.agendamento.model.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

}
