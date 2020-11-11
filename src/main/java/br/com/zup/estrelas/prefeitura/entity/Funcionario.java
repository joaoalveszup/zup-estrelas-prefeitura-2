package br.com.zup.estrelas.prefeitura.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
	@Id
	@Column(name = "id_funcionario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFuncionario;

	@NotNull
	private String nome;

	@NotNull
	@Column(unique = true)
	private String cpf;

	@NotNull
	private Double salario;

	@ManyToOne
	@JoinColumn(name = "id_secretaria", foreignKey = @ForeignKey(name = "FK_SECRETARIA_FUNCIONARIO"))
	private Secretaria secretaria;

	@NotNull
	private String funcao;

	@NotNull
	private boolean concursado;

	@NotNull
	@Column(name = "data_admissao")
	// @Temporal(TemporalType.DATE)
	private LocalDate dataAdmissao;
}
