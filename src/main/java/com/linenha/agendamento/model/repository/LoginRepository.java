package com.linenha.agendamento.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.linenha.agendamento.model.entity.Login;


public interface LoginRepository extends JpaRepository<Login, Long>{
		
	boolean existsByEmail(String email); // com isso ele faria no banco select * from login where email= email
	Optional<Login> findByEmail(String email);
}
