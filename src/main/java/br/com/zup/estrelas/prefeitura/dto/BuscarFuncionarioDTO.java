package br.com.zup.estrelas.prefeitura.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BuscarFuncionarioDTO {
	private Long idFuncionario;
	private String nome;
	private String cpf;
	
	private Double salario;
	
	private Long idSecretaria;
	
	private String funcao;
	
	private boolean concursado;
}
