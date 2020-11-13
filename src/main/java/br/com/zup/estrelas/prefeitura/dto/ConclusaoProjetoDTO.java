package br.com.zup.estrelas.prefeitura.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ConclusaoProjetoDTO {
	private LocalDate dataEntrega;
}
