package br.com.squallsoftware.clinvet.dominios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "anm_animais")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "anm_id")
	private Long id;

	@Column(name = "anm_nome", length = 12, nullable = false)
	@Size(min = 3, max = 12, message = "O nome do animal deve conter entre 3 e 12 caracteres")
	@NotEmpty(message = "O nome do animal é obrigatório.")
	private String nome;

	@NotNull(message = "A data de criação é obrigatoria")
	@Column(name = "anm_data_nascimento", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyy")
	private Date dataNascimento;

	@Column(name = "anm_nonedodono", length = 25, nullable = false)
	@Size(min = 3, max = 25, message = "O nome do dono deve conter entre 3 e 25 caracteres")
	@NotEmpty(message = "O nome do dono é obrigatório.")
	private String nomeDoDono;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeDoDono() {
		return nomeDoDono;
	}

	public void setNomeDoDono(String nomeDoDono) {
		this.nomeDoDono = nomeDoDono;
	}

}
