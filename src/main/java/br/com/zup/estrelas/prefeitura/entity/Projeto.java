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

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projeto {
	@Id
	@Column(name = "id_projeto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProjeto;

	@Column(nullable = false)
	private String nome;

	private String descricao;

	@Column(nullable = false)
	private Double custo;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "id_secretaria", foreignKey = @ForeignKey(name = "FK_SECRETARIA_PROJETO"))
	private Secretaria secretaria;

	@Column(name = "data_inicio", nullable = false)
	private LocalDate dataInicio;

	@Column(name = "data_entrega")
	private LocalDate dataEntrega;

	@Column(columnDefinition = "BOOLEAN DEFAULT false")
	private boolean concluido;
}
