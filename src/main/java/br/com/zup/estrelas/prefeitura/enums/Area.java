package br.com.zup.estrelas.prefeitura.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString @AllArgsConstructor
public enum Area {
	SAUDE("saude"), TRANSITO("transito"), ADMINISTRACAO("administracao"),
	AGRONEGOCIO("agronegocio"), SOCIAL("social"), EDUCACAO("educacao"),
	FAZENDA("fazenda"), GOVERNO("governo"), PLANEJAMENTO("planejamento"), OBRAS("obras");
	
	private String setor;
}
