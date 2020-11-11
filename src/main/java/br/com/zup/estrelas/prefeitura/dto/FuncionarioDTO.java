package br.com.zup.estrelas.prefeitura.dto;

import lombok.Getter;

@Getter
public class FuncionarioDTO {
	private String nome;
	private String cpf;
	
	private Double salario;
	
	private Long idSecretaria;
	
	private String funcao;
	
	private boolean concursado;
}
