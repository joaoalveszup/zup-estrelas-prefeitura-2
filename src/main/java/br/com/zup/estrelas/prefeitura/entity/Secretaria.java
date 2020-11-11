package br.com.zup.estrelas.prefeitura.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.sun.istack.NotNull;

import br.com.zup.estrelas.prefeitura.enums.Area;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Secretaria {
	@Id
	@Column(name = "id_secretaria")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSecretaria;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Area area;

	@NotNull
	@Column(name = "orcamento_projeto")
	private Double orcamentoProjeto;

	@NotNull
	@Column(name = "orcamento_folha")
	private Double orcamentoFolha;

	@NotNull
	private String telefone;

	@NotNull
	private String endereco;

	@NotNull
	private String site;

	@NotNull
	private String email;

	@OneToMany(mappedBy = "secretaria", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Funcionario> funcionarios;

	@OneToMany(mappedBy = "secretaria", cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Projeto> projetos;
}
