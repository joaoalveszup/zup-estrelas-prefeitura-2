package br.com.zup.estrelas.prefeitura.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.zup.estrelas.prefeitura.enums.Area;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecretariaDTO {
	@Enumerated(EnumType.STRING)
	private Area area;
	
	private Double orcamentoProjeto;	
	private Double orcamentoFolha;
	
	private String telefone;
	private String endereco;
	private String site;
	private String email;
}
