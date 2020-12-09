package br.com.squallsoftware.clinvet.dominios;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "vet_veterinarios")
public class Veterinario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vet_id")
	private Long id;

	@Column(name = "vet_nome", length = 25, nullable = false)
	@Size(min = 3, max = 25, message = "O nome do veterinário deve conter entre 3 e 25 caracteres")
	@NotEmpty(message = "O nome do veterinário é obrigatório.")
	private String nome_vet;

	@Column(name = "vet_especialidade", length = 50, nullable = false)
	@Size(min = 3, max = 50, message = "A descrição da especialidade deve conter entre 3 e 50 caracteres")
	@NotEmpty(message = "A descrição da especialidade é obrigatório.")
	private String especialidade;

	@Column(name = "vet_num_crv", length = 25, nullable = false)
	@Size(min = 3, max = 12, message = "O número do CRV deve conter entre 3 e 25 caracteres")
	@NotEmpty(message = "O número do CRV é obrigatório.")
	private String crv;

	private Long idUser;

	@Transient
	@Size(min = 3, max = 10, message = "O nome de usuário deve conter entre 3 e 10 caracteres")
	@NotEmpty(message = "O nome de usuario é obrigatório")
	private String username;
	@Transient
	private String role;
	@Transient
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome_vet() {
		return nome_vet;
	}

	public void setNome_vet(String nome_vet) {
		this.nome_vet = nome_vet;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public String getCrv() {
		return crv;
	}

	public void setCrv(String crv) {
		this.crv = crv;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

}
