package br.com.zup.estrelas.prefeitura.dto;

import lombok.Getter;

@Getter
public class ProjetoDTO {
	private String nome;
	private String descricao;

	private Double custo;

	private Long idSecretaria;
}
