package br.com.squallsoftware.clinvet.dominios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reg_atendimentos")
public class Prontuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reg_id")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER /*cascade = CascadeType.MERGE/*orphanRemoval = true*/)
	@JoinColumn(name ="vet_id")
	@JsonIgnore
	private Veterinario veterinario;

	@OneToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.MERGE/*orphanRemoval = true*/)
	@JoinColumn(name ="amn_id")
	@JsonIgnore
	private Animal animal;

	@NotNull(message = "A data do atendimento é obrigatoria")
	@Column(name = "reg_data_atendimento", nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyy")
	private Date dataAtendimento;

	@Column(name = "vet_observacao", length = 100, nullable = false)
	@Size(min = 3, max = 100, message = "A observação geral do atendimento deve conter entre 3 e 100 caracteres")
	@NotEmpty(message = "A observação geral do atendimento é obrigatório.")
	private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Veterinario getVeterinario() {
		return veterinario;
	}

	public void setVeterinario(Veterinario veterinario) {
		this.veterinario = veterinario;
	}

	public Animal getAnimal() {
		return animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Date getDataAtendimento() {
		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
