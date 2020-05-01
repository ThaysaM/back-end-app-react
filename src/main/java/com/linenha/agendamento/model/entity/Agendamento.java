package com.linenha.agendamento.model.entity;

import java.time.LocalDate;

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

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Entity
@Table(name = "dados_para_agendar", schema = "agendamento")
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
	private Sexo sexoDaCrianca;
	
	@Column(name = "tipo_da_sessao")
	@Enumerated(value = EnumType.STRING)
	private TipoDaSessao tipoDaSessao;
	
	@Column(name = "data_agendada")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate dataAgendada;
	
	@Column(name = "tema")
	@Enumerated(value = EnumType.STRING)
	private TipoDeTema tema;
	
	@ManyToOne
	@JoinColumn(name = "id_login")
	private Login idLogin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDaMae() {
		return nomeDaMae;
	}

	public void setNomeDaMae(String nomeDaMae) {
		this.nomeDaMae = nomeDaMae;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNomeDaCrianca() {
		return nomeDaCrianca;
	}

	public void setNomeDaCrianca(String nomeDaCrianca) {
		this.nomeDaCrianca = nomeDaCrianca;
	}

	public Integer getIdadeDaCrianca() {
		return idadeDaCrianca;
	}

	public void setIdadeDaCrianca(Integer idadeDaCrianca) {
		this.idadeDaCrianca = idadeDaCrianca;
	}

	public Sexo getSexoDaCrianca() {
		return sexoDaCrianca;
	}

	public void setSexoDaCrianca(Sexo sexoDaCrianca) {
		this.sexoDaCrianca = sexoDaCrianca;
	}

	public TipoDaSessao getTipoDaSessao() {
		return tipoDaSessao;
	}

	public void setTipoDaSessao(TipoDaSessao tipoDaSessao) {
		this.tipoDaSessao = tipoDaSessao;
	}

	public LocalDate getDataAgendada() {
		return dataAgendada;
	}

	public void setDataAgendada(LocalDate dataAgendada) {
		this.dataAgendada = dataAgendada;
	}

	public TipoDeTema getTema() {
		return tema;
	}

	public void setTema(TipoDeTema tema) {
		this.tema = tema;
	}

	public Login getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Login idLogin) {
		this.idLogin = idLogin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataAgendada == null) ? 0 : dataAgendada.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idLogin == null) ? 0 : idLogin.hashCode());
		result = prime * result + ((idadeDaCrianca == null) ? 0 : idadeDaCrianca.hashCode());
		result = prime * result + ((nomeDaCrianca == null) ? 0 : nomeDaCrianca.hashCode());
		result = prime * result + ((nomeDaMae == null) ? 0 : nomeDaMae.hashCode());
		result = prime * result + ((sexoDaCrianca == null) ? 0 : sexoDaCrianca.hashCode());
		result = prime * result + ((telefone == null) ? 0 : telefone.hashCode());
		result = prime * result + ((tema == null) ? 0 : tema.hashCode());
		result = prime * result + ((tipoDaSessao == null) ? 0 : tipoDaSessao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (dataAgendada == null) {
			if (other.dataAgendada != null)
				return false;
		} else if (!dataAgendada.equals(other.dataAgendada))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idLogin == null) {
			if (other.idLogin != null)
				return false;
		} else if (!idLogin.equals(other.idLogin))
			return false;
		if (idadeDaCrianca == null) {
			if (other.idadeDaCrianca != null)
				return false;
		} else if (!idadeDaCrianca.equals(other.idadeDaCrianca))
			return false;
		if (nomeDaCrianca == null) {
			if (other.nomeDaCrianca != null)
				return false;
		} else if (!nomeDaCrianca.equals(other.nomeDaCrianca))
			return false;
		if (nomeDaMae == null) {
			if (other.nomeDaMae != null)
				return false;
		} else if (!nomeDaMae.equals(other.nomeDaMae))
			return false;
		if (sexoDaCrianca != other.sexoDaCrianca)
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tema != other.tema)
			return false;
		if (tipoDaSessao != other.tipoDaSessao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Agendamento [id=" + id + ", nomeDaMae=" + nomeDaMae + ", telefone=" + telefone + ", nomeDaCrianca="
				+ nomeDaCrianca + ", idadeDaCrianca=" + idadeDaCrianca + ", sexoDaCrianca=" + sexoDaCrianca
				+ ", tipoDaSessao=" + tipoDaSessao + ", dataAgendada=" + dataAgendada + ", tema=" + tema + ", idLogin="
				+ idLogin + "]";
	}
	
	
}
