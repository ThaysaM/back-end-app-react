package com.linenha.agendamento.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.linenha.agendamento.model.entity.Login.LoginBuilder;
import com.linenha.agendamento.model.enums.StatusAgendamento;
import com.linenha.agendamento.model.enums.TipoDaSessao;
import com.linenha.agendamento.model.enums.TipoDeTema;
import com.linenha.agendamento.model.enums.TipoDoSexo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dados_para_agendar", schema = "agendamento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Agendamento {
	
	@Id
	@Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_da_mae")
	private String nomeDaMae;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "nome_da_crianca")
	private String nomeDaCrianca;
	
	@Column(name = "idade_da_crianca")
	private Integer idadeDaCrianca;
	
	@Column(name = "sexo_da_crianca")
	@Enumerated(value = EnumType.STRING)
	private TipoDoSexo sexoDaCrianca;
	
	@Column(name = "tipo_da_sessao")
	@Enumerated(value = EnumType.STRING)
	private TipoDaSessao tipoDaSessao;
	
	@Column(name = "tema")
	@Enumerated(value = EnumType.STRING)
	private TipoDeTema tema;
	
	@ManyToOne
	@JoinColumn(name = "id_login")
	private Login login;
	
	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusAgendamento status;
	
	@Column(name = "hora_agendada")
	private String hora_agendada;

	@Column(name = "dia")
	private Integer dia;
	
	@Column(name = "mes")
	private Integer mes;
	
	@Column(name = "ano")
	private Integer ano;

}
